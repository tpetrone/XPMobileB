package br.senai.sp.informatica.meuhandler.view;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.senai.sp.informatica.meuhandler.Main;
import br.senai.sp.informatica.meuhandler.model.Album;

import static br.senai.sp.informatica.meuhandler.lib.WebUtilities.configConnection;
import static br.senai.sp.informatica.meuhandler.lib.WebUtilities.readJson;

public class MainActivity extends BaseActivity {
    int code;
    String json;

    private String url = "http://172.16.2.250:8080/AlbumServer/";

    @SuppressLint("StaticFieldLeak")
    public void onClickAsync(final View view) {
        new AsyncTask<Void, Album, Void>() {
            @Override
            protected void onPreExecute() {
                view.setEnabled(false);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                for (long id : listarIds()) {
                    final Album album = leAlbumDaRede(id);

                    publishProgress(album);

                    // Aguarda 4 segundos
                    SystemClock.sleep(5000);
                }

                return null;
            }

            @Override
            protected void onProgressUpdate(Album... albuns) {
                if (albuns[0] != null) {
                    carregaAlbumNaTela(albuns[0]);
                }
            }

            @Override
            protected void onPostExecute(Void voids) {
                view.setEnabled(true);
            }
        }.execute();
    }

    public void onClickHandler(final View view) {
        view.setEnabled(false);

        final Handler handler = new Handler();

        new Thread() {
            @Override
            public void run() {
                for (long id : listarIds()) {
                    final Album album = leAlbumDaRede(id);

                    if (album != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                carregaAlbumNaTela(album);
                            }
                        });
                    }

                    // Aguarda 4 segundos
                    SystemClock.sleep(5000);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.setEnabled(true);
                    }
                });
            }
        }.start();
    }

    public void onClick(final View view) {
        view.setEnabled(false);

        for (long id : listarIds()) {
            final Album album = leAlbumDaRede(id);

            if (album != null) {
                carregaAlbumNaTela(album);
            }

            // Aguarda 4 segundos
            SystemClock.sleep(5000);
        }
        view.setEnabled(true);
    }

    private String leJson(final String url) throws Exception {
        code = HttpURLConnection.HTTP_INTERNAL_ERROR;
        json = null;

        Thread job = new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection con = configConnection(url);

                    if (con != null) {
                        con.setRequestMethod("GET");
                        con.setDoInput(true);
                        con.connect();

                        code = con.getResponseCode();
                        if (code == HttpURLConnection.HTTP_OK) {
                            BufferedReader in =
                                    new BufferedReader(
                                            new InputStreamReader(
                                                    con.getInputStream()));
                            json = readJson(in);
                            in.close();
                        }
                        con.disconnect();
                    }
                } catch (IOException ex) {
                    Log.e("Consultar.in", ex.getMessage());
                }

                if (code != 200)
                    Toast.makeText(Main.context, "Falha em localizar o Album", Toast.LENGTH_LONG).show();

            }
        };

        job.start();
        job.join();

        return json;
    }

    private Album leAlbumDaRede(long id) {
        Album album = null;

        try {
            String json = leJson(url + "album/" + id);

            if (!json.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                album = mapper.readValue(new StringReader(json), Album.class);
            }

        } catch (Exception ex) {
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "leAlbumDaRede", ex);
        }

        return album;
    }

    public List<Long> listarIds() {
        List<Long> listaSaida = new ArrayList<>();

        try {
            String json = leJson(url + "lista/album");

            ObjectMapper mapper = new ObjectMapper();
            listaSaida = Arrays.asList(mapper.readValue(new StringReader(json), Long[].class));

        } catch (Exception ex) {
            Toast.makeText(Main.context, "Falha no acesso ao Servidor", Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "listarIds", ex);
        }

        return listaSaida;
    }

}
