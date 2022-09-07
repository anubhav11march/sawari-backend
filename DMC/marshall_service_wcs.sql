-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ether_service
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
                                      `email` varchar(100) NOT NULL,
                                      `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                      `expires_at` timestamp NULL DEFAULT NULL,
                                      `confirms_at` timestamp NULL DEFAULT NULL,
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `id_UNIQUE` (`id`),
                                      UNIQUE KEY `token_UNIQUE` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirmation_token`
--

LOCK TABLES `confirmation_token` WRITE;
/*!40000 ALTER TABLE `confirmation_token` DISABLE KEYS */;
INSERT INTO `confirmation_token` VALUES (5,'488acbdf-8db0-4857-97bd-8a85db0bed51','enableAccount','zaif99@gmail.com','2022-08-27 19:56:14','2022-08-27 20:11:14','2022-08-27 19:57:12'),(17,'e5950f04-adef-41b4-9165-9d8cd57107b5','resetPassword','zaif99@gmail.com','2022-08-27 20:41:56','2022-08-27 20:56:56','2022-08-27 20:42:16'),(24,'67e12cb4-d129-4478-bab9-c5438e470bf3','enableAccount','zafaralways@gmail.com','2021-12-28 19:21:23','2021-12-28 19:36:23','2021-12-28 19:22:01'),(25,'05e2e669-47c3-42bb-a964-919044940897','resetPassword','zafaralways@gmail.com','2021-12-28 19:24:15','2021-12-28 19:39:15','2021-12-28 19:24:50'),(26,'81752d2d-6b47-45ad-8b21-91797f4e49e7','enableAccount','thedarkthrister@gmail.com','2022-02-11 20:41:50','2022-02-11 20:56:50',NULL);
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
INSERT INTO `content` VALUES (1,83,'Worker Profile QRG for Employees.pdf','application/pdf','Topic 3','Category 1','safd','ENGLISH',NULL,NULL,NULL,'Uploaded','C:\\Users\\zafar.siddiqui\\usr\\author\\83\\application\\pdf\\83_3851_2021-12-30_Worker Profile QRG for Employees.pdf',255897,NULL,'2021-12-30',NULL,0),(2,83,'83_9239_2021-12-30_Worker Profile QRG for Employees.pdf','application/pdf','Topic 3','Category 1','safd','ENGLISH',NULL,NULL,NULL,'Uploaded','C:\\Users\\zafar.siddiqui\\usr\\author\\83\\application\\pdf\\83_9239_2021-12-30_Worker Profile QRG for Employees.pdf',255897,NULL,'2021-12-30',NULL,0),(3,83,'83_4391_2021-12-30_Worker Profile QRG for Employees.pdf','application/pdf','Topic 3','Category 1','safd','ENGLISH',NULL,NULL,NULL,'Uploaded','C:\\Users\\zafar.siddiqui\\usr\\author\\83\\application\\pdf\\83_4391_2021-12-30_Worker Profile QRG for Employees.pdf',255897,NULL,'2021-12-30',NULL,0),(4,83,'83_7715_2021-12-30_Worker Profile QRG for Employees.pdf','application/pdf','Topic 3','Category 1','safd','ENGLISH',NULL,NULL,NULL,'Uploaded','C:\\Users\\zafar.siddiqui\\usr\\author\\83\\application\\pdf\\83_7715_2021-12-30_Worker Profile QRG for Employees.pdf',255897,NULL,'2021-12-30',NULL,0),(5,83,'83_7055_2021-12-30_Worker Profile QRG for Employees.pdf','application/pdf','Topic 3','Category 1','safd','ENGLISH',NULL,NULL,NULL,'Uploaded','C:\\Users\\zafar.siddiqui\\usr\\author\\83\\application\\pdf\\83_7055_2021-12-30_Worker Profile QRG for Employees.pdf',255897,'2021-12-18 23:03:39','2021-12-30',NULL,0),(6,83,'8347862021-12-30-Website_Improved.xlsx','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','Topic 3','Category 1','safd',NULL,NULL,NULL,NULL,NULL,'C:\\Users\\zafar.siddiqui\\var\\aesh\\contents\\author\\83\\.xlsx\\8347862021-12-30-Website_Improved.xlsx',14425,'2021-12-26 21:02:14','2021-12-30',NULL,0),(7,83,'8371082021-12-30-Some opinions.docx','application/vnd.openxmlformats-officedocument.wordprocessingml.document','Topic 45','Category 1d','safdd',NULL,NULL,NULL,NULL,NULL,'C:\\Users\\zafar.siddiqui\\var\\aesh\\contents\\author\\83\\.docx\\8371082021-12-30-Some opinions.docx',18789,'2021-12-26 21:10:07','2021-12-30',NULL,0),(8,83,'8337862021-12-30-WIN_20211110_18_35_30_Pro.jpg','image/jpeg','Topic 450','Category 1d0','safddkk',NULL,NULL,NULL,NULL,NULL,'\\var\\aesh\\contents\\author\\83\\.jpg\\8337862021-12-30-WIN_20211110_18_35_30_Pro.jpg',101204,'2021-12-26 21:16:15','2021-12-30',NULL,0),(9,97,'97578920211228-WIN_20211110_18_35_30_Pro.jpg','image/jpeg','Topic 13 update','Category 1','safd','English',NULL,NULL,NULL,'Uploaded','\\var\\aesh\\contents\\author\\97\\.jpg\\97578920211228-WIN_20211110_18_35_30_Pro.jpg',101204,'2021-12-30 09:34:21','2021-12-28','2021-12-30 11:05:02',0),(10,97,'97365320211231-WIN_20211110_18_35_12_Pro.jpg','image/jpeg','Topic 3','Category 13','author','English',NULL,NULL,NULL,'Uploaded','\\var\\aesh\\contents\\author\\97\\.jpg\\97365320211231-WIN_20211110_18_35_12_Pro.jpg',99472,'2021-12-30 10:09:25','2021-12-31',NULL,0),(11,97,'97537620220112-Some opinions.docx','application/vnd.openxmlformats-officedocument.wordprocessingml.document','Topic 13','Public','were','Chinese','4543','','Only,Scientific','Uploaded','\\var\\aesh\\contents\\author\\97\\.docx\\97537620220112-Some opinions.docx',19332,'2022-01-08 06:51:50','2022-01-12','2022-01-08 06:51:50',0),(12,97,'97834020220111-Website.xlsx','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','Topic 13','Public','1word 2word, 1comma\r\n2  lines','Arabic','4543','','Only,Only','Uploaded','\\var\\aesh\\contents\\author\\97\\.xlsx\\97834020220111-Website.xlsx',12299,'2022-01-08 07:02:36','2022-01-11','2022-01-08 07:02:36',0),(13,97,'97945120220111-Website_Improved.xlsx','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','Topic 13','[Ljava.lang.String;@38d8964d','1word 2word, 1comma\r\n2  lines','Japanese','4543','','Scientific,Figure','Uploaded','\\var\\aesh\\contents\\author\\97\\.xlsx\\97945120220111-Website_Improved.xlsx',14425,'2022-01-08 07:05:15','2022-01-11','2022-01-08 07:05:15',0),(14,97,'97275220220122-some suggestions.xlsx','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','AdminTopic 13','[Ljava.lang.String;@5ae6d143','ret ertbert\r\nt8iu','English','4543','','Scientific,Figure','Uploaded','\\var\\aesh\\contents\\author\\97\\.xlsx\\97275220220122-some suggestions.xlsx',6625,'2022-01-08 07:22:41','2022-01-22','2022-01-08 07:22:41',0),(15,97,'97659120220121-Website_Improved.xlsx','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','Topic 450','Clinical','adc v\r\ndsf\r\ndsf','Chinese','4543','5','Figure,Journal','Uploaded','\\var\\aesh\\contents\\author\\97\\.xlsx\\97659120220121-Website_Improved.xlsx',14425,'2022-01-08 07:29:52','2022-01-21','2022-01-08 07:29:52',0),(16,97,'97206020220121-Website_Improved.xlsx','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','Topic 450','Clinical','adc v\r\ndsf\r\ndsf','Arabic','4543','5','Scientific,Journal','Uploaded','\\var\\aesh\\contents\\author\\97\\.xlsx\\97206020220121-Website_Improved.xlsx',14425,'2022-01-08 07:32:13','2022-01-21','2022-01-08 07:32:13',0),(17,97,'97570720220121-Website_Improved.xlsx','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','Topic 450','Molecular Biology','adc v\r\ndsf\r\ndsf','Arabic','4543','5','Only Manuscript Formatting','Uploaded','\\var\\aesh\\contents\\author\\97\\.xlsx\\97570720220121-Website_Improved.xlsx',14425,'2022-01-08 07:33:24','2022-02-23','2022-02-12 21:54:53',0),(18,97,'97689920220121-Website_Improved.xlsx','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','Topic 450','Public','adc v\r\ndsf\r\ndsf','Arabic','4543','5','Scientific,Journal','Uploaded','\\var\\aesh\\contents\\author\\97\\.xlsx\\97689920220121-Website_Improved.xlsx',14425,'2022-01-08 07:36:28','2022-01-21','2022-01-08 07:36:28',0),(19,97,'97425920220127-Some opinions.docx','application/vnd.openxmlformats-officedocument.wordprocessingml.document','Topic 450','Public Health','','Chinese','4543','4','Manuscript Formatting','Uploaded','\\var\\aesh\\contents\\author\\97\\.docx\\97425920220127-Some opinions.docx',19332,'2022-01-08 15:34:55','2022-01-27','2022-01-11 15:01:00',0),(20,97,'97822220220121-Some opinions.docx','application/vnd.openxmlformats-officedocument.wordprocessingml.document','Topic 450','Public Health','SC','Chinese','4543','1','Only English Editing,Journal Recommendation','Uploaded','\\var\\aesh\\contents\\author\\97\\.docx\\97822220220121-Some opinions.docx',19332,'2022-01-09 00:05:28','2022-02-28','2022-02-12 21:53:42',0),(21,97,'97971120220228-Account summary and transactions - Jul21-Dec21.pdf','application/pdf','AdminTopic 13','Clinical Medicine','','English','4543','','Journal Recommendation','Review Initiated','\\var\\aesh\\contents\\author\\97\\.pdf\\97971120220228-Account summary and transactions - Jul21-Dec21.pdf',758257,'2022-02-12 21:55:39','2022-02-28','2022-02-12 22:00:33',0),(22,99,'99701520220228-1636881840454_809016_IC_979_VCVB60MJ.pdf','application/pdf','zaif999','Clinical Medicine','zaif99','Arabic','4543','','Only English Editing','Uploaded','\\var\\aesh\\contents\\author\\99\\.pdf\\99701520220228-1636881840454_809016_IC_979_VCVB60MJ.pdf',1469278,'2022-02-21 09:09:13','2022-02-28','2022-02-21 09:09:13',0),(23,105,'105380120220315-epfo.pdf','application/pdf','Topic 3','Public Health','test redirect','Arabic','4543','','Only English Editing','Uploaded','\\var\\aesh\\contents\\author\\105\\.pdf\\105380120220315-epfo.pdf',187529,'2022-03-06 18:59:13','2022-03-15','2022-03-06 18:59:13',0),(24,105,'105684620220315-Gmail - Laptop Submission.pdf','application/pdf','Topic 13','Microbiology','test decription','Arabic','4543','','Only Manuscript Formatting','Uploaded','\\var\\aesh\\contents\\author\\105\\.pdf\\105684620220315-Gmail - Laptop Submission.pdf',1070018,'2022-03-06 19:05:43','2022-03-15','2022-03-06 19:05:43',0),(25,105,'105434520220323-My opinions (1).pdf','application/pdf','AdminTopic 13','Public Health','test','English','4543','','Only English Editing','Uploaded','\\var\\aesh\\contents\\author\\105\\.pdf\\105434520220323-My opinions (1).pdf',49655,'2022-03-12 15:51:30','2022-03-23','2022-03-12 15:51:30',0),(26,105,'105354120220429-202200070873_TAZNL_VROV4_202200070873_.pdf','application/pdf','AdminTopic 13','Public Health','test','Chinese','4543','','Scientific Editing','Uploaded','\\var\\aesh\\contents\\author\\105\\.pdf\\105354120220429-202200070873_TAZNL_VROV4_202200070873_.pdf',48578,'2022-03-31 19:54:17','2022-04-29','2022-03-31 19:54:17',0),(27,97,'97979420220622-certificate- business code of conduct.pdf','application/pdf','Topic 3','Microbiology','tesy','Arabic','4543','','Only English Editing','Preliminary-Report Submitted','\\var\\aesh\\contents\\author\\97\\.pdf\\97979420220622-certificate- business code of conduct.pdf',24825,'2022-06-06 15:18:34','2022-06-22','2022-06-06 15:20:52',0),(28,97,'97860420220818-SO2.docx','application/vnd.openxmlformats-officedocument.wordprocessingml.document','new order','Microbiology','new order','English','4543','','Only English Editing','Final-Report Submitted','\\var\\aesh\\contents\\author\\97\\.docx\\97860420220818-SO2.docx',12255,'2022-08-11 15:16:11','2022-08-18','2022-08-11 15:27:33',0);
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
INSERT INTO `content_report` VALUES (1,83,9,97,'83282520211229-WIN_20211110_18_35_12_Pro.jpg','image/jpeg','preliminaryReport','AdminTopic 13','Admin Category 1d0','Admin FIle','English',NULL,'\\var\\aesh\\contents\\author\\83\\.jpg\\83282520211229-WIN_20211110_18_35_12_Pro.jpg',99472,'2021-12-30 09:51:24',NULL,0),(2,83,9,97,'83282520211229-WIN_20211110_18_35_12_Pro.jpg','image/jpeg','preliminaryReport','AdminTopic 13','Admin Category 1d0','Admin FIle','English','','varaeshcontentsauthor83.jpg83282520211229-WIN_20211110_18_35_12_Pro.jpg',99472,'2021-12-30 09:51:24','2021-12-30 09:51:24',0),(3,68,19,97,'83833520220202-Ertiga_Brand_Brochure.pdf','application/pdf','preliminaryReport','AdminTopic 13','Public Health','','Arabic','Scientific Editing','\\var\\aesh\\contents\\author\\83\\.pdf\\83833520220202-Ertiga_Brand_Brochure.pdf',7881042,'2022-02-02 04:39:32','2022-02-02 04:39:32',0),(4,68,20,97,'83988120220209-Ertiga_Brand_Brochure.pdf','application/pdf','preliminaryReport','Transaction Test','Public Health','TT','Chinese','Only English Editing,Journal Recommendation','\\var\\aesh\\contents\\author\\83\\.pdf\\83988120220209-Ertiga_Brand_Brochure.pdf',7881042,'2022-02-08 18:55:58','2022-02-08 18:55:58',0),(5,42,21,97,'83433620220213-Advance Receipt Voucher .pdf','application/pdf','preliminaryReport','AdminTopic 13','Molecular Biology','','English','Journal Recommendation','\\var\\aesh\\contents\\author\\83\\.pdf\\83433620220213-Advance Receipt Voucher .pdf',23324,'2022-02-12 22:01:31','2022-02-12 22:01:50',0),(6,68,22,99,'83720320220221-262757.FY21YECOMP (2).pdf','application/pdf','preliminaryReport','AdminTopic zaif99','Clinical Medicine','zaif999 report','Arabic','Only English Editing','\\var\\aesh\\contents\\author\\83\\.FY21YECOMP (2).pdf\\83720320220221-262757.FY21YECOMP (2).pdf',328282,'2022-02-21 09:11:07','2022-02-21 09:11:07',0),(7,42,25,105,'83135820220312-My opinions (1).pdf','application/pdf','preliminaryReport','AdminTopic 13','Public Health','test','English','Only English Editing,Figure Formatting','\\var\\aesh\\contents\\author\\83\\.pdf\\83135820220312-My opinions (1).pdf',49655,'2022-03-12 15:54:51','2022-03-12 15:54:51',0),(8,42,27,97,'83817820220606-Certificate- Commitment to an Environment of Equality and Respect.pdf','application/pdf','preliminaryReport','Topic 3','Microbiology','test','Arabic','Only English Editing','\\var\\aesh\\contents\\author\\83\\.pdf\\83817820220606-Certificate- Commitment to an Environment of Equality and Respect.pdf',25795,'2022-06-06 15:21:15','2022-06-06 15:21:15',0),(9,67,28,97,'83247520220811-97698620220818-SO2.docx','application/vnd.openxmlformats-officedocument.wordprocessingml.document','preliminaryReport','new order report','Microbiology','new order preliminary report','English','Only English Editing,Figure Formatting','\\var\\aesh\\contents\\author\\83\\.docx\\83247520220811-97698620220818-SO2.docx',12255,'2022-08-11 15:19:24','2022-08-11 15:19:24',0),(10,67,28,97,'83502520220811-83853520220811-97698620220818-SO2.docx','application/vnd.openxmlformats-officedocument.wordprocessingml.document','finalReport','final report','Microbiology','final report','English',NULL,'\\var\\aesh\\contents\\author\\83\\.docx\\83502520220811-83853520220811-97698620220818-SO2.docx',12255,'2022-08-11 15:27:07','2022-08-11 15:27:07',0);
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
INSERT INTO `email_otp` VALUES (1,'abc@123.xy','854715','2021-12-08 16:20:52','2021-12-08 16:35:50',NULL),(2,'zaif99@gmail.com','881188','2021-12-08 18:43:47','2021-12-08 19:00:48',NULL);
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
INSERT INTO `order_details` VALUES (35,97,19,68,3,'Scientific Editing',1000,'USD','2022-02-03 04:32:14','2022-02-18 04:32:14','2022-02-13 04:32:14','2022-02-03 04:34:22','test3','PLACED',1,0),(40,97,20,68,4,'Only English Editing,Journal Recommendation',250,'USD','2022-02-08 18:57:04','2022-03-11 19:14:44','2022-03-06 19:14:44','2022-02-09 19:14:44','payment test','PLACED',1,0),(44,97,21,68,5,'Journal Recommendation',50,'USD','2022-02-12 22:02:45','2022-03-14 22:27:46','2022-03-09 22:27:46','2022-02-14 13:54:58','','PLACED',1,0),(55,99,22,68,6,'Only English Editing',200,'USD','2022-02-21 09:12:00','2022-03-08 09:12:00','2022-03-03 09:12:00','2022-02-21 10:01:33','','PLACED',1,0),(59,105,25,68,7,'Only English Editing,Figure Formatting',250,'USD','2022-03-12 15:57:02','2022-03-27 15:57:02','2022-03-22 15:57:02','2022-03-12 15:57:02','','PLACED',1,0),(60,97,27,68,8,'Only English Editing',200,'USD','2022-08-03 10:56:37','2022-08-18 10:56:37','2022-08-13 10:56:37','2022-08-03 19:19:39','','PLACED',1,0),(67,97,28,67,9,'Only English Editing',200,'USD','2022-08-11 15:22:19','2022-09-10 15:23:09','2022-09-05 15:23:09','2022-08-11 15:26:03','new order remark','APPROVED',1,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `properties`
--

LOCK TABLES `properties` WRITE;
/*!40000 ALTER TABLE `properties` DISABLE KEYS */;
INSERT INTO `properties` VALUES (1,'name_prefix','Dr.,Prof.,Mr.,Ms.,Mrs.,MD.,PD.'),(2,'category','List,Microbiology,Public Health,Nursing,Clinical Medicine,Molecular Biology,Bioinformatics,Meta-Analysis,Others'),(3,'allowed_file_formats','application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/postscript,image/jpeg,application/pdf,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.rar,image/tiff,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/zip'),(4,'topics','Manuscript,Figure Only,Poster,Thesis,Grant'),(5,'language','Arabic,Chinese,English,Japanese,Spanish'),(6,'support_email','support@aseh.com'),(7,'support_email_password','System123#'),(8,'password_regex','(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&-+=()])(?=.*[A-Z]).{8,16}'),(9,'password_reset_link','/init/reset-password?reqType=resetPassword&token='),(10,'password_subject','Request Reset Password'),(11,'password_mail_body','Please click on the below link to reset your password:'),(12,'email_regex','^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$'),(13,'email_confirmation_link','/init/enable-account?reqType=enableAccount&token='),(14,'email_subject','Please Verify Your Email'),(15,'email_mail_body','Thank you for registering with us. Please click on the below link to activate your account:'),(16,'upload_directory','/var/aesh/contents/author/'),(17,'status','Uploaded,Downloaded,Review Initiated,Review Pending,Review Complete,Preliminary-Report Submitted,Final-Report Pending,Final-Report Submitted,Close'),(18,'admin_email','admin@aseh.com'),(19,'alipay_qr','src\\main\\resources\\static\\images\\qrcodes\\ALIPAY_QR\\Screenshot_(1).png'),(20,'wechat_qr','src\\main\\resources\\static\\images\\qrcodes\\WECHAT_QR\\Screenshot_(2).png'),(24,'qrcode_uploader','83'),(27,'QRCODE_NAMES','ALIPAY_QR,WECHAT_QR'),(28,'admin_email_password','System123#'),(29,'contact_us_email','contact-us@aseh.com');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'AUTHOR'),(2,'PRE-EDITOR'),(3,'EDITOR'),(4,'ADMIN');
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
INSERT INTO `service_plans` VALUES (1,'Only English Editing','Only English Editing',200,'USD',30),(2,'Scientific Editing','Scientific Editing',1000,'USD',30),(3,'Only Manuscript Formatting','Only Manuscript Formatting',100,'USD',30),(4,'Figure Formatting','Figure Formatting',50,'USD',30),(5,'Journal Recommendation','Journal Recommendation',50,'USD',30),(6,'Manuscript Formatting','Manuscript Formatting',150,'USD',30),(7,'Academic Illustration','Academic Illustration',0,'USD',30);
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
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscribe_by_email`
--

LOCK TABLES `subscribe_by_email` WRITE;
/*!40000 ALTER TABLE `subscribe_by_email` DISABLE KEYS */;
INSERT INTO `subscribe_by_email` VALUES (7,'test@foreign.com',NULL),(10,'test4@login.com',NULL),(11,'test2@sha.com',NULL),(12,'test3@trigger.com',NULL),(13,'test2@trigger.com',NULL),(14,'test5@trigger.com',NULL),(15,'blank@bl.com',NULL),(16,'test7@trigger.com',NULL),(17,'Student@test.com',NULL),(18,'faculty@test.com',NULL),(19,'student@test2.com',NULL),(20,'student@test3.com',NULL),(21,'student@test4.com',NULL),(22,'faculty@test4.com',NULL),(23,'faculty@test5.com',NULL),(24,'student@test6.com',NULL),(25,'student@test7.com',NULL),(26,'faculty@test7.com',NULL),(27,'studentview2@test9.com',NULL),(28,'studentTest11@test.com',NULL),(29,'faculty@test11.com',NULL),(30,'student@test12',NULL),(31,'student@test12.com',NULL),(32,'studentdemo@test.com',NULL),(33,'studentdemo@test2.com',NULL),(35,'smtkmr89@gmail.com',NULL),(37,'sumit@uptimusdata.com',NULL),(38,'sumit@myezeepay.co.in',NULL),(39,'message@test.com',NULL),(40,'message2@test.com',NULL),(41,'message3@test.com',NULL),(42,'message4@test.com',NULL),(43,'message5@test.com',NULL),(44,'message6@test.com',NULL),(45,'message7@test.com',NULL),(46,'message@test8.com',NULL),(47,'zaid@myezeepay.co.in',NULL),(48,'zafar00always@gmail.com',NULL),(49,'abc@webservlet.test',NULL),(50,'terjg@nghcf.vkm',NULL),(51,'JHON11E00SS@GMAIL.COM',NULL),(52,'facultyNew@test.com',NULL),(53,'facultyNew@test2.com',NULL),(54,'abc@jll.clm',NULL),(55,'abjc@jll.clm',NULL),(56,'abjdc@jll.clm',NULL),(57,'abdc@jll.cl',NULL),(58,'ab3dc@jll.cl',NULL),(60,'ab3785dc@jll.cl',NULL),(61,'khalid900045@gmail.com',NULL),(62,'aswed@sedf.dg',NULL),(63,'asd@dsf.com',NULL),(64,'test1995@gmail.com',NULL),(65,'zaif9449@gmail.com',NULL),(66,'zaif9999@gmail.com',NULL),(67,'zaif9059@gmail.com',NULL),(68,'zaif909@gmail.com',NULL),(69,'thedarkthris00ter@gmail.com',NULL),(70,'zaif9009@gmail.com',NULL),(71,'zaif979@gmail.com',NULL),(72,'zaif499@gmail.com',NULL),(73,'zafaralway21s@gmail.com',NULL),(74,'zafaralway11s@gmail.com',NULL),(75,'zafaralwa11ys@gmail.com',NULL),(76,'zafaralwa14ys@gmail.com',NULL),(77,'zafaralw45ays@gmail.com',NULL),(78,'zaif9129@gmail.com',NULL),(79,'thedarkthrister@gmail.com',NULL),(80,'zaif99999@gmail.com',NULL),(81,'zaif0999@gmail.com',NULL),(82,'zaif099@gmail.com',NULL),(83,'asd',NULL),(84,'qsa',NULL),(85,'zaif9019@gmail.com',NULL),(86,'zaif9029@gmail.com',NULL),(87,'zaif91019@gmail.com',NULL),(88,'zaif9900@gmail.com',NULL),(89,'zaif99@gmail.com',NULL);
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
INSERT INTO `transaction_details` VALUES (3,97,40,'1234',50,'USD','abc','UPI','13/02/2022','2022-02-12 22:07:37','PLACED'),(11,97,44,'123456',50,'USD','\\var\\aesh\\contents\\author\\97\\.png\\MicrosoftTeams-image (2).png','UPI','2022-02-13','2022-02-12 22:27:46','PLACED'),(13,97,67,'123456lks',200,'USD','\\var\\aesh\\contents\\author\\97\\transactions\\screenshot\\.png\\Screenshot (1).png','UPI','2022-08-11','2022-08-11 15:23:09','PAID');
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
INSERT INTO `user_address_education` VALUES (2,83,NULL,NULL,NULL,NULL,'0','testCourse67','test'),(22,97,NULL,NULL,NULL,NULL,'0',NULL,NULL);
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
INSERT INTO `user_mapping` VALUES (1,2,193),(11,22,207);
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
) ENGINE=InnoDB AUTO_INCREMENT=247 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_permission_associate`
--

