package br.senai.sp.informatica.listadeamigos.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.senai.sp.informatica.listadeamigos.Main;

/**
 * Created by pena on 26/01/17.
 */

public class AmigoDao extends SQLiteOpenHelper {
    // Intancia Singleton do AmigoDao (Só existirá uma instância)
    public final static AmigoDao dao = new AmigoDao();

    private AmigoDao() {
        super(Main.getContext(), "DbAmigo", null, 1 /* O nº da Versão do Banco de Dados */);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table amigo (" +
                "id integer primary key autoincrement," +
                "nome text not null," +
                "email text not null," +
                "nascimento long not null," +
                "foto blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists amigo");
        onCreate(db);

    }

    public Amigo localizar(long id) {
        Amigo obj = null;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from amigo where id = ?",
                new String[] { String.valueOf(id) });

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            obj = new Amigo();
            obj.setId(cursor.getLong(0));
            obj.setNome(cursor.getString(1));
            obj.setEmail(cursor.getString(2));
            obj.setNascimento(new Date(cursor.getLong(3)));
            obj.setFoto(cursor.getBlob(4).length > 0 ? cursor.getBlob(4) : null);
        }

        cursor.close();
        db.close();

        return obj;
    }

    public void salvar(Amigo obj) {
        SQLiteDatabase db = null;
        SQLiteStatement sql = null;

        if(obj.getId() == null) {
            // Incluir
            db = getWritableDatabase();
            sql = db.compileStatement("insert into amigo (nome, email, nascimento, foto) values (?, ?, ?, ?)");
        } else {
            // Alterar
            Amigo item = localizar(obj.getId());
            if(item != null) {
                db = getWritableDatabase();
                sql = db.compileStatement("update amigo set nome = ?, email = ?, nascimento = ?, foto = ? where id = ?");
                sql.bindLong(5, obj.getId());
            }
        }

        if(db != null) {
            sql.bindString(1, obj.getNome());
            sql.bindString(2, obj.getEmail());
            sql.bindLong(3, obj.getNascimento().getTime());
            sql.bindBlob(4, obj.getFoto() != null ? obj.getFoto() : new byte[] {});
            sql.execute();
            db.close();
        }
    }

    public void remover(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from amigo where id = " + id);
        db.close();
    }

    public List<Long> listar() {
        List<Long> novaLista = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select id from amigo", null);

        while(cursor.moveToNext())
            novaLista.add(cursor.getLong(0));

        cursor.close();
        db.close();

        return novaLista;
    }

    public List<Amigo> aniversariantesDoDia() {
        SQLiteDatabase db  = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from amigo where " +
                "strftime(\"%d%m\", nascimento/1000, \"unixepoch\") = " +
                "strftime(\"%d%m\", \"now\")", null);

        List<Amigo> lista = aniversariantes(cursor);

        cursor.close();
        db.close();

        return lista;
    }

    public List<Amigo> aniversariantesDoMes() {
        SQLiteDatabase db  = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from amigo where " +
                "strftime(\"%m\", nascimento/1000, \"unixepoch\") = " +
                "strftime(\"%m\", \"now\")", null);

        List<Amigo> lista = aniversariantes(cursor);

        cursor.close();
        db.close();

        return lista;
    }

    private List<Amigo> aniversariantes(Cursor cursor) {
        List<Amigo> lista = new ArrayList<>();

        while (cursor.moveToNext()) {
            Amigo obj = new Amigo();
            obj.setId(cursor.getLong(0));
            obj.setNome(cursor.getString(1));
            obj.setEmail(cursor.getString(2));
            obj.setNascimento(new Date(cursor.getLong(3)));
            obj.setFoto(cursor.getBlob(4).length > 0 ? cursor.getBlob(4) : null);

            lista.add(obj);
        }

        return lista;
    }
}
