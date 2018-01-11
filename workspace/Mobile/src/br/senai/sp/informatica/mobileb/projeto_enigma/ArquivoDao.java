package br.senai.sp.informatica.mobileb.projeto_enigma;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Implementa o mecanismo para a leitura do arquivo
// concentrando as ações necessárias para
// o posicionamento dos parágrafos e linhas
// e obtenção do texto
public class ArquivoDao {
	// Guarda o nome do arquivo a ser lido
	private String nomeArquivo;
	// Guarda o file Handler para o correto fechamento quando solicitado
	private FileReader fr;
	// Mantém um InputStream para a leitura das linhas
	private BufferedReader in;
	// Representa o nº parágrafo atual lido no arquivo
	private int paragrafoAtual;
	// Representa o nº da linha atual lido no arquivo
	private int linhaAtual;
	// Representa o texto da linha atual lida do arquivo
	private String textoAtual;
	
	// Inicializa os argumentos de controle e abre o arquivo para a leitura
	public ArquivoDao(String nomeArquivo) throws DaoException  {
		try {
			this.nomeArquivo = nomeArquivo;
			fr = new FileReader(nomeArquivo); 
			in = new BufferedReader(fr);
			paragrafoAtual = 1;
			linhaAtual = 0;
		} catch (FileNotFoundException ex) {
			throw new DaoException("O Arquivo " + nomeArquivo + "não foi encontrado");
		}
	}

	// Localiza o Texto contido em um código
	public String localizaCodigo(Codigo codigo) throws DaoException {
		try {
			noParagrafo(codigo.getParagrafo());
			String texto = naLinha(codigo.getLinha());
	
			if(texto != null) {
				String[] palavras = texto.split(" ");
				if(palavras.length >= codigo.getPalavra())
					return palavras[codigo.getPalavra()-1];
				else
					return "";
			} else {
				return "";
			}
		} catch (DaoException ex) {
			throw new DaoException("Falhar ao ler o arquivo " + nomeArquivo);
		}
	}
		
	// Posiciona a leitura do arquivo no parágrafo solicitado
	private void noParagrafo(int paragrafo) throws DaoException  {
		String texto = null;
		for(;paragrafoAtual < paragrafo;paragrafoAtual++,linhaAtual = 0) {
			texto = lerLinha();
			while(texto != null && !texto.isEmpty())
				texto = lerLinha();
		}		
	}
	
	// Posiciona a leitura no arquivo na linha do parágrafo solicitado
	private String naLinha(int linha) throws DaoException  {
		if(linhaAtual == 0)
			textoAtual = lerLinha();
		
		while(linhaAtual < linha && textoAtual != null) {
			textoAtual = lerLinha();
		}		
		return textoAtual;
	}
	
	// Lê uma linha e mantém a informação da posição atual
	private String lerLinha() throws DaoException {
		try {
			String texto = in.readLine();
			if(texto != null && !texto.isEmpty())
				linhaAtual++;
			return texto;
		} catch (IOException ex) {
			throw new DaoException("Falha ao ler o arquivo " + nomeArquivo);
		}
	}
	
	// Fecha o Arquivo
	public void fechaArquivo() throws DaoException {
		try {
			if(fr != null) {
				fr.close();
			}
		} catch (IOException ex) {
			throw new DaoException("Falha ao fechar o arquivo " + nomeArquivo);
		}
	}
}
