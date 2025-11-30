-- Fix Ford Puma Gen E model naming inconsistency
-- Keep: "Puma Gen-E" (with hyphen)
-- Delete: "Puma Gen E" (with space)

DELETE FROM cars
WHERE brand = 'Ford'
  AND model = 'Puma Gen E';
