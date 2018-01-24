package br.senai.sp.informatica.mobileb.fileio;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TesteDeLeitura {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		FileInputStream fin = new FileInputStream("/Area/Media/pena/Desktop/cliente.obj");
		ObjectInputStream in = new ObjectInputStream(fin);

		Cliente cli = (Cliente)in.readObject();

		in.close();
		
		System.out.println(cli);
	}
}
