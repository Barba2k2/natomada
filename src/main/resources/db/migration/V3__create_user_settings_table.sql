-- Create user_settings table
CREATE TABLE user_settings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,

    -- General Settings
    language VARCHAR(10) NOT NULL DEFAULT 'pt_BR',
    distance_unit VARCHAR(10) NOT NULL DEFAULT 'km',
    energy_unit VARCHAR(10) NOT NULL DEFAULT 'kwh',
    temperature_unit VARCHAR(10) NOT NULL DEFAULT 'celsius',
    theme VARCHAR(20) NOT NULL DEFAULT 'light',
    map_type VARCHAR(20) NOT NULL DEFAULT 'standard',

    -- Notification Settings
    push_enabled BOOLEAN NOT NULL DEFAULT true,
    email_enabled BOOLEAN NOT NULL DEFAULT true,
    charging_complete BOOLEAN NOT NULL DEFAULT true,
    charging_status_updates BOOLEAN NOT NULL DEFAULT true,
    promotional BOOLEAN NOT NULL DEFAULT false,

    -- Privacy Settings
    share_location BOOLEAN NOT NULL DEFAULT true,
    profile_public BOOLEAN NOT NULL DEFAULT true,
    show_charging_history BOOLEAN NOT NULL DEFAULT true,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_settings_user FOREIGN KEY (user_id)
        REFERENCES users(id) ON DELETE CASCADE
);

-- Create index
CREATE INDEX idx_user_settings_user_id ON user_settings(user_id);

-- Comments
COMMENT ON TABLE user_settings IS 'User preferences and settings';
COMMENT ON COLUMN user_settings.language IS 'User interface language (e.g., pt_BR, en_US)';
COMMENT ON COLUMN user_settings.theme IS 'UI theme (light, dark, auto)';
