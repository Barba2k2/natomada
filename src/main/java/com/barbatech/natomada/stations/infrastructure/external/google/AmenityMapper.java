package com.barbatech.natomada.stations.infrastructure.external.google;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Maps Google Places types to user-friendly amenity names
 */
@Component
public class AmenityMapper {

    // Map of Google Places types to user-friendly amenity names
    private static final Map<String, String> AMENITY_MAP = Map.ofEntries(
        // Parking
        Map.entry("parking", "parking"),
        
        // Food & Drink
        Map.entry("restaurant", "restaurant"),
        Map.entry("cafe", "cafe"),
        Map.entry("food", "food"),
        Map.entry("meal_takeaway", "food"),
        Map.entry("bakery", "food"),
        
        // Shopping
        Map.entry("shopping_mall", "shopping"),
        Map.entry("convenience_store", "convenience_store"),
        Map.entry("supermarket", "shopping"),
        Map.entry("store", "shopping"),
        
        // Services
        Map.entry("gas_station", "gas_station"),
        Map.entry("atm", "atm"),
        Map.entry("bank", "atm"),
        
        // Restrooms (implied by certain types)
        Map.entry("rest_stop", "restroom"),
        Map.entry("lodging", "restroom"),
        Map.entry("hotel", "restroom"),
        
        // Medical
        Map.entry("pharmacy", "pharmacy"),
        Map.entry("hospital", "hospital"),
        Map.entry("doctor", "hospital"),
        
        // Entertainment
        Map.entry("gym", "gym"),
        Map.entry("spa", "spa"),
        Map.entry("movie_theater", "entertainment"),
        
        // Accessibility
        Map.entry("wheelchair_accessible", "wheelchair_accessible")
    );

    /**
     * Convert Google Places types to user-friendly amenity names
     *
     * @param types List of Google Places types
     * @return List of mapped amenity names (deduplicated)
     */
    public List<String> mapTypesToAmenities(List<String> types) {
        if (types == null || types.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> amenities = new ArrayList<>();
        
        for (String type : types) {
            String amenity = AMENITY_MAP.get(type);
            if (amenity != null && !amenities.contains(amenity)) {
                amenities.add(amenity);
            }
        }
        
        return amenities;
    }
}
