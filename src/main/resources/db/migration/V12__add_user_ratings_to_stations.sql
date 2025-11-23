-- Add user rating fields to stations table
ALTER TABLE stations
ADD COLUMN IF NOT EXISTS user_rating DECIMAL(3, 2),
ADD COLUMN IF NOT EXISTS user_review_count INTEGER NOT NULL DEFAULT 0;

-- Create index for better query performance
CREATE INDEX IF NOT EXISTS idx_station_user_rating ON stations(user_rating);

-- Add comments
COMMENT ON COLUMN stations.user_rating IS 'Average rating from app users (calculated from reviews table)';
COMMENT ON COLUMN stations.user_review_count IS 'Number of reviews from app users';
