UPDATE `marshall_service`.`role` SET `role_name` = 'USER' WHERE (`role_id` = '1');
UPDATE `marshall_service`.`role` SET `role_name` = 'SP-USER' WHERE (`role_id` = '2');
UPDATE `marshall_service`.`role` SET `role_name` = 'DRIVER' WHERE (`role_id` = '3');

UPDATE `marshall_service`.`properties` SET `value` = 'application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/postscript,image/jpeg,image/png,application/pdf,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.rar,image/tiff,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/zip' WHERE (`id` = '3');

UPDATE `marshall_service`.`properties` SET `value` = '/var/marshall/docs/driver/' WHERE (`id` = '16');

--profile update
ALTER TABLE `marshall_service`.`profile_contents`
ADD COLUMN `paytm_number` VARCHAR(10) NULL DEFAULT NULL AFTER `aadhar_number`,
ADD COLUMN `rickshaw_back_photo_name` VARCHAR(100) NULL DEFAULT NULL AFTER `rickshaw_front_photo_size`,
ADD COLUMN `rickshaw_back_photo_path` VARCHAR(100) NULL DEFAULT NULL AFTER `rickshaw_back_photo_name`,
ADD COLUMN `rickshaw_back_photo_size` INT NULL DEFAULT NULL AFTER `rickshaw_back_photo_path`,
ADD COLUMN `rickshaw_side_photo_name` VARCHAR(100) NULL DEFAULT NULL AFTER `rickshaw_back_photo_size`,
ADD COLUMN `rickshaw_side_photo_path` VARCHAR(100) NULL DEFAULT NULL AFTER `rickshaw_side_photo_name`,
ADD COLUMN `rickshaw_side_photo_size` INT NULL DEFAULT NULL AFTER `rickshaw_side_photo_path`,
CHANGE COLUMN `rickshaw_photo_name` `rickshaw_front_photo_name` VARCHAR(100) NULL DEFAULT NULL ,
CHANGE COLUMN `rickshaw_photo_path` `rickshaw_front_photo_path` VARCHAR(100) NULL DEFAULT NULL ,
CHANGE COLUMN `rickshaw_photo_size` `rickshaw_front_photo_size` INT NULL DEFAULT NULL ,
ADD UNIQUE INDEX `paytm_number_UNIQUE` (`paytm_number` ASC) VISIBLE,
ADD UNIQUE INDEX `aadhar_number_UNIQUE` (`aadhar_number` ASC) VISIBLE,
ADD UNIQUE INDEX `rickshaw_number_UNIQUE` (`rickshaw_number` ASC) VISIBLE;
;
UPDATE `marshall_service`.`properties` SET `value` = '/var/marshall/docs/' WHERE (`id` = '45');

--fare
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('DISTANCE_RANGE', '0-2,2.1-3.5,3.6-4.5,4.6-5.5,5.6-7');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('DISTANCE_THRESHOLD', '5.6');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('MAX_DISTANCE', '7.0');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('BASE_PRICE', '10');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('DISTANCE_FACTOR', '5');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('DISTANCE_SURCHARGE', '15');

--fare
UPDATE `marshall_service`.`properties` SET `value` = '0-2,2.1-3.5,3.6-4.5,4.6-6,6.1-7' WHERE (`id` = '54');
UPDATE `marshall_service`.`properties` SET `value` = '6.1' WHERE (`id` = '55');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('DISCOUNT_FARE_THRESHOLD', '100');

--location/commsion
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('COMMISSION', '20');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('WRITE_LOCATION_INTERVAL', '15');

--MAP
UPDATE `marshall_service`.`properties` SET `value` = '15' WHERE (`name` = 'COMMISSION');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('REQUEST_ACCEPT_INTERVAL', '20000');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('BOOKING_RADIUS', '3.0');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('MAP_BASE_URL', 'https://maps.googleapis.com/maps/api');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('MAP_DISTANCE_PATH', '/distancematrix/json');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('MAP_KEY', 'AIzaSyANybxLdy_U-1VYJdrh7XBzHqePU1CQ-8c');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('FIREBASE_URL', 'https://fcm.googleapis.com/fcm/send');
INSERT INTO `marshall_service`.`properties` (`name`, `value`) VALUES ('FIREBASE_KEY', 'AAAATM9x-Vk:APA91bGds48ddhXCfGjmCHWUkMeKAVDM7EJ_3Fy7DDkMud2U1LnJVec7aL87lVFTOJIQcCBOwLnoN8w4rBc_qf3IAaNEYZQ8XY1pTya4AGkGesKx5S-Nu42A0--G7xJVG2A2HnCz-8Ux');

