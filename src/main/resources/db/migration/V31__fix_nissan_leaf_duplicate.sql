-- Fix Nissan Leaf duplicate model
-- Keep: "Leaf"
-- Delete: "LeafI" (incorrect duplicate)

DELETE FROM cars
WHERE brand = 'Nissan'
  AND model = 'LeafI';
