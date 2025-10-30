-- Create stations table
CREATE TABLE stations (
    id BIGSERIAL PRIMARY KEY,

    -- External IDs
    ocm_id INTEGER,
    ocm_uuid VARCHAR(100),
    google_place_id VARCHAR(255),
    external_id VARCHAR(255) NOT NULL UNIQUE,

    -- Basic Information
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500) NOT NULL,
    city VARCHAR(100),
    state VARCHAR(50),
    postal_code VARCHAR(20),
    country VARCHAR(50) NOT NULL,
    latitude DECIMAL(10, 7) NOT NULL,
    longitude DECIMAL(10, 7) NOT NULL,
    phone VARCHAR(50),

    -- Operational Status
    is_operational BOOLEAN NOT NULL DEFAULT true,
    total_connectors INTEGER NOT NULL DEFAULT 0,
    connectors JSONB,

    -- Operator Information
    operator_name VARCHAR(255),
    operator_website VARCHAR(500),
    operator_phone VARCHAR(50),
    operator_email VARCHAR(255),

    -- Usage Type
    usage_type VARCHAR(100),
    requires_membership BOOLEAN NOT NULL DEFAULT false,
    pay_at_location BOOLEAN NOT NULL DEFAULT false,
    requires_access_key BOOLEAN NOT NULL DEFAULT false,

    -- Cost
    usage_cost VARCHAR(255),

    -- Ratings
    ocm_rating DECIMAL(3, 2),
    ocm_review_count INTEGER NOT NULL DEFAULT 0,
    google_rating DECIMAL(3, 2),
    google_review_count INTEGER NOT NULL DEFAULT 0,
    combined_rating DECIMAL(3, 2),
    total_reviews INTEGER NOT NULL DEFAULT 0,

    -- Opening Hours
    opening_hours JSONB,
    is_open_24h BOOLEAN NOT NULL DEFAULT false,

    -- Metadata
    last_verified_at TIMESTAMP,
    is_recently_verified BOOLEAN NOT NULL DEFAULT false,
    last_sync_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for stations
CREATE INDEX idx_station_external_id ON stations(external_id);
CREATE INDEX idx_station_ocm_id ON stations(ocm_id);
CREATE INDEX idx_station_location ON stations(latitude, longitude);
CREATE INDEX idx_station_country ON stations(country);
CREATE INDEX idx_station_is_operational ON stations(is_operational);

-- Create favorites table
CREATE TABLE favorites (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    station_id BIGINT NOT NULL,
    notes VARCHAR(500),
    last_visited_at TIMESTAMP,
    visit_count INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_favorite_user FOREIGN KEY (user_id)
        REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_favorite_station FOREIGN KEY (station_id)
        REFERENCES stations(id) ON DELETE CASCADE,
    CONSTRAINT uk_user_station UNIQUE (user_id, station_id)
);

-- Create indexes for favorites
CREATE INDEX idx_favorite_user_id ON favorites(user_id);
CREATE INDEX idx_favorite_station_id ON favorites(station_id);

-- Comments
COMMENT ON TABLE stations IS 'Electric vehicle charging stations';
COMMENT ON COLUMN stations.external_id IS 'Unique identifier across external sources';
COMMENT ON COLUMN stations.connectors IS 'JSON array of available connectors';
COMMENT ON COLUMN stations.combined_rating IS 'Weighted average of OCM and Google ratings';
COMMENT ON TABLE favorites IS 'User favorite charging stations';
