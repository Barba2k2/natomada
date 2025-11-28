-- Seed data for cars catalog
-- Popular electric vehicles in Brazil

INSERT INTO cars (brand, model, battery_capacity, max_speed, fast_charging_power, connector, body_type, image_url, created_at, updated_at) VALUES
-- Tesla
('Tesla', 'Model 3', 60.00, 225, 250, 'CCS2', 'Sedan', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/tesla-model-3.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tesla', 'Model Y', 75.00, 217, 250, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/tesla-model-y.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tesla', 'Model S', 100.00, 322, 250, 'CCS2', 'Sedan', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/tesla-model-s.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- BYD
('BYD', 'Dolphin', 44.90, 150, 60, 'CCS2', 'Hatchback', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/byd-dolphin.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BYD', 'Dolphin Mini', 38.88, 130, 40, 'CCS2', 'Hatchback', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/byd-dolphin-mini.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BYD', 'Seal', 82.50, 180, 150, 'CCS2', 'Sedan', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/byd-seal.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BYD', 'Tang', 86.40, 180, 110, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/byd-tang.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Chevrolet
('Chevrolet', 'Bolt EV', 65.00, 145, 55, 'CCS2', 'Hatchback', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/chevrolet-bolt-ev.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Chevrolet', 'Bolt EUV', 65.00, 145, 55, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/chevrolet-bolt-euv.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Nissan
('Nissan', 'Leaf', 40.00, 144, 50, 'CHAdeMO', 'Hatchback', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/nissan-leaf.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Nissan', 'Ariya', 87.00, 160, 130, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/nissan-ariya.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Volkswagen
('Volkswagen', 'ID.4', 82.00, 160, 125, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/vw-id4.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Volkswagen', 'ID.3', 58.00, 160, 100, 'CCS2', 'Hatchback', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/vw-id3.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- BMW
('BMW', 'iX3', 80.00, 180, 150, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/bmw-ix3.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BMW', 'i4', 83.90, 190, 200, 'CCS2', 'Sedan', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/bmw-i4.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Mercedes-Benz
('Mercedes-Benz', 'EQA', 66.50, 160, 100, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/mercedes-eqa.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Mercedes-Benz', 'EQC', 80.00, 180, 110, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/mercedes-eqc.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Porsche
('Porsche', 'Taycan', 93.40, 260, 270, 'CCS2', 'Sedan', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/porsche-taycan.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Hyundai
('Hyundai', 'Ioniq 5', 72.60, 185, 350, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/hyundai-ioniq5.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Hyundai', 'Kona Electric', 64.00, 167, 100, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/hyundai-kona.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Kia
('Kia', 'EV6', 77.40, 185, 350, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/kia-ev6.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Kia', 'Niro EV', 64.80, 167, 100, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/kia-niro.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Volvo
('Volvo', 'XC40 Recharge', 78.00, 180, 150, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/volvo-xc40.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Volvo', 'C40 Recharge', 78.00, 180, 150, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/volvo-c40.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Audi
('Audi', 'e-tron', 95.00, 200, 150, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/audi-etron.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Audi', 'Q4 e-tron', 82.00, 180, 125, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/audi-q4-etron.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- JAC
('JAC', 'e-JS1', 30.20, 102, 40, 'Type 2', 'Hatchback', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/jac-ejs1.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('JAC', 'e-JS4', 58.00, 150, 60, 'CCS2', 'SUV', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/jac-ejs4.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Renault
('Renault', 'Zoe', 52.00, 135, 50, 'Type 2', 'Hatchback', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/renault-zoe.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Renault', 'Kwid E-Tech', 26.80, 105, 30, 'Type 2', 'Hatchback', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cars/renault-kwid.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Seed translations (pt_BR)
INSERT INTO car_translations (car_id, locale, brand_translated, model_translated, body_type_translated)
SELECT
    id,
    'pt_BR',
    brand,
    model,
    CASE body_type
        WHEN 'Sedan' THEN 'Sedã'
        WHEN 'Hatchback' THEN 'Hatchback'
        WHEN 'SUV' THEN 'SUV'
        ELSE body_type
    END
FROM cars;

-- Add comments
COMMENT ON TABLE cars IS 'Electric vehicle catalog with S3 image URLs';
