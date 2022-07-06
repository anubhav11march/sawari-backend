--View 1--
CREATE
    ALGORITHM = UNDEFINED
    DEFINER = `root`@`localhost`
    SQL SECURITY DEFINER
VIEW `ether_service`.`view_incomplete_user_details` AS
    SELECT
        `ur`.`user_id` AS `user_id`,
        `ur`.`first_name` AS `first_name`,
        `ur`.`middle_name` AS `middle_name`,
        `ur`.`last_name` AS `last_name`,
        `ur`.`user_name` AS `user_name`,
        `ur`.`phone` AS `phone`,
        `ur`.`mobile` AS `mobile`,
        `subs`.`email` AS `email`,
        `subs`.`alternate_email` AS `alternate_email`,
        `ether_service`.`vur`.`role_name` AS `role_name`,
        `ether_service`.`vur`.`type_name` AS `type_name`,
        `ether_service`.`vur`.`permission_name` AS `permission_name`,
        `ur`.`join_date` AS `join_date`,
        `ur`.`is_deleted` AS `is_deleted`,
        `ur`.`is_enable` AS `is_enable`
    FROM
        ((`ether_service`.`user_registration` `ur`
        JOIN `ether_service`.`view_user_rights` `vur`)
        JOIN `ether_service`.`subscribe_by_email` `subs`)
    WHERE
        ((`ur`.`user_id` = `ether_service`.`vur`.`user_id`)
            AND (`ur`.`subs_id` = `subs`.`subs_id`));

--View 2--

CREATE
    ALGORITHM = UNDEFINED
    DEFINER = `admin`@`localhost`
    SQL SECURITY DEFINER
VIEW `ether_service`.`view_user_rights` AS
    SELECT
        `ur`.`user_id` AS `user_id`,
        `ub`.`bridge_id` AS `bridge_id`,
        `upa`.`associate_id` AS `associate_id`,
        `ur`.`first_name` AS `first_name`,
        `ur`.`middle_name` AS `middle_name`,
        `ur`.`last_name` AS `last_name`,
        `ur`.`user_name` AS `user_name`,
        `r`.`role_name` AS `role_name`,
        `t`.`type_name` AS `type_name`,
        `p`.`permission_name` AS `permission_name`,
        `ur`.`join_date` AS `join_date`,
        `ur`.`is_enable` AS `is_enable`,
        `ur`.`is_deleted` AS `is_deleted`
    FROM
        (((((`ether_service`.`user_registration` `ur`
        JOIN `ether_service`.`role` `r`)
        JOIN `ether_service`.`user_type` `t`)
        JOIN `ether_service`.`user_role_type_bridge` `ub`)
        JOIN `ether_service`.`permissions` `p`)
        JOIN `ether_service`.`user_permission_associate` `upa`)
    WHERE
        ((`ur`.`user_id` = `ub`.`user_id`)
            AND (`ub`.`user_role_id` = `r`.`role_id`)
            AND (`ub`.`user_type_id` = `t`.`type_id`)
            AND (`ur`.`user_id` = `upa`.`user_permission_id`)
            AND (`upa`.`permission_id` = `p`.`permission_id`));

--Stored Procedure--

CREATE DEFINER=`admin`@`localhost` PROCEDURE `spUpdateUserRights`(
IN userId int,
IN roleName varchar(25),
IN typeName varchar(25),
IN permissionName varchar(25),
IN associatedId int,
IN bridgeId int,
IN isDeleted tinyint,
IN isEnable tinyint
)
BEGIN
Declare userRoleId int;
Declare userTypeId int;
Declare userPermissionId int;

Select role_id into userRoleId from role where role_name=roleName;
Select type_id into userTypeId from user_type where type_name=typeName;
Select permission_id into userPermissionId from permissions where permission_name=permissionName;

UPDATE user_role_type_bridge SET user_role_id=userRoleId, user_type_id=userTypeId WHERE user_id=userId AND bridge_id=bridgeId;
UPDATE user_permission_associate SET permission_id=userPermissionId WHERE user_permission_id=userId AND associate_id=associateId;
UPDATE user_registration SET is_enable=isEnable, is_deleted=isDeleted WHERE user_id=userId;

END

commit;