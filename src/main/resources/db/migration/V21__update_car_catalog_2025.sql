-- Migration: Update car catalog with 2025 data (1125 vehicles)
-- Source: electric_vehicles_spec_2025_updated.csv
-- Generated: 2025-11-28

-- Clear existing car data
TRUNCATE TABLE car_translations CASCADE;
TRUNCATE TABLE cars CASCADE;

-- Insert updated car catalog
INSERT INTO cars (
    brand, model, battery_capacity, max_speed, fast_charging_power,
    connector, body_type, drivetrain, image_url,
    created_at, updated_at
) VALUES
(
    'Abarth', '500e Convertible', 37.8, 155, 67,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/abarth_500e-convertible.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Abarth', '500e Hatchback', 37.8, 155, 67,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/abarth_500e-hatchback.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Abarth', '500e Scorpionissima', 37.8, 155, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/abarth_500e-scorpionissima.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Abarth', '600e Scorpionissima', 50.8, 200, 79,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/abarth_600e-scorpionissima.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Abarth', '600e Turismo', 50.8, 200, 79,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/abarth_600e-turismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Aiways', 'U5', 60, 150, 78,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/aiways_u5.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Aiways', 'U6', 60, 160, 78,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/aiways_u6.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Alfa Romeo', 'Romeo Junior Elettrica 54 kWh', 50.8, 150, 85,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/alfa-romeo_romeo-junior-elettrica-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Alfa Romeo', 'Romeo Junior Elettrica 54 kWh Veloce', 50.8, 200, 85,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/alfa-romeo_romeo-junior-elettrica-54-kwh-veloce.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Alpine', 'A290 Electric 180 hp', 52, 160, 70,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/alpine_a290-electric-180-hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Alpine', 'A290 Electric 220 hp', 52, 170, 70,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/alpine_a290-electric-220-hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Alpine', 'A390 GT', 89, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/alpine_a390-gt.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Alpine', 'A390 GTS', 89, 220, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/alpine_a390-gts.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 Avant e tron', 75.8, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-avant-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 Avant e tron performance', 94.9, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-avant-e-tron-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 Avant e tron quattro', 94.9, 210, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-avant-e-tron-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 Avant e-tron', 75.8, 210, 150,
    'CCS', 'Station/Estate', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-avant-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 Avant e-tron performance', 94.9, 210, 200,
    'CCS', 'Station/Estate', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-avant-e-tron-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 Avant e-tron quattro', 94.9, 210, 200,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-avant-e-tron-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 e tronI', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-e-troni.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 Sportback e tron', 75.8, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-sportback-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 Sportback e tron performance', 94.9, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-sportback-e-tron-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 Sportback e tron quattro', 94.9, 210, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-sportback-e-tron-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 Sportback e-tron', 75.8, 210, 150,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-sportback-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 Sportback e-tron performance', 94.9, 210, 200,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-sportback-e-tron-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'A6 Sportback e-tron quattro', 94.9, 210, 200,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_a6-sportback-e-tron-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e tron 50 quattro', 64.7, 190, 120,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-50-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e tron 55 quattro', 86.5, 200, 155,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-55-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e tron GT quattro', 85, 245, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-gt-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e tron GT RS', 85, 250, 268,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-gt-rs.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e tron GT RS performance', 97, 250, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-gt-rs-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e tron GT S', 97, 245, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-gt-s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e tron GTFW', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-gtfw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e tron S', 86.5, 210, 155,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e tron S Sportback', 86.5, 210, 155,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-s-sportback.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e tron Sportback 50 quattro', 64.7, 190, 120,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-sportback-50-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e tron Sportback 55 quattro', 86.5, 200, 155,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-sportback-55-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e tronGE', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tronge.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e-tron GT quattro', 85, 245, 281,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-gt-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e-tron GT RS', 85, 250, 281,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-gt-rs.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e-tron GT RS performance', 97, 250, 281,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-gt-rs-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'e-tron GT S', 97, 245, 281,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_e-tron-gt-s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 e tron 35', 52, 160, 118,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-e-tron-35.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 e tron 40', 76.6, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-e-tron-40.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 e tron 45', 77, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-e-tron-45.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 e tron 45 quattro', 77, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-e-tron-45-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 e tron 50 quattro', 76.6, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-e-tron-50-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 e tron 55 quattro', 77, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-e-tron-55-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 e tronI', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-e-troni.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 e-tron 40', 76.6, 160, 110,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-e-tron-40.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 e-tron 45', 77, 180, 120,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-e-tron-45.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 e-tron 45 quattro', 77, 180, 120,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-e-tron-45-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 e-tron 55 quattro', 77, 180, 120,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-e-tron-55-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 Sportback e tron 35', 52, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-sportback-e-tron-35.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 Sportback e tron 40', 76.6, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-sportback-e-tron-40.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 Sportback e tron 45', 77, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-sportback-e-tron-45.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 Sportback e tron 45 quattro', 77, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-sportback-e-tron-45-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 Sportback e tron 50 quattro', 76.6, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-sportback-e-tron-50-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 Sportback e tron 55 quattro', 77, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-sportback-e-tron-55-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 Sportback e-tron 40', 76.6, 160, 110,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-sportback-e-tron-40.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 Sportback e-tron 45', 77, 180, 120,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-sportback-e-tron-45.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 Sportback e-tron 45 quattro', 77, 180, 120,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-sportback-e-tron-45-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q4 Sportback e-tron 55 quattro', 77, 180, 120,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q4-sportback-e-tron-55-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 e tronI', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-e-troni.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 e-tron', 94.9, 210, 150,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 e-tron performance', 94.9, 210, 190,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-e-tron-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 e-tron quattro', 94.9, 210, 200,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-e-tron-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 e-tron Sportback', 94.9, 210, 150,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-e-tron-sportback.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 e-tron Sportback performance', 94.9, 210, 190,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-e-tron-sportback-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 e-tron Sportback quattro', 94.9, 210, 200,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-e-tron-sportback-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 Sportback e tron', 83.0, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-sportback-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 Sportback e tron performance', 100.0, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-sportback-e-tron-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 Sportback e tron quattro', 100.0, 210, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-sportback-e-tron-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 SUV e tron', 83.0, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-suv-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 SUV e tron performance', 100.0, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-suv-e-tron-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q6 SUV e tron quattro', 100.0, 210, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q6-suv-e-tron-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q8 e tronI GE', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q8-e-troni-ge.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q8 Sportback e tron 50 quattro', 89, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q8-sportback-e-tron-50-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q8 Sportback e tron 55 quattro', 106, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q8-sportback-e-tron-55-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q8 SUV e tron 50 quattro', 89, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q8-suv-e-tron-50-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'Q8 SUV e tron 55 quattro', 106, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_q8-suv-e-tron-55-quattro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'S6 Avant e tron', 94.9, 240, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_s6-avant-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'S6 Avant e-tron', 94.9, 240, 200,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_s6-avant-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'S6 Sportback e tron', 94.9, 240, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_s6-sportback-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'S6 Sportback e-tron', 94.9, 240, 200,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_s6-sportback-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'SQ6 e-tron', 94.9, 230, 200,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_sq6-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'SQ6 e-tron Sportback', 94.9, 230, 200,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_sq6-e-tron-sportback.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'SQ6 Sportback e tron', 94.9, 230, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_sq6-sportback-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'SQ6 SUV e tron', 94.9, 230, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_sq6-suv-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'SQ8 Sportback e tron', 106, 210, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_sq8-sportback-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Audi', 'SQ8 SUV e tron', 106, 210, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/audi_sq8-suv-e-tron.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', '4G22 G23', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_4g22-g23.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', '5G60 G61 G90', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_5g60-g61-g90.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i3', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i3.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i3 120 Ah', 37.9, 150, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i3-120-ah.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i3 60 Ah', 18.8, 150, 47,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i3-60-ah.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i3 94 Ah', 27.2, 150, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i3-94-ah.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i3s 120 Ah', 37.9, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i3s-120-ah.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i3s 94 Ah', 27.2, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i3s-94-ah.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i4 eDrive35', 67.1, 190, 95,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i4-edrive35.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i4 eDrive40', 81.3, 190, 131,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i4-edrive40.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i4 M50', 81.3, 225, 131,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i4-m50.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i4 M60 xDrive', 81.1, 225, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i4-m60-xdrive.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i4 xDrive40', 81.3, 200, 131,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i4-xdrive40.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i5 eDrive40 Sedan', 81.2, 193, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i5-edrive40-sedan.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i5 eDrive40 Sedan (MY25)', 81.2, 193, 136,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i5-edrive40-sedan-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i5 eDrive40 Touring', 81.2, 193, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i5-edrive40-touring.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i5 eDrive40 Touring (MY25)', 81.2, 193, 136,
    'CCS', 'Station/Estate', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i5-edrive40-touring-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i5 M60 xDrive Sedan', 81.2, 230, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i5-m60-xdrive-sedan.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i5 M60 xDrive Sedan (MY25)', 81.2, 230, 136,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i5-m60-xdrive-sedan-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i5 M60 xDrive Touring', 81.2, 230, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i5-m60-xdrive-touring.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i5 M60 xDrive Touring (MY25)', 81.2, 230, 136,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i5-m60-xdrive-touring-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i5 xDrive40 Sedan', 81.2, 215, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i5-xdrive40-sedan.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i5 xDrive40 Sedan (MY25)', 81.2, 215, 136,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i5-xdrive40-sedan-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i5 xDrive40 Touring', 81.2, 215, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i5-xdrive40-touring.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i5 xDrive40 Touring (MY25)', 81.2, 215, 136,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i5-xdrive40-touring-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i7 eDrive50', 101.7, 205, 159,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i7-edrive50.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i7 M70 xDrive', 101.7, 250, 159,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i7-m70-xdrive.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'i7 xDrive60', 101.7, 240, 159,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_i7-xdrive60.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'iX M60', 105.2, 250, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_ix-m60.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'iX M70 xDrive', 108.9, 250, 150,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_ix-m70-xdrive.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'iX xDrive40', 71, 200, 148,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_ix-xdrive40.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'iX xDrive45', 94.8, 200, 115,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_ix-xdrive45.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'iX xDrive50', 105.2, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_ix-xdrive50.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'iX xDrive60', 109.1, 200, 150,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_ix-xdrive60.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'iX1 eDrive20', 64.7, 170, 94,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_ix1-edrive20.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'iX1 xDrive30', 64.7, 180, 94,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_ix1-xdrive30.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'iX2 eDrive20', 64.7, 170, 94,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_ix2-edrive20.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'iX2 xDrive30', 64.7, 180, 94,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_ix2-xdrive30.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'iX3', 74, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_ix3.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'iX3 50 xDrive', 108.7, 210, 400,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_ix3-50-xdrive.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'X3', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_x3.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BMW', 'X3G01 G08', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/bmw_x3g01-g08.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'ATTO 2', 45.1, 160, 51,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_atto-2.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'ATTO 2 648 kWh', 58, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_atto-2-648-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'ATTO 3', 60.5, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_atto-3.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'ATTO 3 (MY25)', 60.5, 160, 75,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_atto-3-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'DOLPHIN 44.9 kWh Active', 44.9, 150, 50,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_dolphin-44.9-kwh-active.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'DOLPHIN 44.9 kWh Boost', 44.9, 160, 50,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_dolphin-44.9-kwh-boost.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'DOLPHIN 449 kWh Active', 44.9, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_dolphin-449-kwh-active.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'DOLPHIN 449 kWh Boost', 44.9, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_dolphin-449-kwh-boost.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'DOLPHIN 60.4 kWh', 60.5, 160, 65,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_dolphin-60.4-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'DOLPHIN 604 kWh', 60.5, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_dolphin-604-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'DOLPHIN SURF 30 kWh Active', 30, 150, 45,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_dolphin-surf-30-kwh-active.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'DOLPHIN SURF 43.2 kWh Boost', 43.2, 150, 60,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_dolphin-surf-43.2-kwh-boost.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'DOLPHIN SURF 43.2 kWh Comfort', 43.2, 150, 60,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_dolphin-surf-43.2-kwh-comfort.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'DOLPHIN SURF 432 kWh Boost', 43.2, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_dolphin-surf-432-kwh-boost.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'DOLPHIN SURF 432 kWh Comfort', 43.2, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_dolphin-surf-432-kwh-comfort.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'ETP3', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_etp3.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'HAN', 85.4, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_han.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEAL 61.4 kWh RWD Comfort', 61.4, 220, 75,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_seal-61.4-kwh-rwd-comfort.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEAL 614 kWh RWD Comfort', 61.4, 220, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_seal-614-kwh-rwd-comfort.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEAL 82.5 kWh AWD Excellence', 82.5, 180, 100,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_seal-82.5-kwh-awd-excellence.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEAL 82.5 kWh RWD Design', 82.5, 180, 100,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_seal-82.5-kwh-rwd-design.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEAL 825 kWh AWD Excellence', 82.5, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_seal-825-kwh-awd-excellence.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEAL 825 kWh RWD Design', 82.5, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_seal-825-kwh-rwd-design.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEAL U 71.8 kWh Comfort', 71.8, 175, 72,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_seal-u-71.8-kwh-comfort.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEAL U 718 kWh Comfort', 71.8, 175, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_seal-u-718-kwh-comfort.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEAL U 87 kWh Design', 87, 175, 85,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_seal-u-87-kwh-design.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEALION 7 82.5 kWh AWD Design', 82.5, 215, 115,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_sealion-7-82.5-kwh-awd-design.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEALION 7 82.5 kWh RWD Comfort', 82.5, 215, 115,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_sealion-7-82.5-kwh-rwd-comfort.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEALION 7 825 kWh AWD Design', 82.5, 215, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_sealion-7-825-kwh-awd-design.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEALION 7 825 kWh RWD Comfort', 82.5, 215, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_sealion-7-825-kwh-rwd-comfort.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEALION 7 91.3 kWh AWD Excellence', 91.3, 215, 165,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_sealion-7-91.3-kwh-awd-excellence.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'SEALION 7 913 kWh AWD Excellence', 91.3, 215, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_sealion-7-913-kwh-awd-excellence.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'TANG', 86.4, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_tang.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'BYD', 'TANG Flagship', 108.8, 190, 100,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/byd_tang-flagship.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Cadillac', 'Lyriq 600 E4', 102, 210, 165,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cadillac_lyriq-600-e4.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Cadillac', 'LYRIQ V', 91, 230, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cadillac_lyriq-v.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Cadillac', 'OPTIQ 500 E4', 75, 184, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cadillac_optiq-500-e4.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Cadillac', 'VISTIQ 900 E4', 91, 210, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cadillac_vistiq-900-e4.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Changan', 'Deepal S05 4WD Max', 68, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/changan_deepal-s05-4wd-max.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Changan', 'Deepal S05 RWD', 68, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/changan_deepal-s05-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Changan', 'Deepal S07 Standard', 78, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/changan_deepal-s07-standard.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'BerlingoVAN', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_berlingovan.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'C ZERO', 14.5, 130, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_c-zero.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'C3 VAN', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_c3-van.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'C5 Aircross', 75, 201, 250,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_c5-aircross.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'C5 Aircross Comfort Range', 73.7, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_c5-aircross-comfort-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'C5 Aircross Long Range', 96.9, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_c5-aircross-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e Berlingo M 50 kWh', 46.3, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-berlingo-m-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e Berlingo Multispace', 20.5, 110, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-berlingo-multispace.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e Berlingo XL 50 kWh', 50, 132, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-berlingo-xl-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e C3 Aircross Comfort Range 44 kWh', 43.8, 143, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c3-aircross-comfort-range-44-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e C3 Aircross Extended Range 54 kWh', 53.5, 143, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c3-aircross-extended-range-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e C3 Comfort Range 44 kWh', 43.8, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c3-comfort-range-44-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e C3 Urban Range 30 kWh', 29.8, 125, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c3-urban-range-30-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e C4', 46.3, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c4.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e C4 54 kWh', 50.8, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c4-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e C4 X', 46.3, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c4-x.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e C4 X 54 kWh', 50.8, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c4-x-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e Jumpy Combi M 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-jumpy-combi-m-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e Jumpy Combi M 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-jumpy-combi-m-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e Jumpy Combi XL 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-jumpy-combi-xl-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e Jumpy Combi XL 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-jumpy-combi-xl-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e Jumpy Combi XS 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-jumpy-combi-xs-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e SpaceTourer M 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-spacetourer-m-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e SpaceTourer M 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-spacetourer-m-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e SpaceTourer XL 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-spacetourer-xl-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e SpaceTourer XL 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-spacetourer-xl-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e SpaceTourer XS 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-spacetourer-xs-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e-Berlingo M 50 kWh', 46.3, 135, 80,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-berlingo-m-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e-Berlingo XL 50 kWh', 50, 132, 80,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-berlingo-xl-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e-C3', 43.8, 135, 60,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c3.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e-C3 Aircross', 53.5, 143, 60,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c3-aircross.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e-C4', 46.3, 150, 78,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c4.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e-C4 54 kWh', 50.8, 150, 78,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c4-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e-C4 X', 46.3, 150, 78,
    'CCS', 'Sedan', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c4-x.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e-C4 X 54 kWh', 50.8, 150, 78,
    'CCS', 'Sedan', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-c4-x-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e-SpaceTourer M 50 kWh', 46.3, 130, 78,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-spacetourer-m-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e-SpaceTourer M 75 kWh', 69, 130, 79,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-spacetourer-m-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e-SpaceTourer XL 50 kWh', 46.3, 130, 78,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-spacetourer-xl-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'e-SpaceTourer XL 75 kWh', 69, 130, 79,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_e-spacetourer-xl-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'JumperII', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_jumperii.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Citroen', 'Jumpy', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/citroen_jumpy.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Cupra', 'Born', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_born.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CUPRA', 'Born 150 kW   58 kWh', 58, 160, 124,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_born-150-kw---58-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CUPRA', 'Born 150 kW   59 kWh', 59, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_born-150-kw---59-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CUPRA', 'Born 170 kW   58 kWh', 58, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_born-170-kw---58-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CUPRA', 'Born 170 kW   59 kWh', 59, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_born-170-kw---59-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CUPRA', 'Born 170 kW   77 kWh', 77, 160, 175,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_born-170-kw---77-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CUPRA', 'Born 170 kW   79 kWh', 79, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_born-170-kw---79-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CUPRA', 'Born 170 kW - 59 kWh', 59, 160, 110,
    'CCS', 'Hatchback', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_born-170-kw---59-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CUPRA', 'Born 170 kW - 77 kWh', 77, 160, 125,
    'CCS', 'Hatchback', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_born-170-kw---77-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CUPRA', 'Born VZ', 79, 200, 135,
    'CCS', 'Hatchback', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_born-vz.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Cupra', 'Tavascan', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_tavascan.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CUPRA', 'Tavascan Endurance', 77, 180, 120,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_tavascan-endurance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'CUPRA', 'Tavascan VZ', 77, 180, 120,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/cupra_tavascan-vz.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Dacia', 'Spring Cargo', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/dacia_spring-cargo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Dacia', 'Spring Electric', 25, 125, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/dacia_spring-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Dacia', 'Spring Electric 45', 25, 125, 29,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/dacia_spring-electric-45.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Dacia', 'Spring Electric 65', 25, 125, 29,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/dacia_spring-electric-65.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Dacia', 'Spring Electric 65 Extreme', 25, 125, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/dacia_spring-electric-65-extreme.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Dongfeng', 'Box 31.4 kWh', 29, 140, 50,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/dongfeng_box-31.4-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Dongfeng', 'Box 314 kWh', 29, 140, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/dongfeng_box-314-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Dongfeng', 'Box 42.3 kWh', 40, 140, 51,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/dongfeng_box-42.3-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Dongfeng', 'Box 423 kWh', 40, 140, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/dongfeng_box-423-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Dongfeng', 'M Hero', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/dongfeng_m-hero.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS', '3 E-Tense', 50.8, 150, 85,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds_3-e-tense.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS', 'Automobiles DS 3 Crossback E Tense', 46.3, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds_automobiles-ds-3-crossback-e-tense.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS', 'Automobiles DS 3 E Tense', 50.8, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds_automobiles-ds-3-e-tense.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS', 'Automobiles N4 E Tense', 58.3, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds_automobiles-n4-e-tense.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS', 'Automobiles N8 AWD Long Range', 97.2, 190, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds_automobiles-n8-awd-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS', 'Automobiles N8 FWD', 73.7, 190, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds_automobiles-n8-fwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS', 'Automobiles N8 FWD Long Range', 97.2, 190, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds_automobiles-n8-fwd-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS', 'N 8', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds_n-8.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS', 'N°4 E-Tense', 58.3, 160, 70,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds_n°4-e-tense.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS', 'N°8 AWD Long Range', 97.2, 190, 130,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds_n°8-awd-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS', 'N°8 FWD', 97.2, 190, 100,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds_n°8-fwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS', 'N°8 FWD Long Range', 97.2, 190, 130,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds_n°8-fwd-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'DS3', 'Crossback', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ds3_crossback.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'eGO', 'ewave X', 27, 135, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ego_ewave-x.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Elaris', 'BEO 86 kWh', 81, 150, 65,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/elaris_beo-86-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Farizon', 'S', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/farizon_s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Farizon', 'V6E', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/farizon_v6e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', '500e 3+1 24 kWh', 21.3, 135, 40,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_500e-3+1-24-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', '500e 3+1 42 kWh', 37.3, 150, 67,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_500e-3+1-42-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', '500e 3plus1 24 kWh', 21.3, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_500e-3plus1-24-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', '500e 3plus1 42 kWh', 37.3, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_500e-3plus1-42-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', '500e Cabrio 24 kWh', 21.3, 135, 40,
    'CCS', 'Cabriolet', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_500e-cabrio-24-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', '500e Cabrio 42 kWh', 37.3, 150, 67,
    'CCS', 'Cabriolet', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_500e-cabrio-42-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', '500e Hatchback 24 kWh', 21.3, 135, 40,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_500e-hatchback-24-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', '500e Hatchback 42 kWh', 37.3, 150, 67,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_500e-hatchback-42-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', '600e', 50.8, 150, 79,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_600e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', 'DobloIII', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_dobloiii.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', 'Ducato', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_ducato.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', 'Grande Panda', 43.8, 132, 60,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_grande-panda.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fiat', 'Scudo', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fiat_scudo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fisker', 'Ocean One', 106.5, 205, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fisker_ocean-one.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fisker', 'Ocean Sport', 71, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fisker_ocean-sport.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Fisker', 'Ocean Ultra', 106.5, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/fisker_ocean-ultra.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Capri Extended Range AWD', 79, 180, 135,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_capri-extended-range-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Capri Extended Range RWD', 77, 180, 120,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_capri-extended-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Capri Standard Range RWD', 52, 160, 85,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_capri-standard-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'CapriCX740L', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_capricx740l.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'e Tourneo Courier', 43.6, 145, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_e-tourneo-courier.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'e Tourneo Custom L1 160 kW', 64, 130, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_e-tourneo-custom-l1-160-kw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'e Tourneo Custom L1 210 kW', 64, 130, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_e-tourneo-custom-l1-210-kw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'e Tourneo Custom L2 160 kW', 64, 150, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_e-tourneo-custom-l2-160-kw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'e Tourneo Custom L2 210 kW', 64, 150, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_e-tourneo-custom-l2-210-kw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'e-Tourneo Courier', 43.6, 145, 80,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_e-tourneo-courier.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'e-Tourneo Custom L1 160 kW', 64, 130, 70,
    'CCS', 'Small Passenger Van', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_e-tourneo-custom-l1-160-kw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'e-Tourneo Custom L1 210 kW', 64, 130, 70,
    'CCS', 'Small Passenger Van', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_e-tourneo-custom-l1-210-kw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'e-Tourneo Custom L2 160 kW', 64, 150, 70,
    'CCS', 'Small Passenger Van', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_e-tourneo-custom-l2-160-kw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'e-Tourneo Custom L2 210 kW', 64, 150, 70,
    'CCS', 'Small Passenger Van', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_e-tourneo-custom-l2-210-kw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Explorer Extended Range AWD', 79, 180, 135,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_explorer-extended-range-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Explorer Extended Range RWD', 77, 180, 120,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_explorer-extended-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Explorer Standard Range RWD', 52, 160, 85,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_explorer-standard-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'ExplorerCX740S', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_explorercx740s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Focus Electric', 19.6, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_focus-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach E ER AWD', 88, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-er-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach E ER RWD', 88, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-er-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach E GT', 88, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-gt.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach E Rally', 91, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-rally.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach E SR AWD', 68, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-sr-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach E SR RWD', 68, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-sr-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach EI', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-ei.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach-E ER AWD (MY24)', 91, 180, 115,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-er-awd-(my24).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach-E ER AWD (MY25)', 88, 180, 105,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-er-awd-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach-E ER RWD (MY24)', 91, 180, 115,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-er-rwd-(my24).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach-E ER RWD (MY25)', 88, 180, 105,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-er-rwd-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach-E GT (MY24)', 91, 200, 115,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-gt-(my24).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach-E GT (MY25)', 91, 200, 115,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-gt-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach-E Rally (MY24)', 91, 200, 115,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-rally-(my24).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach-E Rally (MY25)', 91, 200, 115,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-rally-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach-E SR RWD (MY24)', 72.6, 180, 100,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-sr-rwd-(my24).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Mustang Mach-E SR RWD (MY25)', 72.6, 180, 100,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_mustang-mach-e-sr-rwd-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Puma Gen E', 43.6, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_puma-gen-e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Puma Gen-E', 43.6, 160, 85,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_puma-gen-e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'PumaII', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_pumaii.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Tourneo CourierII', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_tourneo-courierii.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Tourneo CustomI', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_tourneo-customi.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Tourneo CustomII', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_tourneo-customii.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Transit CourierII', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_transit-courierii.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Transit CustomI', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_transit-customi.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'Transit CustomII', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_transit-customii.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ford', 'TransitIV', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ford_transitiv.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'GAC', 'Aion', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/gac_aion.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'GAC', 'Hyptec HT', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/gac_hyptec-ht.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Geely', 'EX5', 60.2, 175, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/geely_ex5.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Genesis', 'G80 Electrified', 90, 225, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/genesis_g80-electrified.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Genesis', 'G80 Electrified Luxury', 82.5, 225, 160,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/genesis_g80-electrified-luxury.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Genesis', 'GV60 Performance', 80, 235, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/genesis_gv60-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Genesis', 'GV60 Premium', 74, 185, 200,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/genesis_gv60-premium.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Genesis', 'GV60 Pure', 80, 185, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/genesis_gv60-pure.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Genesis', 'GV60 Sport', 74, 200, 200,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/genesis_gv60-sport.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Genesis', 'GV60 Sport Plus', 74, 235, 200,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/genesis_gv60-sport-plus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Genesis', 'GV70 Electrified', 80, 235, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/genesis_gv70-electrified.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Genesis', 'GV70 Electrified Sport', 74, 235, 190,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/genesis_gv70-electrified-sport.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'GWM', 'ORA 03 48 kWh', 45.4, 160, 45,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/gwm_ora-03-48-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'GWM', 'ORA 03 63 kWh', 59.3, 160, 56,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/gwm_ora-03-63-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'GWM', 'ORA 03 GT', 59.3, 160, 56,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/gwm_ora-03-gt.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'GWM', 'ORA 07 GT', 83.5, 180, 80,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/gwm_ora-07-gt.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'GWM', 'ORA 07 Pro', 64.3, 170, 75,
    'CCS', 'Sedan', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/gwm_ora-07-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'GWM', 'ORA 07 Pure', 64.3, 170, 75,
    'CCS', 'Sedan', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/gwm_ora-07-pure.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Honda', 'e', 28.5, 145, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/honda_e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Honda', 'e Advance', 28.5, 145, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/honda_e-advance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Honda', 'e Ny1', 61.9, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/honda_e-ny1.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Honda', 'e:Ny1', 61.9, 160, 60,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/honda_e:ny1.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Honda', 'eNy1', 61.9, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/honda_eny1.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hongqi', 'E HS7 111 kWh AWD Pro', 95.3, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hongqi_e-hs7-111-kwh-awd-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hongqi', 'E HS7 111 kWh Long Range', 95.3, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hongqi_e-hs7-111-kwh-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hongqi', 'E HS9', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hongqi_e-hs9.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hongqi', 'E HS9 120 kWh', 112, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hongqi_e-hs9-120-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hongqi', 'E HS9 84 kWh', 76.5, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hongqi_e-hs9-84-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hongqi', 'E HS9 99 kWh', 90, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hongqi_e-hs9-99-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hongqi', 'E-HS9 120 kWh', 112, 200, 110,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hongqi_e-hs9-120-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hongqi', 'E-HS9 84 kWh', 76.5, 200, 112,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hongqi_e-hs9-84-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hongqi', 'E-HS9 99 kWh', 90, 200, 112,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hongqi_e-hs9-99-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'INSTER Long Range', 46, 150, 70,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_inster-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'INSTER Standard Range', 39, 140, 60,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_inster-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 63 kWh RWD', 60, 185, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-63-kwh-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 63 kWh RWD (MY24)', 60, 185, 150,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-63-kwh-rwd-(my24).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 84 kWh AWD', 80, 185, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-84-kwh-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 84 kWh AWD (MY24)', 80, 185, 205,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-84-kwh-awd-(my24).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 84 kWh RWD', 80, 185, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-84-kwh-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 84 kWh RWD (MY24)', 80, 185, 205,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-84-kwh-rwd-(my24).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 Long Range 2WD', 70, 185, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-long-range-2wd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 Long Range AWD', 70, 185, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-long-range-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 N', 80, 260, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-n.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 N (MY24)', 80, 260, 205,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-n-(my24).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 Project 45', 70, 185, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-project-45.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 Standard Range 2WD', 54, 185, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-standard-range-2wd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 5 Standard Range AWD', 54, 185, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-5-standard-range-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 6 Long Range 2WD', 74, 185, 200,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-6-long-range-2wd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 6 Long Range AWD', 74, 185, 200,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-6-long-range-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 6 Standard Range 2WD', 50, 185, 120,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-6-standard-range-2wd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 9 Long Range AWD', 106, 200, 195,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-9-long-range-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 9 Long Range RWD', 106, 185, 195,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-9-long-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ 9 Performance AWD', 106, 200, 195,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-9-performance-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'IONIQ Electric', 38.3, 165, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_ioniq-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'Kona Electric 39 kWh', 39.2, 155, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_kona-electric-39-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'Kona Electric 48 kWh', 48.4, 160, 50,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_kona-electric-48-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'Kona Electric 64 kWh', 64, 167, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_kona-electric-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'Kona Electric 65 kWh', 65.4, 170, 86,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_kona-electric-65-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Hyundai', 'Nexo', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/hyundai_nexo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Iveco', 'Daily', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/iveco_daily.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'JAC', 'iEV7s', 39, 132, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/jac_iev7s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Jaecoo', '5 EV', 58.9, 174, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/jaecoo_5-ev.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Jaecoo', 'E5', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/jaecoo_e5.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Jaguar', 'I Pace', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/jaguar_i-pace.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Jaguar', 'I Pace EV320', 84.7, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/jaguar_i-pace-ev320.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Jaguar', 'I Pace EV400', 84.7, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/jaguar_i-pace-ev400.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Jaguar', 'I-Pace EV400', 84.7, 200, 85,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/jaguar_i-pace-ev400.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Jeep', 'Avenger Electric', 50.8, 150, 85,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/jeep_avenger-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Jeep', 'Avenger Electric 1st Edition', 50.8, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/jeep_avenger-electric-1st-edition.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Jeep', 'Compass Electric 74 kWh', 74, 180, 90,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/jeep_compass-electric-74-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Jeep', 'Wrangler', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/jeep_wrangler.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'KGM', 'Musso EV 2WD', 81, 162, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kgm_musso-ev-2wd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'KGM', 'Musso EV 4WD', 81, 177, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kgm_musso-ev-4wd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'KGM', 'Torres EVX', 72, 175, 85,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kgm_torres-evx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'KGM', 'Torres EVX 806 kWh', 79, 175, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kgm_torres-evx-806-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'e Niro 39 kWh', 39.2, 155, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_e-niro-39-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'e Niro 64 kWh', 64, 167, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_e-niro-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'e Soul 39 kWh', 39.2, 157, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_e-soul-39-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'e Soul 392 kWh', 39.2, 157, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_e-soul-392-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV3 Long Range', 78, 170, 105,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev3-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV3 Standard Range', 55, 170, 80,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev3-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV4 Hatchback 583 kWh', 55, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev4-hatchback-583-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV4 Hatchback 814 kWh', 78, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev4-hatchback-814-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV4 Hatchback Long Range', 78, 170, 105,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev4-hatchback-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV4 Hatchback Standard Range', 55, 170, 80,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev4-hatchback-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV4 Sedan Long Range', 78, 170, 105,
    'CCS', 'Sedan', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev4-sedan-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV4 Sedan Standard Range', 55, 170, 80,
    'CCS', 'Sedan', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev4-sedan-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV4I CT', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev4i-ct.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV5', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev5.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV5 814 kWh', 78, 165, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev5-814-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV6 GT', 80, 260, 205,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev6-gt.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV6 Long Range 2WD', 80, 185, 205,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev6-long-range-2wd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV6 Long Range AWD', 80, 185, 205,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev6-long-range-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV6 Standard Range 2WD', 60, 185, 150,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev6-standard-range-2wd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV9 76.1 kWh RWD', 73, 190, 160,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev9-76.1-kwh-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV9 761 kWh RWD', 73, 190, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev9-761-kwh-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV9 99.8 kWh AWD', 96, 200, 194,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev9-99.8-kwh-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV9 99.8 kWh AWD GT', 96, 220, 194,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev9-99.8-kwh-awd-gt.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV9 99.8 kWh AWD GT-Line', 96, 200, 194,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev9-99.8-kwh-awd-gt-line.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV9 99.8 kWh RWD', 96, 185, 194,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev9-99.8-kwh-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV9 998 kWh AWD', 96, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev9-998-kwh-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV9 998 kWh AWD GT', 96, 220, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev9-998-kwh-awd-gt.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV9 998 kWh AWD GT Line', 96, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev9-998-kwh-awd-gt-line.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'EV9 998 kWh RWD', 96, 185, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_ev9-998-kwh-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'Niro EV', 64.8, 167, 70,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_niro-ev.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'Niro Van', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_niro-van.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'PV5', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_pv5.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'PV5 Passenger 515 kWh', 48, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_pv5-passenger-515-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'PV5 Passenger 712 kWh', 67, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_pv5-passenger-712-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'Soul', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_soul.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Kia', 'Soul EV', 27, 145, 50,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/kia_soul-ev.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lancia', 'Ypsilon', 48.1, 150, 80,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lancia_ypsilon.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lancia', 'Ypsilon 54 kWh', 50.8, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lancia_ypsilon-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lancia', 'Ypsilon 54 kWh HF', 50.8, 180, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lancia_ypsilon-54-kwh-hf.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Leapmotor', 'B10', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/leapmotor_b10.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Leapmotor', 'B10 562 kWh', 55, 170, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/leapmotor_b10-562-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Leapmotor', 'B10 671 kWh', 65, 170, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/leapmotor_b10-671-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Leapmotor', 'C10', 81.9, 190, 70,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/leapmotor_c10.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Leapmotor', 'C10 AWD 819 kWh', 81.9, 190, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/leapmotor_c10-awd-819-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Leapmotor', 'C10 RWD 699 kWh', 69.9, 170, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/leapmotor_c10-rwd-699-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Leapmotor', 'C10 RWD 819 kWh', 81.9, 190, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/leapmotor_c10-rwd-819-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Leapmotor', 'T03', 36, 130, 30,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/leapmotor_t03.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'LEVC', 'TX', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/levc_tx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'LEVC', 'VN5', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/levc_vn5.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lexus', 'ES', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lexus_es.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lexus', 'RZ 300e', 64, 160, 100,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lexus_rz-300e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lexus', 'RZ 350e', 72, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lexus_rz-350e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lexus', 'RZ 450e', 64, 160, 100,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lexus_rz-450e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lexus', 'RZ 500e', 72, 160, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lexus_rz-500e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lexus', 'RZ 550e F SPORT', 72, 160, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lexus_rz-550e-f-sport.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lexus', 'UX 300e', 64, 160, 35,
    'CHAdeMO', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lexus_ux-300e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lightyear', '0', 60, 160, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lightyear_0.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Ligier', 'Myli', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ligier_myli.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lotus', 'Eletre', 109, 250, 259,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lotus_eletre.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lotus', 'Eletre 600', 109, 250, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lotus_eletre-600.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lotus', 'Eletre 900', 109, 260, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lotus_eletre-900.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lotus', 'Eletre R', 109, 260, 259,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lotus_eletre-r.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lotus', 'Eletre S', 109, 250, 259,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lotus_eletre-s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lotus', 'Emeya', 98.9, 250, 240,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lotus_emeya.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lotus', 'Emeya 600', 98.9, 250, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lotus_emeya-600.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lotus', 'Emeya 900', 98.9, 256, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lotus_emeya-900.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lotus', 'Emeya R', 98.9, 256, 240,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lotus_emeya-r.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lotus', 'Emeya S', 98.9, 250, 240,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lotus_emeya-s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lucid', 'Air Grand Touring', 112, 270, 184,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lucid_air-grand-touring.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lucid', 'Air Pure RWD', 84, 200, 160,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lucid_air-pure-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lucid', 'Air Touring', 92, 225, 160,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lucid_air-touring.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Lynk&Co', '02', 65, 180, 90,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/lynk&co_02.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MAN', 'TGE', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/man_tge.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maserati', 'GranCabrio Folgore', 83, 290, 190,
    'CCS', 'Cabriolet', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maserati_grancabrio-folgore.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maserati', 'GranTurismo Folgore', 83, 325, 217,
    'CCS', 'Coupe', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maserati_granturismo-folgore.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maserati', 'Grecale Folgore', 95, 220, 110,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maserati_grecale-folgore.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maserati', 'GrecaleI', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maserati_grecalei.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maxus', 'e Deliver 3', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maxus_e-deliver-3.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maxus', 'e Deliver 5', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maxus_e-deliver-5.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maxus', 'e Deliver 7', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maxus_e-deliver-7.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maxus', 'e Deliver 9', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maxus_e-deliver-9.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maxus', 'eTerron 9', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maxus_eterron-9.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maxus', 'Euniq 5', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maxus_euniq-5.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maxus', 'Euniq 6', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maxus_euniq-6.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maxus', 'MIFA 9', 84, 180, 103,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maxus_mifa-9.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Maxus', 'T90', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/maxus_t90.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mazda', '6e 68.8 kWh', 66, 175, 120,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mazda_6e-68.8-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mazda', '6e Long Range 80 kWh', 75, 175, 70,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mazda_6e-long-range-80-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mazda', 'MX 30', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mazda_mx-30.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'B 250e', 28, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_b-250e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'Citan', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_citan.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'CL', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_cl.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'CLA 250+', 85, 210, 235,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_cla-250+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'CLA 250plus', 85, 210, 353,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_cla-250plus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'CLA 350 4MATIC', 85, 210, 235,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_cla-350-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQ', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eq.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQA 250', 66.5, 160, 100,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqa-250.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQA 250+', 70.5, 160, 90,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqa-250+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQA 300 4MATIC', 66.5, 160, 100,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqa-300-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQA 350 4MATIC', 66.5, 160, 100,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqa-350-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQB 250+', 70.5, 160, 90,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqb-250+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQB 300 4MATIC', 66.5, 160, 100,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqb-300-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQB 350 4MATIC', 66.5, 160, 100,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqb-350-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE 300', 89, 210, 120,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-300.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE 350', 89, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-350.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE 350 4MATIC', 90.6, 210, 141,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-350-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE 350+', 96, 210, 141,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-350+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE 350plus', 90.6, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-350plus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE 500 4MATIC', 90.6, 210, 141,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-500-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE AMG 43 4MATIC', 90.6, 210, 141,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-amg-43-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE AMG 53 4MATIC+', 90.6, 220, 141,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-amg-53-4matic+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE AMG 53 4MATICplus', 90.6, 220, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-amg-53-4maticplus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE SU', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-su.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE SUV 300', 90.6, 210, 141,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-suv-300.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE SUV 350 4MATIC', 90.6, 210, 141,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-suv-350-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE SUV 350+', 96, 210, 141,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-suv-350+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE SUV 350plus', 96, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-suv-350plus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE SUV 500 4MATIC', 96, 210, 141,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-suv-500-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE SUV AMG 43 4MATIC', 90.6, 210, 141,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-suv-amg-43-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE SUV AMG 53 4MATIC+', 90.6, 240, 141,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-suv-amg-53-4matic+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQE SUV AMG 53 4MATICplus', 90.6, 240, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqe-suv-amg-53-4maticplus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS 350', 90.6, 210, 130,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-350.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS 450 4MATIC', 118, 210, 160,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-450-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS 450+', 118, 210, 160,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-450+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS 450plus', 118, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-450plus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS 500 4MATIC', 118, 210, 160,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-500-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS 580 4MATIC', 108.4, 210, 160,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-580-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS AMG 53 4MATIC+', 118, 250, 160,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-amg-53-4matic+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS AMG 53 4MATICplus', 118, 250, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-amg-53-4maticplus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS SUV 450 4MATIC', 118, 210, 160,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-suv-450-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS SUV 450+', 118, 210, 160,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-suv-450+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS SUV 450plus', 118, 210, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-suv-450plus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS SUV 500 4MATIC', 118, 210, 160,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-suv-500-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS SUV 580 4MATIC', 118, 210, 160,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-suv-580-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS SUV Maybach 680', 118, 210, 160,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-suv-maybach-680.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQS SUVX', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqs-suvx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQT', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqt.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQT 200 Long', 45, 132, 50,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqt-200-long.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQT 200 Standard', 45, 132, 50,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqt-200-standard.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQV 250 Extra-Long', 60, 160, 60,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqv-250-extra-long.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQV 250 Long', 60, 160, 60,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqv-250-long.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQV 300 Extra Long', 90, 140, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqv-300-extra-long.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQV 300 Extra-Long', 90, 140, 96,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqv-300-extra-long.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'EQV 300 Long', 90, 160, 96,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_eqv-300-long.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'eVito', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_evito.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'eVito Tourer Extra Long 41 kWh', 35, 120, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_evito-tourer-extra-long-41-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'eVito Tourer Extra Long 60 kWh', 60, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_evito-tourer-extra-long-60-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'eVito Tourer Extra Long 90 kWh', 90, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_evito-tourer-extra-long-90-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'eVito Tourer Extra-Long 60 kWh', 60, 160, 60,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_evito-tourer-extra-long-60-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'eVito Tourer Long 41 kWh', 35, 120, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_evito-tourer-long-41-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'eVito Tourer Long 60 kWh', 60, 160, 60,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_evito-tourer-long-60-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'eVito Tourer Long 90 kWh', 90, 160, 96,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_evito-tourer-long-90-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'G 580', 116, 180, 150,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_g-580.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'GIV W', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_giv-w.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'GLC 400 4MATIC', 94, 210, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_glc-400-4matic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'Sprinter', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_sprinter.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'SW V', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_sw-v.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mercedes Benz', 'Vito', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mercedes-benz_vito.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', '4 Electric 51 kWh', 50.8, 160, 68,
    'CCS', 'Hatchback', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_4-electric-51-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', '4 Electric 64 kWh', 61.7, 160, 116,
    'CCS', 'Hatchback', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_4-electric-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', '4 Electric 77 kWh', 74.4, 180, 110,
    'CCS', 'Hatchback', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_4-electric-77-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', '4 Electric XPOWER', 61.7, 200, 116,
    'CCS', 'Hatchback', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_4-electric-xpower.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', '5 Electric Long Range', 57.4, 185, 60,
    'CCS', 'Station/Estate', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_5-electric-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', '5 Electric Standard Range', 46, 185, 53,
    'CCS', 'Station/Estate', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_5-electric-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'Cyberster GT', 74.4, 200, 85,
    'CCS', 'Cabriolet', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_cyberster-gt.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'Cyberster Trophy', 74.4, 195, 85,
    'CCS', 'Cabriolet', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_cyberster-trophy.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'IM5 Long Range', 96.5, 220, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_im5-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'IM5 Performance', 96.5, 270, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_im5-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'IM5 Standard Range', 73.5, 200, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_im5-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'IM6 Long Range', 96.5, 235, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_im6-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'IM6 Performance', 96.5, 240, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_im6-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'Marvel R', 70, 200, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_marvel-r.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'Marvel R Performance', 70, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_marvel-r-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'MG4 Electric 51 kWh', 50.8, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_mg4-electric-51-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'MG4 Electric 64 kWh', 61.7, 160, 142,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_mg4-electric-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'MG4 Electric 77 kWh', 74.4, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_mg4-electric-77-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'MG4 Electric XPOWER', 61.7, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_mg4-electric-xpower.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'MG5 Electric Long Range', 57.4, 185, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_mg5-electric-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'MG5 Electric Standard Range', 46, 185, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_mg5-electric-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'MG5 EV', 48.8, 185, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_mg5-ev.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'MG5 EV Long Range', 57.4, 185, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_mg5-ev-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'MGS5  EV 49 kWh', 47.1, 170, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_mgs5--ev-49-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'MGS5  EV 64 kWh', 62.1, 190, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_mgs5--ev-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'S5  EV 49 kWh', 47.1, 170, 80,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_s5--ev-49-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'S5  EV 64 kWh', 62.1, 190, 90,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_s5--ev-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'ZS EV', 42.5, 140, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_zs-ev.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'ZS EV Long Range', 68.3, 175, 82,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_zs-ev-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MG', 'ZS EV Standard Range', 49, 175, 50,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mg_zs-ev-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MINI', 'Aceman', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mini_aceman.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mini', 'Aceman E', 38.5, 160, 60,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mini_aceman-e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mini', 'Aceman JCW', 49.2, 200, 75,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mini_aceman-jcw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mini', 'Aceman SE', 49.2, 170, 75,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mini_aceman-se.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MINI', 'Cabrio', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mini_cabrio.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mini', 'Cooper E', 36.6, 160, 60,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mini_cooper-e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mini', 'Cooper JCW', 49.2, 200, 75,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mini_cooper-jcw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mini', 'Cooper SE', 49.2, 170, 75,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mini_cooper-se.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mini', 'Cooper SE Convertible', 28.9, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mini_cooper-se-convertible.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MINI', 'Countryman', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mini_countryman.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mini', 'Countryman E', 64.6, 170, 94,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mini_countryman-e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mini', 'Countryman SE ALL4', 64.6, 180, 94,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mini_countryman-se-all4.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'MiniF65', 'F66', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/minif65_f66.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mitsubishi', 'Eclipse Cross 87 kWh', 87, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mitsubishi_eclipse-cross-87-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mitsubishi', 'i MiE', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mitsubishi_i-mie.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Mitsubishi', 'i MiEV', 14.5, 130, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/mitsubishi_i-miev.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nio', 'EL6', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_el6.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'NIO', 'EL6 Long Range', 90, 200, 135,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_el6-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'NIO', 'EL6 Standard Range', 73.5, 200, 110,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_el6-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nio', 'EL7', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_el7.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'NIO', 'EL7 Long Range', 90, 200, 100,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_el7-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'NIO', 'EL7 Standard Range', 73.5, 200, 110,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_el7-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nio', 'EL8', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_el8.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'NIO', 'EL8 Long Range', 90, 200, 190,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_el8-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'NIO', 'EL8 Standard Range', 73.5, 200, 110,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_el8-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nio', 'ET5', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_et5.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'NIO', 'ET5 Long Range', 90, 200, 100,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_et5-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'NIO', 'ET5 Standard Range', 73.5, 200, 110,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_et5-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'NIO', 'ET5 Touring Long Range', 90, 200, 135,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_et5-touring-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'NIO', 'ET5 Touring Standard Range', 73.5, 200, 110,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_et5-touring-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nio', 'ET7', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_et7.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'NIO', 'ET7 Long Range', 90, 200, 100,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_et7-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'NIO', 'ET7 Standard Range', 73.5, 200, 110,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nio_et7-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Ariya 63kWh', 63, 160, 90,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_ariya-63kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Ariya 87kWh', 87, 160, 110,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_ariya-87kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Ariya e 4ORCE 87kWh   225 kW', 87, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_ariya-e-4orce-87kwh---225-kw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Ariya e 4ORCE 87kWh   290 kW', 87, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_ariya-e-4orce-87kwh---290-kw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Ariya e 4ORCE 87kWh   320 kW Nismo', 87, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_ariya-e-4orce-87kwh---320-kw-nismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Ariya e-4ORCE 87kWh - 225 kW', 87, 200, 110,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_ariya-e-4orce-87kwh---225-kw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Ariya e-4ORCE 87kWh - 320 kW Nismo', 87, 200, 110,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_ariya-e-4orce-87kwh---320-kw-nismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'e NV200 Evalia', 22, 123, 46,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_e-nv200-evalia.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'e NV200I', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_e-nv200i.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Interstar', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_interstar.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Leaf', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_leaf.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'LEAF 24 kWh', 22, 144, 46,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_leaf-24-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'LEAF 30 kWh', 28, 144, 47,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_leaf-30-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'LEAF 40 kWh', 39, 144, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_leaf-40-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'LEAF eplus 62 kWh', 59, 157, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_leaf-eplus-62-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'LEAF Extended Range 75 kWh', 75, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_leaf-extended-range-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'LEAF Standard Range 52 kWh', 52, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_leaf-standard-range-52-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'LeafI', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_leafi.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Micra Extended Range 52 kWh', 52, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_micra-extended-range-52-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Micra Standard Range 40 kWh', 40, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_micra-standard-range-40-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Townstar EV Passenger', 45, 132, 50,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_townstar-ev-passenger.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Nissan', 'Townstar EV Passenger L2', 45, 130, 50,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/nissan_townstar-ev-passenger-l2.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Omoda', 'E5', 58.9, 172, 60,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/omoda_e5.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Ampera e', 58, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_ampera-e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Astra Electric', 50.8, 170, 85,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_astra-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Astra Sports Tourer Electric', 50.8, 170, 85,
    'CCS', 'Station/Estate', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_astra-sports-tourer-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'AstraL', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_astral.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Combo e Life 50 kWh', 46.3, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_combo-e-life-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Combo e Life XL 50 kWh', 46.3, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_combo-e-life-xl-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Combo Electric 50 kWh', 50, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_combo-electric-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Combo Electric XL 50 kWh', 50, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_combo-electric-xl-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Combo VanE', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_combo-vane.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Combo-e Life 50 kWh', 46.3, 135, 80,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_combo-e-life-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Combo-e Life XL 50 kWh', 46.3, 135, 80,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_combo-e-life-xl-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'ComboLife E', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_combolife-e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Corsa e', 46.3, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_corsa-e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Corsa Electric 50 kWh', 46.3, 150, 78,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_corsa-electric-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Corsa Electric 51 kWh', 48.1, 150, 80,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_corsa-electric-51-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Corsa Electric 54 kWh', 50.8, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_corsa-electric-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'CorsaF', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_corsaf.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Frontera 44 kWh', 44.0, 140, 60,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_frontera-44-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Frontera Electric 44 kWh', 43.8, 143, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_frontera-electric-44-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Frontera Electric 54 kWh', 53.5, 143, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_frontera-electric-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Grandland 73 kWh', 96.9, 170, 90,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_grandland-73-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Grandland 82 kWh', 96.9, 170, 100,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_grandland-82-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Grandland Electric 73 kWh', 73, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_grandland-electric-73-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Grandland Electric 73 kWh AWD', 73, 170, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_grandland-electric-73-kwh-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Grandland Electric 82 kWh', 82.2, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_grandland-electric-82-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'GrandlandB', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_grandlandb.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Mokka e', 46.3, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_mokka-e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Mokka e 50 kWh', 46.3, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_mokka-e-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Mokka e 54 kWh', 50.8, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_mokka-e-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Mokka Electric', 50.8, 150, 85,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_mokka-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'MokkaB', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_mokkab.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'MovanoC', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_movanoc.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Vivaro e Combi L 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_vivaro-e-combi-l-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Vivaro e Combi L 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_vivaro-e-combi-l-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Vivaro e Combi M 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_vivaro-e-combi-m-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Vivaro e Combi M 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_vivaro-e-combi-m-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Vivaro e Combi S 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_vivaro-e-combi-s-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'VivaroC', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_vivaroc.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'VivaroC 2', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_vivaroc-2.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira e Life L 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-e-life-l-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira e Life L 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-e-life-l-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira e Life M 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-e-life-m-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira e Life M 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-e-life-m-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira e Life S 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-e-life-s-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira Electric 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-electric-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira Electric 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-electric-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira Electric XL 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-electric-xl-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira Electric XL 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-electric-xl-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira LifeA', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-lifea.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira-e Life L2 50 kWh', 69, 130, 78,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-e-life-l2-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira-e Life L2 75 kWh', 69, 130, 79,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-e-life-l2-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira-e Life L3 50 kWh', 69, 130, 78,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-e-life-l3-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Opel', 'Zafira-e Life L3 75 kWh', 69, 130, 79,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/opel_zafira-e-life-l3-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'ORA', 'Funky Cat 48 kWh', 45.4, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ora_funky-cat-48-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'ORA', 'Funky Cat 63 kWh', 59.3, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ora_funky-cat-63-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'ORA', 'Funky Cat GT', 59.3, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ora_funky-cat-gt.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'BoxerII', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_boxerii.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 2008', 46.3, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-2008.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 2008 50 kWh', 46.3, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-2008-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 2008 54 kWh', 50.8, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-2008-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 208', 46.3, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-208.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 208 50 kWh', 46.3, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-208-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 208 51 kWh', 48.1, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-208-51-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 208 54 kWh', 50.8, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-208-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 3008 73 kWh', 73, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-3008-73-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 3008 73 kWh Dual Motor', 73, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-3008-73-kwh-dual-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 3008 97 kWh Long Range', 96.9, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-3008-97-kwh-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 308', 50.8, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-308.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 308 58 kWh', 55.4, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-308-58-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 308 SW', 50.8, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-308-sw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 308 SW 58 kWh', 55.4, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-308-sw-58-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 408 58 kWh', 58.3, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-408-58-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 5008 73 kWh', 73, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-5008-73-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 5008 73 kWh Dual Motor', 73, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-5008-73-kwh-dual-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e 5008 97 kWh Long Range', 96.9, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-5008-97-kwh-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Expert Combi Compact 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-expert-combi-compact-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Expert Combi Long 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-expert-combi-long-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Expert Combi Long 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-expert-combi-long-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Expert Combi Standard 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-expert-combi-standard-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Expert Combi Standard 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-expert-combi-standard-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Rifter Long 50 kWh', 46.3, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-rifter-long-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Rifter M 50 kWh', 50, 132, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-rifter-m-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Rifter Standard 50 kWh', 46.3, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-rifter-standard-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Rifter XL 50 kWh', 50, 132, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-rifter-xl-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Traveller Compact 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-traveller-compact-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Traveller L2 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-traveller-l2-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Traveller L2 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-traveller-l2-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Traveller L3 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-traveller-l3-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Traveller L3 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-traveller-l3-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Traveller Standard 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-traveller-standard-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e Traveller Standard 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-traveller-standard-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-2008 50 kWh', 46.3, 150, 78,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-2008-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-2008 54 kWh', 50.8, 150, 85,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-2008-54-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-208 50 kWh', 46.3, 150, 78,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-208-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-208 51 kWh', 48.1, 150, 80,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-208-51-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-3008 73 kWh', 73, 170, 90,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-3008-73-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-3008 73 kWh Dual Motor', 73, 180, 90,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-3008-73-kwh-dual-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-3008 97 kWh Long Range', 96.9, 170, 135,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-3008-97-kwh-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-308', 50.8, 170, 80,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-308.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-308 SW', 50.8, 150, 80,
    'CCS', 'Station/Estate', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-308-sw.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-408 58 kWh', 58.3, 160, 70,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-408-58-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-5008 73 kWh', 73, 170, 90,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-5008-73-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-5008 73 kWh Dual Motor', 73, 180, 90,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-5008-73-kwh-dual-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-5008 97 kWh Long Range', 96.9, 170, 135,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-5008-97-kwh-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-Rifter M 50 kWh', 50, 132, 80,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-rifter-m-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-Rifter XL 50 kWh', 50, 132, 80,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-rifter-xl-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-Traveller L2 50 kWh', 46.3, 130, 78,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-traveller-l2-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-Traveller L2 75 kWh', 69, 130, 79,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-traveller-l2-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-Traveller L3 50 kWh', 46.3, 130, 78,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-traveller-l3-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'e-Traveller L3 75 kWh', 69, 130, 79,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_e-traveller-l3-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'Expert', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_expert.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'iOn', 14.5, 130, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_ion.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'Partner', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_partner.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Peugeot', 'Partner Tepee Electric', 20.5, 110, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/peugeot_partner-tepee-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '2 Long Range Dual Motor', 79, 205, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_2-long-range-dual-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '2 Long Range Dual Motor (MY26)', 79, 205, 125,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_2-long-range-dual-motor-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '2 Long Range Performance', 75, 205, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_2-long-range-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '2 Long Range Performance (MY26)', 79, 205, 125,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_2-long-range-performance-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '2 Long Range Single Motor', 79, 205, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_2-long-range-single-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '2 Long Range Single Motor (MY26)', 79, 205, 125,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_2-long-range-single-motor-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '2 Standard Range Single Motor', 67, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_2-standard-range-single-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '2 Standard Range Single Motor (MY26)', 67, 205, 110,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_2-standard-range-single-motor-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '3 Long Range Dual motor', 107, 210, 150,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_3-long-range-dual-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '3 Long Range Performance', 107, 210, 150,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_3-long-range-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '3 Long Range Single motor', 107, 180, 150,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_3-long-range-single-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '4 Long Range Dual Motor', 94, 200, 135,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_4-long-range-dual-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Polestar', '4 Long Range Single Motor', 94, 180, 135,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/polestar_4-long-range-single-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Macan 4 Electric', 95, 220, 200,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_macan-4-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Macan 4S Electric', 95, 240, 200,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_macan-4s-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Macan Electric', 95, 220, 200,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_macan-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Macan Turbo Electric', 95, 260, 200,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_macan-turbo-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan', 82.3, 230, 195,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan 4', 82.3, 230, 195,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-4.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan 4 Cross Turismo', 97, 220, 281,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-4-cross-turismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan 4 Plus', 97, 230, 281,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-4-plus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan 4S', 82.3, 250, 195,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-4s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan 4S Cross Turismo', 97, 240, 281,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-4s-cross-turismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan 4S Plus', 97, 250, 281,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-4s-plus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan 4S Plus Sport Turismo', 97, 250, 281,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-4s-plus-sport-turismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan 4S Sport Turismo', 82.3, 250, 195,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-4s-sport-turismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan GTS', 97, 250, 281,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-gts.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan GTS Sport Turismo', 97, 250, 281,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-gts-sport-turismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan Plus', 97, 230, 281,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-plus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan Plus Sport Turismo', 97, 230, 281,
    'CCS', 'Station/Estate', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-plus-sport-turismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan Sport Turismo', 82.3, 230, 195,
    'CCS', 'Station/Estate', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-sport-turismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan Turbo', 97, 260, 281,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-turbo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan Turbo Cross Turismo', 97, 250, 281,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-turbo-cross-turismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan Turbo GT', 97, 290, 281,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-turbo-gt.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan Turbo GT Weissach', 97, 305, 281,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-turbo-gt-weissach.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan Turbo S', 97, 260, 281,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-turbo-s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan Turbo S Cross Turismo', 97, 250, 281,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-turbo-s-cross-turismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan Turbo S Sport Turismo', 97, 260, 281,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-turbo-s-sport-turismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Porsche', 'Taycan Turbo Sport Turismo', 97, 260, 281,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/porsche_taycan-turbo-sport-turismo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', '4 E Tech 40kWh 120hp', 40, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_4-e-tech-40kwh-120hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', '4 E Tech 52kWh 150hp', 52, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_4-e-tech-52kwh-150hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', '4 E-Tech 40kWh 120hp', 40, 150, 55,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_4-e-tech-40kwh-120hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', '4 E-Tech 52kWh 150hp', 52, 150, 70,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_4-e-tech-52kwh-150hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', '5 E Tech 40kWh 120hp', 40, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_5-e-tech-40kwh-120hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', '5 E Tech 40kWh 95hp', 40, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_5-e-tech-40kwh-95hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', '5 E Tech 52kWh 150hp', 52, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_5-e-tech-52kwh-150hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', '5 E-Tech 40kWh 120hp', 40, 150, 55,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_5-e-tech-40kwh-120hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', '5 E-Tech 40kWh 95hp', 40, 130, NULL,
    NULL, 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_5-e-tech-40kwh-95hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', '5 E-Tech 52kWh 150hp', 52, 150, 70,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_5-e-tech-52kwh-150hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Fluence Z E', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_fluence-z-e.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Kangoo E Tech Electric', 45, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_kangoo-e-tech-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Kangoo E-Tech Electric', 45, 135, 50,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_kangoo-e-tech-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Kangoo Express', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_kangoo-express.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Kangoo Grand E Tech Electric', 45, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_kangoo-grand-e-tech-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Kangoo Grand E-Tech Electric', 45, 130, 50,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_kangoo-grand-e-tech-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Kangoo Maxi ZE 33', 31, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_kangoo-maxi-ze-33.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Kangoo VanKangoo III', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_kangoo-vankangoo-iii.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Master', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_master.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Megane E Tech EV40 130hp', 40, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_megane-e-tech-ev40-130hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Megane E Tech EV60 130hp', 60, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_megane-e-tech-ev60-130hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Megane E Tech EV60 220hp', 60, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_megane-e-tech-ev60-220hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Megane E-Tech EV60 130hp (TU2025)', 60, 150, 88,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_megane-e-tech-ev60-130hp-(tu2025).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Megane E-Tech EV60 220hp (TU2025)', 60, 160, 88,
    'CCS', 'Hatchback', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_megane-e-tech-ev60-220hp-(tu2025).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Scenic E Tech EV60 170hp', 60, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_scenic-e-tech-ev60-170hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Scenic E Tech EV87 220hp', 87, 170, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_scenic-e-tech-ev87-220hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Scenic E-Tech EV60 170hp', 60, 150, 75,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_scenic-e-tech-ev60-170hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Scenic E-Tech EV87 220hp', 87, 170, 95,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_scenic-e-tech-ev87-220hp.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Trafic', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_trafic.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Twingo E Tech 275 kWh', 27.5, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_twingo-e-tech-275-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Twingo Electric', 21.3, 135, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_twingo-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'ZOE', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_zoe.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Zoe Q210', 23.3, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_zoe-q210.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Zoe Q90', 41, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_zoe-q90.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Zoe R110', 41, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_zoe-r110.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Zoe R240', 23.3, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_zoe-r240.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Zoe R90', 41, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_zoe-r90.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Zoe R90 Entry', 23.3, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_zoe-r90-entry.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Zoe ZE40 R110', 41, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_zoe-ze40-r110.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Zoe ZE50 R110', 52, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_zoe-ze50-r110.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Renault', 'Zoe ZE50 R135', 52, 140, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/renault_zoe-ze50-r135.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Rolls Royce', 'Royce Spectre', 102, 250, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/rolls-royce_royce-spectre.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Rolls-Royce', 'Spectre', 102, 250, 126,
    'CCS', 'Coupe', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/rolls-royce_spectre.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'SEAT', 'Mii Electric', 32.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/seat_mii-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Seres', '3', 51, 155, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/seres_3.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Citigo', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_citigo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'CITIGOe iV', 32.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_citigoe-iv.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Elroq 50', 52, 160, 90,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_elroq-50.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Elroq 60', 59, 160, 110,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_elroq-60.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Elroq 85', 77, 180, 120,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_elroq-85.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Elroq 85x', 77, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_elroq-85x.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Elroq RS', 79, 180, 135,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_elroq-rs.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq 50', 52, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-50.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq 60', 59, 160, 110,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-60.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq 85', 77, 180, 120,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-85.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq 85x', 77, 180, 120,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-85x.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq Coupe 60', 59, 160, 110,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-coupe-60.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq Coupe 85', 77, 180, 120,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-coupe-85.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq Coupe 85x', 77, 180, 120,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-coupe-85x.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq Coupe iV 60', 58, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-coupe-iv-60.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq Coupe iV 80', 77, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-coupe-iv-80.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq Coupe iV 80x', 77, 160, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-coupe-iv-80x.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq Coupe iV RS', 77, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-coupe-iv-rs.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq Coupe RS', 79, 180, 135,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-coupe-rs.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq i', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-i.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq iV 50', 52, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-iv-50.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq iV 60', 58, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-iv-60.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq iV 80', 77, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-iv-80.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq iV 80x', 77, 160, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-iv-80x.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq iV RS', 77, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-iv-rs.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skoda', 'Enyaq RS', 79, 180, 135,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skoda_enyaq-rs.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skywell', 'BE11 Long Range', 81, 150, 50,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skywell_be11-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skywell', 'BE11 Standard Range', 68, 150, 50,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skywell_be11-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Skywell', 'ET5', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/skywell_et5.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#1 Brabus', 62, 180, 100,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#1-brabus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#1 Premium', 62, 180, 100,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#1-premium.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#1 Pro', 47, 180, 65,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#1-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#1 Pro+', 62, 180, 100,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#1-pro+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#1 Pulse', 62, 180, 100,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#1-pulse.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#1 Pure', 47, 180, 65,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#1-pure.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#1 Pure+', 62, 180, 100,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#1-pure+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#3 Brabus', 62, 180, 100,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#3-brabus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#3 Premium', 62, 180, 110,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#3-premium.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#3 Pro', 47, 180, 65,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#3-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#3 Pro+', 62, 180, 110,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#3-pro+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#5 Brabus', 94, 210, 230,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#5-brabus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#5 Premium', 94, 200, 230,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#5-premium.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#5 Pro', 74.4, 200, 110,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#5-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#5 Pro+', 94, 200, 230,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#5-pro+.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#5 Pulse', 94, 200, 230,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#5-pulse.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '#5 Summit Edition', 94, 200, 230,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_#5-summit-edition.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '1 Brabus', 62, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_1-brabus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '1 Premium', 62, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_1-premium.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '1 Pro', 47, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_1-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '1 Proplus', 62, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_1-proplus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', '1 Pulse', 62, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_1-pulse.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', 'forfour', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_forfour.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Smart', 'fortwo', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/smart_fortwo.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'SsangYong', 'Korando', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ssangyong_korando.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'SsangYong', 'Torres', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/ssangyong_torres.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Subaru', 'Solterra AWD', 64, 160, 100,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/subaru_solterra-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model 3 Long Range AWD (Highland)', 75, 201, 124,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-3-long-range-awd-(highland).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model 3 Long Range RWD', 75, 201, 250,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-3-long-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model 3 Long Range RWD (Highland)', 75, 201, 124,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-3-long-range-rwd-(highland).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model 3 Performance (Highland)', 75, 262, 124,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-3-performance-(highland).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model 3 RWD (Highland CATL LFP64)', 60, 201, 110,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-3-rwd-(highland-catl-lfp64).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S 70', 66.5, 225, 75,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-70.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S 70D', 66.5, 225, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-70d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S 75', 72.5, 225, 75,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-75.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S 75D', 72.5, 225, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-75d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S 85', 80.8, 225, 75,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-85.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S 85D', 80.8, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-85d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S 90D', 85.5, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-90d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S AWD', 95, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S Dual Motor', 95, 250, 140,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-dual-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S Long Range', 95, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S Long Range Plus', 98, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-long-range-plus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S P100D', 95, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-p100d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S P85D', 80.8, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-p85d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S P90D', 85.5, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-p90d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S P90DL', 85.5, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-p90dl.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S Performance', 98, 261, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S Plaid', 95, 282, 140,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-plaid.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model S Standard Range', 72.5, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-s-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X 100D', 95, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-100d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X 60D', 62, 210, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-60d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X 75D', 72.5, 210, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-75d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X 90D', 85.5, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-90d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X AWD', 95, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X Dual Motor', 95, 250, 140,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-dual-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X Long Range', 95, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X Long Range Plus', 98, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-long-range-plus.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X Ludicrous Performance', 95, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-ludicrous-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X P100D', 95, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-p100d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X P90D', 85.5, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-p90d.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X P90DL', 85.5, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-p90dl.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X Performance', 98, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X Plaid', 95, 262, 140,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-plaid.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model X Standard Range', 72.5, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-x-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model Y Long Range AWD', 75, 201, 250,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-y-long-range-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model Y Long Range AWD (Juniper)', 75, 201, 124,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-y-long-range-awd-(juniper).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model Y Long Range AWD Launch Series', 75, 201, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-y-long-range-awd-launch-series.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model Y Long Range RWD', 75, 201, 75,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-y-long-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model Y Long Range RWD (Juniper)', 75, 201, 124,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-y-long-range-rwd-(juniper).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model Y Performance', 79, 250, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-y-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model Y Premium AWD', 79, 201, 75,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-y-premium-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model Y Premium RWD', 75, 201, 75,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-y-premium-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model Y RWD', 60, 201, 175,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-y-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model Y RWD (Juniper)', 60, 201, 110,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-y-rwd-(juniper).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Tesla', 'Model Y Standard RWD', 60, 201, 75,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/tesla_model-y-standard-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'TOGG', 'T10F Long Range AWD', 85, 177, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/togg_t10f-long-range-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'TOGG', 'T10F Long Range RWD', 85, 177, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/togg_t10f-long-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'TOGG', 'T10F Standard Range RWD', 50, 172, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/togg_t10f-standard-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'TOGG', 'T10X Long Range AWD', 85, 185, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/togg_t10x-long-range-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'TOGG', 'T10X Long Range RWD', 85, 185, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/togg_t10x-long-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'TOGG', 'T10X Standard Range RWD', 50, 185, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/togg_t10x-standard-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'bZ4X AWD', 64, 160, 100,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_bz4x-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'bZ4X AWD 731 kWh', 69, 160, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_bz4x-awd-731-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'bZ4X FWD', 64, 160, 100,
    'CCS', 'SUV', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_bz4x-fwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'bZ4X FWD 577 kWh', 54, 140, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_bz4x-fwd-577-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'bZ4X FWD 731 kWh', 69, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_bz4x-fwd-731-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'C HRplus 577 kWh', 54, 140, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_c-hrplus-577-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'C HRplus 77 kWh', 72, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_c-hrplus-77-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'C HRplus 77 kWh AWD', 72, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_c-hrplus-77-kwh-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'Mirai', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_mirai.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'Proace City Verso Electric L1 50 kWh', 69, 130, 80,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_proace-city-verso-electric-l1-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'Proace City Verso Electric L2 50 kWh', 69, 130, 80,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_proace-city-verso-electric-l2-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'Proace City Verso L1 50 kWh', 46.3, 135, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_proace-city-verso-l1-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'Proace City Verso L2 50 kWh', 50, 132, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_proace-city-verso-l2-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'Proace Shuttle L 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_proace-shuttle-l-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'Proace Shuttle L 75 kWh', 69, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_proace-shuttle-l-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'Proace Shuttle M 50 kWh', 46.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_proace-shuttle-m-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'PROACE Verso L 75 kWh', 69, 130, 79,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_proace-verso-l-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'PROACE Verso M 50 kWh', 46.3, 130, 78,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_proace-verso-m-50-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Toyota', 'PROACE Verso M 75 kWh', 69, 130, 79,
    'CCS', 'Small Passenger Van', 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/toyota_proace-verso-m-75-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'VinFast', 'VF 8 Eco Extended Range', 87.7, 200, 120,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/vinfast_vf-8-eco-extended-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'VinFast', 'VF 8 Plus Extended Range', 87.7, 200, 120,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/vinfast_vf-8-plus-extended-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'Caddy4', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_caddy4.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'Crafter', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_crafter.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'e Caravelle L1 210 kW 64 kWh', 63.8, 150, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_e-caravelle-l1-210-kw-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'e Caravelle L2 100 kW 64 kWh', 63.8, 112, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_e-caravelle-l2-100-kw-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'e Caravelle L2 160 kW 64 kWh', 63.8, 150, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_e-caravelle-l2-160-kw-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'e Caravelle L2 210 kW 64 kWh', 63.8, 150, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_e-caravelle-l2-210-kw-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'e Golf', 32, 150, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_e-golf.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'e Transporter Kombi L1 100 kW 64 kWh', 63.8, 112, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_e-transporter-kombi-l1-100-kw-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'e Transporter Kombi L1 160 kW 64 kWh', 63.8, 150, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_e-transporter-kombi-l1-160-kw-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'e Transporter Kombi L1 210 kW 64 kWh', 63.8, 150, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_e-transporter-kombi-l1-210-kw-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'e Transporter Kombi L2 100 kW 64 kWh', 63.8, 112, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_e-transporter-kombi-l2-100-kw-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'e Transporter Kombi L2 160 kW 64 kWh', 63.8, 150, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_e-transporter-kombi-l2-160-kw-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'e Transporter Kombi L2 210 kW 64 kWh', 63.8, 150, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_e-transporter-kombi-l2-210-kw-64-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'e Up', 32.3, 130, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_e-up.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'Golf', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_golf.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'GolfFL', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_golffl.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID 3', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id-3.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID 4', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id-4.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID 5', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id-5.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID 7', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id-7.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID Buzz', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id-buzz.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID Buzz LWB GTX', 86, 160, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id-buzz-lwb-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID Buzz LWB Pro', 86, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id-buzz-lwb-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID Buzz NWB GTX', 79, 160, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id-buzz-nwb-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID Buzz NWB Pro', 79, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id-buzz-nwb-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID Buzz NWB Pure', 59, 145, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id-buzz-nwb-pure.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID Buzz Pro', 77, 145, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id-buzz-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID. Buzz LWB GTX', 86, 160, 145,
    'CCS', 'Small Passenger Van', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.-buzz-lwb-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID. Buzz LWB Pro', 86, 160, 145,
    'CCS', 'Small Passenger Van', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.-buzz-lwb-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID. Buzz NWB GTX', 79, 160, 135,
    'CCS', 'Small Passenger Van', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.-buzz-nwb-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID. Buzz NWB Pro', 79, 160, 135,
    'CCS', 'Small Passenger Van', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.-buzz-nwb-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID. Buzz NWB Pure', 59, 145, 110,
    'CCS', 'Small Passenger Van', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.-buzz-nwb-pure.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.3 GTX', 79, 180, 135,
    'CCS', 'Hatchback', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.3-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.3 GTX Performance', 79, 200, 135,
    'CCS', 'Hatchback', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.3-gtx-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.3 Pro', 59, 160, 110,
    'CCS', 'Hatchback', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.3-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.3 Pro S', 77, 160, 120,
    'CCS', 'Hatchback', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.3-pro-s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.3 Pure', 52, 160, 90,
    'CCS', 'Hatchback', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.3-pure.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.4 GTX', 77, 180, 120,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.4-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.4 Pro', 77, 180, 120,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.4-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.4 Pro 4MOTION', 77, 180, 120,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.4-pro-4motion.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.4 Pure', 52, 160, 87,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.4-pure.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.5 GTX', 77, 180, 120,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.5-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.5 Pro', 77, 180, 120,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.5-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.5 Pure', 52, 160, 87,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.5-pure.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.7 GTX', 86, 180, 145,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.7-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.7 Pro', 77, 180, 125,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.7-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.7 Pro S', 86, 180, 145,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.7-pro-s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.7 Tourer GTX', 86, 180, 145,
    'CCS', 'Station/Estate', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.7-tourer-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.7 Tourer Pro', 77, 180, 125,
    'CCS', 'Station/Estate', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.7-tourer-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID.7 Tourer Pro S', 86, 180, 145,
    'CCS', 'Station/Estate', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id.7-tourer-pro-s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID3 1st', 58, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id3-1st.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID3 GTX', 79, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id3-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID3 GTX Performance', 79, 200, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id3-gtx-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID3 Pro', 59, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id3-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID3 Pro Performance', 58, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id3-pro-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID3 Pro S', 77, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id3-pro-s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID3 Pro S   4 Seats', 77, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id3-pro-s---4-seats.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID3 Pro S   5 Seats', 77, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id3-pro-s---5-seats.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID3 Pure', 52, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id3-pure.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID3 Pure Performance', 45, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id3-pure-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID4 1st', 77, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id4-1st.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID4 GTX', 77, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id4-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID4 Pro', 77, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id4-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID4 Pro 4MOTION', 77, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id4-pro-4motion.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID4 Pro Performance', 77, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id4-pro-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID4 Pure', 52, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id4-pure.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID4 Pure Performance', 52, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id4-pure-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID5 GTX', 77, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id5-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID5 Pro', 77, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id5-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID5 Pro Performance', 77, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id5-pro-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID5 Pure', 52, 160, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id5-pure.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID7 GTX', 86, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id7-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID7 Pro', 77, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id7-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID7 Pro S', 86, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id7-pro-s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID7 Tourer GTX', 86, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id7-tourer-gtx.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID7 Tourer Pro', 77, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id7-tourer-pro.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'ID7 Tourer Pro S', 86, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_id7-tourer-pro-s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'Transporter', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_transporter.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'up', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_up.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volkswagen', 'up I', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volkswagen_up-i.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'C40 Recharge Pure Electric', 67, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_c40-recharge-pure-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'C40 Recharge Single Motor', 66, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_c40-recharge-single-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'C40 Recharge Single Motor ER', 79, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_c40-recharge-single-motor-er.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'C40 Recharge Twin Motor', 79, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_c40-recharge-twin-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'C40 Recharge Twin Pure Electric', 75, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_c40-recharge-twin-pure-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EC40 Single Motor', 67, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ec40-single-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EC40 Single Motor (MY26)', 67, 180, 110,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ec40-single-motor-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EC40 Single Motor ER', 79, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ec40-single-motor-er.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EC40 Single Motor ER (MY26)', 79, 180, 125,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ec40-single-motor-er-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EC40 Twin Motor', 79, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ec40-twin-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EC40 Twin Motor (MY26)', 79, 180, 125,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ec40-twin-motor-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EC40 Twin Motor Performance', 79, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ec40-twin-motor-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EC40 Twin Motor Performance (MY26)', 79, 180, 125,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ec40-twin-motor-performance-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'ES90 Single Motor', 88, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_es90-single-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'ES90 Single Motor (MY26)', 88, 180, 190,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_es90-single-motor-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'ES90 Twin Motor', 102, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_es90-twin-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'ES90 Twin Motor (MY26)', 102, 180, 225,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_es90-twin-motor-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'ES90 Twin Motor Performance', 102, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_es90-twin-motor-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'ES90 Twin Motor Performance (MY26)', 102, 180, 225,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_es90-twin-motor-performance-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX30 Cross Country', 65, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex30-cross-country.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX30 Cross Country (MY26)', 65, 180, 114,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex30-cross-country-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX30 Single Motor', 49, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex30-single-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX30 Single Motor (MY24-26)', 49, 180, 80,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex30-single-motor-(my24-26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX30 Single Motor ER', 65, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex30-single-motor-er.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX30 Single Motor ER (MY24-26)', 65, 180, 113,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex30-single-motor-er-(my24-26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX30 Twin Motor Performance', 65, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex30-twin-motor-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX30 Twin Motor Performance (MY24-26)', 65, 180, 113,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex30-twin-motor-performance-(my24-26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX40 Single Motor', 67, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex40-single-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX40 Single Motor (MY26)', 67, 180, 110,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex40-single-motor-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX40 Single Motor ER', 79, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex40-single-motor-er.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX40 Single Motor ER (MY26)', 79, 180, 125,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex40-single-motor-er-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX40 Twin Motor', 79, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex40-twin-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX40 Twin Motor (MY26)', 79, 180, 125,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex40-twin-motor-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX40 Twin Motor Performance', 79, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex40-twin-motor-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX40 Twin Motor Performance (MY26)', 79, 180, 125,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex40-twin-motor-performance-(my26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX90 Single Motor', 100, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex90-single-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX90 Single Motor (MY24-26)', 100, 180, 140,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex90-single-motor-(my24-26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX90 Twin Motor', 107, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex90-twin-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX90 Twin Motor (MY24-26)', 107, 180, 150,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex90-twin-motor-(my24-26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX90 Twin Motor Performance', 107, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex90-twin-motor-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'EX90 Twin Motor Performance (MY24-26)', 107, 180, 150,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_ex90-twin-motor-performance-(my24-26).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'XC40', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_xc40.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'XC40 P8 AWD Recharge', 75, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_xc40-p8-awd-recharge.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'XC40 Recharge Pure Electric', 67, 160, NULL,
    'CCS', NULL, 'FWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_xc40-recharge-pure-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'XC40 Recharge Single Motor', 66, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_xc40-recharge-single-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'XC40 Recharge Single Motor ER', 79, 180, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_xc40-recharge-single-motor-er.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'XC40 Recharge Twin Motor', 79, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_xc40-recharge-twin-motor.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Volvo', 'XC40 Recharge Twin Pure Electric', 75, 180, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/volvo_xc40-recharge-twin-pure-electric.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Voyah', 'Courage', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/voyah_courage.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Voyah', 'Courage 80 kWh AWD', 77.3, 200, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/voyah_courage-80-kwh-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Voyah', 'Courage 80 kWh RWD', 77.3, 200, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/voyah_courage-80-kwh-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Voyah', 'Dream', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/voyah_dream.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Voyah', 'Free 106 kWh', 100, 200, 80,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/voyah_free-106-kwh.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'XPeng', 'G6', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/xpeng_g6.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'XPENG', 'G6 AWD Performance', 87.5, 200, 180,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/xpeng_g6-awd-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'XPENG', 'G6 RWD Long Range', 87.5, 200, 180,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/xpeng_g6-rwd-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'XPENG', 'G6 RWD Standard Range', 65.3, 200, 140,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/xpeng_g6-rwd-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'XPeng', 'G9', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/xpeng_g9.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'XPENG', 'G9 AWD Performance', 93.1, 200, 224,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/xpeng_g9-awd-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'XPENG', 'G9 RWD Long Range', 93.1, 200, 224,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/xpeng_g9-rwd-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'XPENG', 'G9 RWD Standard Range', 75.8, 200, 170,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/xpeng_g9-rwd-standard-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'XPeng', 'P7', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/xpeng_p7.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'XPENG', 'P7 AWD Performance', 82.7, 200, 138,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/xpeng_p7-awd-performance.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'XPENG', 'P7 RWD Long Range', 82.7, 200, 138,
    'CCS', 'Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/xpeng_p7-rwd-long-range.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'XPENG', 'P7 Wing Edition', 82.7, 200, 138,
    'CCS', 'Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/xpeng_p7-wing-edition.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'ZD', 'D2S', NULL, NULL, NULL,
    'CCS', NULL, NULL, 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zd_d2s.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Zeekr', '001 Long Range RWD', 94, 200, 135,
    'CCS', 'Liftback Sedan', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zeekr_001-long-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Zeekr', '001 Performance AWD', 94, 200, 135,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zeekr_001-performance-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Zeekr', '001 Privilege AWD', 94, 200, 135,
    'CCS', 'Liftback Sedan', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zeekr_001-privilege-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Zeekr', '7X Long Range RWD', 94, 210, 260,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zeekr_7x-long-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Zeekr', '7X Performance AWD', 94, 210, 260,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zeekr_7x-performance-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Zeekr', '7X Premium RWD', 71, 210, 240,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zeekr_7x-premium-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Zeekr', 'X Core RWD', 49, 190, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zeekr_x-core-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Zeekr', 'X Core RWD (MY25)', 49, 190, 70,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zeekr_x-core-rwd-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Zeekr', 'X Long Range RWD', 65, 190, NULL,
    'CCS', NULL, 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zeekr_x-long-range-rwd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Zeekr', 'X Long Range RWD (MY25)', 65, 190, 114,
    'CCS', 'SUV', 'RWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zeekr_x-long-range-rwd-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Zeekr', 'X Privilege AWD', 65, 190, NULL,
    'CCS', NULL, 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zeekr_x-privilege-awd.png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
),
(
    'Zeekr', 'X Privilege AWD (MY25)', 65, 190, 114,
    'CCS', 'SUV', 'AWD', 'https://na-tomada-s3-bucket.s3.us-east-2.amazonaws.com/zeekr_x-privilege-awd-(my25).png',
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
)
;
