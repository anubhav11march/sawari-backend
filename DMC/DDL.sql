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

