CREATE SCHEMA IF NOT EXISTS `Albuns`;

CREATE TABLE IF NOT EXISTS `Album` (
  `idAlbum` int(11) NOT NULL AUTO_INCREMENT,
  `banda` varchar(200) NOT NULL,
  `nome` varchar(200) NOT NULL,
  `genero` varchar(45) NOT NULL,
  `lancamento` timestamp NOT NULL,
  `del` tinyint(1) NOT NULL,
  `capa` mediumtext,
  PRIMARY KEY (`idAlbum`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
