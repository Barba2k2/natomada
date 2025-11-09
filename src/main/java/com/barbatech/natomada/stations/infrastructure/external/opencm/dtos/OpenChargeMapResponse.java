package com.barbatech.natomada.stations.infrastructure.external.opencm.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO from OpenChargeMap API
 * Based on: https://openchargemap.org/site/develop/api
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenChargeMapResponse {

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("UUID")
    private String uuid;

    @JsonProperty("DataProvider")
    private DataProvider dataProvider;

    @JsonProperty("OperatorInfo")
    private OperatorInfo operatorInfo;

    @JsonProperty("UsageType")
    private UsageType usageType;

    @JsonProperty("AddressInfo")
    private AddressInfo addressInfo;

    @JsonProperty("Connections")
    private List<Connection> connections;

    @JsonProperty("NumberOfPoints")
    private Integer numberOfPoints;

    @JsonProperty("StatusType")
    private StatusType statusType;

    @JsonProperty("UsageCost")
    private String usageCost;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataProvider {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("Title")
        private String title;

        @JsonProperty("WebsiteURL")
        private String websiteUrl;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OperatorInfo {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("Title")
        private String title;

        @JsonProperty("WebsiteURL")
        private String websiteUrl;

        @JsonProperty("PhonePrimaryContact")
        private String phonePrimaryContact;

        @JsonProperty("ContactEmail")
        private String contactEmail;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UsageType {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("Title")
        private String title;

        @JsonProperty("IsPayAtLocation")
        private Boolean isPayAtLocation;

        @JsonProperty("IsMembershipRequired")
        private Boolean isMembershipRequired;

        @JsonProperty("IsAccessKeyRequired")
        private Boolean isAccessKeyRequired;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddressInfo {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("Title")
        private String title;

        @JsonProperty("AddressLine1")
        private String addressLine1;

        @JsonProperty("AddressLine2")
        private String addressLine2;

        @JsonProperty("Town")
        private String town;

        @JsonProperty("StateOrProvince")
        private String stateOrProvince;

        @JsonProperty("Postcode")
        private String postcode;

        @JsonProperty("Country")
        private Country country;

        @JsonProperty("Latitude")
        private BigDecimal latitude;

        @JsonProperty("Longitude")
        private BigDecimal longitude;

        @JsonProperty("ContactTelephone1")
        private String contactTelephone1;

        @JsonProperty("AccessComments")
        private String accessComments;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Country {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("ISOCode")
        private String isoCode;

        @JsonProperty("Title")
        private String title;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Connection {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("ConnectionType")
        private ConnectionType connectionType;

        @JsonProperty("Level")
        private Level level;

        @JsonProperty("PowerKW")
        private BigDecimal powerKW;

        @JsonProperty("CurrentType")
        private CurrentType currentType;

        @JsonProperty("Quantity")
        private Integer quantity;

        @JsonProperty("StatusType")
        private StatusType statusType;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ConnectionType {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("Title")
        private String title;

        @JsonProperty("FormalName")
        private String formalName;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Level {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("Title")
        private String title;

        @JsonProperty("Comments")
        private String comments;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentType {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("Title")
        private String title;

        @JsonProperty("Description")
        private String description;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StatusType {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("Title")
        private String title;

        @JsonProperty("IsOperational")
        private Boolean isOperational;
    }
}
