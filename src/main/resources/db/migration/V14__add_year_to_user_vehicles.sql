-- Add year column to user_vehicles table
-- This allows users to specify the year of their specific vehicle
ALTER TABLE user_vehicles ADD COLUMN IF NOT EXISTS year VARCHAR(10);

-- Add comment
COMMENT ON COLUMN user_vehicles.year IS 'Year of the user vehicle (e.g., 2024, 2023)';
