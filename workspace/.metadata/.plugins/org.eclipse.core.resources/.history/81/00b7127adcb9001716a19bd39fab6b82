package br.senai.sp.informatica.mobileb.calculadora;

import java.util.Stack;

public class Calculadora {
	private Stack<Double> pilha = new Stack<>();
	private Operacao op;

	public enum Operacao {
		SOMA {
			public double calcule(double num1, double num2) {
				return num1 + num2;
			}
		},
		SUBTRACAO {
			public double calcule(double num1, double num2) {
				return num1 - num2;
			}
		};

		public abstract double calcule(double num1, double num2);
	}
	
	public void entra(double num) throws CalculadoraException {
		if (pilha.size() <= 1) {
			pilha.push(num);
		} else {
			throw new CalculadoraException("Excedeu a quantidade de números para o cálculo");
		}
	}

	public void setOperacao(Operacao op) {
		this.op = op;
	}

	public double resultado() {
		double num2 = pilha.pop();
		double num1 = pilha.pop();
		double resultado = op.calcule(num1, num2);
		pilha.push(resultado);
		return resultado;
	}
}