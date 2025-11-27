-- Migration: Add gender and remove bio from users table
-- Version: V15

-- Add gender column
ALTER TABLE users ADD COLUMN IF NOT EXISTS gender VARCHAR(10);

-- Drop bio column
ALTER TABLE users DROP COLUMN IF EXISTS bio;
