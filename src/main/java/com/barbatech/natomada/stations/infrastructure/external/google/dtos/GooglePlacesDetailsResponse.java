package com.barbatech.natomada.stations.infrastructure.external.google.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Google Places API Details response
 */
@Data
public class GooglePlacesDetailsResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("result")
    private GooglePlacesResponse.Place result;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("html_attributions")
    private java.util.List<String> htmlAttributions;
}
