CREATE DATABASE `hearthbeat` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `hearthbeat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
