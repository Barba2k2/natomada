-- Remove duplicate car models with naming inconsistencies
-- Total: 54 duplicate models to delete

-- ============================================================================
-- SECTION 1: Remove models with kWh without decimal point (18 deletions)
-- Keep: XX.X kWh (correct)
-- Delete: XXX kWh (incorrect - missing decimal point)
-- ============================================================================

-- BYD DOLPHIN models (3 deletions)
DELETE FROM cars WHERE brand = 'BYD' AND model = 'DOLPHIN 449 kWh Active';
DELETE FROM cars WHERE brand = 'BYD' AND model = 'DOLPHIN 449 kWh Boost';
DELETE FROM cars WHERE brand = 'BYD' AND model = 'DOLPHIN 604 kWh';

-- BYD DOLPHIN SURF models (2 deletions)
DELETE FROM cars WHERE brand = 'BYD' AND model = 'DOLPHIN SURF 432 kWh Boost';
DELETE FROM cars WHERE brand = 'BYD' AND model = 'DOLPHIN SURF 432 kWh Comfort';

-- BYD SEAL models (3 deletions)
DELETE FROM cars WHERE brand = 'BYD' AND model = 'SEAL 614 kWh RWD Comfort';
DELETE FROM cars WHERE brand = 'BYD' AND model = 'SEAL 825 kWh AWD Excellence';
DELETE FROM cars WHERE brand = 'BYD' AND model = 'SEAL 825 kWh RWD Design';

-- BYD SEAL U models (1 deletion)
DELETE FROM cars WHERE brand = 'BYD' AND model = 'SEAL U 718 kWh Comfort';

-- BYD SEALION 7 models (3 deletions)
DELETE FROM cars WHERE brand = 'BYD' AND model = 'SEALION 7 825 kWh AWD Design';
DELETE FROM cars WHERE brand = 'BYD' AND model = 'SEALION 7 825 kWh RWD Comfort';
DELETE FROM cars WHERE brand = 'BYD' AND model = 'SEALION 7 913 kWh AWD Excellence';

-- Dongfeng models (2 deletions)
DELETE FROM cars WHERE brand = 'Dongfeng' AND model = 'Box 314 kWh';
DELETE FROM cars WHERE brand = 'Dongfeng' AND model = 'Box 423 kWh';

-- Kia EV9 models (4 deletions)
DELETE FROM cars WHERE brand = 'Kia' AND model = 'EV9 761 kWh RWD';
DELETE FROM cars WHERE brand = 'Kia' AND model = 'EV9 998 kWh AWD';
DELETE FROM cars WHERE brand = 'Kia' AND model = 'EV9 998 kWh AWD GT';
DELETE FROM cars WHERE brand = 'Kia' AND model = 'EV9 998 kWh RWD';

-- ============================================================================
-- SECTION 2: Remove models with "e XXX" (space) - Keep "e-XXX" (hyphen)
-- Total: 34 deletions
-- ============================================================================

-- Citroen models (10 deletions)
DELETE FROM cars WHERE brand = 'Citroen' AND model = 'e Berlingo M 50 kWh';
DELETE FROM cars WHERE brand = 'Citroen' AND model = 'e Berlingo XL 50 kWh';
DELETE FROM cars WHERE brand = 'Citroen' AND model = 'e C4';
DELETE FROM cars WHERE brand = 'Citroen' AND model = 'e C4 54 kWh';
DELETE FROM cars WHERE brand = 'Citroen' AND model = 'e C4 X';
DELETE FROM cars WHERE brand = 'Citroen' AND model = 'e C4 X 54 kWh';
DELETE FROM cars WHERE brand = 'Citroen' AND model = 'e SpaceTourer M 50 kWh';
DELETE FROM cars WHERE brand = 'Citroen' AND model = 'e SpaceTourer M 75 kWh';
DELETE FROM cars WHERE brand = 'Citroen' AND model = 'e SpaceTourer XL 50 kWh';
DELETE FROM cars WHERE brand = 'Citroen' AND model = 'e SpaceTourer XL 75 kWh';

-- Ford models (5 deletions)
DELETE FROM cars WHERE brand = 'Ford' AND model = 'e Tourneo Courier';
DELETE FROM cars WHERE brand = 'Ford' AND model = 'e Tourneo Custom L1 160 kW';
DELETE FROM cars WHERE brand = 'Ford' AND model = 'e Tourneo Custom L1 210 kW';
DELETE FROM cars WHERE brand = 'Ford' AND model = 'e Tourneo Custom L2 160 kW';
DELETE FROM cars WHERE brand = 'Ford' AND model = 'e Tourneo Custom L2 210 kW';

-- Peugeot models (19 deletions)
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 2008 50 kWh';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 2008 54 kWh';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 208 50 kWh';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 208 51 kWh';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 3008 73 kWh';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 3008 73 kWh Dual Motor';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 3008 97 kWh Long Range';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 308';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 308 SW';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 408 58 kWh';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 5008 73 kWh';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 5008 73 kWh Dual Motor';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e 5008 97 kWh Long Range';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e Rifter M 50 kWh';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e Rifter XL 50 kWh';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e Traveller L2 50 kWh';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e Traveller L2 75 kWh';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e Traveller L3 50 kWh';
DELETE FROM cars WHERE brand = 'Peugeot' AND model = 'e Traveller L3 75 kWh';

-- ============================================================================
-- SECTION 3: Remove models with "XXX e" (space) - Keep "XXX-e" (hyphen)
-- Total: 2 deletions
-- ============================================================================

-- Opel models (2 deletions)
DELETE FROM cars WHERE brand = 'Opel' AND model = 'Combo e Life 50 kWh';
DELETE FROM cars WHERE brand = 'Opel' AND model = 'Combo e Life XL 50 kWh';

-- Total deletions: 54
