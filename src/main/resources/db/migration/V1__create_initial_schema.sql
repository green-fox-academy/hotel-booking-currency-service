CREATE DATABASE `hearthbeat` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `hearthbeat` (
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;