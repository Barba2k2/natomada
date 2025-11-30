-- Fix Kia EV9 GT Line model with incorrect kWh format
-- The model "EV9 998 kWh AWD GT Line" should not exist
-- The correct model is "EV9 99.8 kWh AWD GT-Line" (with decimal and hyphen)

DELETE FROM cars
WHERE brand = 'Kia'
  AND model = 'EV9 998 kWh AWD GT Line';
