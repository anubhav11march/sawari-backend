CREATE TABLE `marshall_service`.`profile_contents` (
                                                    `content_id` BIGINT NOT NULL,
                                                    `content_user_id` INT NOT NULL,
                                                    `profile_photo_name` VARCHAR(100) NULL,
                                                    `profile_photo_path` VARCHAR(100) NULL,
                                                    `profile_photo_size` INT NULL,
                                                    `aadhar_back_photo_name` VARCHAR(100) NULL,
                                                    `aadhar_back_photo_path` VARCHAR(100) NULL,
                                                    `aadhar_back_photo_size` INT NULL,
                                                    `aadhar_front_photo_name` VARCHAR(100) NULL,
                                                    `aadhar_front_photo_path` VARCHAR(100) NULL,
                                                    `aadhar_front_photo_size` INT NULL,
                                                    `rickshaw_photo_name` VARCHAR(100) NULL,
                                                    `rickshaw_photo_path` VARCHAR(100) NULL,
                                                    `rickshaw_photo_size` INT NULL,
                                                    PRIMARY KEY (`content_id`),
                                                    INDEX `content_user_id_idx` (`content_user_id` ASC) VISIBLE,
                                                    CONSTRAINT `content_user_id`
                                                        FOREIGN KEY (`content_user_id`)
                                                            REFERENCES `marshall_service`.`user_registration` (`user_id`)
                                                            ON DELETE RESTRICT
                                                            ON UPDATE CASCADE);

ALTER TABLE `marshall_service`.`profile_contents`
    ADD COLUMN `upload_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP AFTER `rickshaw_photo_size`,
    ADD COLUMN `modify_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP AFTER `upload_date`,
    ADD COLUMN `is_deleted` TINYINT NULL DEFAULT 0 AFTER `modify_date`;

ALTER TABLE `marshall_service`.`profile_contents`
CHANGE COLUMN `content_id` `content_id` BIGINT NOT NULL AUTO_INCREMENT ;

ALTER TABLE `marshall_service`.`confirmation_token`
ADD COLUMN `user_type` VARCHAR(45) NULL AFTER `req_type`;


USE `marshall_service`;
DROP procedure IF EXISTS `spUserLogin`;

USE `marshall_service`;
DROP procedure IF EXISTS `marshall_service`.`spUserLogin`;

DELIMITER $$
USE `marshall_service`$$
CREATE DEFINER=`admin`@`localhost` PROCEDURE `spUserLogin`(
IN userName varchar(255)
)
BEGIN
Select u.user_id,u.user_name, u.first_name, u.middle_name, u.last_name, s.email, u.phone, u.mobile, u.password, u.is_deleted, u.is_enable, r.role_name, t.type_name
from ((user_role_type_bridge b inner join role r on b.user_role_id=r.role_id) inner join user_type t on b.user_type_id=t.type_id) inner join
(user_registration u inner join subscribe_by_email s on u.subs_id=s.subs_id)
on b.user_id=u.user_id
where (u.user_name=userName or s.email=userName or u.mobile=userName) and u.is_deleted=false and u.is_enable=true;
END$$

DELIMITER ;


ALTER TABLE `marshall_service`.`user_registration`
CHANGE COLUMN `first_name` `first_name` VARCHAR(45) NOT NULL DEFAULT 'First-Name' ,
CHANGE COLUMN `last_name` `last_name` VARCHAR(45) NULL ,
CHANGE COLUMN `is_enable` `is_enable` TINYINT NOT NULL DEFAULT 0 ;


ALTER TABLE `marshall_service`.`profile_contents`
ADD COLUMN `aadhar_number` VARCHAR(12) NULL AFTER `profile_photo_size`,
ADD COLUMN `rickshaw_number` VARCHAR(20) NULL AFTER `aadhar_front_photo_size`;

ALTER TABLE `marshall_service`.`profile_contents`
DROP FOREIGN KEY `content_user_id`;
ALTER TABLE `marshall_service`.`profile_contents`
DROP INDEX `content_user_id_idx` ;


USE `marshall_service`;
DROP procedure IF EXISTS `spUserRoleTypeBridge`;

