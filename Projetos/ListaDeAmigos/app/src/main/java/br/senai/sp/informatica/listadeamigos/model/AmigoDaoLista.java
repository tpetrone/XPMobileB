package br.senai.sp.informatica.listadeamigos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pena on 26/01/17.
 */

public class AmigoDaoLista {
    // Intancia Singleton do AmigoDao (Só existirá uma instância)
    public final static AmigoDaoLista dao = new AmigoDaoLista();

    private List<Amigo> lista = new ArrayList<>();
    private long id = 0; // <=== gerador de chave primaria

    private AmigoDaoLista() {
        // Carga temporaria para testes
        lista.add(new Amigo(id++, "Fulano de tal", "fulano@gmail.com"));
        lista.add(new Amigo(id++, "Bentrano da Silva", "beltrano@yahoo.com.br"));
        lista.add(new Amigo(id++, "Ciclano de Souza", "ciclano@uol.com.br"));
    }

    public Amigo localizar(long id) {
        Amigo obj = null;
        for(Amigo amigo : lista) {
           if(amigo.getId() == id) {
               obj = amigo;
               break;
           }
        }

        return obj;
    }

    public void salvar(Amigo obj) {
        if(obj.getId() == null) {
            // Incluir
            obj.setId(id++);
            lista.add(obj);
        } else {
            // Alterar
            Amigo amigo = localizar(obj.getId());
            amigo.setNome(obj.getNome());
            amigo.setEmail(obj.getEmail());
            amigo.setNascimento(obj.getNascimento());
            amigo.setFoto(obj.getFoto());
        }
    }

    public void remover(long id) {
        Amigo obj = localizar(id);
        if(obj != null) {
            lista.remove(obj);
        }
    }

    public List<Long> listar() {
        List<Long> novaLista = new ArrayList<>();

        for(Amigo obj : lista)
            novaLista.add(obj.getId());

        return novaLista;
    }
}
