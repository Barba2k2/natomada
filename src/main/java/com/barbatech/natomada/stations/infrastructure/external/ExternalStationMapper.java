package com.barbatech.natomada.stations.infrastructure.external;

import com.barbatech.natomada.stations.domain.entities.Station;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
            station.setPayAtLocation(usage.getIsPayAtLocation());
            station.setRequiresMembership(usage.getIsMembershipRequired());
            station.setRequiresAccessKey(usage.getIsAccessKeyRequired());
        }

        // Cost
        station.setUsageCost(ocm.getUsageCost());

        // Status
        if (ocm.getStatusType() != null) {
            station.setIsOperational(ocm.getStatusType().getIsOperational());
        }

        // Connectors
        if (ocm.getConnections() != null && !ocm.getConnections().isEmpty()) {
            // Calculate total by summing quantities
            int totalConnectors = ocm.getConnections().stream()
                .mapToInt(conn -> conn.getQuantity() != null ? conn.getQuantity() : 1)
                .sum();
            station.setTotalConnectors(totalConnectors);
            try {
                String connectorsJson = objectMapper.writeValueAsString(mapConnectors(ocm.getConnections()));
                station.setConnectors(connectorsJson);
            } catch (JsonProcessingException e) {
                log.error("Error converting connectors to JSON", e);
                station.setConnectors("[]");
            }
        } else {
            station.setTotalConnectors(ocm.getNumberOfPoints() != null ? ocm.getNumberOfPoints() : 0);
            station.setConnectors("[]");
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

        // Opening hours
        if (place.getOpeningHours() != null && place.getOpeningHours().getWeekdayText() != null) {
            try {
                station.setOpeningHours(objectMapper.writeValueAsString(place.getOpeningHours().getWeekdayText()));
            } catch (JsonProcessingException e) {
                log.error("Error converting opening hours to JSON", e);
            }
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

        // Extract amenities from types
        if (place.getTypes() != null && !place.getTypes().isEmpty()) {
            try {
                List<String> amenities = amenityMapper.mapTypesToAmenities(place.getTypes());
                if (!amenities.isEmpty()) {
                    station.setAmenities(objectMapper.writeValueAsString(amenities));
                    log.info("Extracted {} amenities for station {}: {}", amenities.size(), station.getName(), amenities);
                }
            } catch (JsonProcessingException e) {
                log.error("Error converting amenities to JSON for station {}", station.getName(), e);
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

        // Opening hours
        if (place.getCurrentOpeningHours() != null && place.getCurrentOpeningHours().getWeekdayDescriptions() != null) {
            try {
                station.setOpeningHours(objectMapper.writeValueAsString(place.getCurrentOpeningHours().getWeekdayDescriptions()));
            } catch (JsonProcessingException e) {
                log.error("Error converting opening hours to JSON", e);
            }
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

        // Extract amenities from types
        if (place.getTypes() != null && !place.getTypes().isEmpty()) {
            try {
                List<String> amenities = amenityMapper.mapTypesToAmenities(place.getTypes());
                if (!amenities.isEmpty()) {
                    station.setAmenities(objectMapper.writeValueAsString(amenities));
                    log.info("Extracted {} amenities for station {}: {}", amenities.size(), station.getName(), amenities);
                }
            } catch (JsonProcessingException e) {
                log.error("Error converting amenities to JSON for station {}", station.getName(), e);
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
     */
    private void mergeEvConnectorData(Station station, PlacesV1Response.EVChargeOptions evOptions) {
        try {
            log.info("Merging EV connector data from Google Places v1 for station: {}", station.getName());
            log.debug("Total connectors from Google: {}", evOptions.getConnectorCount());

            if (evOptions.getConnectorAggregation() == null || evOptions.getConnectorAggregation().isEmpty()) {
                log.debug("No connector aggregation data available from Google Places v1");
                return;
            }

            // Parse existing connectors from OpenChargeMap
            List<Map<String, Object>> existingConnectors = new ArrayList<>();
            if (station.getConnectors() != null && !station.getConnectors().isEmpty() && !station.getConnectors().equals("[]")) {
                try {
                    existingConnectors = objectMapper.readValue(
                        station.getConnectors(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Map.class)
                    );
                } catch (JsonProcessingException e) {
                    log.warn("Could not parse existing connectors for station {}: {}", station.getName(), e.getMessage());
                }
            }

            // Keep Google connectors as list to preserve all power variants
            List<PlacesV1Response.ConnectorAggregation> googleConnectors = evOptions.getConnectorAggregation();
            Set<PlacesV1Response.ConnectorAggregation> usedGoogleConnectors = new HashSet<>();

            for (PlacesV1Response.ConnectorAggregation connector : googleConnectors) {
                String normalizedType = normalizeConnectorType(connector.getType());
                log.debug("Google connector: type={}, count={}, available={}, maxCharge={}kW",
                    normalizedType, connector.getCount(), connector.getAvailableCount(), connector.getMaxChargeRateKw());
            }

            // Enrich existing connectors with Google data, matching by type AND power
            for (Map<String, Object> connector : existingConnectors) {
                String type = connector.get("type") != null ? connector.get("type").toString() : null;
                if (type != null) {
                    String normalizedType = normalizeConnectorTypeFromOCM(type);
                    Double ocmPower = connector.get("powerKW") != null ?
                        ((Number) connector.get("powerKW")).doubleValue() : null;

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
                        // Enrich with Google data
                        connector.put("availableCount", googleData.getAvailableCount());
                        connector.put("outOfServiceCount", googleData.getOutOfServiceCount());
                        if (googleData.getMaxChargeRateKw() != null) {
                            connector.put("maxChargeRateKw", googleData.getMaxChargeRateKw());
                        }
                        connector.put("availabilityLastUpdate", googleData.getAvailabilityLastUpdateTime());
                        usedGoogleConnectors.add(googleData);
                        log.debug("Enriched OCM connector {} ({}kW) with Google Places v1 data ({}kW)",
                            type, ocmPower, googleData.getMaxChargeRateKw());
                    }
                }
            }

            // Add any Google connectors that weren't used for enrichment
            for (PlacesV1Response.ConnectorAggregation googleConnector : googleConnectors) {
                if (!usedGoogleConnectors.contains(googleConnector)) {
                    String normalizedType = normalizeConnectorType(googleConnector.getType());

                    // Add new connector from Google
                    Map<String, Object> newConnector = new HashMap<>();
                    newConnector.put("type", normalizedType);
                    newConnector.put("source", "google_places_v1");
                    newConnector.put("quantity", googleConnector.getCount());
                    newConnector.put("availableCount", googleConnector.getAvailableCount());
                    newConnector.put("outOfServiceCount", googleConnector.getOutOfServiceCount());
                    if (googleConnector.getMaxChargeRateKw() != null) {
                        newConnector.put("maxChargeRateKw", googleConnector.getMaxChargeRateKw());
                        newConnector.put("powerKW", googleConnector.getMaxChargeRateKw().doubleValue());
                    }
                    newConnector.put("availabilityLastUpdate", googleConnector.getAvailabilityLastUpdateTime());
                    existingConnectors.add(newConnector);
                    log.info("Added new connector from Google Places v1: {} ({}kW)",
                        normalizedType, googleConnector.getMaxChargeRateKw());
                }
            }

            // Update station with merged connector data
            if (!existingConnectors.isEmpty()) {
                station.setConnectors(objectMapper.writeValueAsString(existingConnectors));

                // Update total connector count
                int totalCount = existingConnectors.stream()
                    .mapToInt(c -> {
                        Object qty = c.get("quantity");
                        if (qty instanceof Integer) return (Integer) qty;
                        if (qty instanceof String) {
                            try {
                                return Integer.parseInt((String) qty);
                            } catch (NumberFormatException e) {
                                return 1;
                            }
                        }
                        return 1;
                    })
                    .sum();
                station.setTotalConnectors(totalCount);

                log.info("Successfully merged {} connectors for station {} (total: {})",
                    existingConnectors.size(), station.getName(), totalCount);
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
     * Map OpenChargeMap connections to our connector format
     */
    private List<Map<String, Object>> mapConnectors(List<OpenChargeMapResponse.Connection> connections) {
        List<Map<String, Object>> connectors = new ArrayList<>();

        for (OpenChargeMapResponse.Connection conn : connections) {
            Map<String, Object> connector = new HashMap<>();

            if (conn.getConnectionType() != null) {
                connector.put("type", conn.getConnectionType().getTitle());
                connector.put("formalName", conn.getConnectionType().getFormalName());
            }

            if (conn.getLevel() != null) {
                connector.put("level", conn.getLevel().getTitle());
            }

            if (conn.getCurrentType() != null) {
                connector.put("currentType", conn.getCurrentType().getTitle());
            }

            connector.put("powerKW", conn.getPowerKW());
            connector.put("quantity", conn.getQuantity() != null ? conn.getQuantity() : 1);

            if (conn.getStatusType() != null) {
                connector.put("status", conn.getStatusType().getTitle());
                connector.put("isOperational", conn.getStatusType().getIsOperational());
            }

            connectors.add(connector);
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
                    .divide(BigDecimal.valueOf(totalCount), 2, BigDecimal.ROUND_HALF_UP);
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