LOCK TABLES `user_permission_associate` WRITE;
/*!40000 ALTER TABLE `user_permission_associate` DISABLE KEYS */;
INSERT INTO `user_permission_associate` VALUES (128,42,2,0),(140,40,1,0),(161,41,1,0),(193,44,1,0),(194,45,1,0),(195,46,1,0),(196,48,1,0),(197,52,1,0),(198,53,1,0),(199,54,1,0),(200,55,1,0),(201,56,1,0),(202,57,1,0),(203,58,1,0),(204,59,1,0),(205,60,1,0),(206,62,1,0),(207,64,1,0),(208,65,1,0),(209,66,1,0),(210,67,1,0),(211,68,1,0),(212,69,1,0),(213,70,1,0),(214,71,1,0),(215,73,1,0),(216,74,1,0),(217,75,1,0),(218,76,1,0),(219,78,1,0),(220,83,1,0),(221,84,1,0),(222,85,1,0),(223,86,1,0),(224,87,1,0),(225,88,1,0),(226,89,1,0),(227,90,1,0),(228,91,1,0),(229,92,1,0),(230,93,1,0),(231,94,1,0),(232,95,1,0),(233,96,1,0),(234,97,1,0),(235,98,1,0),(236,99,1,0),(237,100,1,0),(238,101,1,0),(239,102,1,0),(240,103,1,0),(241,104,1,0),(242,105,1,0),(243,106,1,0),(244,107,1,0),(245,108,1,0),(246,109,1,0);
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
                                     `first_name` varchar(45) DEFAULT 'First-Name',
                                     `middle_name` varchar(45) DEFAULT NULL,
                                     `last_name` varchar(45) NOT NULL DEFAULT 'Last-Name',
                                     `user_name` varchar(50) NOT NULL,
                                     `phone` varchar(15) DEFAULT NULL,
                                     `mobile` varchar(15) DEFAULT NULL,
                                     `password` varchar(100) NOT NULL,
                                     `subs_id` int DEFAULT NULL,
                                     `umapping_id` int DEFAULT NULL,
                                     `join_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     `is_deleted` tinyint NOT NULL DEFAULT '0',
                                     `is_enable` tinyint NOT NULL DEFAULT '1',
                                     PRIMARY KEY (`user_id`),
                                     UNIQUE KEY `user_id_UNIQUE` (`user_id`),
                                     UNIQUE KEY `user_name_UNIQUE` (`user_name`),
                                     UNIQUE KEY `mobile_UNIQUE` (`mobile`),
                                     UNIQUE KEY `subs_id_UNIQUE` (`subs_id`),
                                     UNIQUE KEY `umapping_id_UNIQUE` (`umapping_id`),
                                     KEY `umapping_id_idx` (`umapping_id`),
                                     KEY `subs_id_idx` (`subs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_registration`
--

LOCK TABLES `user_registration` WRITE;
/*!40000 ALTER TABLE `user_registration` DISABLE KEYS */;
INSERT INTO `user_registration` VALUES (10,'test',NULL,'Login','test4Login','123321123','9877899870','1ff2b3704aede04eecb51e50ca698efd50a1379b',10,NULL,'2020-05-07 12:55:32',0,0),(15,'test',NULL,'trigger','test4trigger','123654654','987987987','a94a8fe5ccb19ba61c4c0873d391e987982fbbd3',7,NULL,'2020-05-09 08:06:19',0,0),(16,'test2',NULL,'sha','test2sha','654789654','654456654','ef38b0d70aec301f94b739cc2244c879efc4e0db',11,NULL,'2020-05-09 19:49:56',0,0),(18,'test3',NULL,'4trigger','test34trigger','3321456665','9874566542','b15e6d4ed6b7a5f8930f4a36d1eabf4a9add918a',12,NULL,'2020-05-09 20:06:30',0,0),(19,'test2',NULL,'trigger','test2trigger','987459459','987456658','5ae973298a83ff735d43ef25465ee6a0b7fac8fe',13,NULL,'2020-05-09 20:46:09',0,0),(25,'test5',NULL,'trigger','test5trigger','9874593100','9874563200','911ddc3b8f9a13b5499b6bc4638a2b4f3f68bf23',14,NULL,'2020-05-09 20:55:17',0,0),(26,'test6',NULL,'tri','test6','','','test6',15,NULL,'2020-05-09 21:03:53',0,0),(27,'test7',NULL,'trigger','test7trigger','33332222','3213222332','ea3243132d653b39025a944e70f3ecdf70ee3994',16,NULL,'2020-05-09 21:07:13',0,0),(28,'Student',NULL,'Test1','studentTest','012326269','7827527534','204036a1ef6e7360e536300ea78c6aeb4a9333dd',17,NULL,'2020-05-10 13:00:43',0,0),(29,'Faculty',NULL,'Test1','FacultyTest1','011264264','7527852245','5cee1c23e1604e9ae4f2c0e93bbb4c6eb72b5f2c',18,NULL,'2020-05-10 13:02:31',0,0),(30,'Student',NULL,'Test2','StudentTest2','011246654','785447896','9713e72ec0d14faac9b62bc5eec20f0238e40ae8',19,NULL,'2020-05-10 16:12:18',0,0),(31,'Student',NULL,'Test3','studentTest3','011256586','785479656','c85ba7501f8d780988e48cbde52a1f37ed23c494',20,NULL,'2020-05-10 16:19:40',0,0),(32,'Student',NULL,'Test4','studentTest4','011236545','7854587556','9a330e1731e771fdb7113cb898b9a8cdbadfe58b',21,NULL,'2020-05-10 16:22:42',0,0),(33,'Faculty',NULL,'Test4','facultyTest4','011236545','1236545498','513a8d02324da7d9d750045f7c6dc7d56a664b58',22,NULL,'2020-05-10 16:27:36',0,0),(35,'Faculty',NULL,'Test5','facultyTest5','011452452','45232566520','76361c3fb32efdc2d60778d41b7549b4f15810c8',23,NULL,'2020-05-10 16:36:22',0,0),(36,'Student',NULL,'Test6','studentTest6','011452254785','1254587896','959507f77200e6e315ff6c686ee37df6805d92dd',24,NULL,'2020-05-10 16:38:35',0,0),(37,'Student',NULL,'Test7','studentTest7','0112544785','9899658669','cf5c0d43af3dca52d4ff63ffdddc1742a488cf21',25,NULL,'2020-05-10 19:42:41',0,0),(38,'Faculty',NULL,'Test7','facultyTest7','0112363233','9888965896','6a0aebfc48440c4efeda8ebc20533061e441da9c',26,NULL,'2020-05-10 19:45:33',0,0),(40,'StudentView2',NULL,'Test9','studentTest9','0112555654','999058558','f873b3c855f1b6c2afe3adb0f208240aec0fe8f1',27,16,'2020-05-11 08:30:29',0,0),(41,'Student',NULL,'Test11','studentTest11','011147258','9632587410','studenttest11',28,8,'2020-05-11 09:18:46',0,0),(42,'FacultyUpdate',NULL,'Test11','facultyTest11','011254452','9990785785','02b87516dc8a02d3955ba82b0d06bbf252353c28',29,17,'2020-05-11 09:20:54',0,0),(44,'StudentUpdate8',NULL,'Test12','studentTest12','021235535','7878898998','403bc6def24f3fcab739849cbfda13c460d4605d',31,18,'2020-05-31 08:48:47',0,0),(45,'Student',NULL,'Demo','studentDemo11','011235254','7896987565','5b43876941dabaa14ec53c4d4011ee3ea27ce480',32,NULL,'2020-06-07 10:00:14',0,0),(46,'StudentDemo',NULL,'Test2','studentDemo2','011245454','7858785874','f926df27a1949d38c71f77147364ff152b750b29',33,NULL,'2020-06-07 10:06:48',0,0),(48,'Sumit',NULL,'Kumar','studentTest111','','9876543201','5bfe552918c8ce3bac509d22874807c843e30bfb',37,NULL,'2020-11-28 11:17:36',0,0),(52,'sumit',NULL,'kumar','studentTest110','','963541239','c130768b9f3007460febf66a3dfba5a30ae65bbb',38,NULL,'2020-11-29 12:05:50',0,0),(53,'message',NULL,'test','messageTest1','','123521325','c0eeff34ba5b20edddec59702888a2135da924c3',39,NULL,'2020-11-30 11:43:17',0,0),(54,'Message',NULL,'Test2','messageTest2','','1236321123','5d518609a11dc0f3bbe0cf30f02af3195c6e0ad4',40,NULL,'2020-11-30 11:47:50',0,0),(55,'Message',NULL,'Test3','messageTest3','','7854123369','NewPassword',41,NULL,'2020-11-30 11:56:00',0,0),(56,'Message',NULL,'Test4','messageTest4','','7894569874','f558550f438275bba55486c6da53c9b7495329ad',42,NULL,'2020-11-30 11:57:20',0,0),(57,'message',NULL,'test5','messageTest5','','7894561236','d18a527434dfdeb787b43a346ab68e295036c140',43,NULL,'2020-11-30 11:58:12',0,0),(58,'Message',NULL,'Test6','messageTest6','','1245784512','aac029bdd046025211188571a662aa53c022147c',44,NULL,'2020-11-30 11:59:30',0,0),(59,'Message',NULL,'Test7','messageTest7','','9877898789','c4fe14e062f481430e1e4aa557b9ad01f387122c',45,NULL,'2020-11-30 12:01:02',0,0),(60,'Message',NULL,'Test8','messageTest8','','7684111111','137a7c633446a6d23735d1103dee47600a790753',46,NULL,'2020-11-30 12:02:06',0,0),(62,'zaid',NULL,'ahmed','gtudentTest11','999063636','963541235','b8697767e9f550287bbb1cb45bcda7122f57312b',47,NULL,'2020-12-10 10:42:40',0,0),(64,'Zafar',NULL,'Siddiqui','zafarSiddiqui95','','8439174568','ab01a8044731da9c24375ac56783f6eb610db337',48,NULL,'2020-12-14 13:56:38',0,0),(65,'zasdf',NULL,'hxfsg','test96','','1543652156','ae3500108257e43f56f3d641d0210ac7fd4977cd',50,NULL,'2021-02-06 21:25:11',0,0),(66,'Zafar',NULL,'Siddiqui','jhon11ess','','1478236698','e3e18a8fe4595febc97b025434dd2c6c32fb406b',51,19,'2021-02-12 06:12:31',0,0),(67,'Faculty',NULL,'New','facultyNew11','','1452145214','$2a$10$V1qg2Nudd45Acul8uGweY.L24OHQNxzMiqdy25mIIzk/uzUBhISwi',52,20,'2021-02-19 19:30:50',0,1),(68,'Zafar',NULL,'Siddiqui','facultyNew12','','4587458745','d0112bc7921025bcf0cbdc13fd6cabe0e2e6b000',53,21,'2021-02-19 19:42:50',0,0),(69,'abc',NULL,'xyz','abcd99',NULL,'1234564560','56303da7e27e8419eb39779750f9402578c396ae',54,NULL,'2021-10-21 11:36:24',0,0),(70,'abc',NULL,'xyz','abcd990',NULL,'1234564567','28d2ed2cd8d6ced9bf7cae1f8a32667b6fd8a463',55,NULL,'2021-10-21 12:27:08',0,0),(71,'abcd',NULL,'wxyz','abcd9901',NULL,'1234564568','25a364bd9b67cc66df7ac31f8425acc0530bb7fb',56,NULL,'2021-10-21 12:48:03',0,0),(73,'ab',NULL,'wx','abd990',NULL,'1234564511','29e215d7b507c0b5f53260dc33429d7ebfdd405a',57,NULL,'2021-10-21 12:53:05',0,0),(74,'ab',NULL,'wx','abd3990',NULL,'1234545511','sha1(student@Tet1122)',58,NULL,'2021-10-21 13:32:29',0,0),(75,'ab',NULL,'wx','abd7492',NULL,'1234545516','$2a$10$ht8ybXrGYmxfFHO4GtiY9.qPfilBZPwJEx9DgzwjcVVAhcCdqeV32',60,NULL,'2021-10-26 21:27:19',0,0),(76,'abc',NULL,'mnb','khalid',NULL,'9045210953','$2a$10$8uIvBkOOFNxHnNJHJ82v../O4tZZP/8c5dQeCj7Oc5GMsizeWoGfe',61,NULL,'2021-11-09 19:58:42',0,0),(78,'abc',NULL,'mnb','aserd','abd7492','abd7492','$2a$10$ECswsjHy9QdIgPKEsh257OnuyYp4DdxR8pHLhsw/0FRUXfDc1IUpa',62,NULL,'2021-11-13 08:18:34',0,0),(83,'abc',NULL,'mnb','test1995',NULL,'4569873210','$2a$10$HVMfgUlSryFzAPXmUEUKuulpEAcav7xGOYBlPbqobjc41EY8JFbya',64,1,'2021-11-14 13:24:54',0,1),(84,'abc',NULL,'mnb','testToken',NULL,'8826400940','$2a$10$qM4I7VGGI5m1NzN9eUJAuO3Z7/HG3kf4xw5fDv6vGQJJwxdU1/JCS',65,NULL,'2021-12-08 17:54:13',1,0),(85,'abc',NULL,'mnb','testToken2',NULL,'8826424941','$2a$10$4WyO6ZRkVOwQ1zR.7lnVjenVL/.JYUu4dHYpAd69ak7JYllYssln6',66,NULL,'2021-12-08 18:13:14',0,0),(86,'abc',NULL,'mnb','testToken4',NULL,'8826424943','$2a$10$bPvK/0NrLiNhOs2gw2FGSe6odd/TArtpxYiHiZ3NQ3d2gwLBASltK',67,NULL,'2021-12-10 12:07:29',0,0),(87,'Zafartest',NULL,'Siddiqui','testEmailZafar1',NULL,'8845698712','$2a$10$YzusJQNV/zGaX51SGsX7k.KU//BzsFnQ4zg.ECodpL8CXdweDNp7a',68,NULL,'2021-12-20 16:24:55',0,0),(88,'Zafar',NULL,'Siddiqui','zafar11ess',NULL,'8456987456','$2a$10$Y3q5R78f1MiAXprR3rcqGuOnU2e1UDIGn2lOTPkNSYes3hJR5j5lO',69,NULL,'2021-12-23 14:18:43',0,0),(89,'Zafar',NULL,'Siddiqui','zaif9009',NULL,'8826424940','$2a$10$749c13Ruf/rmrvAFwS2MRukov.KH9dKEeag8fdKyQNQZxt2cu9y46',70,NULL,'2021-12-26 20:40:06',0,1),(90,'Zafar',NULL,'Siddiqui','zaif979',NULL,'8827434841','$2a$10$VToq4f1pafdG3tp/a5B5Zu3H29OAL1xqb.1Hm995S2DlF6PJSexLC',71,NULL,'2021-12-27 20:20:44',0,1),(91,'Zafar',NULL,'Siddiqui','zaif99',NULL,'8876765454','$2a$10$5jg9Ei0Kt2kb42tcxTNwkeCp1Piyhq4Eszsgutvzuj77.DmBKhAFm',72,NULL,'2021-12-27 20:30:32',0,1),(92,'Zafar','Siddiqui','Siddiqui','zafar95',NULL,'8845652520','$2a$10$WKWMx32wM1QfvKh/5v0Gr.pyFfz.lKA8e9R7IW7r8jvH6VaNBR26W',73,NULL,'2021-12-28 19:03:41',0,0),(93,'Zafar','Siddiqui','Siddiqui','zafar96',NULL,'4456859560','$2a$10$BNHmULcS7eit34q0jB.ylugah0tOxJ7vzU1ieyHVs7AUOJ8zI5KGy',74,NULL,'2021-12-28 19:07:11',0,0),(94,'Zafar','Siddiqui','Siddiqui','zafar65',NULL,'4485757410','$2a$10$rlj2xBvZzxxWx1I4g0.qa.td.7Wb/w8fGqxxLi7tcQ3j3vVsOw8ga',75,NULL,'2021-12-28 19:11:35',0,0),(95,'Zafar','Siddiqui','Siddiqui','zafar11',NULL,'4456565650','$2a$10$Na1bnyGXfqY5Divtg9k/L.GSNYv6jX.z4LV8lU.B6PnylMfK452uO',76,NULL,'2021-12-28 19:13:29',0,0),(96,'Zafar','Siddiqui','Siddiqui','zafar14',NULL,'1145857540','$2a$10$NDVdVcATxm89swGRyp48feWeLg/ZTZ2DI.ejwVRzibPL8C95TDqsi',77,NULL,'2021-12-28 19:21:22',0,1),(97,'Zafar','Siddiqui','Siddiqui','zaif41',NULL,'4456585850','$2a$10$x9MinQ/C76IqpZast1uFYOgCT7S4JsJBcWk4KVpooxnfZHBziL/yu',78,11,'2021-12-28 19:31:24',0,1),(98,'Prof. csrf','cstff','cstff','csrfToken',NULL,'+918876765432','$2a$10$hX9j2tumD3Em8VfD.5z37u/ZqRxurbDu9QY3Q5wZS/duEiM4FoGwK',79,NULL,'2022-02-11 20:41:50',0,1),(99,'Prof. Zafar','Siddiqui','Siddiqui','zaif999',NULL,'+918825466585','$2a$10$SF601tQon2Xrz28SNA7lveQswTnvQCy78C4mY5U.AIaACoHjfrUnu',80,NULL,'2022-02-21 09:06:39',0,1),(100,'Dr. Zafar','Siddiqui','Siddiqui','zaif100',NULL,'+918876565676','$2a$10$McnyYaEGQNB8VEWGTgNku.rRZE/YhQPQLv2OLKk4Zf5p.3J7yCkvy',81,NULL,'2022-02-21 19:14:37',0,1),(104,'Dr. Zafar','Siddiqui','Siddiqui','zaif99k',NULL,'+917767878909','$2a$10$pPkbW05HOb4x4ODjwSCsNOY5AYPPfTacrrLGb52RVNNbN9F7npJUS',84,NULL,'2022-02-23 15:03:02',0,1),(105,'Dr. Zafar','Siddiqui','Siddiqui','zaif997',NULL,'+919987898098','$2a$10$36vpq37EzfaJFb8rJfLNQO4wRPiLvQ24Kog0esiMUuhc1uB7qM8kG',85,NULL,'2022-02-24 21:15:00',0,1),(106,'Dr. Zafar','','Siddiqui','zaif11',NULL,NULL,'$2a$10$B2Nl6eIKd0zRfai8673XSeCfR0nhOCOhBwYY.jNiri8JMCrlXmLz6',86,NULL,'2022-08-27 19:37:20',0,1),(107,'Dr. Zafar','','Siddiqui','zaif12',NULL,NULL,'$2a$10$ffnQFk/3WTKU07G1a.2sIecFybq9HOBUIL/kT3/NsOZde2nFxmpu2',87,NULL,'2022-08-27 19:38:41',0,1),(108,'Dr. Zafar','','Siddiqui','zaif13',NULL,NULL,'$2a$10$M7ruO.Xm9tZhXUJMH8rIye/So.zWTVwzSWscP8SvSu7ZgDdWxzAlS',88,NULL,'2022-08-27 19:47:52',0,1),(109,'Dr. Zafar','','Siddiqui','zaif14',NULL,NULL,'$2a$10$EisbI8bnlwVr7X8cD6VyQ.0O0Ov7TVal9sPBVrS16vYCLfwJCJRsG',89,NULL,'2022-08-27 19:55:52',0,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=218 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_type_bridge`
--

