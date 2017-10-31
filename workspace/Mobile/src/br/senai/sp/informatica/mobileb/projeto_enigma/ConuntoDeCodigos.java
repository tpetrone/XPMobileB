package br.senai.sp.informatica.mobileb.projeto_enigma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Esta classe representa o conjunto dos códigos a serem processados
public class ConuntoDeCodigos {
	// Lista de códigos
	private List<Codigo> lista;
	
	// Inicializa os códigos ordenando na sequência correta para
	// a leitura dos textos no arquivo
 	public ConuntoDeCodigos(String chave) {
		lista = new ArrayList<>();
		
		// Divide os grupos do código
		String[] endereco = chave.split(":");
		// Carrega os códigos
		for (int i = 0; i < endereco.length; i+=3) {
			String[] codigo = Arrays.copyOfRange(endereco, i, i+3);
			lista.add(new Codigo(i, codigo));
		}
		
		// ordena o codigo para a localização dos textos no arquivo
		Collections.sort(lista, 
				Comparator.comparing(Codigo::getParagrafo)
				.thenComparing(Codigo::getLinha)
				.thenComparing(Codigo::getPalavra));
	}

 	// Localiza os textos no arquivo informado
 	public void localizaTexto(String nomeArquivo) throws DaoException {
		// Abre o arquivo
		ArquivoDao dao = new ArquivoDao(nomeArquivo);
		// Localiza os Textos
		for (Codigo codigo : lista)
			codigo.setTexto(dao.localizaCodigo(codigo));
		// Fecha o arquivo
		dao.fechaArquivo();
 	}

 	// Monta a frase conforme a ordem original dos códigos com os textos
 	// obtidos do arquivo lido
 	public String montaFrase() {
 		// Ordena os códigos para a posição original
 		Collections.sort(lista, Comparator.comparing(Codigo::getOrdem));
 		// Monta a frase com os textos dos códigos
 		String frase = "";
 		for (Codigo codigo : lista) {
 			String texto = codigo.getTexto()
 					.replaceAll(",", "")
 					.replaceAll("\'", "")
 					.replaceAll("\"", "");
			frase += texto + " ";
		}
 		return frase;
 	}
}
