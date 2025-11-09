package com.barbatech.natomada.stations.infrastructure.external.google.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO from Google Places API (Nearby Search)
 * Based on: https://developers.google.com/maps/documentation/places/web-service/search-nearby
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GooglePlacesResponse {

    @JsonProperty("results")
    private List<Place> results;

    @JsonProperty("status")
    private String status;

    @JsonProperty("next_page_token")
    private String nextPageToken;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Place {
        @JsonProperty("place_id")
        private String placeId;

        @JsonProperty("name")
        private String name;

        @JsonProperty("vicinity")
        private String vicinity;

        @JsonProperty("formatted_address")
        private String formattedAddress;

        @JsonProperty("geometry")
        private Geometry geometry;

        @JsonProperty("rating")
        private BigDecimal rating;

        @JsonProperty("user_ratings_total")
        private Integer userRatingsTotal;

        @JsonProperty("opening_hours")
        private OpeningHours openingHours;

        @JsonProperty("business_status")
        private String businessStatus;

        @JsonProperty("types")
        private List<String> types;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geometry {
        @JsonProperty("location")
        private Location location;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {
        @JsonProperty("lat")
        private BigDecimal lat;

        @JsonProperty("lng")
        private BigDecimal lng;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OpeningHours {
        @JsonProperty("open_now")
        private Boolean openNow;

        @JsonProperty("weekday_text")
        private List<String> weekdayText;
    }
}
