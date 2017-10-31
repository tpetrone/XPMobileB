package api.jdbc.exemplos.MVCcursoLista;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import api.jdbc.exemplos.MVCcursoLista.controller.Controller;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Controller();   // <<== chama o controller para inicializar a aplicação 
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
	}

}
