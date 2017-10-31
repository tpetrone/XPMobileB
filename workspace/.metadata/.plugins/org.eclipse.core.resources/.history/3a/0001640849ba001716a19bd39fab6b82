package api.jdbc.exemplos.MVCcursoLista.model.generic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLCommand {
	PreparedStatement getStatement(Connection con) throws SQLException;
	
	public static int execute(PreparedStatement sql) throws SQLException {
		return sql.executeUpdate();
	}
}