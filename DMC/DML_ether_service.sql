UPDATE ether_service.properties SET value = '/init/enable-account?reqType=enableAccount&token=' WHERE name = 'email_confirmation_link';
UPDATE ether_service.properties SET value = 'domnemmzakbkqeqw' WHERE name = 'support_email_password';

ALTER TABLE `ether_service`.`content`
    CHANGE COLUMN `file_name` `file_name` VARCHAR(500) NOT NULL ,
    CHANGE COLUMN `description` `description` MEDIUMTEXT NULL DEFAULT NULL ;


commit;