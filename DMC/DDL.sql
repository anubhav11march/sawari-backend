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

