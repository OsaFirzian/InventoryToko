-- MariaDB dump 10.19  Distrib 10.4.32-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: inventorytoko
-- ------------------------------------------------------
-- Server version	10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `datatransaksi`
--

DROP TABLE IF EXISTS `datatransaksi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `datatransaksi` (
  `id_transaksi` int(11) NOT NULL AUTO_INCREMENT,
  `nama_pelanggan` varchar(100) NOT NULL,
  `kode_barang` varchar(50) NOT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `tanggal_transaksi` date NOT NULL,
  `status` enum('Sewa','Pembelian','Dikembalikan') DEFAULT 'Sewa',
  PRIMARY KEY (`id_transaksi`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datatransaksi`
--

LOCK TABLES `datatransaksi` WRITE;
/*!40000 ALTER TABLE `datatransaksi` DISABLE KEYS */;
INSERT INTO `datatransaksi` VALUES (2,'Adam','ID031','Sendal Gunung',35,'2025-06-01','Pembelian'),(3,'Adam','ID012','Gaiter Kaki',5,'2025-06-01','Sewa'),(4,'Osa','ID014','Sepatu Gunung',1,'2025-06-01','Dikembalikan'),(5,'Ujang','ID031','Celana',5,'2025-06-01','Sewa'),(6,'Rizki','ID001','Tenda Dome 4 Orang',1,'2025-06-01','Sewa');
/*!40000 ALTER TABLE `datatransaksi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stokbarang`
--

DROP TABLE IF EXISTS `stokbarang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stokbarang` (
  `id_barang` int(11) NOT NULL AUTO_INCREMENT,
  `kode_barang` varchar(10) DEFAULT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `stok_barang` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_barang`),
  UNIQUE KEY `kode_barang` (`kode_barang`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stokbarang`
--

LOCK TABLES `stokbarang` WRITE;
/*!40000 ALTER TABLE `stokbarang` DISABLE KEYS */;
INSERT INTO `stokbarang` VALUES (1,'ID001','Tenda Dome 4 Orang',14),(2,'ID002','Sleeping Bag Polar',20),(3,'ID003','Kompor Portable',10),(4,'ID004','Matras Lipat',25),(5,'ID005','Carrier 60L',12),(6,'ID006','Headlamp LED',18),(7,'ID007','Flysheet Waterproof',14),(8,'ID008','Jas Hujan Outdoor',22),(9,'ID009','Senter Kepala',19),(10,'ID010','Trangia Stove',6),(11,'ID011','Panci Nesting',30),(12,'ID012','Gaiter Kaki',4),(13,'ID013','Trekking Pole',17),(14,'ID014','Sepatu Gunung',7),(15,'ID015','Gas Kaleng',40),(16,'ID016','Saringan Air',11),(17,'ID017','Tarp Nylon',13),(18,'ID018','Meja Lipat',7),(19,'ID019','Kursi Lipat',10),(20,'ID020','Lampu Tenda USB',26),(21,'ID021','Topi Rimba',16),(22,'ID022','Buff Multifungsi',35),(23,'ID023','Ransel Lipat',12),(24,'ID024','Cooking Set',9),(25,'ID025','Powerbank Solar',4),(26,'ID026','Rompi Hiking',6),(27,'ID027','Peluit Survival',21),(28,'ID028','Paracord Gelang',31),(29,'ID029','Kompas Mini',27),(30,'ID030','Raincover Carrier',18),(33,'ID031','Celana',58),(34,'ID032','Jaket Parasut',34),(36,'ID033','Tenda',5);
/*!40000 ALTER TABLE `stokbarang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `Nama` varchar(20) NOT NULL,
  `Password` varchar(15) NOT NULL,
  `Role` enum('Admin','Staff') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('osa','123456','Admin');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-01 22:36:43
