package com.fisei.athanasiaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.fisei.athanasiaapp.adapters.ProductArrayAdapter;
import com.fisei.athanasiaapp.objects.Product;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();
    private ProductArrayAdapter productArrayAdapter;
    private ListView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (ListView) findViewById(R.id.productsRecyclerView);
        productArrayAdapter = new ProductArrayAdapter(this, productList);
        recyclerView.setAdapter(productArrayAdapter);

        try {
            URL url = new URL(getString(R.string.athanasia_api_url));
            GetProductsTask getProductsTask = new GetProductsTask();
            getProductsTask.execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private class GetProductsTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... params){
            HttpURLConnection connection = null;
            try{
                connection = (HttpURLConnection) params[0].openConnection();
                int response = connection.getResponseCode();
                if(response == HttpURLConnection.HTTP_OK){
                    StringBuilder builder = new StringBuilder();
                    try(BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()))){
                        String line;
                        while((line = reader.readLine()) != null){
                            builder.append(line);
                        }
                    } catch (IOException ex){
                        Snackbar.make(findViewById(R.id.coordinatorLayout),
                                R.string.read_error, Snackbar.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }
                    return new JSONObject(builder.toString());
                } else {
                    Snackbar.make(findViewById(R.id.coordinatorLayout),
                            R.string.connect_error, Snackbar.LENGTH_LONG).show();
                }
            } catch (Exception ex){
                Snackbar.make(findViewById(R.id.coordinatorLayout),
                        R.string.connect_error, Snackbar.LENGTH_LONG).show();
                ex.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return null;
        }
        @Override
        protected void onPostExecute(JSONObject weather){
            convertJSONtoArrayList(weather);
            productArrayAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(0);
        }
    }
    private void convertJSONtoArrayList(JSONObject forecast){
        productList.clear();
        try{
            JSONArray list = forecast.getJSONArray("data");
            for(int i = 0; i < list.length(); ++i){
                JSONObject products = list.getJSONObject(i);
                //JSONObject temperatures = products.getJSONObject("temp");
                //JSONObject weather = day.getJSONArray("weather").getJSONObject(0);
                productList.add(new Product(
                        products.getString("id"),
                        products.getString("name"),
                        products.getString("genre"),
                        products.getInt("quantity"),
                        products.getDouble("unitPrice"),
                        products.getDouble("cost"),
                        getString(R.string.icon_athanasia_url),
                        "",
                        ""));
            }
        } catch (JSONException ex){
            ex.printStackTrace();
        }
    }
}