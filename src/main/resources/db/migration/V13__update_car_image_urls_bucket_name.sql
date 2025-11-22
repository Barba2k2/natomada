-- Update existing car image URLs to use correct S3 bucket name
-- Changes from 'natomada-images' to 'na-tomada-s3-bucket'

UPDATE cars
SET image_url = REPLACE(image_url, 'natomada-images.s3.us-east-1.amazonaws.com', 'na-tomada-s3-bucket.s3.us-east-1.amazonaws.com')
WHERE image_url LIKE '%natomada-images.s3.us-east-1.amazonaws.com%';
