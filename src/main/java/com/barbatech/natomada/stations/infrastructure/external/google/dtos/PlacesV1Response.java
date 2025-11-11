package com.barbatech.natomada.stations.infrastructure.external.google.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for Google Places API v1
 * Documentation: https://developers.google.com/maps/documentation/places/web-service/reference/rest/v1/places
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacesV1Response {

    @JsonProperty("places")
    private List<Place> places;

    @JsonProperty("nextPageToken")
    private String nextPageToken;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Place {
        @JsonProperty("id")
        private String id;

        @JsonProperty("displayName")
        private DisplayName displayName;

        @JsonProperty("formattedAddress")
        private String formattedAddress;

        @JsonProperty("location")
        private Location location;

        @JsonProperty("rating")
        private BigDecimal rating;

        @JsonProperty("userRatingCount")
        private Integer userRatingCount;

        @JsonProperty("types")
        private List<String> types;

        @JsonProperty("primaryType")
        private String primaryType;

        @JsonProperty("businessStatus")
        private String businessStatus;

        @JsonProperty("evChargeOptions")
        private EVChargeOptions evChargeOptions;

        @JsonProperty("currentOpeningHours")
        private OpeningHours currentOpeningHours;

        @JsonProperty("photos")
        private List<Photo> photos;

        @JsonProperty("internationalPhoneNumber")
        private String internationalPhoneNumber;

        @JsonProperty("websiteUri")
        private String websiteUri;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DisplayName {
        @JsonProperty("text")
        private String text;

        @JsonProperty("languageCode")
        private String languageCode;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {
        @JsonProperty("latitude")
        private BigDecimal latitude;

        @JsonProperty("longitude")
        private BigDecimal longitude;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EVChargeOptions {
        @JsonProperty("connectorCount")
        private Integer connectorCount;

        @JsonProperty("connectorAggregation")
        private List<ConnectorAggregation> connectorAggregation;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ConnectorAggregation {
        @JsonProperty("type")
        private String type; // EV_CONNECTOR_TYPE_J1772, EV_CONNECTOR_TYPE_CCS_COMBO_1, etc.

        @JsonProperty("maxChargeRateKw")
        private BigDecimal maxChargeRateKw;

        @JsonProperty("count")
        private Integer count;

        @JsonProperty("availableCount")
        private Integer availableCount;

        @JsonProperty("outOfServiceCount")
        private Integer outOfServiceCount;

        @JsonProperty("availabilityLastUpdateTime")
        private String availabilityLastUpdateTime;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OpeningHours {
        @JsonProperty("openNow")
        private Boolean openNow;

        @JsonProperty("weekdayDescriptions")
        private List<String> weekdayDescriptions;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Photo {
        @JsonProperty("name")
        private String name; // Format: places/{place_id}/photos/{photo_reference}

        @JsonProperty("widthPx")
        private Integer widthPx;

        @JsonProperty("heightPx")
        private Integer heightPx;

        @JsonProperty("authorAttributions")
        private List<AuthorAttribution> authorAttributions;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AuthorAttribution {
        @JsonProperty("displayName")
        private String displayName;

        @JsonProperty("uri")
        private String uri;

        @JsonProperty("photoUri")
        private String photoUri;
    }
}
