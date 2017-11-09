package br.senai.sp.informatica.listadecontatos.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by pena on 31/10/2017.
 */

public class ContatoDao {
    // Mantém a única instância de objeto do ContatoDao
    // para possibilitar que a lista de contatos seja
    // única na aplicação
    public static ContatoDao manager = new ContatoDao();
    // Lista onde serão armazenados os objetos Contatos
    private List<Contato> lista;
    // atributo utilizado para a geração do ID para cada
    // novo Contato
    private long id = 0;

    // Construtor que inicializa a lista
    // e temporariamente inicializa dois objetos Contato
    // para teste da aplicação
    private ContatoDao() {
        lista = new ArrayList<>();
        lista.add(new Contato(id++,
                "Fulano de Tal",
                "fulano@gmail.com",
                new GregorianCalendar(1997, 11,20).getTime()));
        lista.add(new Contato(id++,
                "Beltrano da Silva",
                "beltrano@yahoo.com.br",
                new GregorianCalendar(2000,6,12).getTime()));
    }

    /*
        método utilizado para a obtemção de uma lista ordenada de Contatos
     */
    public List<Contato> getLista() {
        Collections.sort(lista);
        return Collections.synchronizedList(lista);
    }

    /*
        médoto utilizado para a localização de um objeto contato a partir de seu ID
     */
    public Contato getContato(Long id) {
        // Pesquisa tradicional
        Contato oJogo = null;
        for(Contato obj : lista) {
            if(obj.getId() == id) {
                oJogo = obj;
                break;
            }
        }

        return oJogo;
    }

    /*
        método utilizado para incluir ou atualizar um contato na lista interna
     */
    public void salvar(Contato obj) {
        // Se o objeto não tem ID é reconhecido como
        // novo objeto a ser incluido na lista
        if(obj.getId() == null) {
            obj.setId(id++);
            lista.add(obj);
        } else {
            // caso contrário é efetuada pesquisa
            // para localizar o objeto antigo na lista
            // pelo id para que seja substituido
            int posicao = lista.indexOf(new Contato(obj.getId()));
            lista.set(posicao, obj);
        }
    }

    /*
        médoto utilizado para remover um determinado Contato a partir de seu ID
     */
    public void remover(Long id) {
        // Localiza o objeto pelo id informado
        // e em seguida remove da lista
        lista.remove(new Contato(id));
    }

}
