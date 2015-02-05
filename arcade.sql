-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 05. Feb 2015 um 16:00
-- Server Version: 5.6.20
-- PHP-Version: 5.5.15

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
`ID` int(11) unsigned NOT NULL COMMENT 'games id',
  `userID` int(11) unsigned NOT NULL COMMENT 'Id für den Uploader',
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'No Title' COMMENT 'Feld fuer Spielename',
  `executePath` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gameStarts` int(11) DEFAULT '0' COMMENT 'Counter für die Spielestarts',
  `gameDuration` int(255) DEFAULT '0' COMMENT 'Spieledauer ',
  `description` text COLLATE utf8_unicode_ci COMMENT 'Infos zu dem Spiel',
  `buttonConfig` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Pfad der Buttonbelegung.txt',
  `credits` text COLLATE utf8_unicode_ci COMMENT 'Infos über die Spielemacher',
  `permanentStore` tinyint(1) unsigned DEFAULT '0',
  `live` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `isEmulatorGame` tinyint(1) unsigned DEFAULT '0',
  `editMode` tinyint(1) unsigned DEFAULT '0',
  `editState` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=326 ;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `generell`
--

CREATE TABLE IF NOT EXISTS `generell` (
  `Spieleroot` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Root Ordner Games'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `generell`
--

INSERT INTO `generell` (`Spieleroot`) VALUES
('C:/Users/Public/Arcade');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `mail` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `salt` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
`id` int(11) unsigned NOT NULL,
  `isregistred` tinyint(1) DEFAULT '0',
  `userlvl` int(3) unsigned NOT NULL DEFAULT '100',
  `registerActivationString` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=103 ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `games`
--
ALTER TABLE `games`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `generell`
--
ALTER TABLE `generell`
 ADD PRIMARY KEY (`Spieleroot`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `games`
--
ALTER TABLE `games`
MODIFY `ID` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'games id',AUTO_INCREMENT=326;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=103;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
