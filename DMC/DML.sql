UPDATE `marshall_service`.`role` SET `role_name` = 'USER' WHERE (`role_id` = '1');
UPDATE `marshall_service`.`role` SET `role_name` = 'SP-USER' WHERE (`role_id` = '2');
UPDATE `marshall_service`.`role` SET `role_name` = 'DRIVER' WHERE (`role_id` = '3');

UPDATE `marshall_service`.`properties` SET `value` = 'application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/postscript,image/jpeg,image/png,application/pdf,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.rar,image/tiff,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/zip' WHERE (`id` = '3');
