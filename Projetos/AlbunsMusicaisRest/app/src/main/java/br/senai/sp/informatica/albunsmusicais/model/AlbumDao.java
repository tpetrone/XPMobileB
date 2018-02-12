package br.senai.sp.informatica.albunsmusicais.model;

import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.senai.sp.informatica.albunsmusicais.Main;
import br.senai.sp.informatica.albunsmusicais.lib.JSONParser;


public class AlbumDao {
    public static AlbumDao instance = new AlbumDao();

    private final String url = "http://172.16.2.250:8080/AlbumServer/";

    private AlbumDao() {}

    public void salvar(Album obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(obj);

            if (obj.getId() == null) {
                // incluir
                new JSONParser.Incluir(url, json, new JSONParser.LocationAndDataCallBack() {
                    @Override
                    public void setResponse(int code, String location, String json) {
                        if(code != 201)
                            Toast.makeText(Main.context, "Falha em salvar o Album", Toast.LENGTH_LONG).show();
                    }
                }).execute();
            } else {
                // atualizar
                new JSONParser.Alterar(url + "album/" + obj.getId(), json, new JSONParser.LocationCallBack() {
                    @Override
                    public void setResponse(int code, String location) {
                        if(code != 200)
                            Toast.makeText(Main.context, "Falha em alterar o Album", Toast.LENGTH_LONG).show();
                    }
                }).execute();
            }
        } catch (Exception ex) {
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
        }
    }

    public void remover(long id) {
        try {
            new JSONParser.Remover(url + "album/" + id, new JSONParser.ResponseCodeCallBack() {
                @Override
                public void setResponse(int code) {
                    if(code != 204)
                        Toast.makeText(Main.context, "Falha ao excluir o Album", Toast.LENGTH_LONG).show();
                }
            }).execute();
        } catch (Exception ex) {
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
        }
    }

    public List<Long> listarIds(String ordem) {
        List<Long> listaSaida = new ArrayList<>();

        try {
            String json = new JSONParser.Consultar(url + "lista/" + ordem.toLowerCase(), new JSONParser.DataCallBack() {
                @Override
                public void setResponse(int code, String json) {
                    if(code != 200)
                        Toast.makeText(Main.context, "Falha ao consultar os Dados", Toast.LENGTH_LONG).show();
                }
            }).execute().get();
            ObjectMapper mapper = new ObjectMapper();
            listaSaida = Arrays.asList(mapper.readValue(new StringReader(json), Long[].class));
        } catch (Exception ex) {
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
        }

        return listaSaida;
    }

    public Album localizar(long id) {
        try {
            String json = new JSONParser.Consultar(url + "album/" + id, new JSONParser.DataCallBack() {
                @Override
                public void setResponse(int code, String json) {
                    if(code != 200)
                        Toast.makeText(Main.context, "Falha em localizar o Album", Toast.LENGTH_LONG).show();
                }
            }).execute().get();

            Album album = null;
            if(!json.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                album = mapper.readValue(new StringReader(json), Album.class);
            }

            return album;
        } catch (Exception ex) {
            Log.e("localizar", ex.getMessage(), ex);
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
        }

        return null;
    }

    public void removerMarcados() {
        try {
            new JSONParser.Remover(url + "marcados", new JSONParser.ResponseCodeCallBack() {
                @Override
                public void setResponse(int code) {
                    if(code != 204)
                        Toast.makeText(Main.context, "Falha ao excluir alguns Albuns", Toast.LENGTH_LONG).show();
                }
            }).execute();
        } catch (Exception ex) {
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
        }
    }

    public boolean existeAlbunsADeletar() {
        boolean existe = false;
        try {
            String json = new JSONParser.Consultar(url + "marcados", new JSONParser.DataCallBack() {
                @Override
                public void setResponse(int code, String json) {
                    if(code != 200)
                        Toast.makeText(Main.context, "Falha em consultar Albuns para apagar", Toast.LENGTH_LONG).show();
                }
            }).execute().get();

            int qtd = 0;
            JsonReader reader = new JsonReader(new StringReader(json));
            reader.beginArray();
            if(reader.hasNext()) {
                reader.beginObject();
                reader.nextName();
                qtd = reader.nextInt();
                reader.endObject();
            }
            reader.endArray();

            if(qtd != 0)
                existe = true;
        } catch (Exception ex) {
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
        }

        return existe;
    }

    public void limpaMarcados() {
        try {
            new JSONParser.Remover(url + "limpar", new JSONParser.ResponseCodeCallBack() {
                @Override
                public void setResponse(int code) {
                    if (code != 204)
                        Toast.makeText(Main.context, "Falha em atualizar alguns Albuns", Toast.LENGTH_LONG).show();
                }
            }).execute();
        } catch (Exception ex) {
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
        }

    }
}
