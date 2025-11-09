package com.barbatech.natomada.stations.infrastructure.external;

import com.barbatech.natomada.stations.domain.entities.Station;
import com.barbatech.natomada.stations.infrastructure.external.google.dtos.GooglePlacesResponse;
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
import java.util.List;
import java.util.Map;

/**
 * Mapper to convert external API responses to Station entities
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalStationMapper {

    private final ObjectMapper objectMapper;

    /**
     * Convert OpenChargeMap response to Station entity
     */
    public Station fromOpenChargeMap(OpenChargeMapResponse ocm) {
        Station station = new Station();

        // External ID (required field)
        station.setExternalId("ocm_" + ocm.getId());

        // IDs
        station.setOcmId(ocm.getId() != null ? ocm.getId().intValue() : null);
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
                station.setPhotoReferences(objectMapper.writeValueAsString(photoRefs));
                log.debug("Stored {} photo references for station {}", photoRefs.size(), station.getName());
            } catch (JsonProcessingException e) {
                log.error("Error converting photo references to JSON", e);
            }
        }

        log.debug("Enriched station {} with Google Places data", station.getName());
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
