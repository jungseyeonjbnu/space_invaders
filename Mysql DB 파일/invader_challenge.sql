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
-- Table structure for table `challenge`
--

DROP TABLE IF EXISTS `challenge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `challenge` (
  `cId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `remove` int DEFAULT NULL,
  `time_atk` int DEFAULT NULL,
  `no_item` int DEFAULT NULL,
  PRIMARY KEY (`cId`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenge`
--

LOCK TABLES `challenge` WRITE;
/*!40000 ALTER TABLE `challenge` DISABLE KEYS */;
INSERT INTO `challenge` VALUES (1,'jimin',1,0,0),(2,'jimin',1,0,0),(3,'jimin',1,0,0),(4,'jimin',2,0,0),(5,'jimin',3,1,0),(6,'jimin',1,0,0),(7,'jimin',1,0,0),(8,'guest',1,0,0),(9,'guest',1,0,0),(10,'guest',2,0,0),(11,'guest',1,0,0),(12,'guest',1,0,0),(13,'guest',1,0,0),(14,'guest',1,0,0),(15,'guest',1,0,0),(16,'guest',1,0,0),(17,'guest',1,0,0),(18,'guest',2,0,0),(19,NULL,0,0,0),(20,'guest',0,0,0),(21,'guest',1,0,0),(22,'guest',1,0,0),(23,NULL,0,0,0),(24,'guest',1,0,0),(25,'guest',1,0,0),(26,'guest',2,0,0),(27,'guest',1,0,0),(28,'guest',1,0,0),(29,'guest',1,0,0),(30,'guest',1,0,0),(31,'guest',2,1,0),(32,'guest',2,1,0),(33,'jimin',1,0,0),(34,'jimin',1,0,0),(35,'guest',0,0,0),(36,'guest',1,0,0),(37,'guest',1,0,0),(38,'guest',1,0,0),(39,'test1',1,0,0),(40,'test1',1,0,0),(41,'test1',2,0,0),(42,'test2',1,0,0),(43,'test2',1,0,0),(44,'test2',2,0,0),(45,'test',1,0,0),(46,'guest',1,0,0),(47,'guest',1,0,0),(48,'guest',1,0,0),(49,'jimin',1,0,0),(50,'jimin',1,0,0),(51,'jimin',1,0,0),(52,'jimin',1,0,0);
/*!40000 ALTER TABLE `challenge` ENABLE KEYS */;
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
