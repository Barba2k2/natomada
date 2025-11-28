-- V21: Allow NULL values in cars table columns
-- Some electric vehicles don't have all specifications available

-- Allow NULL for fast_charging_power (some vehicles don't have fast charging data)
ALTER TABLE cars ALTER COLUMN fast_charging_power DROP NOT NULL;

-- Allow NULL for other optional columns
ALTER TABLE cars ALTER COLUMN max_speed DROP NOT NULL;
ALTER TABLE cars ALTER COLUMN battery_capacity DROP NOT NULL;
ALTER TABLE cars ALTER COLUMN connector DROP NOT NULL;
ALTER TABLE cars ALTER COLUMN body_type DROP NOT NULL;
ALTER TABLE cars ALTER COLUMN drivetrain DROP NOT NULL;
