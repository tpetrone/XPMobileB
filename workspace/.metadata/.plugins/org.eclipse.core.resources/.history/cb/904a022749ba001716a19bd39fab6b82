package api.jdbc.exemplos.MVCcursoLista.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import api.jdbc.exemplos.MVCcursoLista.model.generic.Dao;
import api.jdbc.exemplos.MVCcursoLista.model.generic.DaoException;

public class CursoDao extends Dao<Curso> {
	@Override
	public Connection getConnection() throws DaoException {
		return conecta(() -> {
				Class.forName("com.mysql.jdbc.Driver");

				return DriverManager.getConnection("jdbc:mysql://localhost/CursoJava", "root", "senai");
			});
	}

	@Override
	public void inserir(Curso obj, Connection con) throws SQLException {
		PreparedStatement insereFone = con.prepareStatement("insert into Curso (nome, matricula, descricao) values (?, ?, ?)");
		insereFone.setString(1, obj.getNome());
		insereFone.setDate(2, Date.valueOf(obj.getMatricula()));
		insereFone.setString(3, obj.getDescricao());
		insereFone.execute();
	}

	@Override
	public void atualizar(Curso obj, Connection con) throws SQLException {
		PreparedStatement atualizaFone = con.prepareStatement("update Curso set nome=?, matricula=?, descricao=? where idcurso=?");
		atualizaFone.setString(1, obj.getNome());
		atualizaFone.setDate(2, Date.valueOf(obj.getMatricula()));
		atualizaFone.setString(3, obj.getDescricao());
		atualizaFone.setInt(4, obj.getId());
		atualizaFone.execute();
	}

	@Override
	public Curso criaObjeto(ResultSet resultado) throws SQLException {
		Curso obj = new Curso();
		obj.setId(resultado.getInt("idcurso"));
		obj.setNome(resultado.getString("nome"));
		obj.setMatricula(resultado.getDate("matricula").toLocalDate());
		obj.setDescricao(resultado.getString("descricao"));
		return obj;
	}

	public List<Curso> listaTodos() throws DaoException {
		return listaTodos(con -> con.prepareStatement("select * from curso"));
	}

	public void apaga(Integer id) throws DaoException {
		execute(con -> {
				PreparedStatement sql = con.prepareStatement("delete from curso where idcurso = ?");
				sql.setInt(1, id);
				return sql;
			});
	}
}
