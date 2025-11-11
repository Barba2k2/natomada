package com.barbatech.natomada.stations.infrastructure.external.google;

import com.barbatech.natomada.stations.infrastructure.external.google.dtos.GooglePlacesDetailsResponse;
import com.barbatech.natomada.stations.infrastructure.external.google.dtos.GooglePlacesResponse;
import com.barbatech.natomada.stations.infrastructure.external.google.dtos.PlacesV1Response;
import com.barbatech.natomada.stations.infrastructure.external.google.dtos.PlacesV1SearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;

/**
 * Service for Google Places API integration
 * Documentation: https://developers.google.com/maps/documentation/places/web-service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GooglePlacesService {

    private final RestTemplate restTemplate;

    @Value("${google.places.api.key}")
    private String apiKey;

    // Old API (Legacy)
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place";
    private static final String NEARBY_SEARCH_ENDPOINT = "/nearbysearch/json";
    private static final String DETAILS_ENDPOINT = "/details/json";

    // New API v1
    private static final String BASE_URL_V1 = "https://places.googleapis.com/v1";
    private static final String SEARCH_TEXT_ENDPOINT = "/places:searchText";

    // Field mask for Places API v1 (includes EV charging options)
    private static final String FIELD_MASK = "places.id,places.displayName,places.formattedAddress," +
        "places.location,places.rating,places.userRatingCount,places.types,places.primaryType," +
        "places.businessStatus,places.evChargeOptions,places.currentOpeningHours," +
        "places.photos,places.internationalPhoneNumber,places.websiteUri";

    /**
     * Search for nearby EV charging stations
     *
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     * @param radiusMeters Radius in meters
     * @return Google Places response
     */
    public GooglePlacesResponse searchNearby(
        Double latitude,
        Double longitude,
        Integer radiusMeters
    ) {
        try {
            log.info("Calling Google Places API: lat={}, lon={}, radius={}m",
                     latitude, longitude, radiusMeters);

            String url = UriComponentsBuilder.fromUriString(BASE_URL + NEARBY_SEARCH_ENDPOINT)
                .queryParam("key", apiKey)
                .queryParam("location", latitude + "," + longitude)
                .queryParam("radius", radiusMeters)
                .queryParam("keyword", "ev charging station electric vehicle charger")
                .toUriString();

            log.debug("Google Places URL: {}", url);

            ResponseEntity<GooglePlacesResponse> response = restTemplate.getForEntity(
                url,
                GooglePlacesResponse.class
            );

            GooglePlacesResponse body = response.getBody();

            if (body == null) {
                log.warn("Google Places returned null response");
                return createEmptyResponse();
            }

            if (!"OK".equals(body.getStatus()) && !"ZERO_RESULTS".equals(body.getStatus())) {
                log.warn("Google Places API returned status: {}", body.getStatus());
            }

            int count = body.getResults() != null ? body.getResults().size() : 0;
            log.info("Google Places returned {} stations", count);

            return body;

        } catch (Exception e) {
            log.error("Error calling Google Places API: {}", e.getMessage(), e);
            return createEmptyResponse();
        }
    }

    /**
     * Search for any nearby business (not just charging stations)
     * Used to find businesses with photos when charging station has none
     *
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     * @param radiusMeters Radius in meters
     * @return Google Places response
     */
    public GooglePlacesResponse searchNearbyBusiness(
        Double latitude,
        Double longitude,
        Integer radiusMeters
    ) {
        try {
            log.info("Calling Google Places API for nearby businesses: lat={}, lon={}, radius={}m",
                     latitude, longitude, radiusMeters);

            // Search without type/keyword filters to find any nearby place
            String url = UriComponentsBuilder.fromUriString(BASE_URL + NEARBY_SEARCH_ENDPOINT)
                .queryParam("key", apiKey)
                .queryParam("location", latitude + "," + longitude)
                .queryParam("radius", radiusMeters)
                .queryParam("rankby", "prominence") // Get most prominent places first
                .toUriString();

            log.debug("Google Places Business Search URL: {}", url);

            ResponseEntity<GooglePlacesResponse> response = restTemplate.getForEntity(
                url,
                GooglePlacesResponse.class
            );

            GooglePlacesResponse body = response.getBody();

            if (body == null) {
                log.warn("Google Places returned null response for business search");
                return createEmptyResponse();
            }

            if (!("OK".equals(body.getStatus()) || "ZERO_RESULTS".equals(body.getStatus()))) {
                log.warn("Google Places API returned status: {}", body.getStatus());
            }

            int count = body.getResults() != null ? body.getResults().size() : 0;
            log.info("Google Places returned {} nearby businesses", count);

            return body;

        } catch (Exception e) {
            log.error("Error calling Google Places API for businesses: {}", e.getMessage(), e);
            return createEmptyResponse();
        }
    }

    /**
     * Get detailed information about a place
     *
     * @param placeId Google Place ID
     * @return Place details (simplified - expand as needed)
     */
    public Map<String, Object> getPlaceDetails(String placeId) {
        try {
            log.debug("Getting place details for: {}", placeId);

            String url = UriComponentsBuilder.fromUriString(BASE_URL + DETAILS_ENDPOINT)
                .queryParam("key", apiKey)
                .queryParam("place_id", placeId)
                .queryParam("fields", "name,rating,formatted_phone_number,opening_hours,website")
                .toUriString();

            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            return response.getBody() != null ? response.getBody() : Collections.emptyMap();

        } catch (Exception e) {
            log.error("Error getting place details: {}", e.getMessage(), e);
            return Collections.emptyMap();
        }
    }

    /**
     * Get detailed information about a place and return as Place object
     *
     * @param placeId Google Place ID
     * @return Place details with ratings and photos
     */
    public GooglePlacesResponse.Place getPlaceDetailsAsPlace(String placeId) {
        try {
            log.info("Getting place details for Place ID: {}", placeId);

            String url = UriComponentsBuilder.fromUriString(BASE_URL + DETAILS_ENDPOINT)
                .queryParam("key", apiKey)
                .queryParam("place_id", placeId)
                .queryParam("fields", "place_id,name,rating,user_ratings_total,formatted_phone_number,opening_hours,photos,geometry")
                .toUriString();

            log.debug("Google Places Details URL: {}", url);

            ResponseEntity<GooglePlacesDetailsResponse> response = restTemplate.getForEntity(
                url,
                GooglePlacesDetailsResponse.class
            );

            GooglePlacesDetailsResponse body = response.getBody();

            if (body == null || body.getResult() == null) {
                log.warn("Google Places Details returned null response for Place ID: {}", placeId);
                return null;
            }

            if (!"OK".equals(body.getStatus())) {
                log.warn("Google Places Details API returned status: {} for Place ID: {}", body.getStatus(), placeId);
                return null;
            }

            log.info("Successfully fetched place details for: {} (rating: {})",
                     body.getResult().getName(),
                     body.getResult().getRating());

            return body.getResult();

        } catch (Exception e) {
            log.error("Error getting place details for Place ID {}: {}", placeId, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Search for nearby EV charging stations using NEW Places API v1
     * This API provides EV connector information including type, charge rate, and availability
     *
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     * @param radiusMeters Radius in meters
     * @return Places v1 response with EV charging options
     */
    public PlacesV1Response searchNearbyV1(
        Double latitude,
        Double longitude,
        Integer radiusMeters
    ) {
        try {
            log.info("Calling Google Places API v1: lat={}, lon={}, radius={}m",
                     latitude, longitude, radiusMeters);

            // Build request body
            PlacesV1SearchRequest request = PlacesV1SearchRequest.builder()
                .textQuery("EV charging station")
                .pageSize(20)
                .languageCode("en")
                .locationBias(PlacesV1SearchRequest.LocationBias.builder()
                    .circle(PlacesV1SearchRequest.Circle.builder()
                        .center(PlacesV1SearchRequest.Center.builder()
                            .latitude(latitude)
                            .longitude(longitude)
                            .build())
                        .radius(radiusMeters.doubleValue())
                        .build())
                    .build())
                .rankPreference("DISTANCE")
                .build();

            // Build headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("X-Goog-Api-Key", apiKey);
            headers.set("X-Goog-FieldMask", FIELD_MASK);

            // Build request entity
            HttpEntity<PlacesV1SearchRequest> requestEntity = new HttpEntity<>(request, headers);

            String url = BASE_URL_V1 + SEARCH_TEXT_ENDPOINT;
            log.debug("Google Places v1 URL: {}", url);

            // Make POST request
            ResponseEntity<PlacesV1Response> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                PlacesV1Response.class
            );

            PlacesV1Response body = response.getBody();

            if (body == null) {
                log.warn("Google Places v1 returned null response");
                return createEmptyV1Response();
            }

            int count = body.getPlaces() != null ? body.getPlaces().size() : 0;
            log.info("Google Places v1 returned {} stations", count);

            // Log EV charging info if available
            if (body.getPlaces() != null) {
                body.getPlaces().forEach(place -> {
                    if (place.getEvChargeOptions() != null) {
                        log.debug("Station {} has {} EV connectors",
                            place.getDisplayName() != null ? place.getDisplayName().getText() : "Unknown",
                            place.getEvChargeOptions().getConnectorCount());
                    }
                });
            }

            return body;

        } catch (Exception e) {
            log.error("Error calling Google Places API v1: {}", e.getMessage(), e);
            return createEmptyV1Response();
        }
    }

    private GooglePlacesResponse createEmptyResponse() {
        GooglePlacesResponse response = new GooglePlacesResponse();
        response.setResults(Collections.emptyList());
        response.setStatus("ZERO_RESULTS");
        return response;
    }

    private PlacesV1Response createEmptyV1Response() {
        PlacesV1Response response = new PlacesV1Response();
        response.setPlaces(Collections.emptyList());
        return response;
    }
}
