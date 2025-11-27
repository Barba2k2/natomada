-- Add is_premium field to users table
ALTER TABLE users
ADD COLUMN IF NOT EXISTS is_premium BOOLEAN NOT NULL DEFAULT FALSE;

-- Create subscriptions table
CREATE TABLE IF NOT EXISTS subscriptions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    product_id VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'NONE',
    platform VARCHAR(20), -- 'ios' or 'android'
    original_transaction_id VARCHAR(255),
    latest_receipt_data TEXT,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP,
    auto_renewing BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_user_subscription UNIQUE (user_id),
    CONSTRAINT valid_status CHECK (status IN ('NONE', 'ACTIVE', 'EXPIRED', 'GRACE_PERIOD', 'CANCELLED')),
    CONSTRAINT valid_platform CHECK (platform IS NULL OR platform IN ('ios', 'android'))
);

-- Create index for faster lookups
CREATE INDEX IF NOT EXISTS idx_subscriptions_user_id ON subscriptions(user_id);
CREATE INDEX IF NOT EXISTS idx_subscriptions_status ON subscriptions(status);
CREATE INDEX IF NOT EXISTS idx_subscriptions_end_date ON subscriptions(end_date);

-- Create trigger to update is_premium when subscription changes
CREATE OR REPLACE FUNCTION update_user_premium_status()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE users
    SET is_premium = (
        NEW.status IN ('ACTIVE', 'GRACE_PERIOD', 'CANCELLED')
        AND (NEW.end_date IS NULL OR NEW.end_date > CURRENT_TIMESTAMP)
    )
    WHERE id = NEW.user_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_user_premium_status
AFTER INSERT OR UPDATE ON subscriptions
FOR EACH ROW
EXECUTE FUNCTION update_user_premium_status();