LOCK TABLES `user_role_type_bridge` WRITE;
/*!40000 ALTER TABLE `user_role_type_bridge` DISABLE KEYS */;
INSERT INTO `user_role_type_bridge` VALUES (136,10,2,1),(141,15,1,1),(151,26,1,1),(152,27,1,1),(153,28,1,1),(155,29,2,1),(156,30,1,1),(158,31,1,1),(160,32,1,1),(161,35,2,1),(162,36,1,1),(163,37,1,1),(164,38,2,1),(165,40,1,1),(167,42,2,1),(168,44,1,1),(169,45,1,1),(170,46,1,1),(171,48,1,1),(172,52,1,1),(173,53,1,1),(174,54,1,1),(175,55,1,1),(176,56,1,1),(177,57,1,1),(178,58,1,1),(179,59,1,1),(180,60,1,1),(181,62,1,1),(182,64,1,1),(183,65,1,1),(184,66,1,1),(185,67,2,1),(186,68,2,1),(187,69,1,1),(188,73,1,1),(189,74,1,1),(190,75,1,1),(191,76,1,1),(192,78,1,1),(194,84,1,1),(195,85,1,1),(196,86,1,1),(197,87,1,1),(198,88,1,1),(199,89,1,1),(200,90,1,1),(202,92,1,1),(203,93,1,1),(204,94,1,1),(205,95,1,1),(206,96,1,1),(207,97,1,1),(208,98,1,1),(209,99,1,1),(210,100,1,1),(214,104,1,1),(215,105,1,1),(216,106,1,1),(217,109,1,1),(166,41,1,3),(193,83,4,7);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES (1,'REGISTERED'),(2,'UNREGISTERED'),(3,'PAID_SUBSCRIBER'),(4,'UNPAID_SUBSCRIBER'),(5,'PRELIMINARY_EDITOR'),(6,'EDITOR'),(7,'ADMIN'),(8,'SUPER_ADMIN'),(9,'EXPIRED_USER');
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
-- Dumping events for database 'ether_service'
--

--
-- Dumping routines for database 'ether_service'
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
    where (u.user_name=userName or s.email=userName) and u.is_deleted=false and u.is_enable=true;
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
    where ((role_id=userRoleId and type_id=userTypeId) and user_id=(Select user_id from user_registration where user_name=userName));
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

-- Dump completed on 2022-08-28 16:29:49
