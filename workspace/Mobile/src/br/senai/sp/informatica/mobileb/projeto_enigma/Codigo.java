package br.senai.sp.informatica.mobileb.projeto_enigma;

//Esta classe define a estrutura do codigo sua ordem e o texto associado
//Para carregar o texto a coleção de codigos deve ser ordenado por (paragrafo, linha e palavra)
//para imprimir a frase final a coleção de codigos deve ser ordenada por ordem
public class Codigo {
	private int ordem;
	private int paragrafo;
	private int linha;
	private int palavra;
	private String texto;

	// o construtor deve receber a ordem e os numeros que compõem um código
	// (paragrafo, linha e palavra)
	public Codigo(int ordem, String[] codigo) {
		try {
			if (codigo.length == 3) {
				this.ordem = ordem;
				this.paragrafo = Integer.parseInt(codigo[0]);
				this.linha = Integer.parseInt(codigo[1]);
				this.palavra = Integer.parseInt(codigo[2]);
			} else {
				throw new Error("Código errado: " + codigo);
			}
		} catch (NumberFormatException ex) {
			throw new Error("Código errado: " + codigo);
		}
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public int getParagrafo() {
		return paragrafo;
	}

	public void setParagrafo(int paragrafo) {
		this.paragrafo = paragrafo;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getPalavra() {
		return palavra;
	}

	public void setPalavra(int palavra) {
		this.palavra = palavra;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}