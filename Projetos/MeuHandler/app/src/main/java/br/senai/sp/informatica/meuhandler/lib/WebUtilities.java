package br.senai.sp.informatica.meuhandler.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebUtilities {

    public static HttpURLConnection configConnection(String url)
            throws MalformedURLException, IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Accept-Charset", "utf-8");
        con.setConnectTimeout(15000);
        con.setReadTimeout(10000);

        return con;
    }

    public static String readJson(BufferedReader in) throws IOException {
        StringBuilder buffer = new StringBuilder(100);
        String linha = in.readLine();
        while (linha != null) {
            buffer.append(linha);
            linha = in.readLine();
        }
        return buffer.toString();
    }

}
