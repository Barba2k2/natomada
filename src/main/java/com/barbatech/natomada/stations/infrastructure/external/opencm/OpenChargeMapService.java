package com.barbatech.natomada.stations.infrastructure.external.opencm;

import com.barbatech.natomada.stations.infrastructure.external.opencm.dtos.OpenChargeMapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Service for OpenChargeMap API integration
 * Documentation: https://openchargemap.org/site/develop/api
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OpenChargeMapService {

    private final RestTemplate restTemplate;

    @Value("${opencm.api.key}")
    private String apiKey;

    @Value("${opencm.api.base-url}")
    private String baseUrl;

    /**
     * Get charging station by OCM ID
     *
     * @param ocmId OpenChargeMap station ID
     * @return Charging station details
     */
    public OpenChargeMapResponse getById(Integer ocmId) {
        try {
            log.info("Calling OpenChargeMap API to get station by ID: {}", ocmId);

            String url = UriComponentsBuilder.fromUriString(baseUrl + "/poi/")
                .queryParam("key", apiKey)
                .queryParam("chargepointid", ocmId)
                .queryParam("compact", "false")
                .queryParam("verbose", "false")
                .toUriString();

            log.debug("OpenChargeMap URL: {}", url);

            ResponseEntity<List<OpenChargeMapResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OpenChargeMapResponse>>() {}
            );

            List<OpenChargeMapResponse> stations = response.getBody();

            if (stations == null || stations.isEmpty()) {
                log.warn("OpenChargeMap returned no station for ID: {}", ocmId);
                return null;
            }

            log.info("OpenChargeMap returned station: {}", stations.get(0).getAddressInfo().getTitle());
            return stations.get(0);

        } catch (Exception e) {
            log.error("Error calling OpenChargeMap API for ID {}: {}", ocmId, e.getMessage(), e);
            return null;
        }
    }

    /**
     * Search for nearby charging stations
     *
     * @param latitude Latitude coordinate
     * @param longitude Longitude coordinate
     * @param radiusKM Radius in kilometers
     * @param maxResults Maximum number of results
     * @return List of charging stations from OpenChargeMap
     */
    public List<OpenChargeMapResponse> searchNearby(
        Double latitude,
        Double longitude,
        Integer radiusKM,
        Integer maxResults
    ) {
        try {
            log.info("Calling OpenChargeMap API: lat={}, lon={}, radius={}km, max={}",
                     latitude, longitude, radiusKM, maxResults);

            String url = UriComponentsBuilder.fromUriString(baseUrl + "/poi/")
                .queryParam("key", apiKey)
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("distance", radiusKM)
                .queryParam("distanceunit", "KM")
                .queryParam("maxresults", maxResults)
                .queryParam("compact", "false")
                .queryParam("verbose", "false")
                .toUriString();

            log.debug("OpenChargeMap URL: {}", url);

            ResponseEntity<List<OpenChargeMapResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OpenChargeMapResponse>>() {}
            );

            List<OpenChargeMapResponse> stations = response.getBody();

            if (stations == null) {
                log.warn("OpenChargeMap returned null response");
                return Collections.emptyList();
            }

            log.info("OpenChargeMap returned {} stations", stations.size());
            return stations;

        } catch (Exception e) {
            log.error("Error calling OpenChargeMap API: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
