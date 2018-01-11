package api.jdbc.resposta.ex01;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings({ "serial" })
public class CadFone extends JFrame implements ActionListener {
	private JPanel contentPane;
	private JLabel lblDdd;
	private JTextField tfDdd;
	private JLabel lblNum;
	private JTextField tfNum;
	private JLabel lblTipo;
	private JComboBox<TipoFone> cbTipo;
	private JButton btnSalvar;
	private JButton btnListar;
	private JButton btnSair;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadFone frame = new CadFone();
					frame.setVisible(true);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
					ex.printStackTrace();
				}
			}
		});
	}

	public CadFone() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 494, 128);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblDdd = new JLabel("DDD");
		lblDdd.setBounds(19, 17, 61, 16);
		contentPane.add(lblDdd);

		tfDdd = new JTextField();
		tfDdd.setBounds(64, 11, 46, 28);
		contentPane.add(tfDdd);
		tfDdd.setColumns(10);

		lblNum = new JLabel("N\u00BA");
		lblNum.setBounds(122, 17, 61, 16);
		contentPane.add(lblNum);

		tfNum = new JTextField();
		tfNum.setBounds(149, 11, 113, 28);
		contentPane.add(tfNum);
		tfNum.setColumns(10);

		lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(274, 17, 61, 16);
		contentPane.add(lblTipo);

		cbTipo = new JComboBox<>();
		cbTipo.setModel(new DefaultComboBoxModel<>(TipoFone.values()));
		cbTipo.setBounds(313, 13, 159, 27);
		contentPane.add(cbTipo);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(this);
		btnSalvar.setBounds(19, 51, 117, 29);
		contentPane.add(btnSalvar);

		btnListar = new JButton("Listar");
		btnListar.addActionListener(this);
		btnListar.setBounds(186, 51, 117, 29);
		contentPane.add(btnListar);

		btnSair = new JButton("Sair");
		btnSair.addActionListener(this);
		btnSair.setBounds(355, 52, 117, 29);
		contentPane.add(btnSair);
	}

	public void actionPerformed(ActionEvent ev) {
		try {
			String cmd = ev.getActionCommand();

			if (cmd.equals("Salvar")) {
				Fone obj = new Fone();
				obj.setDdd(tfDdd.getText());
				obj.setNumero(tfNum.getText());
				TipoFone tipo = cbTipo.getItemAt(cbTipo.getSelectedIndex());
				if(!tipo.equals(TipoFone.SELECIONE))
					obj.setTipo(tipo);
				else
					throw new Exception("O Tipo de Telefone não foi selecionado");

				FoneDao.manager.salvar(obj);

				tfDdd.setText("");
				tfNum.setText("");
				cbTipo.setSelectedIndex(0);
				tfDdd.requestFocus();
			} else if (cmd.equals("Listar")) {
				String msg = "Cad. Telefones\n\n";
				for (Fone obj : FoneDao.manager.listaTodos())
					msg += obj + "\n";
				JOptionPane.showMessageDialog(this, msg);
			} else {
				System.exit(0);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}
}
