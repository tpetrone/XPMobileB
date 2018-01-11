package br.senai.sp.informatica.mobileb.jdbc.exemplos.MVCcursoLista.model.generic;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface Config {
	Connection loadConfig() throws ClassNotFoundException, SQLException;
}
