-- Fix remaining Audi models that end with "e tron" (without space after)
-- These were missed by V25 because they don't have a space after "tron"

UPDATE cars
SET model = REPLACE(model, 'e tron', 'E-tron')
WHERE brand = 'Audi' AND model LIKE '%e tron' AND model NOT LIKE '%E-tron%';
