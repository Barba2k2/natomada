-- Fix Hongqi E-HS9 models naming inconsistency
-- Keep: "E-HS9" (with hyphen)
-- Delete: "E HS9" (with space)

-- Delete duplicates with space
DELETE FROM cars WHERE brand = 'Hongqi' AND model = 'E HS9';
DELETE FROM cars WHERE brand = 'Hongqi' AND model = 'E HS9 84 kWh';
DELETE FROM cars WHERE brand = 'Hongqi' AND model = 'E HS9 99 kWh';
DELETE FROM cars WHERE brand = 'Hongqi' AND model = 'E HS9 120 kWh';
