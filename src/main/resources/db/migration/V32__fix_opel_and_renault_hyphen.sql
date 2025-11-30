-- Fix Opel and Renault model naming
-- Delete Opel duplicates
-- Add hyphen to Renault E Tech models (change "E Tech" to "E-Tech")

-- ============================================================================
-- Opel: Delete duplicates (3 deletions)
-- ============================================================================

DELETE FROM cars WHERE brand = 'Opel' AND model = 'ComboLife E';
DELETE FROM cars WHERE brand = 'Opel' AND model = 'Combo VanE';
DELETE FROM cars WHERE brand = 'Opel' AND model = 'CorsaF';

-- ============================================================================
-- Renault: Add hyphen to E Tech models (5 updates)
-- Change "E Tech" to "E-Tech"
-- ============================================================================

-- Megane models (3 updates)
UPDATE cars SET model = 'Megane E-Tech EV40 130hp' WHERE brand = 'Renault' AND model = 'Megane E Tech EV40 130hp';
UPDATE cars SET model = 'Megane E-Tech EV60 130hp' WHERE brand = 'Renault' AND model = 'Megane E Tech EV60 130hp';
UPDATE cars SET model = 'Megane E-Tech EV60 220hp' WHERE brand = 'Renault' AND model = 'Megane E Tech EV60 220hp';

-- Scenic models (2 updates)
UPDATE cars SET model = 'Scenic E-Tech EV60 170hp' WHERE brand = 'Renault' AND model = 'Scenic E Tech EV60 170hp';
UPDATE cars SET model = 'Scenic E-Tech EV87 220hp' WHERE brand = 'Renault' AND model = 'Scenic E Tech EV87 220hp';

-- Total: 3 deletions (Opel) + 5 updates (Renault)
