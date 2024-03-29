package br.senai.sp.informatica.albunsmusicais.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by pena on 18/11/2017.
 */

public class AlbumDao {
    public static AlbumDao instance = new AlbumDao();
    private List<Album> lista;
    private long id = 0;

    private AlbumDao() {
        lista = new ArrayList<>();
        lista.add(new Album(id++, "Astrix", "One Step Ahead", "Dance",
                new GregorianCalendar(1995, Calendar.MAY, 13).getTime()));
        lista.add(new Album(id++, "Cavo", "Bright Nights Dark Days", "Rock",
                new GregorianCalendar(2009, Calendar.SEPTEMBER, 11).getTime()));
    }

    public void salvar(Album obj) {
        if(obj.getId() == null) {
            obj.setId(id++);
            lista.add(obj);
        } else {
            lista.set(lista.indexOf(obj), obj);
        }
    }

    public void remover(Long id) {
        lista.remove(new Album(id));
    }

    public List<Long> listarIds() {
        List<Long> ids = new ArrayList<>();
        for(Album obj : lista)
            ids.add(obj.getId());
        return ids;
    }

    public Album localizar(Long id) {
        return lista.get(lista.indexOf(new Album(id)));
    }

    public void removerMarcados() {
        // Constroi a lista dos Albuns a serem removidos
        List<Album> osAlbuns = new ArrayList<>();
        for(Album obj : lista) {
            if(obj.isDel()) {
                osAlbuns.add(obj);
            }
        }

        // Remove todos os Albuns da lista dos excluiveis
        for(Album album : osAlbuns) {
            remover(album.getId());
        }
    }

    public boolean existeAlbunsADeletar() {
        boolean existe = false;
        for(Album album : lista)
            if( (existe = album.isDel()) )
                break;

        return existe;
    }

    public void limpaMarcados() {
        for(Album obj : lista) {
            if(obj.isDel()) {
                obj.setDel(false);
            }
        }
    }
}
