-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 15. Dez 2014 um 10:56
-- Server Version: 5.6.16
-- PHP-Version: 5.5.9

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
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'games id',
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
  `editState` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=291 ;

--
-- Daten für Tabelle `games`
--

INSERT INTO `games` (`ID`, `userID`, `title`, `executePath`, `gameStarts`, `gameDuration`, `description`, `buttonConfig`, `credits`, `permanentStore`, `live`, `isEmulatorGame`, `editMode`, `editState`) VALUES
(214, 4, 'SpieleID 214', 'putty.exe', 0, 0, 'bescheret', '[{"ENTER":"eins"},{"SHIFT":"zwei"},{"FOO":"drei"},{"Left click":"vier"},{"right click":"funf"},{"ENTER":"sechs"},{"SHIFT":"sieben"},{"FOO":"acht"},{"Left click":"neun"},{"right click":"zehn"}]', 'mitwitt', 0, 1, 0, 0, NULL),
(215, 4, 'SpieleID 215', 'defender', 0, 0, '123', '[{"ENTER":"eins"},{"SHIFT":"zwei"},{"FOO":"drei"},{"Left click":"vier"},{"right click":"funf"},{"ENTER":"sechs"},{"SHIFT":"sieben"},{"FOO":"acht"},{"Left click":"neun"},{"right click":"zehn"}]', '123', 0, 1, 1, 0, NULL),
(287, 77, 'Justice League', '/putty.exe', 0, 0, '', '[{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""}]', '', 1, 1, 0, 1, '{"buttonlayout":"complete","gameupload":"complete","details":"complete","exechooser":"complete","coverupload":"complete"}'),
(288, 77, 'Batman Begins', '/putty.exe', 0, 0, '', '[{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""}]', '', 1, 1, 0, 1, '{"buttonlayout":"complete","gameupload":"complete","details":"complete","exechooser":"complete","coverupload":"complete"}'),
(289, 77, 'Lord of the Rings', '/putty.exe', 0, 0, '', '[{"ENTER":"Dance"},{"SHIFT":"DANCE harder"},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""}]', '', 1, 1, 0, 1, '{"buttonlayout":"complete","gameupload":"complete","details":"complete","exechooser":"complete","coverupload":"complete"}'),
(290, 77, 'Lego', '/putty.exe', 0, 0, '', '[{"ENTER":"Brick1"},{"SHIFT":"Brick2"},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""}]', '', 1, 1, 0, 1, '{"buttonlayout":"complete","gameupload":"complete","details":"complete","exechooser":"complete","coverupload":"complete"}'),
(285, 77, 'GTA', '/putty.exe', 0, 0, '', '[{"ENTER":"Jump"},{"SHIFT":"duck"},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""}]', '', 1, 1, 0, 1, '{"buttonlayout":"complete","gameupload":"complete","details":"complete","exechooser":"complete","coverupload":"complete"}'),
(286, 77, 'No Title', NULL, 0, 0, NULL, '[{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""},{"unused":""}]', NULL, 0, 0, 0, 0, '{"buttonlayout":"incomplete","gameupload":"incomplete","details":"incomplete","exechooser":"incomplete","coverupload":"incomplete"}');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `generell`
--

CREATE TABLE IF NOT EXISTS `generell` (
  `Spieleroot` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Root Ordner Games',
  PRIMARY KEY (`Spieleroot`)
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
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `isregistred` tinyint(1) DEFAULT '0',
  `userlvl` int(3) unsigned NOT NULL DEFAULT '100',
  `registerActivationString` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=78 ;

--
-- Daten für Tabelle `user`
--

INSERT INTO `user` (`mail`, `password`, `salt`, `id`, `isregistred`, `userlvl`, `registerActivationString`) VALUES
('123', '123', '', 77, 1, 100, NULL),
('hh-amburg1985@web.de', '123123', '1234', 61, 1, 100, '376194023270423');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
