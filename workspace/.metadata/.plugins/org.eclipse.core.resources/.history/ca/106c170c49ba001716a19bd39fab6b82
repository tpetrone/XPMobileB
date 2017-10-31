package api.jdbc.exemplos.MVCcursoLista.model.generic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLQuery {
	PreparedStatement getStatement(Connection con) throws SQLException;
	
	public static ResultSet execute(PreparedStatement sql) throws SQLException {
		return sql.executeQuery();
	}
}