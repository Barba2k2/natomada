-- Add station_ocm_id column to search_history table
ALTER TABLE search_history
ADD COLUMN station_ocm_id VARCHAR(255);

-- Create index for faster lookups
CREATE INDEX idx_search_history_station_ocm_id ON search_history(station_ocm_id);
