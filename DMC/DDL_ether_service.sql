CREATE TABLE `ether_service`.`content_report`
(
    `admin_file_id`     int          NOT NULL AUTO_INCREMENT,
    `file_user_id`      int          NOT NULL,
    `author_file_id`    int          NOT NULL,
    `author_user_id`    int          NOT NULL,
    `file_name`         varchar(100) NOT NULL,
    `file_format`       varchar(155) NOT NULL,
    `report_type`       varchar(45)  NOT NULL,
    `topic`             varchar(100) NOT NULL,
    `category`          varchar(45)  NOT NULL,
    `description`       varchar(500)          DEFAULT NULL,
    `language`          varchar(45)           DEFAULT 'ENGLISH',
    `proposed_services` varchar(255)          DEFAULT NULL,
    `path`              varchar(150)          DEFAULT NULL,
    `size`              int                   DEFAULT NULL,
    `upload_date`       timestamp    NULL     DEFAULT CURRENT_TIMESTAMP,
    `modify_date`       timestamp    NULL     DEFAULT NULL,
    `is_deleted`        tinyint      NOT NULL DEFAULT '0',
    PRIMARY KEY (`admin_file_id`),
    UNIQUE KEY `admin_file_id_UNIQUE` (`admin_file_id`),
    KEY `file_user_id_idx` (`file_user_id`),
    KEY `author_file_id_idx` (`author_file_id`),
    KEY `author_user_id_idx` (`author_user_id`),
    CONSTRAINT `admin_user_id` FOREIGN KEY (`file_user_id`) REFERENCES `ether_service`.`user_registration` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `author_file_id` FOREIGN KEY (`author_file_id`) REFERENCES `ether_service`.`content` (`author_file_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4;

DROP TABLE `ether_service`.`content`;

CREATE TABLE `ether_service`.`content`
(
    `author_file_id`     int          NOT NULL AUTO_INCREMENT,
    `file_user_id`       int          NOT NULL,
    `file_name`          varchar(100) NOT NULL,
    `file_format`        varchar(155) NOT NULL,
    `topic`              varchar(100) NOT NULL,
    `category`           varchar(45)  NOT NULL,
    `description`        varchar(500)          DEFAULT NULL,
    `language`           varchar(45)           DEFAULT 'ENGLISH',
    `words_count`        varchar(45)           DEFAULT NULL,
    `number_of_figures`  varchar(5)            DEFAULT NULL,
    `requested_services` varchar(255)          DEFAULT NULL,
    `file_status`        varchar(45)           DEFAULT 'Uploaded',
    `path`               varchar(150)          DEFAULT NULL,
    `size`               int                   DEFAULT NULL,
    `upload_date`        timestamp    NULL     DEFAULT CURRENT_TIMESTAMP,
    `request_serve_date` date                  DEFAULT NULL,
    `modify_date`        timestamp    NULL     DEFAULT NULL,
    `is_deleted`         tinyint      NOT NULL DEFAULT '0',
    PRIMARY KEY (`author_file_id`),
    UNIQUE KEY `author_file_id_UNIQUE` (`author_file_id`) /*!80000 INVISIBLE */,
    KEY `file_user_id_idx` (`file_user_id`),
    CONSTRAINT `file_user_id` FOREIGN KEY (`file_user_id`) REFERENCES `ether_service`.`user_registration` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 11
  DEFAULT CHARSET = utf8mb4;


INSERT INTO ether_service.properties (name, value) VALUES ('status', 'Uploaded,Downloaded,Review Initiated,Review Pending,Review Complete,Preliminary-Report Submitted,Final-Report Pending,Final-Report Submitted,Close');

# 16/01/20211
ALTER TABLE `ether_service`.`order_details`
    ADD COLUMN `modify_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP AFTER `remark`,
    CHANGE COLUMN `remark` `remark` VARCHAR(100) NULL DEFAULT 'Remark' ;

ALTER TABLE `ether_service`.`order_details`
    DROP FOREIGN KEY `plan_id`;
ALTER TABLE `ether_service`.`order_details`
    ADD COLUMN `report_id` INT NOT NULL AFTER `file_id`,
    ADD COLUMN `estimated_amount` VARCHAR(45) NULL AFTER `selected_services`,
    CHANGE COLUMN `plan_id` `selected_services` VARCHAR(150) NULL DEFAULT NULL ,
    ADD INDEX `report_id_idx` (`report_id` ASC) VISIBLE,
    DROP INDEX `plan_id_idx` ;
;
ALTER TABLE `ether_service`.`order_details`
    ADD CONSTRAINT `report_id`
        FOREIGN KEY (`report_id`)
            REFERENCES `ether_service`.`content_report` (`admin_file_id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE;

USE `ether_service`;
DROP procedure IF EXISTS `spInsertOrUpdateTransactionDetails`;

USE `ether_service`;
DROP procedure IF EXISTS `ether_service`.`spInsertOrUpdateTransactionDetails`;
;

DELIMITER $$
USE `ether_service`$$
CREATE DEFINER=`admin`@`%` PROCEDURE `spInsertOrUpdateTransactionDetails`(
    IN userBillingId int,
    IN orderId int,
    IN transactionId varchar(45),
    IN amount double,
    IN currency varchar(5),
    IN screenshotPath varchar(150),
    IN paymentMode varchar(15),
    IN paymentDate varchar(10),
    IN modifyDate timestamp,
    IN paymentStatus varchar(15)
)
BEGIN
    INSERT INTO transaction_details (`user_billing_id`,`order_id`,`transaction_id`,`amount`,`currency`,`screenshot_path`,`payment_mode`,`payment_date`, `modify_date`,
                                     `payment_status`)
        VALUES (userBillingId, orderId, transactionId, amount, currency, screenshotPath, paymentMode, paymentDate, modifyDate, paymentStatus) AS alias
    ON DUPLICATE KEY UPDATE
                         amount=alias.amount, currency=alias.currency, screenshot_path=alias.screenshot_path, payment_mode=alias.payment_mode,
                         payment_date=alias.payment_date, modify_date=alias.modify_date, payment_status=alias.payment_status;

    UPDATE order_details SET is_paid=true WHERE order_id=orderId;

    SELECT * FROM transaction_details WHERE user_billing_id=userBillingId AND order_id=orderId AND transaction_id=transactionId AND payment_date=paymentDate;
END$$

DELIMITER ;
;



commit;