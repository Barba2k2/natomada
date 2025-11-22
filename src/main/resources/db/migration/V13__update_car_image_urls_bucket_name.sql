-- Update existing car image URLs to use correct S3 bucket name and region
-- Changes from 'natomada-images' to 'na-tomada-s3-bucket' and 'us-east-1' to 'us-east-2'

UPDATE cars
SET image_url = REPLACE(REPLACE(image_url, 'natomada-images', 'na-tomada-s3-bucket'), 'us-east-1', 'us-east-2')
WHERE image_url LIKE '%amazonaws.com%';
