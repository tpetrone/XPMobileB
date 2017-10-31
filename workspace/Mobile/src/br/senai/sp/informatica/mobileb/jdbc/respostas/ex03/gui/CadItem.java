package br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.senai.sp.informatica.mobileb.jdbc.respostas.ex03.model.Item;

@SuppressWarnings("serial")
public class CadItem extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblCodProd;
	private JTextField tfProd;
	private JLabel lblValor;
	private JTextField tfValor;
	private JLabel lblQuant;
	private JTextField tfQuant;
	private JButton btnGravar;
	private JButton btnFechar;
	private Item item;

	public CadItem(Item item) {
		setModal(true);
		this.item = item;
			
		setTitle("Cad. Item");
		setBounds(100, 100, 283, 221);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblCodProd = new JLabel("Cod. Prod.");
		lblCodProd.setBounds(21, 28, 76, 16);
		contentPanel.add(lblCodProd);
		
		tfProd = new JTextField();
		tfProd.setBounds(94, 22, 134, 28);
		contentPanel.add(tfProd);
		tfProd.setColumns(10);
		
		lblValor = new JLabel("Valor");
		lblValor.setBounds(21, 68, 61, 16);
		contentPanel.add(lblValor);
		
		tfValor = new JTextField();
		tfValor.setBounds(70, 62, 134, 28);
		contentPanel.add(tfValor);
		tfValor.setColumns(10);
		
		lblQuant = new JLabel("Quant.");
		lblQuant.setBounds(21, 110, 61, 16);
		contentPanel.add(lblQuant);
		
		tfQuant = new JTextField();
		tfQuant.setBounds(70, 104, 134, 28);
		contentPanel.add(tfQuant);
		tfQuant.setColumns(10);
		
		btnGravar = new JButton("Gravar");
		btnGravar.addActionListener(this);
		btnGravar.setBounds(21, 152, 117, 29);
		contentPanel.add(btnGravar);
		
		btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(this);
		btnFechar.setBounds(143, 152, 117, 29);
		contentPanel.add(btnFechar);
	}

	public void actionPerformed(ActionEvent ev) {
		String cmd = ev.getActionCommand();
		
		if(cmd.equals("Gravar")) {
			item.setCodProd(Integer.parseInt(tfProd.getText()));
			item.setValor(Double.parseDouble(tfValor.getText()));
			item.setQuantidade(Integer.parseInt(tfQuant.getText()));
		} else {
			item.setId(-1);
		}
		setVisible(false);
	}
}
