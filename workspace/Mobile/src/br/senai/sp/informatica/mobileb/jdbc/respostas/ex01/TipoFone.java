package br.senai.sp.informatica.mobileb.jdbc.respostas.ex01;

public enum TipoFone {
	SELECIONE {
		@Override
		public String toString() {
			return "-- Selecione --";
		}
	},
	CELULAR,
	COMERCIAL,
	FAX,
	RECADO,
	RESIDENCIAL;
}
