package api.jdbc.exemplos.MVCcursoLista.model.generic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Dao<T extends Registro> {
	protected Connection conecta(Config cfg) throws DaoException {
		try {
			return cfg.loadConfig();
		} catch (ClassNotFoundException ex) {
			throw new DaoException("Houve problema em localizar o Driver JDBC");
		} catch (SQLException ex) {
			throw new DaoException("Houve problema ao conectar ao Banco de Dados");
		}
	}
	
	// Insere um novo registro ou Atualiza um registro existente
	public void salvar(T obj) throws DaoException {
		String acao = "";
		Connection con = getConnection();
		try {
			if (obj.getId() == null) {
				acao = "inserção";
				inserir(obj, con);
			} else {
				acao = "atualização";
				atualizar(obj, con);
			}
		} catch (SQLException ex) {
			throw new DaoException("Houve problema na " + acao + " do registro");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new DaoException("Houve problema no fechamento da conexão");
			}
		}
	}

	// Retorna a lista de todos os Telefones existentes no banco de dados
	public List<T> listaTodos(SQLQuery sql) throws DaoException {
		Connection con = getConnection();
		try {			
			List<T> lista = new ArrayList<>();
			
			PreparedStatement query = sql.getStatement(con);
			ResultSet resposta = SQLQuery.execute(query);
			while(resposta.next()) {
				T obj = criaObjeto(resposta);
				lista.add(obj);
			}
			
			return lista;
		} catch(SQLException ex) {
			throw new DaoException("Erro na consulta dos registros");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new DaoException("Houve problema no fechamento da conexão");
			}
		}
	}
	
	protected void execute(SQLCommand sql) throws DaoException {
		Connection con = getConnection();
		try {
			PreparedStatement query = sql.getStatement(con);
			SQLCommand.execute(query);
		} catch(SQLException ex) {
			throw new DaoException("Erro na consulta dos registros");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new DaoException("Houve problema no fechamento da conexão");
			}
		}		
	}
	
	public abstract Connection getConnection() throws DaoException;
	
	public abstract void inserir(T obj, Connection con) throws SQLException;
	
	public abstract void atualizar(T obj, Connection con) throws SQLException;
	
	public abstract T criaObjeto(ResultSet resultado) throws SQLException;
	
}


