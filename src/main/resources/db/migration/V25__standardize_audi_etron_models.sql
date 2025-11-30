-- Standardize all Audi e-tron models to use "E-tron" (capital E with hyphen)
-- Fixes inconsistencies: "e tron", "e-tron" -> "E-tron"
-- Maintains suffixes separated: "e tronI" -> "E-tron I", "e tronGE" -> "E-tron GE"

-- Replace "e tronI" with "E-tron I" (with space before I)
UPDATE cars
SET model = REPLACE(model, 'e tronI', 'E-tron I')
WHERE brand = 'Audi' AND model LIKE '%e tronI%';

-- Replace "e tronGE" with "E-tron GE" (with space before GE)
UPDATE cars
SET model = REPLACE(model, 'e tronGE', 'E-tron GE')
WHERE brand = 'Audi' AND model LIKE '%e tronGE%';

-- Replace "e tronFW" with "E-tron FW" (with space before FW)
UPDATE cars
SET model = REPLACE(model, 'e tronFW', 'E-tron FW')
WHERE brand = 'Audi' AND model LIKE '%e tronFW%';

-- Replace "e tron " (space after) with "E-tron "
UPDATE cars
SET model = REPLACE(model, 'e tron ', 'E-tron ')
WHERE brand = 'Audi' AND model LIKE '%e tron %';

-- Replace "e-tron" (lowercase e) with "E-tron" (for models that already have hyphen)
UPDATE cars
SET model = REPLACE(model, 'e-tron', 'E-tron')
WHERE brand = 'Audi' AND model LIKE '%e-tron%';
