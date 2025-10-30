-- Create cars table
CREATE TABLE cars (
    id BIGSERIAL PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    battery_capacity DECIMAL(10, 2) NOT NULL,
    max_speed INTEGER NOT NULL,
    fast_charging_power INTEGER NOT NULL,
    connector VARCHAR(50) NOT NULL,
    body_type VARCHAR(50) NOT NULL,
    image_url VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for cars
CREATE INDEX idx_car_brand ON cars(brand);
CREATE INDEX idx_car_body_type ON cars(body_type);
CREATE INDEX idx_car_connector ON cars(connector);

-- Create car_translations table (i18n)
CREATE TABLE car_translations (
    id BIGSERIAL PRIMARY KEY,
    car_id BIGINT NOT NULL,
    locale VARCHAR(10) NOT NULL,
    brand_translated VARCHAR(100),
    model_translated VARCHAR(100),
    body_type_translated VARCHAR(50) NOT NULL,

    CONSTRAINT fk_car_translation_car FOREIGN KEY (car_id)
        REFERENCES cars(id) ON DELETE CASCADE,
    CONSTRAINT uk_car_locale UNIQUE (car_id, locale)
);

-- Create indexes for car_translations
CREATE INDEX idx_car_translation_locale ON car_translations(locale);
CREATE INDEX idx_car_translation_car_id ON car_translations(car_id);

-- Create user_vehicles table
CREATE TABLE user_vehicles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    car_id BIGINT NOT NULL,
    nickname VARCHAR(100),
    license_plate VARCHAR(20),
    color VARCHAR(50),
    is_primary BOOLEAN NOT NULL DEFAULT false,
    total_charges INTEGER NOT NULL DEFAULT 0,
    total_kwh_charged DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    last_charged_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_vehicle_user FOREIGN KEY (user_id)
        REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_vehicle_car FOREIGN KEY (car_id)
        REFERENCES cars(id) ON DELETE CASCADE,
    CONSTRAINT uk_user_car UNIQUE (user_id, car_id)
);

-- Create indexes for user_vehicles
CREATE INDEX idx_user_vehicle_user_id ON user_vehicles(user_id);
CREATE INDEX idx_user_vehicle_car_id ON user_vehicles(car_id);
CREATE INDEX idx_user_vehicle_is_primary ON user_vehicles(user_id, is_primary);

-- Comments
COMMENT ON TABLE cars IS 'Electric vehicle catalog';
COMMENT ON COLUMN cars.battery_capacity IS 'Battery capacity in kWh';
COMMENT ON COLUMN cars.max_speed IS 'Maximum speed in km/h';
COMMENT ON COLUMN cars.fast_charging_power IS 'Fast charging power in kW';
COMMENT ON COLUMN cars.image_url IS 'URL to car image (CDN, S3, etc)';

COMMENT ON TABLE car_translations IS 'Translations for car information (i18n)';
COMMENT ON COLUMN car_translations.locale IS 'Locale code (pt_BR, en_US, etc)';

COMMENT ON TABLE user_vehicles IS 'Vehicles owned by users';
COMMENT ON COLUMN user_vehicles.is_primary IS 'User primary/default vehicle';
COMMENT ON COLUMN user_vehicles.total_charges IS 'Total charging sessions for this vehicle';
COMMENT ON COLUMN user_vehicles.total_kwh_charged IS 'Total kWh charged for this vehicle';
