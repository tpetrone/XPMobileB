package br.senai.sp.informatica.meucalculo.calculadora;

import android.graphics.Path;

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
		},
		MULTIPLICACAO {
			@Override
			public double calcule(double num1, double num2) {
				return num1 * num2;
			}
		},
		DIVISAO {
			@Override
			public double calcule(double num1, double num2) {
				return num1 / num2;
			}
		},
		PERCENTAGEM {
			@Override
			public double calcule(double num1, double num2) {
				return num1 * (num2 / 100);
			}
		};

		public abstract double calcule(double num1, double num2);

		public static Operacao localiza(String simbolo) {
			switch (simbolo) {
				case "+":
					return SOMA;
				case "-":
					return SUBTRACAO;
				case "X":
					return MULTIPLICACAO;
				case "/":
					return DIVISAO;
				case "%":
					return PERCENTAGEM;
				default:
					throw new IllegalArgumentException("O Símbolo " + simbolo + " não define uma Operação");
			}
		}
	}
	
	public void entra(double num) throws CalculadoraException {
		if (pilha.size() <= 1 || pilha.size() == 1 && op!= null) {
			pilha.push(num);
		} else {
			throw new CalculadoraException("Excedeu a quantidade de números para o cálculo");
		}
	}

	public void setOperacao(String simbolo) throws CalculadoraException {
		setOperacao(Operacao.localiza(simbolo));
	}

	public void setOperacao(Operacao op) throws CalculadoraException {
		if (this.op == null && pilha.size() > 0) {
			this.op = op;
		} else if (this.op != null) {
			throw new CalculadoraException("Operador já foi informado");
		} else {
			throw new CalculadoraException("Ausência de números");
		}
	}

	public double resultado() {
		double resultado = 0;

		if(op != null) {
			double num2 = pilha.pop();
			double num1 = pilha.pop();
			resultado = op.calcule(num1, num2);
			op = null;
		}

		return resultado;
	}

	public void reseta() {
		pilha.clear();
		op = null;
	}

}