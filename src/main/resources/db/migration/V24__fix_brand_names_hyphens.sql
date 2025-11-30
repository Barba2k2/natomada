-- Fix brand names: Only Mercedes-Benz should have hyphen
-- Rolls-Royce should be Rolls Royce (without hyphen)
-- Mercedes Benz should be Mercedes-Benz (with hyphen)

-- Fix Rolls-Royce (remove hyphen)
UPDATE cars
SET brand = 'Rolls Royce'
WHERE brand = 'Rolls-Royce';

-- Fix Mercedes Benz (add hyphen)
UPDATE cars
SET brand = 'Mercedes-Benz'
WHERE brand = 'Mercedes Benz';
