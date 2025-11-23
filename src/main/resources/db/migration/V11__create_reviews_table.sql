-- Create reviews table
CREATE TABLE IF NOT EXISTS reviews (
    id BIGSERIAL PRIMARY KEY,
    station_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment VARCHAR(1000),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign keys
    CONSTRAINT fk_review_station FOREIGN KEY (station_id)
        REFERENCES stations(id) ON DELETE CASCADE,
    CONSTRAINT fk_review_user FOREIGN KEY (user_id)
        REFERENCES users(id) ON DELETE CASCADE,

    -- Unique constraint: one review per user per station
    CONSTRAINT uk_review_station_user UNIQUE (station_id, user_id)
);

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_review_station_id ON reviews(station_id);
CREATE INDEX IF NOT EXISTS idx_review_user_id ON reviews(user_id);
CREATE INDEX IF NOT EXISTS idx_review_created_at ON reviews(created_at);

-- Add comment
COMMENT ON TABLE reviews IS 'User reviews for charging stations';
COMMENT ON COLUMN reviews.rating IS 'Rating from 1 to 5 stars';
COMMENT ON COLUMN reviews.comment IS 'Optional text review (max 1000 characters)';
