package br.senai.sp.informatica.listadejogos.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Created by pena on 31/10/2017.
 */

public class JogoDao {
    // Mantém a única instância de objeto do JogoDao
    // para possibilitar que a lista de jogos seja
    // única na aplicação
    public static JogoDao manager = new JogoDao();
    // Lista onde serão armazenados os objetos Jogos
    private List<Jogo> lista;
    // atributo utilizado para a geração do ID para cada
    // novo Jogo
    private long id = 0;

    // Construtor que inicializa a lista
    // e temporariamente inicializa dois objetos Jogo
    // para teste da aplicação
    private JogoDao() {
        lista = new ArrayList<>();
        lista.add(new Jogo(id++, "Mortal Combat", "Luta"));
        lista.add(new Jogo(id++, "Final Fantasy XII", "RPG"));
    }

    /*
        método utilizado para a obtemção de uma lista ordenada de Jogos
     */
    public List<Jogo> getLista() {
        // suportado no Java 8
        Collections.sort(lista);
        return Collections.unmodifiableList(lista);
        // suportado em todas as versões do Java
        // return Collections.synchronizedList(lista);
    }

    /*
        médoto utilizado para a localização de um objeto jogo a partir de seu ID
     */
    public Jogo getJogo(final Long id) {
        // Pesquisa tradicional
        Jogo oJogo = null;
        for(Jogo obj : lista) {
            if(obj.getId() == id) {
                oJogo = obj;
                break;
            }
        }

        // Pesquisa utilizando o recurso de Collections
        // necessita que a classe tenha construtor especializado
        // além da implementação dos métodos equals e hashcode
        Jogo jogoLocalizado =
                lista.get(lista.indexOf(new Jogo(id)));

        // Utiliza a implementação funcional em Java 8
//        Jogo outroJogo = lista.stream()
//                .filter(obj -> obj.getId() == id)
//                .findAny().orElse(null);

        return oJogo;
    }

    /*
        método utilizado para incluir ou atualizar um jogo na lista interna
     */
    public void salvar(Jogo obj) {
        // Se o objeto não tem ID é reconhecido como
        // novo objeto a ser incluido na lista
        if(obj.getId() == null) {
            obj.setId(id++);
            lista.add(obj);
        } else {
            // caso contrário é efetuada pesquisa
            // para localizar o objeto antigo na lista
            // pelo id para que seja substituido
            int posicao = lista.indexOf(new Jogo(obj.getId()));
            lista.set(posicao, obj);
        }
    }

    /*
        médoto utilizado para remover um determinado Jogo a partir de seu ID
     */
    public void remover(Long id) {
        // Localiza o objeto pelo id informado
        // e em seguida remove da lista
        lista.remove(new Jogo(id));
    }


    /*
        médoto utilizado para remover todos os jogos selecionados para exclusão
     */
    public void apagarOsSelecionados() {
        // Constroi a lista dos Jogos a serem removidos
        List<Jogo> osJogos = new ArrayList<>();
        for(Jogo obj : lista) {
            if(obj.isDel()) {
                osJogos.add(obj);
            }
        }

        // Remove todos os Jogos da lista dos excluiveis
        for(Jogo jogo : osJogos) {
            remover(jogo.getId());
        }
    }
}
