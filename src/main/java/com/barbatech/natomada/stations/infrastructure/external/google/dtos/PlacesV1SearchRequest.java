package com.barbatech.natomada.stations.infrastructure.external.google.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Request DTO for Google Places API v1 Text Search
 * Documentation: https://developers.google.com/maps/documentation/places/web-service/text-search
 */
@Data
@Builder
public class PlacesV1SearchRequest {

    @JsonProperty("textQuery")
    private String textQuery;

    @JsonProperty("pageSize")
    @Builder.Default
    private Integer pageSize = 20;

    @JsonProperty("languageCode")
    private String languageCode;

    @JsonProperty("regionCode")
    private String regionCode;

    @JsonProperty("locationBias")
    private LocationBias locationBias;

    @JsonProperty("evOptions")
    private EVOptions evOptions;

    @JsonProperty("minRating")
    private BigDecimal minRating;

    @JsonProperty("openNow")
    private Boolean openNow;

    @JsonProperty("rankPreference")
    private String rankPreference; // DISTANCE or RELEVANCE

    @Data
    @Builder
    public static class LocationBias {
        @JsonProperty("circle")
        private Circle circle;
    }

    @Data
    @Builder
    public static class Circle {
        @JsonProperty("center")
        private Center center;

        @JsonProperty("radius")
        private Double radius; // in meters
    }

    @Data
    @Builder
    public static class Center {
        @JsonProperty("latitude")
        private Double latitude;

        @JsonProperty("longitude")
        private Double longitude;
    }

    @Data
    @Builder
    public static class EVOptions {
        @JsonProperty("minimumChargingRateKw")
        private Double minimumChargingRateKw;

        @JsonProperty("connectorTypes")
        private List<String> connectorTypes;
    }
}
