CREATE TABLE search_history (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    search_query VARCHAR(500) NOT NULL,
    station_id BIGINT,
    station_name VARCHAR(255),
    station_address VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_search_history_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_search_history_station
        FOREIGN KEY (station_id)
        REFERENCES stations(id)
        ON DELETE SET NULL
);

CREATE INDEX idx_search_history_user_created ON search_history(user_id, created_at DESC);
CREATE INDEX idx_search_history_station ON search_history(station_id);
