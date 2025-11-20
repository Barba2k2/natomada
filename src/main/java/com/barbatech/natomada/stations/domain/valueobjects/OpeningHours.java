package com.barbatech.natomada.stations.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Value Object: OpeningHours
 *
 * Type-safe representation of opening hours.
 * Following Axel Engineering Doctrine: explicit types over strings.
 *
 * Doctrine principles applied:
 * - Predictability: Deterministic behavior for time checks
 * - Explicitness: Clear schedule structure
 * - Domain Logic: Business rules for opening hours
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHours {

    private Map<DayOfWeek, DaySchedule> schedule;

    @JsonProperty("weekday_text")
    private List<String> weekdayText;

    /**
     * Nested Value Object: DaySchedule
     *
     * Represents opening and closing times for a specific day
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DaySchedule {
        private LocalTime open;
        private LocalTime close;

        /**
         * Domain logic: Check if open at specific time
         *
         * Business rule: Station is open if time is between open and close
         *
         * @param time the time to check
         * @return true if open at specified time
         */
        public boolean isOpenAt(LocalTime time) {
            if (open == null || close == null || time == null) {
                return false;
            }
            return time.isAfter(open) && time.isBefore(close);
        }
    }

    /**
     * Domain logic: Check if open on specific day
     *
     * @param day the day of week to check
     * @return true if schedule exists for this day
     */
    public boolean isOpenOn(DayOfWeek day) {
        return schedule != null && schedule.containsKey(day);
    }

    /**
     * Domain logic: Check if open now
     *
     * Business rule: Station is open if:
     * 1. Schedule exists for today
     * 2. Current time is within opening hours
     *
     * @return true if station is open right now
     */
    public boolean isOpenNow() {
        DayOfWeek today = DayOfWeek.from(LocalDate.now());
        LocalTime now = LocalTime.now();

        if (!isOpenOn(today)) {
            return false;
        }

        DaySchedule todaySchedule = schedule.get(today);
        return todaySchedule != null && todaySchedule.isOpenAt(now);
    }

    /**
     * Domain logic: Get opening time for specific day
     *
     * @param day the day of week
     * @return opening time or null if not available
     */
    public LocalTime getOpeningTime(DayOfWeek day) {
        if (schedule == null || !schedule.containsKey(day)) {
            return null;
        }
        DaySchedule daySchedule = schedule.get(day);
        return daySchedule != null ? daySchedule.getOpen() : null;
    }

    /**
     * Domain logic: Get closing time for specific day
     *
     * @param day the day of week
     * @return closing time or null if not available
     */
    public LocalTime getClosingTime(DayOfWeek day) {
        if (schedule == null || !schedule.containsKey(day)) {
            return null;
        }
        DaySchedule daySchedule = schedule.get(day);
        return daySchedule != null ? daySchedule.getClose() : null;
    }
}
