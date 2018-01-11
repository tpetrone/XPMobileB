package br.senai.sp.informatica.mobileb.jdbc.respostas.ex02;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.senai.sp.informatica.mobileb.jdbc.respostas.lib.DB;
import br.senai.sp.informatica.mobileb.jdbc.respostas.lib.GerenteException;


public class GerenteDeDados {
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
			con = DB.newInstance().getConnection("api.jdbc.resposta.ex02.contrato");

			// Cria as declarações SQL para operações do Banco de Dados
			consultaTodos = con.prepareStatement("Select * from contrato");
			removeId = con.prepareStatement("delete from contrato where id=?");
			inserirReg = con.prepareStatement("insert into contrato (data, numero, descricao) values (?, ?, ?)");
			atualizaReg = con.prepareStatement("update contrato set data=?, numero=?, descricao=? where id=?");
			localizaId = con.prepareStatement("Select * from contrato where id=?");
			quantos = con.prepareStatement("Select count(*) from contrato");
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new GerenteException("Problemas na conexão com o Banco de Dados");
		}
	}

	public void salvar(Contrato obj) throws GerenteException {
		try {
			if (obj.getId() == null) { // É registro Novo?
				inserirReg.setDate(1, new Date(obj.getData().getTime()));
				inserirReg.setString(2, obj.getNumero());
				inserirReg.setString(3, obj.getDescricao());
				inserirReg.execute();
			} else { // Atualiza registro
				atualizaReg.setInt(4, obj.getId());
				atualizaReg.setDate(1, new Date(obj.getData().getTime()));
				atualizaReg.setString(2, obj.getNumero());
				atualizaReg.setString(3, obj.getDescricao());
				atualizaReg.execute();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new GerenteException("Erro na Conexão com o Banco de Dados");
		}
	}

	public List<Contrato> listaTodos() throws GerenteException {
		List<Contrato> lista = null;
		try {
			lista = new ArrayList<Contrato>();

			ResultSet resultado = consultaTodos.executeQuery();

			while (resultado.next()) {
				Contrato obj = new Contrato();
				obj.setId(resultado.getInt("id"));
				obj.setData(resultado.getDate("data"));
				obj.setNumero(resultado.getString("numero"));
				obj.setDescricao(resultado.getString("descricao"));
				lista.add(obj);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new GerenteException("Erro na Conexão com o Banco de Dados");
		}
		return lista;
	}

	public Contrato localiza(Integer id) throws GerenteException {
		Contrato obj = null;
		try {
			localizaId.setInt(1, id);
			ResultSet resultado = localizaId.executeQuery();

			if (resultado.next()) {
				obj = new Contrato();
				obj.setId(resultado.getInt("id"));
				obj.setData(resultado.getDate("data"));
				obj.setNumero(resultado.getString("numero"));
				obj.setDescricao(resultado.getString("descricao"));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new GerenteException("Erro na Conexão com o Banco de Dados");
		}
		return obj;
	}

	public int quantos() throws GerenteException {
		int quantidade = 0;
		try {
			ResultSet resultado = quantos.executeQuery();

			if (resultado.next()) {
				quantidade = resultado.getInt(1);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new GerenteException("Erro na Conexão com o Banco de Dados");
		}
		return quantidade;
	}

	public void remover(Integer id) throws GerenteException {
		try {
			removeId.setInt(1, id);
			removeId.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new GerenteException("Erro na Conexão com o Banco de Dados");
		}
	}

	public void fechar() {
		try {
			DB.newInstance().close("api.jdbc.resposta.ex02.contrato");
		} catch (GerenteException ex) {
		}
	}
}
