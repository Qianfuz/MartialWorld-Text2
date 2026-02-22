-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: game
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `enemy_define`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS`enemy_define` (
  `id` int NOT NULL AUTO_INCREMENT,
  `basic_hp` int NOT NULL,
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enemy_define`
--

LOCK TABLES `enemy_define` WRITE;
/*!40000 ALTER TABLE `enemy_define` DISABLE KEYS */;
INSERT  IGNORE INTO `enemy_define` VALUES (1,50,'山贼'),(2,60,'野兽'),(3,300,'龙宫守卫');
/*!40000 ALTER TABLE `enemy_define` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enemy_init`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS`enemy_init` (
  `enemy_id` int NOT NULL,
  `enemy_lv` int NOT NULL,
  KEY `enemy_init_enemy_define_id_fk` (`enemy_id`),
  CONSTRAINT `enemy_init_enemy_define_id_fk` FOREIGN KEY (`enemy_id`) REFERENCES `enemy_define` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enemy_init`
--

LOCK TABLES `enemy_init` WRITE;
/*!40000 ALTER TABLE `enemy_init` DISABLE KEYS */;
INSERT  IGNORE INTO `enemy_init` VALUES (1,1),(2,1),(1,3),(3,1),(3,500);
/*!40000 ALTER TABLE `enemy_init` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enemy_skill`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS`enemy_skill` (
  `enemy_id` int NOT NULL,
  `skill_id` int NOT NULL,
  `basic_atk` int NOT NULL,
  `name` varchar(15) NOT NULL,
  PRIMARY KEY (`enemy_id`,`skill_id`),
  CONSTRAINT `enemy_skill_enemy_define_id_fk` FOREIGN KEY (`enemy_id`) REFERENCES `enemy_define` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enemy_skill`
--

LOCK TABLES `enemy_skill` WRITE;
/*!40000 ALTER TABLE `enemy_skill` DISABLE KEYS */;
INSERT  IGNORE INTO `enemy_skill` VALUES (1,1,5,'投石'),(1,2,8,'拳击'),(1,3,-15,'恢复'),(1,4,15,'秘技'),(2,1,8,'撕咬'),(2,2,10,'冲撞'),(2,3,-10,'休整'),(2,4,20,'围攻'),(3,1,200,'龙宫秘术'),(3,2,300,'重击'),(3,3,-999,'生生不息'),(3,4,10000,'泉涌');
/*!40000 ALTER TABLE `enemy_skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `index_skill`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `index_skill` (
  `index` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`index`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `index_skill`
--

LOCK TABLES `index_skill` WRITE;
/*!40000 ALTER TABLE `index_skill` DISABLE KEYS */;
INSERT  IGNORE INTO `index_skill` VALUES (1),(2),(3),(4),(5),(6),(7),(8);
/*!40000 ALTER TABLE `index_skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS`item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS`medicine` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `restore_hp` int DEFAULT NULL,
  `restore_mp` int DEFAULT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine`
--

LOCK TABLES `medicine` WRITE;
/*!40000 ALTER TABLE `medicine` DISABLE KEYS */;
INSERT  IGNORE INTO `medicine` VALUES (1,'血瓶',50,0,50),(2,'蓝瓶',0,50,50),(3,'灵芝',0,100,300),(4,'金疮药',200,0,500);
/*!40000 ALTER TABLE `medicine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine_number`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS`medicine_number` (
  `player_id` int NOT NULL,
  `medicine_id` int NOT NULL,
  `number` int DEFAULT '5',
  PRIMARY KEY (`player_id`,`medicine_id`),
  KEY `medicine_number_medicine_id_fk` (`medicine_id`),
  CONSTRAINT `medicine_number_medicine_id_fk` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`id`),
  CONSTRAINT `medicine_number_player_id_fk` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine_number`
--

LOCK TABLES `medicine_number` WRITE;
/*!40000 ALTER TABLE `medicine_number` DISABLE KEYS */;
INSERT  IGNORE INTO `medicine_number` VALUES (8,1,5),(8,2,5),(8,3,5),(8,4,5),(27,1,5),(27,2,5),(27,3,5),(27,4,5),(30,1,5),(30,2,5),(30,3,5),(30,4,5),(31,1,5),(31,2,5),(31,3,5),(31,4,5),(32,1,5),(32,2,5),(32,3,5),(32,4,5),(33,1,5),(33,2,5),(33,3,5),(33,4,5),(34,1,5),(34,2,5),(34,3,5),(34,4,5);
/*!40000 ALTER TABLE `medicine_number` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS`player` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `hp` int NOT NULL DEFAULT '100' COMMENT '血量',
  `mp` int NOT NULL DEFAULT '100' COMMENT '蓝量',
  `money` int NOT NULL DEFAULT '500' COMMENT '铜币',
  `exp` int NOT NULL DEFAULT '1' COMMENT '经验',
  `exp_max` int NOT NULL DEFAULT '500' COMMENT '经验上限',
  `lv` int NOT NULL DEFAULT '1' COMMENT '等级',
  PRIMARY KEY (`id`),
  UNIQUE KEY `player_pk_2` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT  IGNORE INTO `player` VALUES (7,'12345','12345',100,100,500,1,500,1),(8,'潜伏','1234',100,100,500,1,500,1),(10,'test1','1234',100,100,500,1,500,1),(12,'1111','1111',100,100,500,1,500,1),(14,'潜伏2','1234',100,100,500,1,500,1),(15,'潜伏3','1234',100,100,500,1,500,1),(16,'test2','1111',100,100,500,1,500,1),(18,'11111','1111',100,100,500,1,500,1),(19,'小明','1111',100,100,500,1,500,1),(20,'小红','1111',100,100,184,61,500,1),(21,'147','147',100,100,500,1,500,1),(22,'1111111','111111',100,100,42170,1,500,3),(25,'test22','1111',100,100,8000,1,500,3),(26,'123','123',100,100,888,281,500,1),(27,'5555','5555',49307496,49307496,21562,6157,50552,96),(28,'6666','6666',100,100,284,61,500,1),(29,'7777','7777',100,100,500,1,500,1),(30,'88','88',100,100,500,1,500,1),(31,'1231','1231',100,100,500,1,500,1),(32,'qq','qq',100,100,500,1,500,1),(33,'aa','aa',100,100,500,1,500,1),(34,'zz','zz',100,100,500,1,500,1);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill_define`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS`skill_define` (
  `id` int NOT NULL AUTO_INCREMENT,
  `skill_name` varchar(20) NOT NULL COMMENT '技能名',
  `limited_lv` int NOT NULL COMMENT '等级限制',
  `upgrade_cost` int NOT NULL COMMENT '升级花费',
  `basic_atk` int NOT NULL COMMENT '基础伤害',
  `basic_mp_cost` int NOT NULL COMMENT '基础蓝耗',
  `type` int NOT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill_define`
--

LOCK TABLES `skill_define` WRITE;
/*!40000 ALTER TABLE `skill_define` DISABLE KEYS */;
INSERT  IGNORE INTO `skill_define` VALUES (1,'念力飞扇',1,300,20,15,1),(2,'企鹅拳',1,100,15,0,1),(3,'无影手',3,500,25,20,1),(4,'点穴手',5,800,20,25,1),(5,'狂龙傲天拳',1,2000,85,30,2),(6,'惊天混元掌',1,2000,75,30,2),(7,'八卦迷踪腿',1,2000,70,30,2),(8,'灵气寒霜指',1,2000,70,30,2),(9,'道威无极真气',1,2000,100,30,2),(10,'百步烈火',15,4000,150,50,3),(11,'御龙在天',10,1500,80,30,3);
/*!40000 ALTER TABLE `skill_define` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill_equip`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS`skill_equip` (
  `player_id` int NOT NULL,
  `slot_index` int NOT NULL,
  `skill_id` int DEFAULT NULL,
  PRIMARY KEY (`player_id`,`slot_index`),
  KEY `skill_equip_skill_define_id_fk` (`skill_id`),
  CONSTRAINT `skill_equip_player_id_fk` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `skill_equip_skill_define_id_fk` FOREIGN KEY (`skill_id`) REFERENCES `skill_define` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill_equip`
--

LOCK TABLES `skill_equip` WRITE;
/*!40000 ALTER TABLE `skill_equip` DISABLE KEYS */;
INSERT  IGNORE INTO `skill_equip` VALUES (8,1,NULL),(8,2,NULL),(8,3,NULL),(8,4,NULL),(8,5,NULL),(8,6,NULL),(8,7,NULL),(8,8,NULL),(15,1,NULL),(15,2,NULL),(15,3,NULL),(15,4,NULL),(15,5,NULL),(15,6,NULL),(15,7,NULL),(15,8,NULL),(16,1,NULL),(16,2,NULL),(16,3,NULL),(16,4,NULL),(16,5,NULL),(16,6,NULL),(16,7,NULL),(16,8,NULL),(18,1,NULL),(18,2,NULL),(18,4,NULL),(18,5,NULL),(18,6,NULL),(18,7,NULL),(18,8,NULL),(19,1,NULL),(19,2,NULL),(19,3,NULL),(19,4,NULL),(19,5,NULL),(19,6,NULL),(19,7,NULL),(19,8,NULL),(20,3,NULL),(20,4,NULL),(20,5,NULL),(20,6,NULL),(20,7,NULL),(20,8,NULL),(22,1,NULL),(22,2,NULL),(22,3,NULL),(22,4,NULL),(22,5,NULL),(22,6,NULL),(22,7,NULL),(22,8,NULL),(25,1,NULL),(25,2,NULL),(25,3,NULL),(25,4,NULL),(25,5,NULL),(25,6,NULL),(25,7,NULL),(25,8,NULL),(26,3,NULL),(26,5,NULL),(26,6,NULL),(26,7,NULL),(27,5,NULL),(27,6,NULL),(27,7,NULL),(27,8,NULL),(28,2,NULL),(28,3,NULL),(28,4,NULL),(28,5,NULL),(28,6,NULL),(28,7,NULL),(28,8,NULL),(29,1,NULL),(29,2,NULL),(29,3,NULL),(29,4,NULL),(29,5,NULL),(29,6,NULL),(29,7,NULL),(29,8,NULL),(30,1,NULL),(30,2,NULL),(30,3,NULL),(30,4,NULL),(30,5,NULL),(30,6,NULL),(30,7,NULL),(30,8,NULL),(31,1,NULL),(31,2,NULL),(31,3,NULL),(31,4,NULL),(31,5,NULL),(31,6,NULL),(31,7,NULL),(31,8,NULL),(32,1,NULL),(32,2,NULL),(32,3,NULL),(32,4,NULL),(32,5,NULL),(32,6,NULL),(32,7,NULL),(32,8,NULL),(33,1,NULL),(33,2,NULL),(33,3,NULL),(33,4,NULL),(33,5,NULL),(33,6,NULL),(33,7,NULL),(33,8,NULL),(34,1,NULL),(34,2,NULL),(34,3,NULL),(34,4,NULL),(34,5,NULL),(34,6,NULL),(34,7,NULL),(34,8,NULL),(18,3,1),(20,1,1),(21,1,1),(21,3,1),(26,1,1),(26,4,1),(26,8,1),(27,1,1),(28,1,1),(20,2,2),(21,2,2),(26,2,2),(27,2,2),(21,4,4),(21,5,5),(27,3,5),(21,6,6),(21,7,7),(21,8,8),(27,4,9);
/*!40000 ALTER TABLE `skill_equip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill_lv`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS`skill_lv` (
  `player_id` int NOT NULL,
  `skill_id` int NOT NULL,
  `lv` int NOT NULL DEFAULT '0' COMMENT '等级',
  PRIMARY KEY (`player_id`,`skill_id`),
  KEY `skill_lv_skill_define_id_fk` (`skill_id`),
  CONSTRAINT `skill_lv_player_id_fk` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
  CONSTRAINT `skill_lv_skill_define_id_fk` FOREIGN KEY (`skill_id`) REFERENCES `skill_define` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill_lv`
--

LOCK TABLES `skill_lv` WRITE;
/*!40000 ALTER TABLE `skill_lv` DISABLE KEYS */;
INSERT  IGNORE INTO `skill_lv` VALUES (8,1,0),(8,2,0),(8,3,0),(8,4,0),(8,5,0),(8,6,0),(8,7,0),(8,8,0),(8,9,0),(8,10,0),(8,11,0),(15,1,0),(15,2,0),(15,3,0),(15,4,0),(15,5,0),(15,6,0),(15,7,0),(15,8,0),(15,9,0),(15,10,0),(15,11,0),(16,1,0),(16,2,0),(16,3,0),(16,4,0),(16,5,0),(16,6,0),(16,7,0),(16,8,0),(16,9,0),(16,10,0),(16,11,0),(18,1,0),(18,2,0),(18,3,0),(18,4,0),(18,5,0),(18,6,0),(18,7,0),(18,8,0),(18,9,0),(18,10,0),(18,11,0),(19,1,0),(19,2,0),(19,3,0),(19,4,0),(19,5,0),(19,6,0),(19,7,0),(19,8,0),(19,9,0),(19,10,0),(19,11,0),(20,1,1),(20,2,1),(20,3,0),(20,4,0),(20,5,0),(20,6,0),(20,7,0),(20,8,0),(20,9,0),(20,10,0),(20,11,0),(21,1,0),(21,2,0),(21,3,0),(21,4,0),(21,5,0),(21,6,0),(21,7,0),(21,8,0),(21,9,0),(21,10,0),(21,11,2),(22,1,4),(22,2,4),(22,3,7),(22,4,2),(22,5,0),(22,6,0),(22,7,0),(22,8,0),(22,9,0),(22,10,1),(22,11,2),(25,1,41),(25,2,33),(25,3,6),(25,4,2),(25,5,0),(25,6,0),(25,7,0),(25,8,0),(25,9,0),(25,10,3),(25,11,2),(26,1,56),(26,2,99),(26,3,0),(26,4,0),(26,5,0),(26,6,0),(26,7,0),(26,8,0),(26,9,0),(26,10,0),(26,11,0),(27,1,2),(27,2,171),(27,3,1),(27,4,4),(27,5,57),(27,6,0),(27,7,0),(27,8,0),(27,9,15),(27,10,12),(27,11,4),(28,1,1),(28,2,0),(28,3,0),(28,4,0),(28,5,0),(28,6,0),(28,7,0),(28,8,0),(28,9,0),(28,10,0),(28,11,0),(29,1,0),(29,2,0),(29,3,0),(29,4,0),(29,5,0),(29,6,0),(29,7,0),(29,8,0),(29,9,0),(29,10,0),(29,11,0),(30,1,0),(30,2,0),(30,3,0),(30,4,0),(30,5,0),(30,6,0),(30,7,0),(30,8,0),(30,9,0),(30,10,0),(30,11,0),(31,1,0),(31,2,0),(31,3,0),(31,4,0),(31,5,0),(31,6,0),(31,7,0),(31,8,0),(31,9,0),(31,10,0),(31,11,0),(32,1,0),(32,2,0),(32,3,0),(32,4,0),(32,5,0),(32,6,0),(32,7,0),(32,8,0),(32,9,0),(32,10,0),(32,11,0),(33,1,0),(33,2,0),(33,3,0),(33,4,0),(33,5,0),(33,6,0),(33,7,0),(33,8,0),(33,9,0),(33,10,0),(33,11,0),(34,1,0),(34,2,0),(34,3,0),(34,4,0),(34,5,0),(34,6,0),(34,7,0),(34,8,0),(34,9,0),(34,10,0),(34,11,0);
/*!40000 ALTER TABLE `skill_lv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'game'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-22  4:48:03
