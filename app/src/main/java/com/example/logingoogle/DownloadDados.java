package com.example.logingoogle;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DownloadDados extends AsyncTask<String, Void, ArrayList<Digimon>> {

    public ArrayList<Digimon> digimonArrayList = new ArrayList<>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e("onPreExecute:", "...Download das informações");
    }

    @Override
    protected ArrayList<Digimon> doInBackground(String... strings) {
        String urlString = strings[0];
        URL url;

        try {
            url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            String texto = stringBuilder.toString();

            if (texto != null) {
                digimonArrayList = getDados(texto);
                return digimonArrayList;
            } else {
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return digimonArrayList;
    }

    private ArrayList<Digimon> getDados(String texto) {
        ArrayList<Digimon> digimons = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(texto);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectItem = jsonArray.getJSONObject(i);

                String name = jsonObjectItem.getString("name");
                String img = jsonObjectItem.getString("img");
                String level = jsonObjectItem.getString("level");

                Digimon digimon = new Digimon(name, img, level);
                digimons.add(digimon);

                // Limitar o número de digimons para os testes:
                if (i == 2)
                    return digimons;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return digimons;
    }

    @Override
    protected void onPostExecute(ArrayList<Digimon> digimons) {
        super.onPostExecute(digimons);
        // Aqui você pode utilizar a lista de Digimons retornada para realizar as ações desejadas no seu app.
    }
}
