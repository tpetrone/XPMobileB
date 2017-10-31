package br.senai.sp.informatica.mobileb.projeto_enigma;

import java.awt.FileDialog;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class DesvendaCodigo {
	public static void main(String[] args) {
		try {
			// Obtém o nome do Arquivo
			String nomeDoArquivo = solicitaArquivo();
	
			if(nomeDoArquivo != null) {		
				// Lê o código
				String chave = JOptionPane.showInputDialog("Informe o código");
				// Carrega o código separando as chaves de acesso aos textos
				ConuntoDeCodigos dao = new ConuntoDeCodigos(chave);
				// Localiza os Textos no arquivo
				dao.localizaTexto(nomeDoArquivo);
				// Obtém a frase decodificada
				String frase = dao.montaFrase();
				// Apresenta a frase
				JOptionPane.showMessageDialog(null, frase);
			}
		} catch (DaoException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
	
	// Apresenta um diálogo para solicitar o nome do arquivo a ser lido
	public static String solicitaArquivo() {
		// Abre o arquivo de Texto
		FileDialog fd = new FileDialog((JDialog) null, "Abrir", FileDialog.LOAD);
		fd.setVisible(true);
		return fd.getDirectory() + fd.getFile();

	}	
}

