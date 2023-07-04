-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: new_marshall_service
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `confirmation_token`
--

DROP TABLE IF EXISTS `confirmation_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `confirmation_token` (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `token` varchar(500) NOT NULL,
                                      `req_type` varchar(45) DEFAULT NULL,
                                      `user_type` varchar(45) DEFAULT NULL,
                                      `email` varchar(100) NOT NULL,
                                      `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                      `expires_at` timestamp NULL DEFAULT NULL,
                                      `confirms_at` timestamp NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `id_UNIQUE` (`id`),
                                      UNIQUE KEY `token_UNIQUE` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirmation_token`
--

LOCK TABLES `confirmation_token` WRITE;
/*!40000 ALTER TABLE `confirmation_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `confirmation_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `content`
--

DROP TABLE IF EXISTS `content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `content` (
                           `author_file_id` int NOT NULL AUTO_INCREMENT,
                           `file_user_id` int NOT NULL,
                           `file_name` varchar(500) NOT NULL,
                           `file_format` varchar(155) NOT NULL,
                           `topic` varchar(100) NOT NULL,
                           `category` varchar(45) NOT NULL,
                           `description` mediumtext,
                           `language` varchar(45) DEFAULT 'ENGLISH',
                           `words_count` varchar(45) DEFAULT NULL,
                           `number_of_figures` varchar(5) DEFAULT NULL,
                           `requested_services` varchar(255) DEFAULT NULL,
                           `file_status` varchar(45) DEFAULT 'Uploaded',
                           `path` varchar(150) DEFAULT NULL,
                           `size` int DEFAULT NULL,
                           `upload_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                           `request_serve_date` date DEFAULT NULL,
                           `modify_date` timestamp NULL DEFAULT NULL,
                           `is_deleted` tinyint NOT NULL DEFAULT '0',
                           PRIMARY KEY (`author_file_id`),
                           UNIQUE KEY `author_file_id_UNIQUE` (`author_file_id`) /*!80000 INVISIBLE */,
                           KEY `file_user_id_idx` (`file_user_id`),
                           CONSTRAINT `file_user_id` FOREIGN KEY (`file_user_id`) REFERENCES `user_registration` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content`
--

LOCK TABLES `content` WRITE;
/*!40000 ALTER TABLE `content` DISABLE KEYS */;
/*!40000 ALTER TABLE `content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `content_report`
--

DROP TABLE IF EXISTS `content_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `content_report` (
                                  `admin_file_id` int NOT NULL AUTO_INCREMENT,
                                  `file_user_id` int NOT NULL,
                                  `author_file_id` int NOT NULL,
                                  `author_user_id` int NOT NULL,
                                  `file_name` varchar(100) NOT NULL,
                                  `file_format` varchar(155) NOT NULL,
                                  `report_type` varchar(45) NOT NULL,
                                  `topic` varchar(100) NOT NULL,
                                  `category` varchar(45) NOT NULL,
                                  `description` varchar(500) DEFAULT NULL,
                                  `language` varchar(45) DEFAULT 'ENGLISH',
                                  `proposed_services` varchar(255) DEFAULT NULL,
                                  `path` varchar(150) DEFAULT NULL,
                                  `size` int DEFAULT NULL,
                                  `upload_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                  `modify_date` timestamp NULL DEFAULT NULL,
                                  `is_deleted` tinyint NOT NULL DEFAULT '0',
                                  PRIMARY KEY (`admin_file_id`),
                                  UNIQUE KEY `admin_file_id_UNIQUE` (`admin_file_id`),
                                  KEY `file_user_id_idx` (`file_user_id`),
                                  KEY `author_file_id_idx` (`author_file_id`),
                                  KEY `author_user_id_idx` (`author_user_id`),
                                  CONSTRAINT `admin_user_id` FOREIGN KEY (`file_user_id`) REFERENCES `user_registration` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                  CONSTRAINT `author_file_id` FOREIGN KEY (`author_file_id`) REFERENCES `content` (`author_file_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `content_report`
--

LOCK TABLES `content_report` WRITE;
/*!40000 ALTER TABLE `content_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `content_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_otp`
--

DROP TABLE IF EXISTS `email_otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_otp` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `email` varchar(150) DEFAULT NULL,
                             `otp` varchar(10) DEFAULT NULL,
                             `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                             `expires_at` timestamp NULL DEFAULT NULL,
                             `confirms_at` timestamp NULL DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `id_UNIQUE` (`id`),
                             UNIQUE KEY `otp_UNIQUE` (`otp`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_otp`
--

LOCK TABLES `email_otp` WRITE;
/*!40000 ALTER TABLE `email_otp` DISABLE KEYS */;
/*!40000 ALTER TABLE `email_otp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback_response`
--

DROP TABLE IF EXISTS `feedback_response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback_response` (
                                     `feedback_id` int NOT NULL AUTO_INCREMENT,
                                     `user_feedback_id` int NOT NULL DEFAULT '0',
                                     `subject` text,
                                     `feedback` text NOT NULL,
                                     `response` text NOT NULL,
                                     `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     PRIMARY KEY (`feedback_id`),
                                     UNIQUE KEY `feedback_id_UNIQUE` (`feedback_id`),
                                     KEY `user_id_idx` (`user_feedback_id`),
                                     CONSTRAINT `user_feedback_id` FOREIGN KEY (`user_feedback_id`) REFERENCES `user_registration` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_response`
--

LOCK TABLES `feedback_response` WRITE;
/*!40000 ALTER TABLE `feedback_response` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback_response` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
                                 `order_id` bigint NOT NULL AUTO_INCREMENT,
                                 `author_id` int NOT NULL DEFAULT '0',
                                 `file_id` int NOT NULL,
                                 `admin_id` int NOT NULL,
                                 `report_id` int NOT NULL,
                                 `selected_services` varchar(150) DEFAULT NULL,
                                 `estimated_amount` double DEFAULT '0',
                                 `currency` varchar(5) DEFAULT 'USD',
                                 `order_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 `expiry_date` timestamp NOT NULL COMMENT 'select register_date, datediff(current_timestamp, register_date) as time from user where datediff(current_timestamp, register_date)>15;',
                                 `warning_date` timestamp NOT NULL,
                                 `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                 `remark` varchar(100) DEFAULT 'Remark',
                                 `status` varchar(10) NOT NULL,
                                 `is_active` tinyint NOT NULL DEFAULT '0',
                                 `is_paid` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`order_id`),
                                 UNIQUE KEY `order_id_UNIQUE` (`order_id`),
                                 UNIQUE KEY `file_id_UNIQUE` (`file_id`),
                                 KEY `author_id_idx` (`author_id`),
                                 KEY `file_id_idx` (`file_id`),
                                 KEY `report_id_idx` (`report_id`),
                                 KEY `admin_id_idx` (`admin_id`),
                                 CONSTRAINT `admin_id` FOREIGN KEY (`admin_id`) REFERENCES `content_report` (`file_user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                 CONSTRAINT `author_id` FOREIGN KEY (`author_id`) REFERENCES `user_registration` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                 CONSTRAINT `file_id` FOREIGN KEY (`file_id`) REFERENCES `content` (`author_file_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                 CONSTRAINT `report_id` FOREIGN KEY (`report_id`) REFERENCES `content_report` (`admin_file_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
                               `permission_id` int NOT NULL AUTO_INCREMENT,
                               `permission_name` varchar(45) NOT NULL DEFAULT 'Permission',
                               PRIMARY KEY (`permission_id`),
                               UNIQUE KEY `permission_id_UNIQUE` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_contents`
--

DROP TABLE IF EXISTS `profile_contents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile_contents` (
                                    `content_id` bigint NOT NULL AUTO_INCREMENT,
                                    `content_user_id` int NOT NULL,
                                    `profile_photo_name` varchar(100) DEFAULT NULL,
                                    `profile_photo_path` varchar(100) DEFAULT NULL,
                                    `profile_photo_size` int DEFAULT NULL,
                                    `aadhar_number` varchar(12) DEFAULT NULL,
                                    `aadhar_back_photo_name` varchar(100) DEFAULT NULL,
                                    `aadhar_back_photo_path` varchar(100) DEFAULT NULL,
                                    `aadhar_back_photo_size` int DEFAULT NULL,
                                    `aadhar_front_photo_name` varchar(100) DEFAULT NULL,
                                    `aadhar_front_photo_path` varchar(100) DEFAULT NULL,
                                    `aadhar_front_photo_size` int DEFAULT NULL,
                                    `rickshaw_number` varchar(20) DEFAULT NULL,
                                    `rickshaw_photo_name` varchar(100) DEFAULT NULL,
                                    `rickshaw_photo_path` varchar(100) DEFAULT NULL,
                                    `rickshaw_photo_size` int DEFAULT NULL,
                                    `upload_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                    `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                    `is_deleted` tinyint DEFAULT '0',
                                    PRIMARY KEY (`content_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_contents`
--

LOCK TABLES `profile_contents` WRITE;
/*!40000 ALTER TABLE `profile_contents` DISABLE KEYS */;
/*!40000 ALTER TABLE `profile_contents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `properties`
--

DROP TABLE IF EXISTS `properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `properties` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `name` varchar(45) NOT NULL,
                              `value` varchar(500) DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `id_UNIQUE` (`id`),
                              UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `properties`
--

LOCK TABLES `properties` WRITE;
/*!40000 ALTER TABLE `properties` DISABLE KEYS */;
INSERT INTO `properties` VALUES (30,'name_prefix','Dr.,Prof.,Mr.,Ms.,Mrs.,MD.,PD.'),(31,'category','List,Microbiology,Public Health,Nursing,Clinical Medicine,Molecular Biology,Bioinformatics,Meta-Analysis,Others'),(32,'allowed_file_formats','application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/postscript,image/jpeg,image/png,application/pdf,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.rar,image/tiff,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/zip'),(33,'topics','Manuscript,Figure Only,Poster,Thesis,Grant'),(34,'language','Arabic,Chinese,English,Japanese,Spanish'),(35,'support_email','alphaeditorialsciencehub@gmail.com'),(36,'support_email_password','mtomkzslidyfiyns'),(37,'password_regex','(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&-+=()])(?=.*[A-Z]).{8,16}'),(38,'password_reset_link','/init/reset-password?reqType=resetPassword&token='),(39,'password_subject','Request Reset Password'),(40,'password_mail_body','Please click on the below link to reset your password:'),(41,'email_regex','^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$'),(42,'email_confirmation_link','/init/enable-account?reqType=enableAccount&token='),(43,'email_subject','Please Verify Your Email'),(44,'email_mail_body','Thank you for registering with us. Please click on the below link to activate your account:'),(45,'upload_directory','/var/marshall/docs/driver/'),(46,'status','Uploaded,Downloaded,Review Initiated,Review Pending,Review Complete,Preliminary-Report Submitted,Final-Report Pending,Final-Report Submitted,Close'),(47,'admin_email','alphaeditorialsciencehub@gmail.com'),(48,'alipay_qr','src\\main\\resources\\static\\images\\qrcodes\\ALIPAY_QR\\Screenshot_(1).png'),(49,'wechat_qr','src\\main\\resources\\static\\images\\qrcodes\\WECHAT_QR\\Screenshot_(2).png'),(50,'qrcode_uploader','83'),(51,'QRCODE_NAMES','ALIPAY_QR,WECHAT_QR'),(52,'admin_email_password','mtomkzslidyfiyns'),(53,'contact_us_email','alphaeditorialsciencehub@gmail.com');
/*!40000 ALTER TABLE `properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
                        `role_id` int NOT NULL AUTO_INCREMENT,
                        `role_name` varchar(45) NOT NULL DEFAULT 'Role',
                        PRIMARY KEY (`role_id`),
                        UNIQUE KEY `role_id_UNIQUE` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (5,'USER'),(6,'SP-USER'),(7,'DRIVER'),(8,'ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_plans`
--

DROP TABLE IF EXISTS `service_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_plans` (
                                 `plan_id` int NOT NULL AUTO_INCREMENT,
                                 `name` varchar(45) NOT NULL DEFAULT 'Plan-Name',
                                 `description` text,
                                 `price` double NOT NULL DEFAULT '0',
                                 `currency` varchar(10) NOT NULL DEFAULT 'USD',
                                 `validity` int NOT NULL,
                                 PRIMARY KEY (`plan_id`),
                                 UNIQUE KEY `plan_id_UNIQUE` (`plan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_plans`
--

LOCK TABLES `service_plans` WRITE;
/*!40000 ALTER TABLE `service_plans` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_plans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscribe_by_email`
--

DROP TABLE IF EXISTS `subscribe_by_email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscribe_by_email` (
                                      `subs_id` int NOT NULL AUTO_INCREMENT,
                                      `email` varchar(255) NOT NULL,
                                      `alternate_email` varchar(255) DEFAULT NULL,
                                      PRIMARY KEY (`subs_id`),
                                      UNIQUE KEY `subs_id_UNIQUE` (`subs_id`),
                                      UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscribe_by_email`
--

LOCK TABLES `subscribe_by_email` WRITE;
/*!40000 ALTER TABLE `subscribe_by_email` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscribe_by_email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_details`
--

DROP TABLE IF EXISTS `transaction_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_details` (
                                       `billing_id` bigint NOT NULL AUTO_INCREMENT,
                                       `user_billing_id` int NOT NULL DEFAULT '0',
                                       `order_id` bigint NOT NULL DEFAULT '0',
                                       `transaction_id` varchar(45) NOT NULL,
                                       `amount` double NOT NULL DEFAULT '0',
                                       `currency` varchar(5) DEFAULT 'USD',
                                       `screenshot_path` varchar(150) NOT NULL,
                                       `payment_mode` varchar(15) NOT NULL DEFAULT 'Payment-Mode',
                                       `payment_date` varchar(10) NOT NULL,
                                       `modify_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       `payment_status` varchar(15) NOT NULL DEFAULT 'UNKNOWN',
                                       PRIMARY KEY (`billing_id`,`transaction_id`),
                                       UNIQUE KEY `billing_id_UNIQUE` (`billing_id`),
                                       UNIQUE KEY `transaction_id_UNIQUE` (`transaction_id`),
                                       UNIQUE KEY `order_id_UNIQUE` (`order_id`),
                                       KEY `user_id_idx` (`user_billing_id`),
                                       KEY `order_id_idx` (`order_id`),
                                       CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `order_details` (`order_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                       CONSTRAINT `user_billing_id` FOREIGN KEY (`user_billing_id`) REFERENCES `user_registration` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_details`
--

LOCK TABLES `transaction_details` WRITE;
/*!40000 ALTER TABLE `transaction_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_address_education`
--

DROP TABLE IF EXISTS `user_address_education`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_address_education` (
                                          `address_education_id` int NOT NULL AUTO_INCREMENT,
                                          `user_address_education_id` int NOT NULL DEFAULT '0',
                                          `street` varchar(100) DEFAULT NULL,
                                          `city` varchar(45) DEFAULT NULL,
                                          `state` varchar(45) DEFAULT NULL,
                                          `country` varchar(45) DEFAULT NULL,
                                          `zip_code` varchar(45) DEFAULT NULL,
                                          `department` varchar(100) DEFAULT NULL,
                                          `institution` varchar(100) DEFAULT NULL,
                                          PRIMARY KEY (`address_education_id`),
                                          UNIQUE KEY `address_education_id_UNIQUE` (`address_education_id`),
                                          UNIQUE KEY `user_address_education_id_UNIQUE` (`user_address_education_id`),
                                          KEY `user_address_education_id_idx` (`user_address_education_id`),
                                          CONSTRAINT `user_address_education_id` FOREIGN KEY (`user_address_education_id`) REFERENCES `user_registration` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address_education`
--

LOCK TABLES `user_address_education` WRITE;
/*!40000 ALTER TABLE `user_address_education` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_address_education` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_mapping`
--

DROP TABLE IF EXISTS `user_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_mapping` (
                                `umapping_id` int NOT NULL AUTO_INCREMENT,
                                `address_education_id` int NOT NULL,
                                `bridge_id` int NOT NULL,
                                PRIMARY KEY (`umapping_id`,`bridge_id`),
                                UNIQUE KEY `bridge_id_UNIQUE` (`bridge_id`),
                                UNIQUE KEY `umapping_id_UNIQUE` (`umapping_id`),
                                KEY `address_education_id_idx` (`address_education_id`),
                                KEY `bridge_id_idx` (`bridge_id`) /*!80000 INVISIBLE */,
                                CONSTRAINT `address_education_id` FOREIGN KEY (`address_education_id`) REFERENCES `user_address_education` (`address_education_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                CONSTRAINT `bridge_id` FOREIGN KEY (`bridge_id`) REFERENCES `user_role_type_bridge` (`bridge_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_mapping`
--

LOCK TABLES `user_mapping` WRITE;
/*!40000 ALTER TABLE `user_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_permission_associate`
--

DROP TABLE IF EXISTS `user_permission_associate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_permission_associate` (
                                             `associate_id` int NOT NULL AUTO_INCREMENT,
                                             `user_permission_id` int NOT NULL DEFAULT '0',
                                             `permission_id` int NOT NULL DEFAULT '1',
                                             `is_deleted` tinyint NOT NULL DEFAULT '0',
                                             PRIMARY KEY (`associate_id`,`user_permission_id`,`permission_id`),
                                             UNIQUE KEY `associate_id_UNIQUE` (`associate_id`),
                                             UNIQUE KEY `user_permission_id_UNIQUE` (`user_permission_id`),
                                             KEY `user_id_idx` (`user_permission_id`),
                                             KEY `permission_id_idx` (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=254 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_permission_associate`
--

LOCK TABLES `user_permission_associate` WRITE;
/*!40000 ALTER TABLE `user_permission_associate` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_permission_associate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_registration`
--

DROP TABLE IF EXISTS `user_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_registration` (
                                     `user_id` int NOT NULL AUTO_INCREMENT,
                                     `first_name` varchar(45) NOT NULL DEFAULT 'First-Name',
                                     `middle_name` varchar(45) DEFAULT NULL,
                                     `last_name` varchar(45) DEFAULT NULL,
                                     `user_name` varchar(50) NOT NULL,
                                     `phone` varchar(15) DEFAULT NULL,
                                     `mobile` varchar(15) DEFAULT NULL,
                                     `password` varchar(100) NOT NULL,
                                     `subs_id` int DEFAULT NULL,
                                     `umapping_id` int DEFAULT NULL,
                                     `join_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     `is_deleted` tinyint NOT NULL DEFAULT '0',
                                     `is_enable` tinyint NOT NULL DEFAULT '0',
                                     PRIMARY KEY (`user_id`),
                                     UNIQUE KEY `user_id_UNIQUE` (`user_id`),
                                     UNIQUE KEY `user_name_UNIQUE` (`user_name`),
                                     UNIQUE KEY `mobile_UNIQUE` (`mobile`),
                                     UNIQUE KEY `subs_id_UNIQUE` (`subs_id`),
                                     UNIQUE KEY `umapping_id_UNIQUE` (`umapping_id`),
                                     KEY `umapping_id_idx` (`umapping_id`),
                                     KEY `subs_id_idx` (`subs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_registration`
--

LOCK TABLES `user_registration` WRITE;
/*!40000 ALTER TABLE `user_registration` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_registration` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`admin`@`localhost`*/ /*!50003 TRIGGER `user_registration_AFTER_INSERT` AFTER INSERT ON `user_registration` FOR EACH ROW BEGIN
    insert into user_permission_associate(user_permission_id,permission_id)
    select distinctrow u.user_id,p.permission_id from
        (user_registration u inner join permissions p)
    where p.permission_id=1 and u.user_id=(select max(user_id) from user_registration);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `user_role_type_bridge`
--

DROP TABLE IF EXISTS `user_role_type_bridge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role_type_bridge` (
                                         `bridge_id` int NOT NULL AUTO_INCREMENT,
                                         `user_id` int NOT NULL,
                                         `user_role_id` int NOT NULL,
                                         `user_type_id` int DEFAULT NULL,
                                         PRIMARY KEY (`bridge_id`,`user_id`,`user_role_id`),
                                         UNIQUE KEY `bridge_id_UNIQUE` (`bridge_id`),
                                         UNIQUE KEY `user_id_UNIQUE` (`user_id`),
                                         KEY `user_id_idx` (`user_id`),
                                         KEY `user_role_id_idx` (`user_role_id`),
                                         KEY `user_type_id_idx` (`user_type_id`),
                                         CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user_registration` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                         CONSTRAINT `user_role_id` FOREIGN KEY (`user_role_id`) REFERENCES `role` (`role_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
                                         CONSTRAINT `user_type_id` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`type_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=222 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_type_bridge`
--

LOCK TABLES `user_role_type_bridge` WRITE;
/*!40000 ALTER TABLE `user_role_type_bridge` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role_type_bridge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_type` (
                             `type_id` int NOT NULL AUTO_INCREMENT,
                             `type_name` varchar(45) NOT NULL DEFAULT 'Type',
                             PRIMARY KEY (`type_id`),
                             UNIQUE KEY `type_id_UNIQUE` (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES (10,'REGISTERED'),(11,'UNREGISTERED'),(12,'PAID_SUBSCRIBER'),(13,'UNPAID_SUBSCRIBER'),(14,'PRELIMINARY_EDITOR'),(15,'EDITOR'),(16,'ADMIN'),(17,'SUPER_ADMIN'),(18,'EXPIRED_USER');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `view_incomplete_user_details`
--

DROP TABLE IF EXISTS `view_incomplete_user_details`;
/*!50001 DROP VIEW IF EXISTS `view_incomplete_user_details`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_incomplete_user_details` AS SELECT
                                                           1 AS `user_id`,
                                                           1 AS `first_name`,
                                                           1 AS `middle_name`,
                                                           1 AS `last_name`,
                                                           1 AS `user_name`,
                                                           1 AS `phone`,
                                                           1 AS `mobile`,
                                                           1 AS `email`,
                                                           1 AS `alternate_email`,
                                                           1 AS `role_name`,
                                                           1 AS `type_name`,
                                                           1 AS `permission_name`,
                                                           1 AS `join_date`,
                                                           1 AS `is_deleted`,
                                                           1 AS `is_enable`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_user_details`
--

DROP TABLE IF EXISTS `view_user_details`;
/*!50001 DROP VIEW IF EXISTS `view_user_details`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_user_details` AS SELECT
                                                1 AS `umapping_id`,
                                                1 AS `user_id`,
                                                1 AS `bridge_id`,
                                                1 AS `associate_id`,
                                                1 AS `permission_id`,
                                                1 AS `type_id`,
                                                1 AS `role_id`,
                                                1 AS `address_education_id`,
                                                1 AS `subs_id`,
                                                1 AS `first_name`,
                                                1 AS `middle_name`,
                                                1 AS `last_name`,
                                                1 AS `user_name`,
                                                1 AS `email`,
                                                1 AS `alternate_email`,
                                                1 AS `phone`,
                                                1 AS `mobile`,
                                                1 AS `street`,
                                                1 AS `city`,
                                                1 AS `state`,
                                                1 AS `country`,
                                                1 AS `zip_code`,
                                                1 AS `department`,
                                                1 AS `institution`,
                                                1 AS `type_name`,
                                                1 AS `role_name`,
                                                1 AS `join_date`,
                                                1 AS `is_enable`,
                                                1 AS `is_deleted`,
                                                1 AS `permission_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_user_rights`
--

DROP TABLE IF EXISTS `view_user_rights`;
/*!50001 DROP VIEW IF EXISTS `view_user_rights`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_user_rights` AS SELECT
                                               1 AS `user_id`,
                                               1 AS `bridge_id`,
                                               1 AS `associate_id`,
                                               1 AS `first_name`,
                                               1 AS `middle_name`,
                                               1 AS `last_name`,
                                               1 AS `user_name`,
                                               1 AS `role_name`,
                                               1 AS `type_name`,
                                               1 AS `permission_name`,
                                               1 AS `join_date`,
                                               1 AS `is_enable`,
                                               1 AS `is_deleted`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'new_marshall_service'
--

--
-- Dumping routines for database 'new_marshall_service'
--
/*!50003 DROP PROCEDURE IF EXISTS `spInsertOrUpdateOrderDetails` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `spInsertOrUpdateOrderDetails`(
    IN authorId int,
    IN fileId int,
    IN adminId int,
    IN reportId int,
    IN selectedServices varchar(150),
    IN estimatedAmount double,
    IN currency varchar(5),
    IN orderDate timestamp,
    IN expiryDate timestamp,
    IN warningDate timestamp,
    IN modifyDate timestamp,
    IN remark varchar(100),
    IN status varchar(10),
    IN isActive tinyint,
    IN isPaid tinyint
)
BEGIN
    INSERT INTO order_details (`author_id`,`file_id`,`admin_id`,`report_id`,`selected_services`,`estimated_amount`,`currency`,`order_date`,
                               `expiry_date`,`warning_date`,`modify_date`,`remark`,`status`,`is_active`,`is_paid`)
        VALUES (authorId, fileId, adminId, reportId, selectedServices, estimatedAmount, currency, orderDate, expiryDate, warningDate, modifyDate,
                remark, status, isActive, isPaid) AS alias
    ON DUPLICATE KEY UPDATE
                         file_id=alias.file_id, admin_id=alias.admin_id, report_Id=alias.report_id, selected_services=alias.selected_services,
                         estimated_amount=alias.estimated_amount, currency=alias.currency, order_date=alias.order_date, expiry_date=alias.expiry_date,
                         warning_date=alias.warning_date, modify_date=alias.modify_date, remark=alias.remark, status=alias.status, is_active=alias.is_active, is_paid=alias.is_paid
    ;
    SELECT * FROM order_details WHERE author_id=authorId AND file_id=fileId AND report_id=reportId AND modify_date=modifyDate;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `spInsertOrUpdateTransactionDetails` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `spInsertOrUpdateUserDetails` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `spInsertOrUpdateUserDetails`(
    IN userId int,
    IN firstName varchar(50),
    IN middleName varchar(50),
    IN lastName varchar(50),
    IN phone varchar(50),
    IN mobile varchar(50),
    IN alternateEmail varchar(255),
    IN street varchar(100),
    IN city  varchar(50),
    IN state varchar(50),
    IN country varchar(50),
    IN zipcode varchar(50),
    IN department varchar(100),
    IN institution varchar(100)
)
BEGIN
    Declare userAddressId int;
    Declare userBridgeId int;

    INSERT INTO user_address_education (user_address_education_id, street, city, state, country, zip_code, department, institution)
    VALUES (userId, street, city, state, country, zipcode, department, institution)
    ON DUPLICATE KEY UPDATE
                         street = values(street), city = values(city), state=values(state), country=values(country),
                         department=values(department), institution=values(institution);

    /*updating user mapping table*/
    Select address_education_id into userAddressId from user_address_education where user_address_education_id = userId;
    Select bridge_id into userBridgeId from
        (user_role_type_bridge b inner join user_registration u on b.user_id=u.user_id)
    where u.user_id=userId;

    insert into user_mapping(address_education_id,bridge_id)
    Select distinct a.address_education_id,b.bridge_id from (user_registration u inner join user_address_education a on u.user_id = a.user_address_education_id
                                                                                 inner join user_role_type_bridge b on b.user_id=u.user_id)
    where (a.address_education_id=userAddressId and bridge_id=userBridgeId)
    ON DUPLICATE KEY UPDATE
                         address_education_id=values(address_education_id), bridge_id=values(bridge_id);

    /*updating user mapping id into user_registration table*/
    Update user_registration set umapping_id=(Select max(umapping_id) from user_mapping) where user_id=userId;

    /*updating alternate email id into subscribe_by_email table*/
    Update subscribe_by_email set alternate_email=alternateEmail where subs_id=(select subs_id from user_registration where user_id=userId);

    /*updating user details table*/
    Update user_registration
    Set first_name=firstName, middle_name=middleName, last_name=lastName, phone=phone, mobile=mobile
    Where user_id=userId;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `spUpdateUserDetails` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `spUpdateUserDetails`(

    IN userId int,
    IN firstName varchar(50),
    IN lastName varchar(50),
    IN middleName varchar(50),
    IN phone varchar(50),
    IN mobile varchar(50),
    IN alternateEmail varchar(255),
    IN street varchar(50),
    IN city  varchar(50),
    IN state varchar(50),
    IN country varchar(50),
    IN zipcode varchar(50),
    IN department varchar(100),
    IN institution varchar(100)
)
BEGIN
    Update
        (((user_mapping um inner join ((user_role_type_bridge b inner join role r on b.user_role_id=r.role_id) inner join user_type t on b.user_type_id=t.type_id) on um.bridge_id=b.bridge_id)
            inner join ((user_registration ur inner join (user_permission_associate upa inner join permissions p on upa.permission_id=p.permission_id) on ur.user_id=upa.user_permission_id)
                inner join subscribe_by_email subs on ur.subs_id=subs.subs_id)on um.umapping_id=ur.umapping_id)
            inner join user_address_education ua on um.address_education_id=ua.address_education_id)
    Set
        ur.first_name=firstName, ur.middle_name=middleName, ur.last_name=lastName, ur.user_name=userName, ur.phone=phone, ur.mobile=mobile,
        ua.street=street, ua.city=city, ua.state=state, ua.country=country, ua.zip_code=zipcode,
        ua.department=department, ua.institution=institution,
        subs.alternate_email=alternateEmail
    Where ur.user_id=userId;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `spUpdateUserRights` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `spUpdateUserRights`(
    IN userId int,
    IN roleName varchar(25),
    IN typeName varchar(25),
    IN permissionName varchar(25),
    IN associateId int,
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

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `spUserLogin` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `spUserLogin`(
    IN userName varchar(255)
)
BEGIN
    Select u.user_id,u.user_name, u.first_name, u.middle_name, u.last_name, s.email, u.phone, u.mobile, u.password, u.is_deleted, u.is_enable, r.role_name, t.type_name
    from ((user_role_type_bridge b inner join role r on b.user_role_id=r.role_id) inner join user_type t on b.user_type_id=t.type_id) inner join
         (user_registration u inner join subscribe_by_email s on u.subs_id=s.subs_id)
         on b.user_id=u.user_id
    where (u.user_name=userName or s.email=userName or u.mobile=userName) and u.is_deleted=false and u.is_enable=true;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `spUserMapping` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `spUserMapping`(
    IN userId int
)
BEGIN
    Declare userAddressId int;
    Declare userBridgeId int;

    Select address_education_id into userAddressId from user_address_education where user_address_education_id = userId;
    Select bridge_id into userBridgeId from
        (user_role_type_bridge b inner join user_registration u on b.user_id=u.user_id)
    where u.user_id=userId;

    insert into user_mapping(address_education_id,bridge_id)
    Select distinct a.address_education_id,b.bridge_id from
        (user_registration u inner join user_address_education a on u.user_id = a.user_address_education_id
                             inner join user_role_type_bridge b on b.user_id=u.user_id)
    where (a.address_education_id=userAddressId and bridge_id=userBridgeId);

    Update user_registration set umapping_id=(Select max(umapping_id) from user_mapping) where user_id=userId;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `spUserRoleTypeBridge` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
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
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `view_incomplete_user_details`
--

/*!50001 DROP VIEW IF EXISTS `view_incomplete_user_details`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
    /*!50013 DEFINER=`admin`@`localhost` SQL SECURITY DEFINER */
    /*!50001 VIEW `view_incomplete_user_details` AS select `ur`.`user_id` AS `user_id`,`ur`.`first_name` AS `first_name`,`ur`.`middle_name` AS `middle_name`,`ur`.`last_name` AS `last_name`,`ur`.`user_name` AS `user_name`,`ur`.`phone` AS `phone`,`ur`.`mobile` AS `mobile`,`subs`.`email` AS `email`,`subs`.`alternate_email` AS `alternate_email`,`vur`.`role_name` AS `role_name`,`vur`.`type_name` AS `type_name`,`vur`.`permission_name` AS `permission_name`,`ur`.`join_date` AS `join_date`,`ur`.`is_deleted` AS `is_deleted`,`ur`.`is_enable` AS `is_enable` from ((`user_registration` `ur` join `view_user_rights` `vur`) join `subscribe_by_email` `subs`) where ((`ur`.`user_id` = `vur`.`user_id`) and (`ur`.`subs_id` = `subs`.`subs_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_user_details`
--

/*!50001 DROP VIEW IF EXISTS `view_user_details`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
    /*!50013 DEFINER=`admin`@`localhost` SQL SECURITY DEFINER */
    /*!50001 VIEW `view_user_details` AS select `um`.`umapping_id` AS `umapping_id`,`ur`.`user_id` AS `user_id`,`b`.`bridge_id` AS `bridge_id`,`upa`.`associate_id` AS `associate_id`,`p`.`permission_id` AS `permission_id`,`t`.`type_id` AS `type_id`,`r`.`role_id` AS `role_id`,`ua`.`address_education_id` AS `address_education_id`,`subs`.`subs_id` AS `subs_id`,`ur`.`first_name` AS `first_name`,`ur`.`middle_name` AS `middle_name`,`ur`.`last_name` AS `last_name`,`ur`.`user_name` AS `user_name`,`subs`.`email` AS `email`,`subs`.`alternate_email` AS `alternate_email`,`ur`.`phone` AS `phone`,`ur`.`mobile` AS `mobile`,`ua`.`street` AS `street`,`ua`.`city` AS `city`,`ua`.`state` AS `state`,`ua`.`country` AS `country`,`ua`.`zip_code` AS `zip_code`,`ua`.`department` AS `department`,`ua`.`institution` AS `institution`,`t`.`type_name` AS `type_name`,`r`.`role_name` AS `role_name`,`ur`.`join_date` AS `join_date`,`ur`.`is_enable` AS `is_enable`,`ur`.`is_deleted` AS `is_deleted`,`p`.`permission_name` AS `permission_name` from (((`user_mapping` `um` join ((`user_role_type_bridge` `b` join `role` `r` on((`b`.`user_role_id` = `r`.`role_id`))) join `user_type` `t` on((`b`.`user_type_id` = `t`.`type_id`))) on((`um`.`bridge_id` = `b`.`bridge_id`))) join ((`user_registration` `ur` join (`user_permission_associate` `upa` join `permissions` `p` on((`upa`.`permission_id` = `p`.`permission_id`))) on((`ur`.`user_id` = `upa`.`user_permission_id`))) join `subscribe_by_email` `subs` on((`ur`.`subs_id` = `subs`.`subs_id`))) on((`um`.`umapping_id` = `ur`.`umapping_id`))) join `user_address_education` `ua` on((`um`.`address_education_id` = `ua`.`address_education_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_user_rights`
--

/*!50001 DROP VIEW IF EXISTS `view_user_rights`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
    /*!50013 DEFINER=`admin`@`localhost` SQL SECURITY DEFINER */
    /*!50001 VIEW `view_user_rights` AS select `ur`.`user_id` AS `user_id`,`ub`.`bridge_id` AS `bridge_id`,`upa`.`associate_id` AS `associate_id`,`ur`.`first_name` AS `first_name`,`ur`.`middle_name` AS `middle_name`,`ur`.`last_name` AS `last_name`,`ur`.`user_name` AS `user_name`,`r`.`role_name` AS `role_name`,`t`.`type_name` AS `type_name`,`p`.`permission_name` AS `permission_name`,`ur`.`join_date` AS `join_date`,`ur`.`is_enable` AS `is_enable`,`ur`.`is_deleted` AS `is_deleted` from (((((`user_registration` `ur` join `role` `r`) join `user_type` `t`) join `user_role_type_bridge` `ub`) join `permissions` `p`) join `user_permission_associate` `upa`) where ((`ur`.`user_id` = `ub`.`user_id`) and (`ub`.`user_role_id` = `r`.`role_id`) and (`ub`.`user_type_id` = `t`.`type_id`) and (`ur`.`user_id` = `upa`.`user_permission_id`) and (`upa`.`permission_id` = `p`.`permission_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-27  2:08:58
