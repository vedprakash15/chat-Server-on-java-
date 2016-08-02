-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.13-rc-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema server
--

CREATE DATABASE IF NOT EXISTS server;
USE server;

--
-- Definition of table `myusers`
--

DROP TABLE IF EXISTS `myusers`;
CREATE TABLE `myusers` (
  `UserName` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY  (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `myusers`
--

/*!40000 ALTER TABLE `myusers` DISABLE KEYS */;
INSERT INTO `myusers` (`UserName`,`Password`) VALUES 
 ('Ajeet','123456'),
 ('Akash','123'),
 ('Alka','Alka'),
 ('Anil','123'),
 ('he','he'),
 ('jeet','jeet'),
 ('jitendra','jitendra'),
 ('Manu','Manu'),
 ('me','me'),
 ('mohan','mohan'),
 ('moni','moni'),
 ('o','o'),
 ('Q','Q'),
 ('Roshan','123'),
 ('Soni','Soni'),
 ('Sonu','Sonu'),
 ('t','t'),
 ('Tanu','Tanu'),
 ('ut','12'),
 ('utk','utk'),
 ('Vibhuti','1234'),
 ('Vikash','1234'),
 ('y','Y');
/*!40000 ALTER TABLE `myusers` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
