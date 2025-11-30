-- Fix ocm_id column type from INTEGER to VARCHAR(255)
-- This allows storing OCM IDs in format "ocm_123456"

-- Drop existing index on ocm_id if exists
DROP INDEX IF EXISTS idx_station_ocm_id;

-- Alter ocm_id column type to VARCHAR
ALTER TABLE stations
    ALTER COLUMN ocm_id TYPE VARCHAR(255) USING ocm_id::VARCHAR;

-- Make ocm_id NOT NULL and UNIQUE
ALTER TABLE stations
    ALTER COLUMN ocm_id SET NOT NULL;

-- Recreate unique index on ocm_id
CREATE UNIQUE INDEX IF NOT EXISTS idx_station_ocm_id ON stations(ocm_id);

-- Drop external_id column if it exists (we'll use ocm_id instead)
ALTER TABLE stations
    DROP COLUMN IF EXISTS external_id;

-- Add comment
COMMENT ON COLUMN stations.ocm_id IS 'OpenChargeMap ID in format ocm_123456';
