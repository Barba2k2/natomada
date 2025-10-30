-- Seed data for cars catalog
-- Popular electric vehicles in Brazil

INSERT INTO cars (brand, model, battery_capacity, max_speed, fast_charging_power, connector, body_type, image_url, created_at, updated_at) VALUES
-- Tesla
('Tesla', 'Model 3', 60.00, 225, 250, 'CCS2', 'Sedan', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/tesla-model-3.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tesla', 'Model Y', 75.00, 217, 250, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/tesla-model-y.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tesla', 'Model S', 100.00, 322, 250, 'CCS2', 'Sedan', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/tesla-model-s.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- BYD
('BYD', 'Dolphin', 44.90, 150, 60, 'CCS2', 'Hatchback', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/byd-dolphin.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BYD', 'Dolphin Mini', 38.88, 130, 40, 'CCS2', 'Hatchback', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/byd-dolphin-mini.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BYD', 'Seal', 82.50, 180, 150, 'CCS2', 'Sedan', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/byd-seal.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BYD', 'Tang', 86.40, 180, 110, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/byd-tang.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Chevrolet
('Chevrolet', 'Bolt EV', 65.00, 145, 55, 'CCS2', 'Hatchback', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/chevrolet-bolt-ev.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Chevrolet', 'Bolt EUV', 65.00, 145, 55, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/chevrolet-bolt-euv.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Nissan
('Nissan', 'Leaf', 40.00, 144, 50, 'CHAdeMO', 'Hatchback', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/nissan-leaf.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Nissan', 'Ariya', 87.00, 160, 130, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/nissan-ariya.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Volkswagen
('Volkswagen', 'ID.4', 82.00, 160, 125, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/vw-id4.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Volkswagen', 'ID.3', 58.00, 160, 100, 'CCS2', 'Hatchback', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/vw-id3.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- BMW
('BMW', 'iX3', 80.00, 180, 150, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/bmw-ix3.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BMW', 'i4', 83.90, 190, 200, 'CCS2', 'Sedan', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/bmw-i4.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Mercedes-Benz
('Mercedes-Benz', 'EQA', 66.50, 160, 100, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/mercedes-eqa.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Mercedes-Benz', 'EQC', 80.00, 180, 110, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/mercedes-eqc.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Porsche
('Porsche', 'Taycan', 93.40, 260, 270, 'CCS2', 'Sedan', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/porsche-taycan.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Hyundai
('Hyundai', 'Ioniq 5', 72.60, 185, 350, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/hyundai-ioniq5.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Hyundai', 'Kona Electric', 64.00, 167, 100, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/hyundai-kona.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Kia
('Kia', 'EV6', 77.40, 185, 350, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/kia-ev6.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Kia', 'Niro EV', 64.80, 167, 100, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/kia-niro.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Volvo
('Volvo', 'XC40 Recharge', 78.00, 180, 150, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/volvo-xc40.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Volvo', 'C40 Recharge', 78.00, 180, 150, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/volvo-c40.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Audi
('Audi', 'e-tron', 95.00, 200, 150, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/audi-etron.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Audi', 'Q4 e-tron', 82.00, 180, 125, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/audi-q4-etron.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- JAC
('JAC', 'e-JS1', 30.20, 102, 40, 'Type 2', 'Hatchback', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/jac-ejs1.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('JAC', 'e-JS4', 58.00, 150, 60, 'CCS2', 'SUV', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/jac-ejs4.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Renault
('Renault', 'Zoe', 52.00, 135, 50, 'Type 2', 'Hatchback', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/renault-zoe.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Renault', 'Kwid E-Tech', 26.80, 105, 30, 'Type 2', 'Hatchback', 'https://natomada-images.s3.us-east-1.amazonaws.com/cars/renault-kwid.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Seed translations (pt_BR)
INSERT INTO car_translations (car_id, locale, brand_translated, model_translated, body_type_translated, created_at, updated_at)
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
    END,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM cars;

-- Add comments
COMMENT ON TABLE cars IS 'Electric vehicle catalog with S3 image URLs';
