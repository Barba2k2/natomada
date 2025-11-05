package com.barbatech.natomada.infrastructure.seeders;

import com.barbatech.natomada.cars.domain.entities.Car;
import com.barbatech.natomada.cars.infrastructure.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Service to seed Car database from CSV file
 * Reads electric_vehicles_spec_2025.csv.csv and populates the cars table
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CarSeederService {

    private final CarRepository carRepository;

    @Value("${app.seeder.csv-path:../na-tomada/electric_vehicles_spec_2025.csv.csv}")
    private String csvPath;

    @Value("${app.seeder.images-path:../na-tomada/cars}")
    private String imagesPath;

    @Value("${app.seeder.skip-if-exists:true}")
    private boolean skipIfExists;

    @Value("${app.seeder.s3-bucket-name:na-tomada-s3-bucket}")
    private String s3BucketName;

    @Value("${app.seeder.s3-region:us-east-2}")
    private String s3Region;

    @Value("${app.seeder.s3-cars-folder:cars}")
    private String s3CarsFolder;

    /**
     * Execute the seeder
     * This method is idempotent - it won't duplicate cars if they already exist
     */
    @Transactional
    public void seed() {
        log.info("🌱 Starting Car Seeder...");

        // Check if we should skip seeding
        if (skipIfExists && carRepository.count() > 0) {
            log.info("✅ Cars already exist in database ({} cars). Skipping seed.", carRepository.count());
            return;
        }

        try {
            // Load image mappings
            Map<String, String> imageMap = loadImageMappings();
            log.info("📁 Loaded {} car images from directory", imageMap.size());

            // Read and process CSV
            List<Car> cars = readCarsFromCSV(imageMap);
            log.info("📋 Parsed {} cars from CSV", cars.size());

            // Save to database
            List<Car> savedCars = carRepository.saveAll(cars);
            log.info("✅ Successfully seeded {} cars to database", savedCars.size());

        } catch (Exception e) {
            log.error("❌ Error seeding cars: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to seed cars", e);
        }
    }

    /**
     * Load image mappings from the cars directory
     * Maps brand_model to image file path
     */
    private Map<String, String> loadImageMappings() {
        Map<String, String> imageMap = new HashMap<>();
        Path imagesDir = Paths.get(imagesPath);

        if (!Files.exists(imagesDir)) {
            log.warn("⚠️ Images directory not found: {}", imagesPath);
            return imageMap;
        }

        try (Stream<Path> paths = Files.list(imagesDir)) {
            paths.filter(Files::isRegularFile)
                .filter(path -> {
                    String name = path.getFileName().toString().toLowerCase();
                    return name.endsWith(".png") || name.endsWith(".jpg") ||
                           name.endsWith(".jpeg") || name.endsWith(".webp");
                })
                .forEach(path -> {
                    String fileName = path.getFileName().toString();
                    String key = extractKeyFromFileName(fileName);
                    imageMap.put(key, path.toAbsolutePath().toString());
                });
        } catch (Exception e) {
            log.error("Error loading image mappings: {}", e.getMessage(), e);
        }

        return imageMap;
    }

    /**
     * Extract search key from image file name
     * Examples:
     *   "Tesla_Model_3I.webp" -> "tesla model 3"
     *   "BYD Dolphin GS 2026.png" -> "byd dolphin"
     */
    private String extractKeyFromFileName(String fileName) {
        // Remove extension
        String nameWithoutExt = fileName.replaceAll("\\.(png|jpg|jpeg|webp)$", "");

        // Remove trailing codes like "I", "II", "III", generation codes
        nameWithoutExt = nameWithoutExt.replaceAll("[I]+$", "")
                                       .replaceAll("\\s+\\d{4}$", "")  // Remove year
                                       .replaceAll("\\s+[A-Z]{2,}$", ""); // Remove codes

        // Replace underscores with spaces and normalize
        return nameWithoutExt.replace("_", " ")
                            .trim()
                            .toLowerCase();
    }

    /**
     * Find matching image for a car
     */
    private String findImage(String brand, String model, Map<String, String> imageMap) {
        String searchKey = (brand + " " + model).toLowerCase().trim();

        // Try exact match first
        if (imageMap.containsKey(searchKey)) {
            return imageMap.get(searchKey);
        }

        // Try partial match (brand + first word of model)
        String[] modelParts = model.toLowerCase().split("\\s+");
        if (modelParts.length > 0) {
            String partialKey = (brand + " " + modelParts[0]).toLowerCase().trim();
            if (imageMap.containsKey(partialKey)) {
                return imageMap.get(partialKey);
            }
        }

        // Try just brand match
        String brandKey = brand.toLowerCase().trim();
        Optional<String> brandMatch = imageMap.keySet().stream()
            .filter(key -> key.startsWith(brandKey))
            .findFirst();

        return brandMatch.map(imageMap::get).orElse(null);
    }

    /**
     * Read cars from CSV file
     */
    private List<Car> readCarsFromCSV(Map<String, String> imageMap) throws Exception {
        List<Car> cars = new ArrayList<>();
        Path csvFile = Paths.get(csvPath);

        if (!Files.exists(csvFile)) {
            throw new RuntimeException("CSV file not found: " + csvPath);
        }

        try (BufferedReader reader = Files.newBufferedReader(csvFile)) {
            String line;
            boolean isHeader = true;

            while ((line = reader.readLine()) != null) {
                // Skip header
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                try {
                    Car car = parseCarFromCSVLine(line, imageMap);
                    if (car != null) {
                        cars.add(car);
                    }
                } catch (Exception e) {
                    log.warn("⚠️ Error parsing line: {} - {}", line, e.getMessage());
                }
            }
        }

        return cars;
    }

    /**
     * Parse a single car from CSV line
     * CSV columns: brand,model,top_speed_kmh,battery_capacity_kWh,battery_type,number_of_cells,
     *              torque_nm,efficiency_wh_per_km,range_km,acceleration_0_100_s,
     *              fast_charging_power_kw_dc,fast_charge_port,towing_capacity_kg,cargo_volume_l,
     *              seats,drivetrain,segment,length_mm,width_mm,height_mm,car_body_type,source_url
     */
    private Car parseCarFromCSVLine(String line, Map<String, String> imageMap) {
        String[] fields = splitCSVLine(line);

        if (fields.length < 22) {
            log.warn("Invalid CSV line (not enough fields): {}", line);
            return null;
        }

        try {
            String brand = fields[0].trim();
            String model = fields[1].trim();
            Integer topSpeed = parseIntOrNull(fields[2]);
            BigDecimal batteryCapacity = parseBigDecimalOrNull(fields[3]);
            Integer fastChargingPower = parseIntOrNull(fields[10]);
            String connector = fields[11].trim();
            String bodyType = fields[20].trim();

            // Validate required fields
            if (brand.isEmpty() || model.isEmpty() || batteryCapacity == null ||
                topSpeed == null || fastChargingPower == null || connector.isEmpty() || bodyType.isEmpty()) {
                log.warn("Missing required fields for car: {} {}", brand, model);
                return null;
            }

            // Find image URL from S3
            String imageUrl = findS3ImageUrl(brand, model, imageMap);
            if (imageUrl == null) {
                log.warn("⚠️ No image found for {} {}, using placeholder", brand, model);
                imageUrl = "https://via.placeholder.com/400x300?text=" + brand.replace(" ", "+") + "+" + model.replace(" ", "+");
            }

            return Car.builder()
                .brand(brand)
                .model(model)
                .batteryCapacity(batteryCapacity)
                .maxSpeed(topSpeed)
                .fastChargingPower(fastChargingPower)
                .connector(connector)
                .bodyType(bodyType)
                .imageUrl(imageUrl)
                .build();

        } catch (Exception e) {
            log.error("Error creating car from line: {} - {}", line, e.getMessage());
            return null;
        }
    }

    /**
     * Split CSV line handling quoted fields
     */
    private String[] splitCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(field.toString());
                field = new StringBuilder();
            } else {
                field.append(c);
            }
        }
        fields.add(field.toString());

        return fields.toArray(new String[0]);
    }

    /**
     * Parse Integer from string, return null if invalid
     */
    private Integer parseIntOrNull(String value) {
        try {
            return value != null && !value.trim().isEmpty() ?
                   Integer.parseInt(value.trim()) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Parse BigDecimal from string, return null if invalid
     */
    private BigDecimal parseBigDecimalOrNull(String value) {
        try {
            return value != null && !value.trim().isEmpty() ?
                   new BigDecimal(value.trim()) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Find S3 image URL for a car
     * Images are already uploaded to S3, just need to generate the URL
     */
    private String findS3ImageUrl(String brand, String model, Map<String, String> imageMap) {
        String imagePath = findImage(brand, model, imageMap);

        if (imagePath == null) {
            return null;
        }

        try {
            Path path = Paths.get(imagePath);
            String fileName = path.getFileName().toString();

            // Generate S3 URL
            // Format: https://bucket-name.s3.region.amazonaws.com/folder/filename
            String s3Url = String.format("https://%s.s3.%s.amazonaws.com/%s/%s",
                s3BucketName,
                s3Region,
                s3CarsFolder,
                fileName
            );

            log.debug("✅ Found S3 image for {} {}: {}", brand, model, s3Url);
            return s3Url;

        } catch (Exception e) {
            log.error("Error generating S3 URL for {} {}: {}", brand, model, e.getMessage());
            return null;
        }
    }
}
