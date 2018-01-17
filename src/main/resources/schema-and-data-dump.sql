-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: restaurant-2.0
-- ------------------------------------------------------
-- Server version	5.7.14-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `c_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `c_ukr_title` varchar(50) NOT NULL,
  `c_en_title` varchar(50) NOT NULL,
  `c_ru_title` varchar(50) NOT NULL,
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_ukr_title` (`c_ukr_title`),
  UNIQUE KEY `c_en_title` (`c_en_title`),
  UNIQUE KEY `c_ru_title` (`c_ru_title`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Пiца','Pizza','Пицца'),(2,'М\'ясо','Meat','Мясо'),(3,'Супи','Soups','Супы');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoices` (
  `inv_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `inv_user` bigint(20) NOT NULL,
  `inv_price` double(10,2) NOT NULL,
  `inv_state` enum('PAID','UNPAID') DEFAULT NULL,
  `inv_creation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`inv_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (2,1,123.00,'UNPAID','2018-01-07 16:18:28');
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items` (
  `i_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `i_ukr_title` varchar(50) NOT NULL,
  `i_en_title` varchar(50) NOT NULL,
  `i_ru_title` varchar(50) NOT NULL,
  `i_ukr_description` varchar(255) NOT NULL,
  `i_en_description` varchar(255) NOT NULL,
  `i_ru_description` varchar(255) NOT NULL,
  `i_price` float NOT NULL,
  `i_category` bigint(20) NOT NULL,
  `i_image_id` varchar(50) CHARACTER SET ucs2 NOT NULL,
  PRIMARY KEY (`i_id`),
  UNIQUE KEY `i_ukr_title` (`i_ukr_title`,`i_category`),
  UNIQUE KEY `i_ru_title` (`i_ru_title`,`i_category`),
  UNIQUE KEY `i_en_title` (`i_en_title`,`i_category`),
  KEY `i_category` (`i_category`),
  CONSTRAINT `items_ibfk_1` FOREIGN KEY (`i_category`) REFERENCES `categories` (`c_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES (1,'Маргарiта','Margherita','Маргарита','Типова неаполітанська піца, виготовлена з помідорами Сан-Марцано, моцареллою, свіжим базиліком, солі та оливковою олією','A typical Neapolitan pizza made with tomatoes of San Marzano, mozzarella-fiat-latte, fresh basil, salt and extra virgin olive oil','Типичная неаполитанская пицца, сделанная c помидорами Сан-Марцано, моцареллой, свежим базиликом, солью и оливковым маслом',123,1,'88586e2c-d545-49de-a5c6-a7f430055877.jpg'),(2,'Чотирi Сира','Four Cheese','Четыре Сыра','Це класичний варіант однієї з найчудовіших комбінацій хліба та сиру, яку можна собі уявити','This is the classic version of one of the most wonderful combinations of bread and cheese imaginable','Это классическая версия одной из самых замечательных комбинаций хлеба и сыра, которые только можно себе представить',88,1,'ca17b534-1138-4a90-ab0d-3be136519155.jpg'),(3,'Пеперонi','Pepperoni','Пепперони','Американська різноманітність салями, виготовлена з відварених свинини та яловичини, змішаної разом і приправлена паприкою чи іншим перцем чилі','American variety of salami, made from cured pork and beef mixed together and seasoned with paprika or other chili pepper','Американська різноманітність салями, виготовлена з відварених свинини та яловичини, змішаної разом і приправлена паприкою чи іншим перцем чилі',79,1,'1d7492e6-ee5b-4730-a7e7-8afa4496850b.jpg'),(4,'Гаспачо','Gazpacho','Гаспачо','Блюдо іспанської кухні, холодний суп з протертих в пюре свіжих овочів ','A dish of Spanish cuisine, cold soup from freshly mashed vegetables','Блюдо испанской кухни, холодный суп из протертых в пюре свежих овощей',77,3,'9c96c129-b4c9-4c5a-bf40-01c7bc4c4f88.jpg'),(5,'Спаржевый крем-суп ','Cream of asparagus soup','Спаржевый крем-суп','Зроблено зі свіжою спаржею, цибулею-пореєм, курячим бульйоном і вершками. Смачний суп для весняного вечері','Made with fresh asparagus, leeks, chicken broth, and cream. A delicious soup for a spring dinner','Сделано со свежей спаржей, луком-пореем, куриным бульоном и сливками. Вкусный суп для весеннего ужина',70,3,'ef219b0d-7630-4a9f-ae05-4a7cbd4c67c2.jpg'),(6,'Буйабес','Bouillabaisse','Буйабес','Блюдо французької кухні, рибний суп, характерний для середземноморського узбережжя Франції. Є оригінальним провансальським рибним супом','A dish of French cuisine, fish soup, typical of the Mediterranean coast of France. Is the original Provencal fish soup','Блюдо французской кухни, рыбный суп, характерный для средиземноморского побережья Франции. Является оригинальным провансальским рыбным супом',100,3,'11dc5879-f579-4e54-9a18-c65b419aa77c.jpg'),(7,'Гарбузово-імбирний суп','Pumpkin & ginger soup','Суп из тыквы и имбиря','Бархатий і ароматичний гарбузовий імбирний суп, що приносить задоволення на будь-який день ','Velvety and aromatic Pumpkin Ginger Soup, a comforting dish for every day','Бархатный и ароматный суп с имбирем из тыквы, приятное блюдо на каждый день ',60,3,'134aa3dc-0734-4026-a0ab-c86eaa0c80ef.jpg'),(8,'Крем-суп з грибами','Creamy mushroom soup','Сливочный грибной суп','Насичений і ароматний грибний суп з дикими і білими грибами ','Rich and filling mushroom soup with both wild and porcini mushrooms','Насыщенный и ароматный грибной суп с дикими и белыми грибами',77,3,'c01c419a-3327-46b6-86b3-dc22e5f5daeb.jpg'),(9,'Тiбон','T-bone','Тибон','Стейк з яловичини вирізаний з поясничної частини',' Steak of beef cut from the short loin','Стейк из говядины вырезанный из поясничной части ',220,2,'eacf472a-f3fb-40ac-8327-0a7331a4a59a.jpg'),(10,'Філе міньйон','Filet mignon','Филе миньон','Стейк з надзвичайною ніжністю, винятковим смаком та вишуканою якістю ','A steak with extreme tenderness, exceptional flavor, and outstanding quality','Стейк с экстремальной нежностью, исключительным вкусом и выдающимся качеством ',230,2,'e9957583-6b33-47cc-b811-f98aa2748002.jpg');
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_items` (
  `oi_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `oi_order` bigint(20) NOT NULL,
  `oi_item` bigint(20) NOT NULL,
  `oi_item_number` bigint(20) NOT NULL,
  PRIMARY KEY (`oi_id`),
  KEY `oi_order` (`oi_order`),
  KEY `oi_item` (`oi_item`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`oi_order`) REFERENCES `orders` (`o_id`) ON DELETE CASCADE,
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`oi_item`) REFERENCES `items` (`i_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (2,2,1,1),(19,10,2,1),(20,10,3,2);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `o_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `o_invoice` bigint(20) NOT NULL,
  `o_state` enum('NEW','PROCESSED','MODIFIED','REJECTED') NOT NULL,
  `o_creation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`o_id`),
  KEY `o_invoice` (`o_invoice`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`o_invoice`) REFERENCES `invoices` (`inv_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2,2,'PROCESSED','2018-01-07 14:17:53'),(10,2,'MODIFIED','2018-01-16 18:51:56');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `u_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `u_role` enum('CLIENT','ADMIN') DEFAULT NULL,
  `u_email` varchar(30) NOT NULL,
  `u_first_name` varchar(20) NOT NULL,
  `u_last_name` varchar(20) NOT NULL,
  `u_password_hash` char(32) NOT NULL,
  PRIMARY KEY (`u_id`),
  UNIQUE KEY `u_email` (`u_email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'CLIENT','jane937@rambler.ru','Jane','Petrova','25d55ad283aa40af464c76d713c7ad'),(2,'ADMIN','admin@gmail.com','Alexandr','Lebedko','1bbd886460827015e5d65ed44252251');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-17 12:23:23
