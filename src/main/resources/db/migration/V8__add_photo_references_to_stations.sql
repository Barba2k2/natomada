-- Add photo_references column to stations table
-- This will store Google Places photo references as JSON array

ALTER TABLE stations
ADD COLUMN photo_references JSONB;

-- Add index for better query performance
CREATE INDEX idx_stations_photo_references ON stations USING GIN (photo_references);

-- Add comment for documentation
COMMENT ON COLUMN stations.photo_references IS 'JSON array of Google Places photo references';
