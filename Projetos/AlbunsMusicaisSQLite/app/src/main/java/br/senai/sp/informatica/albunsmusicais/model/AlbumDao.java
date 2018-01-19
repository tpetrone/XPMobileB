package br.senai.sp.informatica.albunsmusicais.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.senai.sp.informatica.albunsmusicais.Main;

/**
 * Created by pena on 18/11/2017.
 */

public class AlbumDao extends SQLiteOpenHelper {
    public static AlbumDao instance = new AlbumDao();

    private AlbumDao() {
        super(Main.getContext(), "DbAlbum", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table album (" +
                "id integer primary key autoincrement," +
                "banda text not null," +
                "album text not null," +
                "genero text not null," +
                "lancamento long not null," +
                "del int not null," +
                "capa blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists album");
        onCreate(db);

    }

    private void setData(SQLiteStatement sql, Album obj) {
        sql.bindString(1, obj.getBanda());
        sql.bindString(2, obj.getAlbum());
        sql.bindString(3, obj.getGenero());
        sql.bindLong(4, obj.getLancamento().getTime());
        sql.bindLong(5, (obj.isDel() ? 1 : 0));
        sql.bindBlob(6, obj.getCapa() != null ? obj.getCapa() : new byte[] {});
    }

    private Album getData(Cursor cursor) {
        Album obj = new Album();
        obj.setId(cursor.getLong(0));
        obj.setBanda(cursor.getString(1));
        obj.setAlbum(cursor.getString(2));
        obj.setGenero(cursor.getString(3));
        obj.setLancamento(new Date(cursor.getLong(4)));
        obj.setDel(cursor.getLong(5) == 1);
        obj.setCapa(cursor.getBlob(6).length > 0 ? cursor.getBlob(6) : null);
        return obj;
    }

    public void salvar(Album obj) {
        // Abre o banco de dados para escrita
        SQLiteDatabase db = getWritableDatabase();

        // Produra registro com o mesmo nome
        Cursor cursor = db.rawQuery("select * from album where lower(banda)=?",
                new String[] {obj.getBanda().toLowerCase()});

        if (cursor.getCount() == 0) { // Não encontrado, salva
            String sql = "insert into album (banda, album, genero, lancamento, del, capa) " +
                    "values (?,?,?,?,?,?)";
            SQLiteStatement insert = db.compileStatement(sql);
            setData(insert, obj);
            insert.execute();
            cursor.close();
            cursor = db.rawQuery("select last_insert_rowid() from album", null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                obj.setId(cursor.getLong(0));
            }
        } else {       // Encontrado, atualiza
            cursor.moveToFirst();
            obj.setId(cursor.getLong(0));

            String sql = "update album set banda=?, album=?, genero=?, lancamento=?, del=?, capa=?" +
                    "where id=?";
            SQLiteStatement update = db.compileStatement(sql);
            setData(update, obj);
            update.bindLong(7, obj.getId());
            update.execute();
        }
        cursor.close();
        db.close();
    }

    public void remover(Long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from produto where id=" + id);
        db.close();
    }

    public List<Long> listarIds(String ordem) {
        String query;
        if(ordem.equals("Banda")) {
            query = "select id from album order by banda";
        } else if(ordem.equals("Album")){
            query = "select id from album order by album";
        } else {
            query = "select id from album order by lancamento";
        }

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Long> lista = new ArrayList<>();

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            for (int i = 0; i < cursor.getCount(); i++) {
                lista.add(cursor.getLong(0));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return lista;
    }

    public Album localizar(Long id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from album where id=?",
                new String[] {String.valueOf(id)});
        Album obj = null;
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            obj = getData(cursor);
        }
        cursor.close();
        db.close();

        return obj;
    }

    public void removerMarcados() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from album where del = 1");
        db.close();
    }

    public boolean existeAlbunsADeletar() {
        boolean existe = false;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from album where del = 1", null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            if(cursor.getInt(0) > 0)
                existe = true;

            cursor.close();
        }

        db.close();

        return existe;
    }

    public void limpaMarcados() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("update album set del = 0 where del = 1");
        db.close();
    }
}
