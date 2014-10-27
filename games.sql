-- phpMyAdmin SQL Dump
-- version 4.1.14.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 21. Okt 2014 um 18:51
-- Server Version: 5.1.73-log
-- PHP-Version: 5.5.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `arcade`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `games`
--

CREATE TABLE IF NOT EXISTS `games` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'games id',
  `Userid` int(11) unsigned NOT NULL COMMENT 'Id für den Uploader',
  `Spielename` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Feld fuer Spielename',
  `ExecutePath` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Spielestarts` int(11) NOT NULL DEFAULT '0' COMMENT 'Counter für die Spielestarts',
  `Spieledauer` int(255) NOT NULL DEFAULT '0' COMMENT 'Spieledauer ',
  `Spieleinfo` text COLLATE utf8_unicode_ci NOT NULL COMMENT 'Infos zu dem Spiel',
  `Buttonbelegung` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Pfad der Buttonbelegung.txt',
  `Credits` text COLLATE utf8_unicode_ci NOT NULL COMMENT 'Infos über die Spielemacher',
  `Video-Cover` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Video oder Coverpfad',
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=8 ;

--
-- Daten für Tabelle `games`
--

INSERT INTO `games` (`Id`, `Userid`, `Spielename`, `ExecutePath`, `Spielestarts`, `Spieledauer`, `Spieleinfo`, `Buttonbelegung`, `Credits`, `Video-Cover`) VALUES
(1, 1, 'Testspiel', 'To the Moon/To the Moon.exe', 0, 0, 'Dies ist das Testspiel zum prüfen des Front- und Backends.', 'Key_A;Key_B;Key_C;Key_D;Key_E;Key_F;Key_G;Key_H;Key_I;Key_J;Key_K;MODIFIER_SHIFT', 'Hier stehen die Credits. Wer hat das Spiel entwickelt, wer hat mitgewirkt usw.', '/Cover/cover.jpg');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
