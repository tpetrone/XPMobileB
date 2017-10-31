package br.senai.sp.informatica.mobileb.jdbc.respostas.ex02;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.senai.sp.informatica.mobileb.jdbc.respostas.lib.GerenteException;


@SuppressWarnings("serial")
public class CadContrato extends JFrame implements ActionListener {

	private GerenteDeDados dados;
	private ContratoModel model;

	private JPanel contentPane;
	private JLabel lblN;
	private JTextField tfNum;
	private JLabel lblDescrio;
	private JTextField tfDesc;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnInserir;
	private JButton btnApagar;
	private JButton btnSair;
	private JLabel lblData;
	private JFormattedTextField tfData;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadContrato frame = new CadContrato();
					frame.setVisible(true);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
	}

	public CadContrato() throws GerenteException {
		setTitle("Cad. Contrato");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		lblN = new JLabel("N\u00BA");

		tfNum = new JTextField();
		tfNum.setColumns(10);

		lblDescrio = new JLabel("Descri\u00E7\u00E3o");

		tfDesc = new JTextField();
		tfDesc.setColumns(10);

		scrollPane = new JScrollPane();

		dados = new GerenteDeDados();
		model = new ContratoModel(dados);
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);

		// Parametriza um Text Fileld especializado para a edição das Datas
		table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(criaMascara("##/##/####")));
		
		btnInserir = new JButton("Inserir");
		btnInserir.addActionListener(this);

		btnApagar = new JButton("Apagar");
		btnApagar.addActionListener(this);

		btnSair = new JButton("Sair");
		btnSair.addActionListener(this);

		lblData = new JLabel("Data");

		tfData = criaMascara("##/##/####");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblN, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(26)
							.addComponent(tfNum)))
					.addGap(7)
					.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(textField, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
					.addGap(123))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(13)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDescrio, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(70)
							.addComponent(tfDesc, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)))
					.addGap(72))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(13)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
					.addGap(17))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(19)
					.addComponent(btnInserir, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(24)
					.addComponent(btnApagar, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(24)
					.addComponent(btnSair, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addGap(22))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(lblN))
						.addComponent(tfNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(lblData))
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(lblDescrio))
						.addComponent(tfDesc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnInserir)
						.addComponent(btnApagar)
						.addComponent(btnSair))
					.addGap(8))
		);
		contentPane.setLayout(gl_contentPane);	}

	private JFormattedTextField criaMascara(String mascara) {

		try {
			MaskFormatter formatter = new MaskFormatter(mascara);
			textField = new JFormattedTextField(formatter);
		} catch (ParseException exc) {
			textField = new JTextField();
		}

		return (JFormattedTextField) textField;
	}

	public void actionPerformed(ActionEvent ev) {
		String cmd = ev.getActionCommand();

		try {
			if (cmd.equals("Inserir")) {
				try {
					Contrato obj = new Contrato();
					obj.setData(tfData.getText());
					obj.setNumero(tfNum.getText());
					obj.setDescricao(tfDesc.getText());

					model.adiciona(obj);

					tfData.setValue(null);
					tfNum.setText("");
					tfDesc.setText("");
					tfNum.requestFocus();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage());
				}
			} else if (cmd.equals("Apagar")) {
				int linha = table.getSelectedRow();
				if (linha != -1) {
					if (table.getRowSorter() != null) {
						linha = table.getRowSorter().convertRowIndexToModel(
								linha);
					}
					model.remove(linha);
				} else {
					JOptionPane.showMessageDialog(this,
							"Nenhuma linha foi selecinada");
				}
			} else {
				dados.fechar();
				System.exit(0);
			}
		} catch (GerenteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}
}
