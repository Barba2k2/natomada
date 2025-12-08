package com.barbatech.natomada.stations.infrastructure.external;

import com.barbatech.natomada.stations.domain.entities.Station;
import com.barbatech.natomada.stations.domain.valueobjects.Connector;
import com.barbatech.natomada.stations.domain.valueobjects.OpeningHours;
import com.barbatech.natomada.stations.infrastructure.external.google.AmenityMapper;
import com.barbatech.natomada.stations.infrastructure.external.google.dtos.GooglePlacesResponse;
import com.barbatech.natomada.stations.infrastructure.external.google.dtos.PlacesV1Response;
import com.barbatech.natomada.stations.infrastructure.external.opencm.dtos.OpenChargeMapResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mapper to convert external API responses to Station entities
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalStationMapper {

    private final ObjectMapper objectMapper;
    private final AmenityMapper amenityMapper;

    /**
     * Convert OpenChargeMap response to Station entity
     */
    public Station fromOpenChargeMap(OpenChargeMapResponse ocm) {
        Station station = new Station();

        // Initialize fields with database defaults (since we're not using builder)
        station.setRequiresMembership(false);
        station.setPayAtLocation(false);
        station.setRequiresAccessKey(false);
        station.setIsOperational(true);
        station.setIsOpen24h(false);
        station.setIsRecentlyVerified(false);
        station.setTotalConnectors(0);
        station.setOcmReviewCount(0);
        station.setGoogleReviewCount(0);
        station.setTotalReviews(0);

        // IDs
        station.setOcmId(ocm.getId() != null ? "ocm_" + ocm.getId() : null);
        station.setOcmUuid(ocm.getUuid());

        // Basic info
        if (ocm.getAddressInfo() != null) {
            OpenChargeMapResponse.AddressInfo addr = ocm.getAddressInfo();
            station.setName(addr.getTitle() != null ? addr.getTitle() : "Charging Station");
            station.setLatitude(addr.getLatitude());
            station.setLongitude(addr.getLongitude());
            station.setAddress(buildAddress(addr.getAddressLine1(), addr.getAddressLine2()));
            station.setCity(addr.getTown());
            station.setState(addr.getStateOrProvince());
            station.setPostalCode(addr.getPostcode());
            station.setPhone(addr.getContactTelephone1());

            if (addr.getCountry() != null) {
                station.setCountry(addr.getCountry().getTitle());
            }
        }

        // Operator info
        if (ocm.getOperatorInfo() != null) {
            OpenChargeMapResponse.OperatorInfo op = ocm.getOperatorInfo();
            station.setOperatorName(op.getTitle());
            station.setOperatorWebsite(op.getWebsiteUrl());
            station.setOperatorPhone(op.getPhonePrimaryContact());
            station.setOperatorEmail(op.getContactEmail());
        }

        // Usage type
        if (ocm.getUsageType() != null) {
            OpenChargeMapResponse.UsageType usage = ocm.getUsageType();
            station.setUsageType(usage.getTitle());
            // Use Boolean.TRUE.equals() to safely handle null values (defaults to false)
            station.setPayAtLocation(Boolean.TRUE.equals(usage.getIsPayAtLocation()));
            station.setRequiresMembership(Boolean.TRUE.equals(usage.getIsMembershipRequired()));
            station.setRequiresAccessKey(Boolean.TRUE.equals(usage.getIsAccessKeyRequired()));
        }

        // Cost
        station.setUsageCost(ocm.getUsageCost());

        // Status
        if (ocm.getStatusType() != null && ocm.getStatusType().getIsOperational() != null) {
            station.setIsOperational(ocm.getStatusType().getIsOperational());
        }

        // Connectors - Type-safe (Axel Engineering Doctrine: explicit types over strings)
        if (ocm.getConnections() != null && !ocm.getConnections().isEmpty()) {
            // Calculate total by summing quantities
            int totalConnectors = ocm.getConnections().stream()
                .mapToInt(conn -> conn.getQuantity() != null ? conn.getQuantity() : 1)
                .sum();
            station.setTotalConnectors(totalConnectors);
            // Direct assignment - no JSON serialization needed
            station.setConnectors(mapConnectors(ocm.getConnections()));
        } else {
            station.setTotalConnectors(ocm.getNumberOfPoints() != null ? ocm.getNumberOfPoints() : 0);
            station.setConnectors(new ArrayList<>());
        }

        // OCM Rating
        station.setOcmRating(null); // OCM doesn't provide rating in basic response
        station.setOcmReviewCount(0);

        // Metadata
        station.setLastSyncAt(LocalDateTime.now());
        station.setLastVerifiedAt(LocalDateTime.now());

        return station;
    }

    /**
     * Enrich station with Google Places data
     */
    public void enrichWithGooglePlaces(Station station, GooglePlacesResponse.Place place) {
        if (place == null) return;

        station.setGooglePlaceId(place.getPlaceId());

        // If name is missing, use Google's
        if (station.getName() == null || station.getName().isEmpty()) {
            station.setName(place.getName());
        }

        // Rating
        station.setGoogleRating(place.getRating());
        station.setGoogleReviewCount(place.getUserRatingsTotal() != null ? place.getUserRatingsTotal() : 0);

        // Calculate combined rating
        updateCombinedRating(station);

        // Opening hours - Type-safe (Axel Engineering Doctrine)
        if (place.getOpeningHours() != null && place.getOpeningHours().getWeekdayText() != null) {
            OpeningHours openingHours = OpeningHours.builder()
                .weekdayText(place.getOpeningHours().getWeekdayText())
                .build();
            station.setOpeningHours(openingHours);
        }

        // Photo references
        log.debug("Processing photos for station {}: photos={}", station.getName(), place.getPhotos());
        if (place.getPhotos() != null && !place.getPhotos().isEmpty()) {
            try {
                List<String> photoRefs = new ArrayList<>();
                // Get up to 5 photo references
                int maxPhotos = Math.min(place.getPhotos().size(), 5);
                for (int i = 0; i < maxPhotos; i++) {
                    String photoRef = place.getPhotos().get(i).getPhotoReference();
                    if (photoRef != null) {
                        photoRefs.add(photoRef);
                    }
                }
                if (photoRefs.isEmpty()) {
                    log.info("Google returned {} photos but all photo_reference fields were null for station {}", place.getPhotos().size(), station.getName());
                } else {
                    station.setPhotoReferences(objectMapper.writeValueAsString(photoRefs));
                    log.info("Stored {} photo references for station {}", photoRefs.size(), station.getName());
                }
            } catch (JsonProcessingException e) {
                log.error("Error converting photo references to JSON", e);
            }
        } else {
            log.info("No photos available from Google Places for station {} (place_id: {})", station.getName(), place.getPlaceId());
        }

        // Extract amenities from types - Type-safe (Axel Engineering Doctrine)
        if (place.getTypes() != null && !place.getTypes().isEmpty()) {
            List<String> amenities = amenityMapper.mapTypesToAmenities(place.getTypes());
            if (!amenities.isEmpty()) {
                station.setAmenities(amenities);  // Direct assignment
                log.info("Extracted {} amenities for station {}: {}", amenities.size(), station.getName(), amenities);
            }
        }

        log.debug("✅ Enriched station {} with Google Places data [UPDATED CODE]", station.getName());
    }

    /**
     * Enrich station with Google Places API v1 data (includes EV connector information)
     */
    public void enrichWithGooglePlacesV1(Station station, PlacesV1Response.Place place) {
        if (place == null) return;

        station.setGooglePlaceId(place.getId());

        // If name is missing, use Google's
        if (station.getName() == null || station.getName().isEmpty()) {
            if (place.getDisplayName() != null) {
                station.setName(place.getDisplayName().getText());
            }
        }

        // Rating
        if (place.getRating() != null) {
            station.setGoogleRating(place.getRating());
        }
        station.setGoogleReviewCount(place.getUserRatingCount() != null ? place.getUserRatingCount() : 0);

        // Calculate combined rating
        updateCombinedRating(station);

        // Opening hours - Type-safe (Axel Engineering Doctrine)
        if (place.getCurrentOpeningHours() != null && place.getCurrentOpeningHours().getWeekdayDescriptions() != null) {
            OpeningHours openingHours = OpeningHours.builder()
                .weekdayText(place.getCurrentOpeningHours().getWeekdayDescriptions())
                .build();
            station.setOpeningHours(openingHours);
        }

        // Photo references - Places API v1 uses different photo format
        if (place.getPhotos() != null && !place.getPhotos().isEmpty()) {
            try {
                List<String> photoRefs = new ArrayList<>();
                // Get up to 5 photos
                int maxPhotos = Math.min(place.getPhotos().size(), 5);
                for (int i = 0; i < maxPhotos; i++) {
                    PlacesV1Response.Photo photo = place.getPhotos().get(i);
                    if (photo.getName() != null) {
                        // Store the photo name (format: places/{place_id}/photos/{photo_reference})
                        photoRefs.add(photo.getName());
                    }
                }
                if (!photoRefs.isEmpty()) {
                    station.setPhotoReferences(objectMapper.writeValueAsString(photoRefs));
                    log.info("Stored {} photo references from Places v1 for station {}", photoRefs.size(), station.getName());
                }
            } catch (JsonProcessingException e) {
                log.error("Error converting photo references to JSON", e);
            }
        }

        // Extract amenities from types - Type-safe (Axel Engineering Doctrine)
        if (place.getTypes() != null && !place.getTypes().isEmpty()) {
            List<String> amenities = amenityMapper.mapTypesToAmenities(place.getTypes());
            if (!amenities.isEmpty()) {
                station.setAmenities(amenities);  // Direct assignment
                log.info("Extracted {} amenities for station {}: {}", amenities.size(), station.getName(), amenities);
            }
        }

        // EV Connector information from Places API v1
        if (place.getEvChargeOptions() != null) {
            mergeEvConnectorData(station, place.getEvChargeOptions());
        }

        log.info("✅ Enriched station {} with Google Places v1 data (including EV connectors)", station.getName());
    }

    /**
     * Merge EV connector data from Google Places v1 with existing OpenChargeMap connector data
     * (Axel Engineering Doctrine: Immutable value objects, rebuild instead of mutate)
     */
    private void mergeEvConnectorData(Station station, PlacesV1Response.EVChargeOptions evOptions) {
        try {
            log.info("Merging EV connector data from Google Places v1 for station: {}", station.getName());
            log.debug("Total connectors from Google: {}", evOptions.getConnectorCount());

            if (evOptions.getConnectorAggregation() == null || evOptions.getConnectorAggregation().isEmpty()) {
                log.debug("No connector aggregation data available from Google Places v1");
                return;
            }

            // Get existing connectors from OpenChargeMap (Type-safe - Axel Engineering Doctrine)
            List<Connector> existingConnectors = station.getConnectors() != null ?
                new ArrayList<>(station.getConnectors()) : new ArrayList<>();

            // Google connectors
            List<PlacesV1Response.ConnectorAggregation> googleConnectors = evOptions.getConnectorAggregation();
            Set<PlacesV1Response.ConnectorAggregation> usedGoogleConnectors = new HashSet<>();

            for (PlacesV1Response.ConnectorAggregation connector : googleConnectors) {
                String normalizedType = normalizeConnectorType(connector.getType());
                log.debug("Google connector: type={}, count={}, available={}, maxCharge={}kW",
                    normalizedType, connector.getCount(), connector.getAvailableCount(), connector.getMaxChargeRateKw());
            }

            // Rebuild connectors list with enriched data (immutability principle)
            List<Connector> mergedConnectors = new ArrayList<>();

            // Enrich existing OCM connectors with Google data, matching by type AND power
            for (Connector ocmConnector : existingConnectors) {
                String type = ocmConnector.getType();
                if (type != null) {
                    String normalizedType = normalizeConnectorTypeFromOCM(type);
                    Double ocmPower = ocmConnector.getPower() != null ?
                        ocmConnector.getPower().doubleValue() : null;

                    // Find matching Google connector by type and similar power
                    PlacesV1Response.ConnectorAggregation googleData = googleConnectors.stream()
                        .filter(gc -> !usedGoogleConnectors.contains(gc))
                        .filter(gc -> {
                            String gcType = normalizeConnectorType(gc.getType());
                            if (!normalizedType.equalsIgnoreCase(gcType)) {
                                return false;
                            }
                            // If both have power info, match by similar power (within 2 kW tolerance)
                            if (ocmPower != null && gc.getMaxChargeRateKw() != null) {
                                double powerDiff = Math.abs(ocmPower - gc.getMaxChargeRateKw().doubleValue());
                                return powerDiff <= 2.0;
                            }
                            // If no power info, don't match - let it be added separately
                            return false;
                        })
                        .findFirst()
                        .orElse(null);

                    if (googleData != null) {
                        // Rebuild connector with Google enrichment data (immutability)
                        Connector enriched = Connector.builder()
                            .id(ocmConnector.getId())
                            .type(ocmConnector.getType())
                            .power(googleData.getMaxChargeRateKw() != null ?
                                googleData.getMaxChargeRateKw() : ocmConnector.getPower())
                            .voltage(ocmConnector.getVoltage())
                            .amps(ocmConnector.getAmps())
                            .current(ocmConnector.getCurrent())
                            .quantity(googleData.getCount() != null ? googleData.getCount() : ocmConnector.getQuantity())
                            .status(ocmConnector.getStatus())
                            .isOperational(ocmConnector.getIsOperational())
                            .build();

                        mergedConnectors.add(enriched);
                        usedGoogleConnectors.add(googleData);
                        log.debug("Enriched OCM connector {} ({}kW) with Google Places v1 data ({}kW)",
                            type, ocmPower, googleData.getMaxChargeRateKw());
                    } else {
                        // No match, keep original
                        mergedConnectors.add(ocmConnector);
                    }
                } else {
                    // No type, keep original
                    mergedConnectors.add(ocmConnector);
                }
            }

            // Add any Google connectors that weren't used for enrichment
            for (PlacesV1Response.ConnectorAggregation googleConnector : googleConnectors) {
                if (!usedGoogleConnectors.contains(googleConnector)) {
                    String normalizedType = normalizeConnectorType(googleConnector.getType());

                    // Create new connector from Google data
                    Connector newConnector = Connector.builder()
                        .type(normalizedType)
                        .power(googleConnector.getMaxChargeRateKw())
                        .quantity(googleConnector.getCount())
                        .isOperational(true) // Assume operational if in Google data
                        .build();

                    mergedConnectors.add(newConnector);
                    log.info("Added new connector from Google Places v1: {} ({}kW)",
                        normalizedType, googleConnector.getMaxChargeRateKw());
                }
            }

            // Update station with merged connector data (direct assignment, no JSON)
            if (!mergedConnectors.isEmpty()) {
                station.setConnectors(mergedConnectors);

                // Update total connector count
                int totalCount = mergedConnectors.stream()
                    .mapToInt(c -> c.getQuantity() != null ? c.getQuantity() : 1)
                    .sum();
                station.setTotalConnectors(totalCount);

                log.info("Successfully merged {} connectors for station {} (total: {})",
                    mergedConnectors.size(), station.getName(), totalCount);
            }
        } catch (Exception e) {
            log.error("Error merging EV connector data for station {}: {}", station.getName(), e.getMessage(), e);
        }
    }

    /**
     * Normalize Google Places connector type to standardized format
     * Maps from EV_CONNECTOR_TYPE_J1772 -> J1772
     */
    private String normalizeConnectorType(String googleType) {
        if (googleType == null) return "Unknown";

        // Remove EV_CONNECTOR_TYPE_ prefix
        String normalized = googleType.replace("EV_CONNECTOR_TYPE_", "");

        // Map specific types
        switch (normalized) {
            case "TYPE_2":
                return "Type 2 (Mennekes)";
            case "CCS_COMBO_1":
                return "CCS (Type 1)";
            case "CCS_COMBO_2":
                return "CCS (Type 2)";
            case "J1772":
                return "Type 1 (J1772)";
            case "CHADEMO":
                return "CHAdeMO";
            case "TESLA":
                return "Tesla";
            case "NACS":
                return "NACS";
            case "GB_T":
                return "GB/T";
            case "WALL_OUTLET":
                return "Wall Outlet";
            default:
                return normalized;
        }
    }

    /**
     * Normalize OpenChargeMap connector type to match Google format
     */
    private String normalizeConnectorTypeFromOCM(String ocmType) {
        if (ocmType == null) return "Unknown";

        String lower = ocmType.toLowerCase();

        // Map OCM types to standardized format
        if (lower.contains("type 2") || lower.contains("mennekes")) {
            return "Type 2 (Mennekes)";
        } else if (lower.contains("ccs") && (lower.contains("type 1") || lower.contains("combo 1"))) {
            return "CCS (Type 1)";
        } else if (lower.contains("ccs") && (lower.contains("type 2") || lower.contains("combo 2"))) {
            return "CCS (Type 2)";
        } else if (lower.contains("type 1") || lower.contains("j1772")) {
            return "Type 1 (J1772)";
        } else if (lower.contains("chademo")) {
            return "CHAdeMO";
        } else if (lower.contains("tesla")) {
            return "Tesla";
        } else if (lower.contains("nacs")) {
            return "NACS";
        } else if (lower.contains("gb/t") || lower.contains("gbt")) {
            return "GB/T";
        }

        return ocmType; // Return original if no match
    }

    /**
     * Map OpenChargeMap connections to Connector value objects (Axel Engineering Doctrine: type-safe)
     */
    private List<Connector> mapConnectors(List<OpenChargeMapResponse.Connection> connections) {
        List<Connector> connectors = new ArrayList<>();

        for (OpenChargeMapResponse.Connection conn : connections) {
            Connector.ConnectorBuilder builder = Connector.builder();

            if (conn.getConnectionType() != null) {
                builder.type(conn.getConnectionType().getTitle());
            }

            if (conn.getCurrentType() != null) {
                builder.current(conn.getCurrentType().getTitle());
            }

            if (conn.getPowerKW() != null) {
                builder.power(conn.getPowerKW());
            }

            builder.quantity(conn.getQuantity() != null ? conn.getQuantity() : 1);

            if (conn.getStatusType() != null) {
                builder.status(conn.getStatusType().getTitle());
                builder.isOperational(conn.getStatusType().getIsOperational());
            }

            connectors.add(builder.build());
        }

        return connectors;
    }

    /**
     * Build full address from components
     */
    private String buildAddress(String line1, String line2) {
        if (line1 == null) return line2;
        if (line2 == null) return line1;
        return line1 + ", " + line2;
    }

    /**
     * Create a new Station entity from Google Places API v1 response
     * Used as fallback when OpenChargeMap is unavailable
     */
    public Station fromGooglePlacesV1(PlacesV1Response.Place place) {
        Station station = new Station();

        // Initialize fields with database defaults
        station.setRequiresMembership(false);
        station.setPayAtLocation(false);
        station.setRequiresAccessKey(false);
        station.setIsOperational(true);
        station.setIsOpen24h(false);
        station.setIsRecentlyVerified(false);
        station.setTotalConnectors(0);
        station.setOcmReviewCount(0);
        station.setGoogleReviewCount(0);
        station.setTotalReviews(0);

        // Google Place ID (use as primary identifier when no OCM data)
        station.setGooglePlaceId(place.getId());

        // Generate OCM-style ID from Google Place ID for consistency
        // Format: "google_{first_8_chars}" to avoid conflicts
        if (place.getId() != null && place.getId().length() >= 8) {
            station.setOcmId("google_" + place.getId().substring(0, 8));
        } else {
            station.setOcmId("google_" + place.getId());
        }

        // Name
        if (place.getDisplayName() != null) {
            station.setName(place.getDisplayName().getText());
        } else {
            station.setName("Charging Station");
        }

        // Location
        if (place.getLocation() != null) {
            station.setLatitude(place.getLocation().getLatitude());
            station.setLongitude(place.getLocation().getLongitude());
        }

        // Address
        if (place.getFormattedAddress() != null) {
            String[] addressParts = place.getFormattedAddress().split(", ");
            if (addressParts.length > 0) {
                station.setAddress(addressParts[0]);
            }
            if (addressParts.length > 1) {
                station.setCity(addressParts[1]);
            }
            if (addressParts.length > 2) {
                // Try to extract state and postal code
                String lastPart = addressParts[addressParts.length - 1];
                if (addressParts.length > 3) {
                    station.setState(addressParts[2].replaceAll("[0-9-]", "").trim());
                }
                station.setCountry(lastPart);
            }
        }

        // Contact info (if available in structured format)
        if (place.getInternationalPhoneNumber() != null) {
            station.setPhone(place.getInternationalPhoneNumber());
        }

        // Rating
        if (place.getRating() != null) {
            station.setGoogleRating(place.getRating());
            station.setCombinedRating(place.getRating());
        }
        station.setGoogleReviewCount(place.getUserRatingCount() != null ? place.getUserRatingCount() : 0);
        station.setTotalReviews(station.getGoogleReviewCount());

        // Opening hours
        if (place.getCurrentOpeningHours() != null && place.getCurrentOpeningHours().getWeekdayDescriptions() != null) {
            OpeningHours openingHours = OpeningHours.builder()
                .weekdayText(place.getCurrentOpeningHours().getWeekdayDescriptions())
                .build();
            station.setOpeningHours(openingHours);
        }

        // Photos
        if (place.getPhotos() != null && !place.getPhotos().isEmpty()) {
            try {
                List<String> photoRefs = new ArrayList<>();
                int maxPhotos = Math.min(place.getPhotos().size(), 5);
                for (int i = 0; i < maxPhotos; i++) {
                    PlacesV1Response.Photo photo = place.getPhotos().get(i);
                    if (photo.getName() != null) {
                        photoRefs.add(photo.getName());
                    }
                }
                if (!photoRefs.isEmpty()) {
                    station.setPhotoReferences(objectMapper.writeValueAsString(photoRefs));
                }
            } catch (JsonProcessingException e) {
                log.error("Error converting photo references to JSON", e);
            }
        }

        // Amenities - Extract from specific amenity fields
        List<String> amenities = extractAmenitiesFromPlace(place);
        if (!amenities.isEmpty()) {
            station.setAmenities(amenities);
        }

        // EV Connector information
        if (place.getEvChargeOptions() != null) {
            station.setConnectors(mapGoogleEvConnectors(place.getEvChargeOptions()));

            // Calculate total connectors
            int totalCount = station.getConnectors().stream()
                .mapToInt(c -> c.getQuantity() != null ? c.getQuantity() : 1)
                .sum();
            station.setTotalConnectors(totalCount);
        } else {
            station.setConnectors(new ArrayList<>());
            station.setTotalConnectors(0);
        }

        // Metadata
        station.setLastSyncAt(LocalDateTime.now());
        station.setLastVerifiedAt(LocalDateTime.now());

        log.info("Created new station from Google Places v1: {} ({} connectors)",
            station.getName(), station.getTotalConnectors());

        return station;
    }

    /**
     * Map Google Places EV connector data to Connector value objects
     */
    private List<Connector> mapGoogleEvConnectors(PlacesV1Response.EVChargeOptions evOptions) {
        List<Connector> connectors = new ArrayList<>();

        if (evOptions.getConnectorAggregation() == null || evOptions.getConnectorAggregation().isEmpty()) {
            return connectors;
        }

        for (PlacesV1Response.ConnectorAggregation connector : evOptions.getConnectorAggregation()) {
            String normalizedType = normalizeConnectorType(connector.getType());

            Connector newConnector = Connector.builder()
                .type(normalizedType)
                .power(connector.getMaxChargeRateKw())
                .quantity(connector.getCount())
                .isOperational(true)
                .build();

            connectors.add(newConnector);
        }

        return connectors;
    }

    /**
     * Extract amenities from Google Places v1 specific amenity fields
     */
    private List<String> extractAmenitiesFromPlace(PlacesV1Response.Place place) {
        List<String> amenities = new ArrayList<>();

        // Restroom
        if (Boolean.TRUE.equals(place.getRestroom())) {
            amenities.add("restroom");
        }

        // Parking options
        if (place.getParkingOptions() != null) {
            PlacesV1Response.ParkingOptions parking = place.getParkingOptions();
            if (Boolean.TRUE.equals(parking.getFreeParking()) ||
                Boolean.TRUE.equals(parking.getFreeParkingLot()) ||
                Boolean.TRUE.equals(parking.getFreeStreetParking()) ||
                Boolean.TRUE.equals(parking.getFreeGarageParking())) {
                amenities.add("free_parking");
            } else if (Boolean.TRUE.equals(parking.getPaidParking()) ||
                       Boolean.TRUE.equals(parking.getPaidParkingLot()) ||
                       Boolean.TRUE.equals(parking.getPaidStreetParking()) ||
                       Boolean.TRUE.equals(parking.getPaidGarageParking())) {
                amenities.add("paid_parking");
            }
        }

        // Payment options
        if (place.getPaymentOptions() != null) {
            PlacesV1Response.PaymentOptions payment = place.getPaymentOptions();
            if (Boolean.TRUE.equals(payment.getAcceptsCreditCards())) {
                amenities.add("credit_cards");
            }
            if (Boolean.TRUE.equals(payment.getAcceptsDebitCards())) {
                amenities.add("debit_cards");
            }
            if (Boolean.TRUE.equals(payment.getAcceptsNfc())) {
                amenities.add("contactless_payment");
            }
            if (Boolean.TRUE.equals(payment.getAcceptsCashOnly())) {
                amenities.add("cash_only");
            }
        }

        // Accessibility
        if (place.getAccessibilityOptions() != null) {
            PlacesV1Response.AccessibilityOptions accessibility = place.getAccessibilityOptions();
            if (Boolean.TRUE.equals(accessibility.getWheelchairAccessibleParking()) ||
                Boolean.TRUE.equals(accessibility.getWheelchairAccessibleEntrance()) ||
                Boolean.TRUE.equals(accessibility.getWheelchairAccessibleRestroom())) {
                amenities.add("wheelchair_accessible");
            }
        }

        return amenities;
    }

    /**
     * Calculate combined rating from OCM and Google ratings
     */
    private void updateCombinedRating(Station station) {
        BigDecimal ocmRating = station.getOcmRating();
        BigDecimal googleRating = station.getGoogleRating();
        int ocmCount = station.getOcmReviewCount() != null ? station.getOcmReviewCount() : 0;
        int googleCount = station.getGoogleReviewCount() != null ? station.getGoogleReviewCount() : 0;

        if (ocmRating != null && googleRating != null) {
            // Weighted average based on review count
            int totalCount = ocmCount + googleCount;
            if (totalCount > 0) {
                BigDecimal weighted = ocmRating
                    .multiply(BigDecimal.valueOf(ocmCount))
                    .add(googleRating.multiply(BigDecimal.valueOf(googleCount)))
                    .divide(BigDecimal.valueOf(totalCount), 2, RoundingMode.HALF_UP);
                station.setCombinedRating(weighted);
            }
        } else if (googleRating != null) {
            station.setCombinedRating(googleRating);
        } else if (ocmRating != null) {
            station.setCombinedRating(ocmRating);
        }

        station.setTotalReviews(ocmCount + googleCount);
    }
}
