-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: invader
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `invader`
--

DROP TABLE IF EXISTS `invader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invader` (
  `id` int NOT NULL AUTO_INCREMENT,
  `play_time` int DEFAULT NULL,
  `stage` int DEFAULT NULL,
  `kill_count` int DEFAULT NULL,
  `play_score` int DEFAULT NULL,
  `coin` int DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invader`
--

LOCK TABLES `invader` WRITE;
/*!40000 ALTER TABLE `invader` DISABLE KEYS */;
INSERT INTO `invader` VALUES (1,40,4,20,0,30,'testId','testPw'),(2,20,2,50,0,50,NULL,NULL),(3,103,2,72,1398,50,'guest','guest'),(4,103,2,72,1398,50,'guest','guest'),(5,103,2,72,1398,50,'guest','guest'),(6,103,2,72,1398,50,'guest','guest'),(7,20,2,50,500,50,NULL,NULL),(8,20,2,50,500,50,NULL,NULL),(9,20,2,50,500,50,NULL,NULL),(10,20,2,50,500,50,NULL,NULL),(11,103,2,72,1398,50,'guest','guest'),(12,103,2,72,1398,50,'guest','guest'),(13,29,2,36,2482,50,'guest','guest'),(14,31,2,36,2322,50,'guest','guest'),(15,48,1,48,1000,50,'guest','guest'),(16,17,1,12,705,50,'guest','guest'),(17,14,1,12,857,50,'guest','guest'),(18,21,1,12,571,50,'guest','guest'),(19,15,1,12,800,50,'guest','guest'),(20,15,1,12,800,50,'guest','guest'),(21,121,0,0,0,50,'guest','guest'),(22,43,2,36,1674,50,'guest','guest'),(23,78,2,12,153,50,'guest','guest'),(24,103,1,24,233,35,'guest','guest'),(25,30,2,36,2400,20,'guest','guest'),(26,44,1,48,1090,20,'guest','guest'),(27,11,1,12,1090,50,'guest','guest'),(28,8,1,12,1500,50,'guest','guest'),(29,12,1,12,1000,50,'guest','guest'),(30,65,2,48,1476,50,'guest','guest'),(31,17,1,12,705,50,'guest','guest'),(32,12,1,12,1000,50,'guest','guest'),(33,37,2,37,2000,50,'guest','guest'),(34,73,3,72,2958,50,'guest','guest'),(35,14,1,12,857,51,'guest','guest'),(36,112,5,180,8035,67,'guest','guest'),(37,95,5,180,9473,69,'guest','guest'),(38,11,1,12,1090,51,'guest','guest'),(39,114,1,32,280,58,'guest','guest'),(40,189,1,32,169,58,'guest','guest'),(41,93,3,109,3516,67,'guest','guest'),(42,11,1,12,1090,51,'guest','guest'),(43,17,1,12,705,51,'guest','guest'),(44,91,4,120,5274,64,'guest','guest'),(45,34,2,36,2117,56,'guest','guest'),(46,35,2,36,2057,52,'guest','guest'),(47,65,3,72,3323,62,'guest','guest'),(48,28,2,36,2571,53,'jimin','5042'),(49,27,2,36,2666,53,'guest','guest'),(50,12,1,12,1000,54,'guest','guest'),(51,9,1,12,1333,53,'guest','guest'),(52,64,4,120,7500,39,'guest','guest'),(53,9,1,12,1333,50,'guest','guest'),(54,11,1,12,1090,51,'guest','guest'),(55,41,2,48,2341,56,'guest','guest'),(56,73,1,60,821,61,'guest','guest'),(57,25,2,36,2880,57,'guest','guest'),(58,23,1,12,521,59,'guest','guest'),(59,103,4,165,6407,75,'guest','guest'),(60,14,1,12,857,54,'guest','guest'),(61,12,1,12,1000,54,'guest','guest'),(62,91,4,177,7780,84,'user1','1234'),(63,119,4,120,4033,70,'guest','guest'),(64,17,1,12,705,54,'guest','guest'),(65,18,1,12,666,51,'jimin','5042'),(66,14,1,12,857,51,'jimin','5042'),(67,55,4,120,8727,76,'jimin','5042'),(68,13,1,12,923,37,'jimin','5042'),(69,89,1,12,134,37,'jimin','5042'),(70,42,3,72,5142,63,'guest','guest'),(71,21,1,12,571,53,'guest','guest'),(72,21,1,12,571,51,'guest','guest'),(73,40,2,36,1800,33,'guest','guest'),(74,12,1,12,1000,32,'guest','guest'),(75,21,0,11,0,51,'guest','guest'),(76,15,1,12,800,51,'guest','guest'),(77,66,3,72,3272,63,'guest','guest'),(78,11,1,12,1141,51,'guest','guest'),(79,32,1,35,1151,58,'guest','guest'),(80,2,0,2,51,51,'guest','guest'),(81,13,1,12,923,53,'jimin','5042'),(82,17,0,11,52,52,'jimin','5042'),(83,51,2,41,1663,56,'guest','guest'),(84,43,2,51,2423,51,'test1','1234'),(85,59,2,65,2246,43,'test2','1234'),(86,21,1,12,623,52,'test','1234'),(87,25,2,42,3399,39,'guest','guest'),(88,41,2,49,2420,30,'jimin','5042');
/*!40000 ALTER TABLE `invader` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-28  1:54:11
