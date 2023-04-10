-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: marshall_service
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirmation_token`
--

LOCK TABLES `confirmation_token` WRITE;
/*!40000 ALTER TABLE `confirmation_token` DISABLE KEYS */;
INSERT INTO `confirmation_token` VALUES (31,'964623','enableAccount','USER','khalidsaif9045@gmail.com','2022-10-26 21:34:23','2022-10-26 21:49:23','2022-10-26 21:35:08'),(32,'435580','enableAccount','USER','talal.wasif19@gmail.com','2022-10-26 21:37:51','2022-10-26 21:52:51','2022-10-26 21:38:17'),(33,'461308','enableAccount','USER','zafaralways@gmail.com','2022-10-26 21:39:05','2022-10-26 21:54:05','2022-10-26 21:39:26'),(34,'612149','enableAccount','DRIVER','8826424940','2022-10-26 21:55:54','2022-10-26 22:10:54','2022-10-26 21:56:53'),(35,'139140','enableAccount','USER','that@tha.tha','2022-10-28 23:57:42','2022-10-29 00:12:42',NULL);
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
-- Table structure for table `driver_booking_status`
--

DROP TABLE IF EXISTS `driver_booking_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `driver_booking_status` (
                                         `id` int NOT NULL AUTO_INCREMENT,
                                         `booking_request_id` int DEFAULT NULL,
                                         `driver_id` int DEFAULT NULL,
                                         `booking_status` varchar(10) DEFAULT NULL,
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `id_UNIQUE` (`id`),
                                         UNIQUE KEY `booking_UNIQUE` (`booking_request_id`,`driver_id`,`booking_status`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver_booking_status`
--

LOCK TABLES `driver_booking_status` WRITE;
/*!40000 ALTER TABLE `driver_booking_status` DISABLE KEYS */;
INSERT INTO `driver_booking_status` VALUES (1,22,124,'ACCEPT'),(4,22,125,'CLOSE'),(9,23,124,'OPEN'),(10,23,125,'OPEN'),(11,24,124,'OPEN'),(12,24,125,'OPEN'),(15,26,124,'OPEN'),(16,26,125,'OPEN'),(19,27,124,'ACCEPT'),(20,27,125,'CLOSE'),(21,28,124,'OPEN'),(22,28,125,'OPEN'),(23,29,124,'CLOSE'),(24,29,125,'CLOSE'),(25,30,124,'OPEN'),(26,30,125,'OPEN'),(27,31,124,'CLOSE'),(28,31,125,'CLOSE'),(29,32,124,'CLOSE'),(30,32,125,'CLOSE'),(31,33,124,'ACCEPT'),(32,33,125,'CLOSE'),(33,37,124,'ACCEPT'),(34,37,125,'CLOSE'),(35,38,124,'ACCEPT'),(37,38,124,'OPEN'),(36,38,125,'OPEN'),(39,39,124,'ACCEPT'),(41,39,124,'OPEN'),(40,39,125,'OPEN'),(43,41,124,'ACCEPT'),(44,41,125,'CLOSE'),(45,43,124,'ACCEPT'),(46,44,124,'ACCEPT');
/*!40000 ALTER TABLE `driver_booking_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver_status`
--

DROP TABLE IF EXISTS `driver_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `driver_status` (
                                 `driver_id` int NOT NULL,
                                 `status` varchar(10) NOT NULL,
                                 PRIMARY KEY (`driver_id`),
                                 UNIQUE KEY `id_UNIQUE` (`driver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver_status`
--

LOCK TABLES `driver_status` WRITE;
/*!40000 ALTER TABLE `driver_status` DISABLE KEYS */;
INSERT INTO `driver_status` VALUES (124,'ON_DUTY'),(125,'ON_DUTY');
/*!40000 ALTER TABLE `driver_status` ENABLE KEYS */;
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
-- Table structure for table `firebase_token`
--

DROP TABLE IF EXISTS `firebase_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `firebase_token` (
                                  `id` int NOT NULL AUTO_INCREMENT,
                                  `user_id` int DEFAULT NULL,
                                  `token` varchar(255) DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `id_UNIQUE` (`id`),
                                  UNIQUE KEY `driver_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `firebase_token`
--

LOCK TABLES `firebase_token` WRITE;
/*!40000 ALTER TABLE `firebase_token` DISABLE KEYS */;
INSERT INTO `firebase_token` VALUES (1,124,'firebaseToken12334');
/*!40000 ALTER TABLE `firebase_token` ENABLE KEYS */;
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
INSERT INTO `permissions` VALUES (1,'ReadOnly'),(2,'WriteOnly'),(3,'ReadWriteOnly'),(4,'Blocked');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_contents`
--

DROP TABLE IF EXISTS `profile_contents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile_contents` (
                                    `profile_id` bigint NOT NULL AUTO_INCREMENT,
                                    `profile_user_id` int NOT NULL,
                                    `profile_photo_name` varchar(100) DEFAULT NULL,
                                    `profile_photo_path` varchar(100) DEFAULT NULL,
                                    `profile_photo_size` int DEFAULT NULL,
                                    `aadhar_number` varchar(12) DEFAULT NULL,
                                    `paytm_number` varchar(10) DEFAULT NULL,
                                    `aadhar_back_photo_name` varchar(100) DEFAULT NULL,
                                    `aadhar_back_photo_path` varchar(100) DEFAULT NULL,
                                    `aadhar_back_photo_size` int DEFAULT NULL,
                                    `aadhar_front_photo_name` varchar(100) DEFAULT NULL,
                                    `aadhar_front_photo_path` varchar(100) DEFAULT NULL,
                                    `aadhar_front_photo_size` int DEFAULT NULL,
                                    `rickshaw_number` varchar(20) DEFAULT NULL,
                                    `rickshaw_front_photo_name` varchar(100) DEFAULT NULL,
                                    `rickshaw_front_photo_path` varchar(100) DEFAULT NULL,
                                    `rickshaw_front_photo_size` int DEFAULT NULL,
                                    `rickshaw_back_photo_name` varchar(100) DEFAULT NULL,
                                    `rickshaw_back_photo_path` varchar(100) DEFAULT NULL,
                                    `rickshaw_back_photo_size` int DEFAULT NULL,
                                    `rickshaw_side_photo_name` varchar(100) DEFAULT NULL,
                                    `rickshaw_side_photo_path` varchar(100) DEFAULT NULL,
                                    `rickshaw_side_photo_size` int DEFAULT NULL,
                                    `upload_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                    `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                    `is_deleted` tinyint DEFAULT '0',
                                    PRIMARY KEY (`profile_id`),
                                    UNIQUE KEY `paytm_number_UNIQUE` (`paytm_number`),
                                    UNIQUE KEY `aadhar_number_UNIQUE` (`aadhar_number`),
                                    UNIQUE KEY `rickshaw_number_UNIQUE` (`rickshaw_number`),
                                    KEY `profile_user_id_idx` (`profile_user_id`),
                                    CONSTRAINT `profile_user_id` FOREIGN KEY (`profile_user_id`) REFERENCES `user_registration` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_contents`
--

LOCK TABLES `profile_contents` WRITE;
/*!40000 ALTER TABLE `profile_contents` DISABLE KEYS */;
INSERT INTO `profile_contents` VALUES (3,124,NULL,NULL,NULL,'904500784045','8826424940','124-4368-Screenshot (1).png','\\var\\marshall\\docs\\driver\\124\\124-4368-Screenshot (1).png',495880,'124-1795-Screenshot (2).png','\\var\\marshall\\docs\\driver\\124\\124-1795-Screenshot (2).png',491321,'1234',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2022-10-26 21:55:47','2022-10-26 23:27:37',0),(4,118,'118-3835-Screenshot (1).png','\\var\\marshall\\docs\\driver\\118\\118-3835-Screenshot (1).png',495880,NULL,'456',NULL,NULL,NULL,NULL,NULL,NULL,'123',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2022-10-28 18:59:15','2022-10-28 20:58:38',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `properties`
--

LOCK TABLES `properties` WRITE;
/*!40000 ALTER TABLE `properties` DISABLE KEYS */;
INSERT INTO `properties` VALUES (32,'allowed_file_formats','application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/postscript,image/jpeg,image/png,application/pdf,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.rar,image/tiff,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/zip'),(35,'support_email','sawaricabs5@gmail.com'),(36,'support_email_password','dcmomkukxhwizrgv'),(37,'password_regex','(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&-+=()])(?=.*[A-Z]).{8,16}'),(38,'password_reset_link','/init/reset-password?reqType=resetPassword&token='),(39,'password_subject','Request Reset Password'),(40,'password_mail_body','Please click on the below link to reset your password:'),(41,'email_regex','^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$'),(42,'email_confirmation_link','/init/enable-account?reqType=enableAccount&token='),(43,'email_subject','Please Verify Your Email'),(44,'email_mail_body','Thank you for registering with us. Please click on the below link to activate your account:'),(45,'upload_directory','/var/marshall/docs/'),(47,'admin_email','sawaricabs5@gmail.com'),(48,'alipay_qr','\\var\\marshall\\docs\\images\\qrcodes\\ALIPAY_QR\\qrcode.jpg'),(49,'wechat_qr','src\\main\\resources\\static\\images\\qrcodes\\WECHAT_QR\\Screenshot_(2).png'),(50,'qrcode_uploader','119'),(51,'QRCODE_NAMES','ALIPAY_QR,WECHAT_QR'),(52,'admin_email_password','dcmomkukxhwizrgv'),(53,'contact_us_email','sawaricabs5@gmail.com'),(54,'DISTANCE_RANGE','0-2,2.1-3.5,3.6-4.5,4.6-6,6.1-7'),(55,'DISTANCE_THRESHOLD','6.1'),(56,'MAX_DISTANCE','7.0'),(57,'BASE_PRICE','10'),(58,'DISTANCE_FACTOR','5'),(59,'DISTANCE_SURCHARGE','15'),(60,'DISCOUNT_FARE_THRESHOLD','100'),(61,'COMMISSION','15'),(62,'WRITE_LOCATION_INTERVAL','900000'),(63,'REQUEST_ACCEPT_INTERVAL','20000'),(64,'BOOKING_RADIUS','3000'),(65,'MAP_BASE_URL','https://maps.googleapis.com/maps/api'),(66,'MAP_DISTANCE_PATH','/distancematrix/json'),(67,'MAP_KEY','AIzaSyANybxLdy_U-1VYJdrh7XBzHqePU1CQ-8c'),(68,'FIREBASE_URL','https://fcm.googleapis.com/fcm/send'),(69,'FIREBASE_KEY','AAAATM9x-Vk:APA91bGds48ddhXCfGjmCHWUkMeKAVDM7EJ_3Fy7DDkMud2U1LnJVec7aL87lVFTOJIQcCBOwLnoN8w4rBc_qf3IAaNEYZQ8XY1pTya4AGkGesKx5S-Nu42A0--G7xJVG2A2HnCz-8Ux'),(73,'TWILIO_ACCOUNT_SID','AC79a60feb6f43005ee7105255e50de90d'),(74,'TWILIO_AUTH_TOKEN','d0a99cda610153ac52f359029ffc1f9c'),(75,'SUPPORT_MOBILE_NUMBER','+16292586574');
/*!40000 ALTER TABLE `properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ride_request`
--

DROP TABLE IF EXISTS `ride_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ride_request` (
                                `date` date DEFAULT NULL,
                                `booking_request_id` int NOT NULL AUTO_INCREMENT,
                                `customer_id` int DEFAULT NULL,
                                `customer_name` varchar(45) DEFAULT NULL,
                                `mobile_no` varchar(10) DEFAULT NULL,
                                `pickup_location_points` varchar(45) DEFAULT NULL,
                                `pickup_location_word` varchar(155) DEFAULT NULL,
                                `drop_location_points` varchar(45) DEFAULT NULL,
                                `drop_location_word` varchar(155) DEFAULT NULL,
                                `passengers` varchar(1) DEFAULT NULL,
                                `distance` decimal(4,2) DEFAULT NULL,
                                `fare` decimal(6,3) DEFAULT NULL,
                                `discount` decimal(6,3) DEFAULT NULL,
                                `currency` varchar(3) DEFAULT 'INR',
                                `otp` varchar(10) DEFAULT NULL,
                                `booking_status` varchar(10) DEFAULT NULL,
                                `payment_mode` varchar(10) DEFAULT NULL,
                                `payment_status` varchar(10) DEFAULT 'UNPAID',
                                `driver_id` int DEFAULT NULL,
                                `modify_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                PRIMARY KEY (`booking_request_id`),
                                UNIQUE KEY `request_id_UNIQUE` (`booking_request_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ride_request`
--

LOCK TABLES `ride_request` WRITE;
/*!40000 ALTER TABLE `ride_request` DISABLE KEYS */;
INSERT INTO `ride_request` VALUES ('2023-02-01',22,117,NULL,NULL,'126.03,122.03','Thokar no 3, Near Al-shifa Hospital, Abul Fazal Enclave Part 1, Jamia Nagar, New Delhi-110025','136.03,132.03','Gate No. 3, Jamia Millia Islamia University, Jamia Nagar, New Delhi-110025','1',1.10,30.000,NULL,NULL,'299033','CLOSE','Cash','PAID',124,'2023-03-23 20:14:55'),('2023-02-01',23,117,NULL,'8826424940','126.03,122.03','Thokar no 3, Near Al-shifa Hospital, Abul Fazal Enclave Part 1, Jamia Nagar, New Delhi-110025','136.03,132.03','Gate No. 3, Jamia Millia Islamia University, Jamia Nagar, New Delhi-110025','1',1.10,30.000,NULL,NULL,'475811','CANCEL','Cash',NULL,NULL,'2023-03-23 20:15:09'),('2023-02-04',24,117,NULL,'7777777777','28.0300273,79.1220946',NULL,'28.0306261,79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'112388','NOT_SERVED','Cash',NULL,NULL,'2023-02-03 19:24:46'),('2023-02-04',25,117,NULL,'7777777777','28.0300273,79.1220946',NULL,'28.0306261,79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'236562','NOT_SERVED','Cash',NULL,NULL,'2023-02-03 19:26:45'),('2023-02-04',26,117,NULL,'7777777777','28.0300273,79.1220946',NULL,'28.0306261,79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'343242','NOT_SERVED','Cash',NULL,NULL,'2023-02-03 20:27:52'),('2023-02-04',27,117,NULL,'7777777777','28.0300273,79.1220946',NULL,'28.0306261,79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'504499','CANCEL','Cash',NULL,124,'2023-03-23 20:15:10'),('2023-02-04',28,117,NULL,'7777777777','28.0300273,79.1220946',NULL,'28.0306261,79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'843230','NOT_SERVED','Cash',NULL,NULL,'2023-02-03 21:03:35'),('2023-02-04',29,117,NULL,'7777777777','28.0300273,79.1220946',NULL,'28.0306261,79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'533666','CANCEL','Cash',NULL,124,'2023-03-23 20:15:11'),('2023-02-04',30,117,NULL,'7777777777','28.0300273,79.1220946',NULL,'28.0306261,79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'962260','NOT_SERVED','Cash',NULL,NULL,'2023-02-03 21:07:11'),('2023-02-04',31,117,NULL,'7777777777','28.0300273,79.1220946',NULL,'28.0306261,79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'339259','NOT_SERVED','Cash',NULL,124,'2023-02-03 21:11:41'),('2023-02-04',32,117,NULL,'7777777777','28.0300273,79.1220946',NULL,'28.0306261,79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'820198','NOT_SERVED','Cash',NULL,124,'2023-02-03 21:16:17'),('2023-02-04',33,117,NULL,'7777777777','28.0300273,79.1220946',NULL,'28.0306261,79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'575346','CLOSE','Cash','PAID',124,'2023-03-23 20:15:12'),('2023-02-07',34,117,'User 1 khalid','7777777777','28.0300273,79.1220946',NULL,'28.0306261,79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'578505','NOT_SERVED','Cash','PENDING',NULL,'2023-02-06 22:03:22'),('2023-02-07',35,117,'User 1 khalid','7777777777','28.0300273,79.1220946',NULL,'28.0306261,79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'341082','CANCEL','Cash','PENDING',NULL,'2023-02-06 22:06:45'),('2023-02-07',36,117,'User 1 khalid','7777777777','28.0300273,-79.1220946',NULL,'28.0306261,-79.1278998',NULL,'1',4.90,60.000,NULL,NULL,'789741','NOT_SERVED','Cash','PENDING',NULL,'2023-02-06 22:07:52'),('2023-02-07',37,117,'User 1 khalid','7777777777','40.6655101,-73.89188969999998',NULL,'40.598566,-73.7527626',NULL,'1',4.90,60.000,NULL,NULL,'688245','NOT_SERVED','Cash','PENDING',NULL,'2023-02-06 22:13:08'),('2023-02-07',38,117,'User 1 khalid','7777777777','40.6655101,-73.89188969999998',NULL,'40.598566,-73.7527626',NULL,'1',4.90,60.000,NULL,NULL,'326549','NOT_SERVED','Cash','PENDING',NULL,'2023-02-06 22:16:18'),('2023-02-07',39,117,'User 1 khalid','7777777777','40.6655101,-73.89188969999998',NULL,'40.598566,-73.7527626',NULL,'1',4.90,60.000,NULL,NULL,'947932','CANCEL','Cash','PENDING',NULL,'2023-03-23 20:15:14'),('2023-02-10',40,117,'User 1 khalid','','28.5571183,77.2929675','Alshifa Multispeciality Hospital, Abul Fazal Enclave Part 1, Abul Fazal Enclave, Jamia Nagar, Okhla, New Delhi, Delhi, India','28.5623169,77.2803832','Jamia Millia Islamia, Jamia Millia Islamia, Jamia Nagar, Okhla, New Delhi, Delhi, India','1',2.00,30.000,NULL,'INR','874509','NOT_SERVED','Cash','PENDING',NULL,'2023-02-09 19:55:22'),('2023-02-10',41,117,'User 1 khalid','','28.5571183,77.2929675','Alshifa Multispeciality Hospital, Abul Fazal Enclave Part 1, Abul Fazal Enclave, Jamia Nagar, Okhla, New Delhi, Delhi, India','28.5623169,77.2803832','Jamia Millia Islamia, Jamia Millia Islamia, Jamia Nagar, Okhla, New Delhi, Delhi, India','1',2.00,30.000,NULL,'INR','826624','CLOSE','Cash','PAID',124,'2023-03-23 20:15:15'),('2023-02-11',42,117,'User 1 khalid','','28.5571183,77.2929675','Alshifa Multispeciality Hospital, Abul Fazal Enclave Part 1, Abul Fazal Enclave, Jamia Nagar, Okhla, New Delhi, Delhi, India','28.5623169,77.2803832','Jamia Millia Islamia, Jamia Millia Islamia, Jamia Nagar, Okhla, New Delhi, Delhi, India','1',2.00,30.000,NULL,'INR','173866','CANCEL','Cash','PENDING',NULL,'2023-03-23 20:15:16'),('2023-03-28',43,117,'User 1 khalid','9077051653','28.5571183,77.2929675','Alshifa Multispeciality Hospital, Abul Fazal Enclave Part 1, Abul Fazal Enclave, Jamia Nagar, Okhla, New Delhi, Delhi, India','28.5623169,77.2803832','Jamia Millia Islamia, Jamia Millia Islamia, Jamia Nagar, Okhla, New Delhi, Delhi, India','1',2.00,30.000,NULL,'INR','928742','CANCEL','Cash','PENDING',124,'2023-03-27 19:23:08'),('2023-03-28',44,117,'User 1 khalid','9077051653','28.5571183,77.2929675','Alshifa Multispeciality Hospital, Abul Fazal Enclave Part 1, Abul Fazal Enclave, Jamia Nagar, Okhla, New Delhi, Delhi, India','28.5623169,77.2803832','Jamia Millia Islamia, Jamia Millia Islamia, Jamia Nagar, Okhla, New Delhi, Delhi, India','1',2.00,30.000,NULL,'INR','195198','CLOSE','Cash','PAID',124,'2023-03-27 19:25:22');
/*!40000 ALTER TABLE `ride_request` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscribe_by_email`
--

LOCK TABLES `subscribe_by_email` WRITE;
/*!40000 ALTER TABLE `subscribe_by_email` DISABLE KEYS */;
INSERT INTO `subscribe_by_email` VALUES (92,'khalidsaif9045@gmail.com',NULL),(93,'talal.wasif20@gmail.com',NULL),(94,'zafaralways@gmail.com',NULL),(95,'8826424940',NULL),(96,'that@tha.tha',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address_education`
--

LOCK TABLES `user_address_education` WRITE;
/*!40000 ALTER TABLE `user_address_education` DISABLE KEYS */;
INSERT INTO `user_address_education` VALUES (24,124,NULL,NULL,NULL,NULL,'0',NULL,NULL),(29,117,NULL,NULL,NULL,NULL,'0',NULL,NULL),(31,118,NULL,NULL,NULL,NULL,'0',NULL,NULL);
/*!40000 ALTER TABLE `user_address_education` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_location`
--

DROP TABLE IF EXISTS `user_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_location` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `user_id` int NOT NULL,
                                 `latitude` decimal(11,7) DEFAULT NULL,
                                 `longitude` decimal(11,7) DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `id_UNIQUE` (`id`),
                                 UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_location`
--

LOCK TABLES `user_location` WRITE;
/*!40000 ALTER TABLE `user_location` DISABLE KEYS */;
INSERT INTO `user_location` VALUES (1,124,28.5567130,77.2930533),(2,125,28.5690132,77.2799539);
/*!40000 ALTER TABLE `user_location` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_mapping`
--

LOCK TABLES `user_mapping` WRITE;
/*!40000 ALTER TABLE `user_mapping` DISABLE KEYS */;
INSERT INTO `user_mapping` VALUES (13,24,225),(18,29,222),(20,31,223);
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
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_permission_associate`
--

LOCK TABLES `user_permission_associate` WRITE;
/*!40000 ALTER TABLE `user_permission_associate` DISABLE KEYS */;
INSERT INTO `user_permission_associate` VALUES (254,117,1,0),(255,118,1,0),(256,119,1,0),(257,124,1,0),(258,125,1,0),(259,126,1,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_registration`
--

LOCK TABLES `user_registration` WRITE;
/*!40000 ALTER TABLE `user_registration` DISABLE KEYS */;
INSERT INTO `user_registration` VALUES (117,'User 1',NULL,'khalid','khalidsaif9045@gmail.com',NULL,'9077051653','$2a$10$dsIIkJD/BDywKOHJUpcGKuEVfeX0tumYGRT23EplOBFKzXyyPyPta',92,18,'2022-10-26 21:34:23',0,1),(118,'User 2',NULL,'Wasif','talal.wasif19@gmail.com',NULL,'7351492129','$2a$10$6QCuJzK8VwAqqO7l8I1iquzD.cmNESD0SLqdUBhPlQINTgHVp9Ovm',93,20,'2022-10-26 21:37:50',0,1),(119,'Admin',NULL,NULL,'zafaralways@gmail.com',NULL,'8888888888','$2a$10$SYHm90Ja07NGkGN5yUT7/u1kDsxtOpzzlbAp9xKya/.m6CMAbdOWa',94,NULL,'2022-10-26 21:39:04',0,1),(124,'Driver',NULL,'1','8826424940',NULL,'8826424940','$2a$10$HVK75ap068VV8EQ8n7.f0O6fq.aW3.Nq1aeNKBrI50KO1TikEN6Oa',95,13,'2022-10-26 21:54:41',0,1);
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
    insert into marshall_service.user_permission_associate(user_permission_id,permission_id)
    select distinctrow u.user_id,p.permission_id from
        (marshall_service.user_registration u inner join marshall_service.permissions p)
    where p.permission_id=1 and u.user_id=(select max(user_id) from marshall_service.user_registration);
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
) ENGINE=InnoDB AUTO_INCREMENT=228 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_type_bridge`
--

LOCK TABLES `user_role_type_bridge` WRITE;
/*!40000 ALTER TABLE `user_role_type_bridge` DISABLE KEYS */;
INSERT INTO `user_role_type_bridge` VALUES (222,117,5,10),(223,118,5,10),(225,124,7,10),(224,119,8,16);
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
-- Dumping events for database 'marshall_service'
--

--
-- Dumping routines for database 'marshall_service'
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

-- Dump completed on 2023-04-10  2:11:24
