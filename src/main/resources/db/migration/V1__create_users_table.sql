-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(500),
    bio TEXT,
    total_charges INTEGER NOT NULL DEFAULT 0,
    total_kwh_charged DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    total_stations_visited INTEGER NOT NULL DEFAULT 0,
    email_verified_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_user_phone ON users(phone);

-- Comments
COMMENT ON TABLE users IS 'User accounts table';
COMMENT ON COLUMN users.total_charges IS 'Total number of charging sessions';
COMMENT ON COLUMN users.total_kwh_charged IS 'Total kWh charged across all sessions';
COMMENT ON COLUMN users.total_stations_visited IS 'Total unique charging stations visited';
