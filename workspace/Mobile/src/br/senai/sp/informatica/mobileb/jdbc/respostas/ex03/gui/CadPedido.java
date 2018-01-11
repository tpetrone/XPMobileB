package br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.db.GerenteDeDados;
import br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.db.GerenteException;
import br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.model.Item;
import br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.model.Pedido;

@SuppressWarnings("serial")
public class CadPedido extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JLabel lblNPedido;
	private JTextField tfPedido;
	private JLabel lblCodCliente;
	private JTextField tfCliente;
	private JLabel lblEndEntrega;
	private JTextField tfEnd;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnGravar;
	private JButton btnIncluirItem;
	private JButton btnSair;
	private GerenteDeDados dados;
	private ItemModel model;
	private List<Item> lista;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadPedido frame = new CadPedido();
					frame.setVisible(true);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
	}

	public CadPedido() throws GerenteException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNPedido = new JLabel("N\u00BA Pedido");
		lblNPedido.setBounds(16, 25, 75, 16);
		contentPane.add(lblNPedido);

		tfPedido = new JTextField();
		tfPedido.setBounds(90, 19, 104, 28);
		contentPane.add(tfPedido);
		tfPedido.setColumns(10);

		lblCodCliente = new JLabel("Cod. Cliente");
		lblCodCliente.setBounds(219, 25, 88, 16);
		contentPane.add(lblCodCliente);

		tfCliente = new JTextField();
		tfCliente.setBounds(310, 19, 117, 28);
		contentPane.add(tfCliente);
		tfCliente.setColumns(10);

		lblEndEntrega = new JLabel("End. Entrega");
		lblEndEntrega.setBounds(16, 64, 88, 16);
		contentPane.add(lblEndEntrega);

		tfEnd = new JTextField();
		tfEnd.setBounds(100, 59, 327, 28);
		contentPane.add(tfEnd);
		tfEnd.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 92, 411, 129);
		contentPane.add(scrollPane);

		dados = new GerenteDeDados();
		lista = new ArrayList<Item>();
		model = new ItemModel(lista);
		table = new JTable(model);
		scrollPane.setViewportView(table);

		btnGravar = new JButton("Gravar");
		btnGravar.addActionListener(this);
		btnGravar.setBounds(16, 233, 117, 29);
		contentPane.add(btnGravar);

		btnIncluirItem = new JButton("Incluir Item");
		btnIncluirItem.addActionListener(this);
		btnIncluirItem.setBounds(166, 233, 117, 29);
		contentPane.add(btnIncluirItem);

		btnSair = new JButton("Sair");
		btnSair.addActionListener(this);
		btnSair.setBounds(310, 233, 117, 29);
		contentPane.add(btnSair);
	}

	public void actionPerformed(ActionEvent ev) {
		String cmd = ev.getActionCommand();

		try {
			if (cmd.equals("Gravar")) {
				Pedido ped  = new Pedido();
				ped.setNumero(Integer.parseInt(tfPedido.getText()));
				ped.setCodCliente(Integer.parseInt(tfCliente.getText()));
				ped.setEndEntrega(tfEnd.getText());
				ped.setItens(lista);
				
				dados.salvar(ped);
				
				lista = new ArrayList<Item>();
				model.setLista(lista);
				
				tfPedido.setText("");
				tfCliente.setText("");
				tfEnd.setText("");
				tfPedido.requestFocus();
			} else if (cmd.equals("Incluir Item")) {
				Item item = new Item();
				// chama o dialogo
				CadItem dialog = new CadItem(item);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				// verificar se o item deve ser incluido incluido
				if(item.getId() == null)
					model.add(item);
			} else {
				dados.fechar();
				System.exit(0);
			}
		} catch (GerenteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
			ex.printStackTrace();
		}
	}
}
