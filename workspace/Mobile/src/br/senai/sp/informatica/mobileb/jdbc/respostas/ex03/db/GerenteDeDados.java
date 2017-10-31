package br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.model.Item;
import br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.model.Pedido;


public class GerenteDeDados {
	private String url = "jdbc:mysql://localhost:3306/projeto";
	private Connection con;
	private PreparedStatement consultaPedido;
	private PreparedStatement consultaItem;
	private PreparedStatement removeId;
	private PreparedStatement removeItemId;
	private PreparedStatement inserirReg;
	private PreparedStatement inserirItemReg;
	private PreparedStatement atualizaReg;
	private PreparedStatement atualizaItemReg;
	private PreparedStatement localizaId;
	private PreparedStatement quantos;
	private PreparedStatement getId;

	// Inicializa a conexão e cria as declarações SQL
	public GerenteDeDados() throws GerenteException {
		try {
			// Registra o Driver JDBC
			Class.forName("com.mysql.jdbc.Driver");
			// Inicia a conexão com Banco de Dados
			con = DriverManager.getConnection(url, "root", "");
			// Cria as declarações SQL para operações do Banco de Dados
			consultaPedido = con.prepareStatement("Select * from pedido");
			consultaItem = con.prepareStatement("Select * from item where idpedido=?");
			
			removeItemId = con.prepareStatement("delete from item where idpedido=?");
			removeId = con.prepareStatement("delete from pedido where idpedido=?");

			inserirReg = con.prepareStatement(
					"insert into pedido (numero, codCliente, endEntrega) values (? , ? , ?)");			
			inserirItemReg = con.prepareStatement(
					"insert into item (idpedido, codProd, valor, quant) values (? , ? , ? , ?)");
			
			atualizaReg = con
					.prepareStatement(
							"update pedido set numero=?, codCliente=?, " +
							"logradouro=?, complemento=?, endEntrega=? where idpedido=?");
			atualizaItemReg = con
				.prepareStatement(
						"update item set codProd=?, valor=?, quant=? where iditem=?");
				
			localizaId = con.prepareStatement(
					"Select * from pedido where p.idpedido=? ");

			quantos = con.prepareStatement("Select count(*) from pedido");
			
			getId = con.prepareStatement("select last_insert_id() as id from pedido");
			
		} catch (ClassNotFoundException ex) {
			throw new GerenteException("Driver JDBC não encontrado");
		} catch (SQLException ex) {
			throw new GerenteException("Problemas na conexão com o Banco de Dados");
		}
	}

	public void salvar(Pedido obj) throws GerenteException {
		try {
			if (obj.getId() == null) { // É registro Novo?
				inserirReg.setInt(1, obj.getNumero());
				inserirReg.setInt(2, obj.getCodCliente());
				inserirReg.setString(3, obj.getEndEntrega());
				inserirReg.execute();
				
				ResultSet resposta = getId.executeQuery();
				if(resposta.next()) {
					int id = resposta.getInt("id");
					
					for (Item item : obj.getItens()) {
						inserirItemReg.setInt(1, id);
						inserirItemReg.setInt(2, item.getCodProd());
						inserirItemReg.setDouble(3, item.getValor());
						inserirItemReg.setInt(4, item.getQuantidade());
						inserirItemReg.execute();
					}
				} else {
					throw new GerenteException("Houveram problemas na gravação dos dados de Pedido");					
				}
			} else { // Atualiza registro
				
				atualizaReg.setInt(4, obj.getId());
				atualizaReg.setInt(1, obj.getNumero());
				atualizaReg.setInt(2, obj.getCodCliente());
				atualizaReg.setString(3, obj.getEndEntrega());
				atualizaReg.execute();
				
				for (Item item : obj.getItens()) {
					atualizaItemReg.setInt(4, item.getId());
					atualizaItemReg.setInt(1, item.getCodProd());
					atualizaItemReg.setDouble(2, item.getValor());
					atualizaItemReg.setInt(3, item.getQuantidade());
					atualizaItemReg.execute();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new GerenteException("Erro na Conxão com o Banco de Dados");
		}
	}

	public List<Pedido> listaTodos() throws GerenteException {
		List<Pedido> lista = null;
		try {
			lista = new ArrayList<Pedido>();

			ResultSet resultado = consultaPedido.executeQuery();

			while (resultado.next()) {
				lista.add(carregaDados(resultado));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new GerenteException("Erro na Conxão com o Banco de Dados");
		}
		return lista;
	}

	public Pedido localiza(Integer id) throws GerenteException {
		Pedido obj = null;
		try {
			localizaId.setInt(1, id);
			ResultSet resultado = localizaId.executeQuery();

			if(resultado.next()) {
				obj = carregaDados(resultado);
			}
			
		} catch (SQLException ex) {
			throw new GerenteException("Erro na Conxão com o Banco de Dados");
		}
		return obj;
	}

	private Pedido carregaDados(ResultSet dados) throws GerenteException {
		Pedido obj = new Pedido();

		try {
			int id = dados.getInt("idpedido");		
			
			obj.setId(id);
			obj.setNumero(dados.getInt("numero"));
			obj.setCodCliente(dados.getInt("codCliente"));
			obj.setEndEntrega(dados.getString("endEntrega"));
			
			consultaItem.setInt(1, id);
			ResultSet itens = consultaItem.executeQuery();
			List<Item> listaItens = new ArrayList<Item>();
			while(itens.next()) {
				Item item = new Item();
				item.setId(itens.getInt("iditem"));
				item.setCodProd(itens.getInt("codProd"));
				item.setValor(itens.getDouble("valor"));
				item.setQuantidade(itens.getInt("quant"));
				listaItens.add(item);
			}
			obj.setItens(listaItens);
		} catch (SQLException ex) {
			ex.printStackTrace();
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
			removeItemId.setInt(1, id);
			removeItemId.execute();
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