USE `marshall_service`;
DROP procedure IF EXISTS `marshall_service`.`spUserRoleTypeBridge`;

DELIMITER $$
USE `marshall_service`$$
CREATE DEFINER=`admin`@`localhost` PROCEDURE `spUserRoleTypeBridge`(
IN userName varchar(50),
IN userRoleName varchar(25),
IN userTypeName varchar(25)
)
BEGIN
Declare userRoleId int;
Declare userTypeId int;
Declare userId int;

Select role_id into userRoleId from role where role_name=userRoleName;
Select type_id into userTypeId from user_type where type_name=userTypeName;

insert into user_role_type_bridge(user_id,user_role_id,user_type_id)
Select distinct user_id, role_id,type_id from (user_registration inner join (role inner join user_type))
 where ((role_id=userRoleId and type_id=userTypeId) and user_id=(Select user_id from user_registration where user_name=userName or mobile=userName));
END$$

DELIMITER ;



--Foreign Key at profile_contents
ALTER TABLE `marshall_service`.`profile_contents`
ADD INDEX `profile_user_id_idx` (`profile_user_id` ASC) VISIBLE;
;
ALTER TABLE `marshall_service`.`profile_contents`
ADD CONSTRAINT `profile_user_id`
  FOREIGN KEY (`profile_user_id`)
  REFERENCES `marshall_service`.`user_registration` (`user_id`)
  ON DELETE RESTRICT
  ON UPDATE CASCADE;


--driverStatusForBookingRequest
CREATE TABLE `marshall_service`.`driver_status` (
  `driver_id` INT NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`driver_id`),
  UNIQUE INDEX `id_UNIQUE` (`driver_id` ASC) VISIBLE);


CREATE TABLE `marshall_service`.`user_location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `latitude` VARCHAR(45) NULL,
  `longitude` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE);


CREATE TABLE `marshall_service`.`driver_booking_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `booking_request_id` INT NULL,
  `driver_id` INT NULL,
  `booking_status` VARCHAR(10) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `booking_UNIQUE` (`booking_request_id` ASC, `driver_id` ASC, `booking_status` ASC) VISIBLE);


CREATE TABLE `marshall_service`.`ride_request` (
  `booking_request_id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NULL,
  `mobile_no` VARCHAR(10) NULL,
  `pickup_location_points` VARCHAR(45) NULL,
  `drop_location_points` VARCHAR(45) NULL,
  `passengers` VARCHAR(1) NULL,
  `distance` DECIMAL(4,2) NULL,
  `fare` DECIMAL(6,3) NULL,
  `currency` VARCHAR(3) NULL DEFAULT 'INR',
  `otp` VARCHAR(10) NULL,
  `booking_status` VARCHAR(10) NULL,
  `payment_mode` VARCHAR(10) NULL,
  `payment_status` VARCHAR(10) NULL DEFAULT 'UNPAID',
  `driver_id` INT NULL,
  `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`booking_request_id`),
  UNIQUE INDEX `request_id_UNIQUE` (`booking_request_id` ASC) VISIBLE);

--adding pickup & drop location as words
ALTER TABLE `marshall_service`.`ride_request`
ADD COLUMN `pickup_location_word` VARCHAR(155) NULL AFTER `pickup_location_points`,
ADD COLUMN `drop_location_word` VARCHAR(155) NULL AFTER `drop_location_points`;

--add discount column in ride_request
ALTER TABLE `marshall_service`.`ride_request`
ADD COLUMN `discount` DECIMAL(6,3) NULL AFTER `fare`;

--add date col
ALTER TABLE `marshall_service`.`ride_request`
ADD COLUMN `modify_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP AFTER `driver_id`,
CHANGE COLUMN `date` `date` DATE NULL FIRST;

--firebase
CREATE TABLE `marshall_service`.`driver_firebase_token` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `driver_id` INT NULL,
  `token` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `driver_id_UNIQUE` (`driver_id` ASC) VISIBLE);

--add customer name
ALTER TABLE `marshall_service`.`ride_request`
ADD COLUMN `customer_name` VARCHAR(45) NULL AFTER `customer_id`;

  commit;
