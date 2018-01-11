package api.jdbc.exemplos.curso;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import api.jdbc.lib.GerenteException;

@SuppressWarnings("serial")
public class CadCurso extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JLabel lblNome;
	private JTextField tfNome;
	private JLabel lblDescr;
	private JTextField tfDescr;
	private JButton btnIncluir;
	private JButton btnListar;
	private JButton btnSair;
	private JLabel lblMatricula;
	private JTextField tfData;
	
	private GerenteDeDados dados;  // <====== Data Access Object (DAO)

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadCurso frame =  new CadCurso();
					frame.conecta();  // <==== Tenta conectar ao Banco de Dados antes de apresentar o Frame
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CadCurso() {
		setTitle("Cad Curso");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 218);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNome = new JLabel("Nome");
		lblNome.setBounds(21, 23, 61, 16);
		contentPane.add(lblNome);

		tfNome = new JTextField();
		tfNome.setBounds(109, 17, 134, 28);
		contentPane.add(tfNome);
		tfNome.setColumns(10);

		lblDescr = new JLabel("Descr.");
		lblDescr.setBounds(21, 97, 61, 16);
		contentPane.add(lblDescr);

		tfDescr = new JTextField();
		tfDescr.setBounds(109, 91, 301, 28);
		contentPane.add(tfDescr);
		tfDescr.setColumns(10);

		btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(this);
		btnIncluir.setBounds(21, 131, 117, 29);
		contentPane.add(btnIncluir);

		btnListar = new JButton("Listar");
		btnListar.addActionListener(this);
		btnListar.setBounds(162, 131, 117, 29);
		contentPane.add(btnListar);

		btnSair = new JButton("Sair");
		btnSair.addActionListener(this);
		btnSair.setBounds(293, 131, 117, 29);
		contentPane.add(btnSair);

		lblMatricula = new JLabel("Matricula");
		lblMatricula.setBounds(21, 57, 61, 16);
		contentPane.add(lblMatricula);

		tfData = new JTextField();
		tfData.setBounds(109, 51, 134, 28);
		contentPane.add(tfData);
		tfData.setColumns(10);
	}

	public void conecta() {  // < == Conecta ao Banco de Dados
		try {
			dados = new GerenteDeDados();
			setVisible(true);
		} catch (GerenteException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	
	public void actionPerformed(ActionEvent ev) {
		Object botao = ev.getSource();
		try {
			if (botao.equals(btnIncluir)) {
				try {
					DateFormat df = DateFormat.getDateInstance();
					df.setLenient(false);

					Curso obj = new Curso();
					obj.setNome(tfNome.getText());
					obj.setMatricula(df.parse(tfData.getText()));
					obj.setDescricao(tfDescr.getText());

					dados.salvar(obj);  // <=== Grava o Curso no Banco de Dados

					tfNome.setText("");
					tfData.setText("");
					tfDescr.setText("");
					tfNome.requestFocus();
				} catch (ParseException ex) {
					JOptionPane.showMessageDialog(this, "Data de matricula invalida");
				}
			} else if (botao.equals(btnListar)) {
				String txt = "Cadastro de Cursos\n\n";
				List<Curso> lista = dados.listaTodos(); // <=== obtem os Cursos do Banco de Dados
				for (int i = 0; i < lista.size(); i++) {
					Curso obj = lista.get(i);
					txt += obj + "\n";
				}
				JOptionPane.showMessageDialog(this, txt);
			} else {
				dados.fechar(); // <==== fecha a conexão ao Banco de Dados
				System.exit(0);
			}
		} catch (GerenteException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}
}
