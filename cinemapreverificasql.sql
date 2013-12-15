-- phpMyAdmin SQL Dump
-- version 4.0.6deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 15, 2013 at 03:18 PM
-- Server version: 5.5.34-0ubuntu0.13.10.1
-- PHP Version: 5.5.3-1ubuntu2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `cinemapreverificasql`
--

-- --------------------------------------------------------

--
-- Table structure for table `attori`
--

CREATE TABLE IF NOT EXISTS `attori` (
  `CodAttore` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(20) COLLATE utf8_bin NOT NULL,
  `AnnoNascita` int(4) DEFAULT NULL,
  `Nazionalita` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`CodAttore`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=7 ;

--
-- Dumping data for table `attori`
--

INSERT INTO `attori` (`CodAttore`, `Nome`, `AnnoNascita`, `Nazionalita`) VALUES
(1, 'R. Williams', 1963, 'Inglese'),
(2, 'Monsieur', 1970, 'Francese'),
(3, 'Bonnejour', 1970, 'Francese'),
(4, 'M. Mastroianni', 1950, 'Italiano'),
(5, 'S. Loren', 1950, 'Italiano'),
(6, 'M. Giacomo', 1995, 'Italiano');

-- --------------------------------------------------------

--
-- Table structure for table `film`
--

CREATE TABLE IF NOT EXISTS `film` (
  `CodFilm` int(11) NOT NULL AUTO_INCREMENT,
  `Titolo` varchar(50) COLLATE utf8_bin NOT NULL,
  `AnnoProduzione` int(4) DEFAULT NULL,
  `Nazionalita` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `Regista` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `Genere` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`CodFilm`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=19 ;

--
-- Dumping data for table `film`
--

INSERT INTO `film` (`CodFilm`, `Titolo`, `AnnoProduzione`, `Nazionalita`, `Regista`, `Genere`) VALUES
(1, 'Fantascienza1', 1989, 'Giapponese', 'giap1', 'Fantascienza'),
(2, 'Fantascienza2', 1991, 'Giapponese', 'giap1', 'Fantascienza'),
(3, 'Fantascienza3', 1989, 'Francese', 'fra1', 'Fantascienza'),
(4, 'Fantascienza4', 1952, 'Francese', 'fra1', 'Fantascienza'),
(5, 'Fantascienza5', 1990, 'Francese', 'fra1', 'Fantascienza'),
(6, 'Fellini1', 1989, 'Italiano', 'F. Fellini', 'Fantascienza'),
(7, 'Fellini2', 1950, 'Italiano', 'F. Fellini', 'Fantascienza'),
(8, 'Fellini3', 1980, 'Italiano', 'F. Fellini', 'Fantascienza'),
(9, 'FilmRWilliams1', 2000, 'Tedesco', 'S. Conosciuto', 'Romantico'),
(10, 'FilmRWilliams2', 2004, 'Tedesco', 'S. Conosciuto', 'Strano'),
(11, 'FilmRWilliams3', 1995, 'Tedesco', 'S. Conosciuto', 'Fantascienza'),
(12, 'Casablanca', 2000, 'Tedesco', 'S. Conosciuto', 'Ciao'),
(13, 'Il Signore degli Anelli', 1996, 'USA', 'Jackson', 'Fantastico'),
(14, 'Lo Hobbit 1', 2012, 'USA', 'Jackson', 'Fantastico'),
(15, 'Lo Hobbit 2', 2013, 'USA', 'Jackson', 'Fantastico'),
(16, 'Spielberg1', 2000, 'Spagnolo', 'S. Spielberg', 'Strano'),
(17, 'Spielberg2', 2000, 'Spagnolo', 'S. Spielberg', 'Strano'),
(18, 'Spielberg3', 2000, 'Spagnolo', 'S. Spielberg', 'Strano');

-- --------------------------------------------------------

--
-- Table structure for table `proiezioni`
--

CREATE TABLE IF NOT EXISTS `proiezioni` (
  `CodProiezione` int(11) NOT NULL AUTO_INCREMENT,
  `CodFilm` int(11) NOT NULL,
  `CodSala` int(11) NOT NULL,
  `Incasso` int(11) DEFAULT NULL,
  `DataProiezione` date DEFAULT NULL,
  PRIMARY KEY (`CodProiezione`),
  KEY `CodFilm` (`CodFilm`),
  KEY `CodSala` (`CodSala`),
  KEY `CodSala_2` (`CodSala`),
  KEY `CodFilm_2` (`CodFilm`),
  KEY `CodSala_3` (`CodSala`),
  KEY `CodFilm_3` (`CodFilm`,`CodSala`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=20 ;

--
-- Dumping data for table `proiezioni`
--

INSERT INTO `proiezioni` (`CodProiezione`, `CodFilm`, `CodSala`, `Incasso`, `DataProiezione`) VALUES
(1, 1, 1, 5000, '2004-12-25'),
(2, 6, 6, 2000, '2004-12-25'),
(3, 5, 2, 3000, '2004-12-25'),
(4, 8, 7, 10000, '2004-12-25'),
(5, 9, 6, 7000, '2004-12-25'),
(6, 10, 7, 4500, '2004-12-25'),
(7, 6, 2, 1000, '2005-01-10'),
(8, 7, 3, 1000, '2005-01-01'),
(9, 8, 2, 1000, '2005-01-18'),
(10, 1, 1, 7000, '1998-10-10'),
(11, 4, 1, 25000, '2005-01-01'),
(12, 9, 2, 200, '2001-01-04'),
(13, 13, 10, 23000, '2013-12-13'),
(14, 14, 9, 17000, '2013-12-13'),
(15, 13, 10, 13000, '2013-12-13'),
(16, 15, 10, 35000, '2012-10-10'),
(17, 18, 1, 10, '0015-12-12'),
(18, 18, 1, 15, '2014-01-12'),
(19, 18, 1, 156, '2012-12-25');

-- --------------------------------------------------------

--
-- Table structure for table `recita`
--

CREATE TABLE IF NOT EXISTS `recita` (
  `CodRecita` int(11) NOT NULL AUTO_INCREMENT,
  `CodAttore` int(11) NOT NULL,
  `CodFilm` int(11) NOT NULL,
  PRIMARY KEY (`CodRecita`),
  KEY `CodAttore` (`CodAttore`,`CodFilm`),
  KEY `CodFilm` (`CodFilm`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=27 ;

--
-- Dumping data for table `recita`
--

INSERT INTO `recita` (`CodRecita`, `CodAttore`, `CodFilm`) VALUES
(17, 1, 1),
(3, 1, 4),
(4, 1, 7),
(1, 1, 9),
(15, 1, 12),
(19, 2, 1),
(5, 2, 3),
(26, 2, 6),
(20, 3, 1),
(18, 3, 3),
(6, 3, 4),
(13, 3, 6),
(7, 4, 1),
(8, 4, 7),
(21, 5, 1),
(11, 5, 2),
(22, 6, 1),
(24, 6, 6),
(23, 6, 9),
(14, 6, 13),
(25, 6, 15);

-- --------------------------------------------------------

--
-- Table structure for table `sale`
--

CREATE TABLE IF NOT EXISTS `sale` (
  `CodSala` int(11) NOT NULL AUTO_INCREMENT,
  `Posti` int(11) NOT NULL,
  `Nome` varchar(50) COLLATE utf8_bin NOT NULL,
  `Citta` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`CodSala`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=11 ;

--
-- Dumping data for table `sale`
--

INSERT INTO `sale` (`CodSala`, `Posti`, `Nome`, `Citta`) VALUES
(1, 100, 'Sala100PostiPi', 'Pisa'),
(2, 50, 'Sala50PostiPi', 'Pisa'),
(3, 60, 'Sala60PostiPi', 'Pisa'),
(4, 61, 'Sala61PostiPi', 'Pisa'),
(5, 70, 'Sala70PostiNa', 'Napoli'),
(6, 30, 'Sala30PostiNa', 'Napoli'),
(7, 80, 'Sala80PostiNa', 'Napoli'),
(8, 30, 'Sala30PostiCu', 'Cuneo'),
(9, 369, 'Monviso', 'Cuneo'),
(10, 600, 'Cinelandia1', 'Cuneo');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `proiezioni`
--
ALTER TABLE `proiezioni`
  ADD CONSTRAINT `proiezioni_ibfk_1` FOREIGN KEY (`CodSala`) REFERENCES `sale` (`CodSala`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `proiezioni_ibfk_2` FOREIGN KEY (`CodFilm`) REFERENCES `film` (`CodFilm`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `recita`
--
ALTER TABLE `recita`
  ADD CONSTRAINT `recita_ibfk_1` FOREIGN KEY (`CodAttore`) REFERENCES `attori` (`CodAttore`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `recita_ibfk_2` FOREIGN KEY (`CodFilm`) REFERENCES `film` (`CodFilm`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
