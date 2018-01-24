package br.senai.sp.informatica.mobileb.fileio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TesteDeGravacao {
	public static void main(String[] args) throws IOException {
		Cliente cli = new Cliente();
		cli.setNomeFantasia("Jocar Peças LTDA");
		cli.setRazaoSocial("Empr. de Peças 1/2 Boca LTDA");
		cli.setCnpj("45.786.345/0001-80");
		
		FileOutputStream fout = new FileOutputStream("/Area/Media/pena/Desktop/cliente.obj");
		ObjectOutputStream out = new ObjectOutputStream(fout);
		
		out.writeObject(cli);
		
		out.close();
	}
}
