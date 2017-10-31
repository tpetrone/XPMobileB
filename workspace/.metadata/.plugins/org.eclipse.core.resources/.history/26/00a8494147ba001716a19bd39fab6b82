package api.jdbc.resposta.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FoneDao {
	public static FoneDao manager = new FoneDao();
	
	private FoneDao() {		
	}
	
	private Connection getConnection() throws GerenteException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			return DriverManager.getConnection("jdbc:mysql://localhost/CursoJava", "root", "senai");
		} catch (ClassNotFoundException ex) {
			throw new GerenteException("Houve problema em localizar o Driver JDBC");		
		} catch (SQLException ex) {
			throw new GerenteException("Houve problema ao conectar ao Banco de Dados CursoJava");		
		}
	}
	
	// Insere um novo registro ou Atualiza um registro existente
	public void salvar(Fone obj) throws GerenteException {
		String acao = "";
		Connection con = getConnection();
		try {
			if(obj.getId() == null) { // Insere um novo registro
				acao = "inserção";
				PreparedStatement insereFone = con.prepareStatement("insert into telefone (ddd, numero, tipo) values (?, ?, ?)");
				insereFone.setString(1, obj.getDdd());
				insereFone.setString(2, obj.getNumero());
				insereFone.setString(3, obj.getTipo().toString());
				insereFone.execute();
			} else { // Atualiza um registro existente
				acao = "atualização";
				PreparedStatement atualizaFone = con.prepareStatement("update telefone set ddd=?, numero=?, tipo=? where idtelefone=?");
				atualizaFone.setString(1, obj.getDdd());
				atualizaFone.setString(2, obj.getNumero());
				atualizaFone.setString(3, obj.getTipo().toString());
				atualizaFone.setInt(4, obj.getId());
				atualizaFone.execute();
			}
		} catch (SQLException ex) {
			throw new GerenteException("Houve problema na " + acao + " do registro");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new GerenteException("Houve problema no fechamento da conexão");
			}
		}
		
	}

	// Cria um objeto Telefone extraindo os dados do Resultado da consulta SQL
	private Fone criaObjeto(ResultSet resultado) throws SQLException {
		Fone obj = new Fone();
		obj.setId(resultado.getInt("idtelefone"));
		obj.setDdd(resultado.getString("ddd"));
		obj.setNumero(resultado.getString("numero"));
		obj.setTipo(TipoFone.valueOf(resultado.getString("tipo")));
		return obj;
	}

	
	// Retorna a lista de todos os Telefones existentes no banco de dados
	public List<Fone> listaTodos() throws GerenteException {
		Connection con = getConnection();
		try {			
			List<Fone> lista = new ArrayList<>();
			Statement listaFone = con.createStatement();
			
			ResultSet resposta = listaFone.executeQuery("select * from telefone");
			while(resposta.next()) {
				Fone obj = criaObjeto(resposta);
				lista.add(obj);
			}
			
			return lista;
		} catch(SQLException ex) {
			throw new GerenteException("Erro na consulta dos registros");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				throw new GerenteException("Houve problema no fechamento da conexão");
			}
		}
	}
}
