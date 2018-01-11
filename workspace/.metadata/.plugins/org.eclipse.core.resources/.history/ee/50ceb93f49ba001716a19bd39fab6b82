package api.jdbc.exemplos.MVCcursoLista.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.time.ZoneId;
import java.util.Date;

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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import api.jdbc.exemplos.MVCcursoLista.controller.Controller;
import api.jdbc.exemplos.MVCcursoLista.model.Curso;
import api.jdbc.exemplos.MVCcursoLista.model.generic.DaoException;
import fundamentos.lib.StatusValidador;
import fundamentos.lib.SwUtil;
import fundamentos.lib.TableVerificaData;
import fundamentos.lib.TableVerificaRegex;
import fundamentos.lib.VerificaCellEditor;
import fundamentos.lib.VerificaData;
import fundamentos.lib.VerificaRegex;


@SuppressWarnings("serial")
public class CadCurso extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JLabel lblNome;
	private JTextField tfNome;
	private JLabel lblDescr;
	private JTextField tfDescr;
	private JButton btnIncluir;
	private JButton btnApagar;
	private JButton btnSair;
	private JLabel lblMatrcula;
	private JFormattedTextField tfMatr;
	private JLabel errNome;
	private JLabel errMatr;
	private JLabel errDescr;
	private DateFormat formataData;
	private JScrollPane scrollPane;
	private JTable table;
	
	private Controller ctrl;  // <<==== local para guardar a instância do controller
	
	public CadCurso(Controller instancia, TableModel model)  throws DaoException { // <<== recebe as referências do controller e do TableModel
		ctrl = instancia;  // <<== guarda a referência do controller
		
		setTitle("Cad Curso");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		errNome = new JLabel(" ");
		errNome.setForeground(Color.RED);
		
		errMatr = new JLabel(" ");
		errMatr.setForeground(Color.RED);
		
		errDescr = new JLabel(" ");
		errDescr.setForeground(Color.RED);

		lblNome = new JLabel("Nome");

		tfNome = new JTextField();
		tfNome.setColumns(10);
		tfNome.setInputVerifier(new VerificaRegex(errNome, ".{1,45}"));
		
		lblMatrcula = new JLabel("Matr\u00EDcula");
		
		formataData = DateFormat.getDateInstance();
		formataData.setLenient(false);
		
		tfMatr = new JFormattedTextField(formataData);				
		tfMatr.setColumns(10);
		tfMatr.setInputVerifier(new VerificaData(errMatr));

		lblDescr = new JLabel("Descr.");

		tfDescr = new JTextField();
		tfDescr.setColumns(10);
		tfDescr.setInputVerifier(new VerificaRegex(errDescr, ".{5,450}"));

		btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(this);

		btnApagar = new JButton("Apagar");
		btnApagar.setVerifyInputWhenFocusTarget(false);
		btnApagar.addActionListener(this);

		btnSair = new JButton("Sair");
		btnSair.setVerifyInputWhenFocusTarget(false);
		btnSair.addActionListener(this);
		
		scrollPane = new JScrollPane();
			
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(16)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
							.addGap(27)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(errNome, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(tfNome, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
									.addGap(45)))
							.addGap(33))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblMatrcula)
							.addGap(30)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(errMatr, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(tfMatr, 134, 134, 134)
									.addGap(192))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblDescr, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(errDescr, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
								.addComponent(tfDescr, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
							.addGap(25))))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(47)
					.addComponent(btnIncluir, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
					.addGap(24)
					.addComponent(btnApagar, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
					.addGap(14)
					.addComponent(btnSair, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
					.addGap(61))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
					.addGap(11))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(lblNome))
						.addComponent(tfNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(1)
					.addComponent(errNome)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfMatr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMatrcula))
					.addGap(2)
					.addComponent(errMatr)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(lblDescr))
						.addComponent(tfDescr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(errDescr)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnIncluir)
						.addComponent(btnApagar)
						.addComponent(btnSair))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
					.addGap(15))
		);
		
		table = new JTable();
		table.setModel(model);  // <<=== passa o TableModel para o JTable
		table.setAutoCreateRowSorter(true);
		
		// Parametriza as colunas de edicão do JTable
		
		table.getColumnModel().getColumn(0).setCellEditor(new VerificaCellEditor(new TableVerificaRegex(".{1,45}")));
			
		table.getColumnModel().getColumn(1).setCellEditor(new VerificaCellEditor(new TableVerificaData(formataData)));
			
		table.getColumnModel().getColumn(2).setCellEditor(new VerificaCellEditor(new TableVerificaRegex(".{5,450}")));

		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void actionPerformed(ActionEvent ev) {
		Object botao = ev.getSource();
		try {
			if (botao.equals(btnIncluir)) {
				StatusValidador status = SwUtil.verificaStatus(this);
				if(status.equals(StatusValidador.OK)) {
					Curso obj = new Curso();
					obj.setNome(tfNome.getText());
				    obj.setMatricula(((Date) tfMatr.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
					obj.setDescricao(tfDescr.getText());

					// utilizar o Dao para salvar o novo objeto Curso
					ctrl.adiciona(obj);  // <<==  repassa o obj Curso para o controller salvar no banco de dados

					SwUtil.limpa(this);
					tfNome.requestFocus();
				} else {
					JOptionPane.showMessageDialog(this, "Existem campos com erro");
				}
			} else if (botao.equals(btnApagar)) {
				int linha = table.getSelectedRow();
				
				if(linha > -1) {
					if(table.getRowSorter() != null) 
						linha = table.getRowSorter().convertRowIndexToModel(linha);
					
					// utilizar o Dao para remover o objeto Curso 
					ctrl.remove(linha);  // <<== solicita ao controller para remover o objeto Curso correspondente a linha selecionada
				} else {
					JOptionPane.showMessageDialog(this, "Uma linha na tabela deve ser selecionada");
				}
			} else {
				System.exit(0);
			}
		} catch (DaoException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
		}
	}
}
