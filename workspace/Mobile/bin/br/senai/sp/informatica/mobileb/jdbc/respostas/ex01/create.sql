CREATE TABLE `Telefone` (
  `idTelefone` int(11) NOT NULL AUTO_INCREMENT,
  `ddd` varchar(3) NOT NULL,
  `numero` varchar(10) NOT NULL,
  `tipo` varchar(15) NOT NULL,
  PRIMARY KEY (`idTelefone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
