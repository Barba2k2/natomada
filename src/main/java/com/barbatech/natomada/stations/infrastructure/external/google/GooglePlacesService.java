package com.barbatech.natomada.stations.infrastructure.external.google;

import com.barbatech.natomada.stations.infrastructure.external.google.dtos.GooglePlacesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place";
    private static final String NEARBY_SEARCH_ENDPOINT = "/nearbysearch/json";
    private static final String DETAILS_ENDPOINT = "/details/json";

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
                .queryParam("type", "electric_vehicle_charging_station")
                .queryParam("keyword", "charging station")
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

    private GooglePlacesResponse createEmptyResponse() {
        GooglePlacesResponse response = new GooglePlacesResponse();
        response.setResults(Collections.emptyList());
        response.setStatus("ZERO_RESULTS");
        return response;
    }
}
