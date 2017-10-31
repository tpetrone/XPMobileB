package br.senai.sp.informatica.mobileb.exemplos.objeto;

public class Funcionario {
	private String nome;
	private double salario;
	private int matricula;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	@Override
	public String toString() {
		return "nome: " + nome + 
				" salario: " + salario + 
				" matricula: " + matricula;
	}

	
}
