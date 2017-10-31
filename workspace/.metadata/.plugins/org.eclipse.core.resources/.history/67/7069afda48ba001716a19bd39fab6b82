package api.jdbc.exemplos.cursoLista;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GerenteDeDados {
	private String url = "jdbc:mysql://localhost:3306/projeto";
	private Connection con;
	private PreparedStatement consultaTodos;
	private PreparedStatement removeId;
	private PreparedStatement inserirReg;
	private PreparedStatement atualizaReg;
	private PreparedStatement localizaId;
	private PreparedStatement quantos;

	// Inicializa a conexão e cria as declarações SQL
	public GerenteDeDados() throws GerenteException {
		try {
			// Registra o Driver JDBC
			Class.forName("com.mysql.jdbc.Driver");
			// Inicia a conexão com Banco de Dados
			con = DriverManager.getConnection(url, "root", "senai");
			// Cria as declarações SQL para operações do Banco de Dados
			consultaTodos = con.prepareStatement("Select * from curso");
			removeId = con.prepareStatement("delete from curso where idcurso=?");
			inserirReg = con.prepareStatement("insert into curso (nome, matricula, descricao) values (?, ?, ?)");
			atualizaReg = con.prepareStatement("update curso set nome=?, matricula=?, descricao=? where idcurso=?");
			localizaId = con.prepareStatement("Select * from curso where idcurso=?");
			quantos = con.prepareStatement("Select count(*) as contador from curso as c");
		} catch (ClassNotFoundException ex) {
			throw new GerenteException("Driver JDBC não encontrado");
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new GerenteException("Problemas na conexão com o Banco de Dados");
		}
	}

	public void salvar(Curso obj) throws GerenteException {
		try {
			if (obj.getId() == null) { // É registro Novo?
				inserirReg.setString(1, obj.getNome());
				inserirReg.setDate(2, Date.valueOf(obj.getMatricula()));
				inserirReg.setString(3, obj.getDescricao());
				inserirReg.execute();
			} else { // Atualiza registro
				atualizaReg.setInt(4,obj.getId());
				atualizaReg.setString(1, obj.getNome());
				atualizaReg.setDate(2, Date.valueOf(obj.getMatricula()));
				atualizaReg.setString(3, obj.getDescricao());
				atualizaReg.execute();
			}
		} catch (SQLException ex) {
			throw new GerenteException("Erro na Conxão com o Banco de Dados");
		}
	}

	private Curso carregaRegistro(ResultSet resultado) throws SQLException {
		Curso obj = new Curso();
		obj.setId(resultado.getInt("idcurso"));
		obj.setNome(resultado.getString("nome"));
		obj.setMatricula(resultado.getDate("matricula").toLocalDate());
		obj.setDescricao(resultado.getString("descricao"));
		return obj;
	}

	public List<Curso> listaTodos() throws GerenteException {
		List<Curso> lista = null;
		try {
			lista = new ArrayList<Curso>();

			ResultSet resultado = consultaTodos.executeQuery();

			while (resultado.next()) {
				lista.add(carregaRegistro(resultado));
			}
			
		} catch (SQLException ex) {
			throw new GerenteException("Erro na Conxão com o Banco de Dados");
		}
		return lista;
	}
	
	public Curso localiza(Integer id) throws GerenteException {
		Curso obj = null;
		try {
			localizaId.setInt(1, id);
			ResultSet resultado = localizaId.executeQuery();

			if(resultado.next()) {
				obj = carregaRegistro(resultado);
			}
			
		} catch (SQLException ex) {
			throw new GerenteException("Erro na Conxão com o Banco de Dados");
		}
		return obj;
	}

	public int quantos() throws GerenteException {
		int quantidade = 0;
		try {
			ResultSet resultado = quantos.executeQuery();

			if(resultado.next()) {
				quantidade = resultado.getInt(1);
			}
			
		} catch (SQLException ex) {
			throw new GerenteException("Erro na Conxão com o Banco de Dados");
		}
		return quantidade;
	}

	public void remover(Integer id) throws GerenteException {
		try {
			removeId.setInt(1, id);
			removeId.execute();
		}catch(SQLException ex) {
			throw new GerenteException("Erro na Conxão com o Banco de Dados");
		}
	}

	public void fechar() {
		try {
			con.close();
		}catch(SQLException ex) {
		}
	}
}
