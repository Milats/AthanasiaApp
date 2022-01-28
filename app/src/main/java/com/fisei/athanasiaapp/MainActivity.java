package com.fisei.athanasiaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.fisei.athanasiaapp.adapters.ProductArrayAdapter;
import com.fisei.athanasiaapp.objects.Product;
import com.fisei.athanasiaapp.objects.UserClient;
import com.fisei.athanasiaapp.services.ProductService;
import com.fisei.athanasiaapp.services.UserClientService;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();
    private ProductArrayAdapter productArrayAdapter;
    private ListView recyclerView;

    public UserClient userClient = new UserClient();
    private Bundle user = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user = getIntent().getExtras();
        userClient.ID = user.getInt("id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializa componentes
        InititializeViewComponents();

        //Realiza la petición GET de la lista de productos en un tarea asincrónica
        GetProductsTask getProductsTask = new GetProductsTask();
        getProductsTask.execute();
        GetUserClientInfo getUserClientInfo = new GetUserClientInfo();
        getUserClientInfo.execute();

    }
    private class GetProductsTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... params){
            productList.clear();
            productList = ProductService.GetAllProducts();
            return null;
        }
        @Override
        protected void onPostExecute(JSONObject jsonObject){
            productArrayAdapter.clear();
            productArrayAdapter.addAll(productList);
            productArrayAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(0);
        }
    }
    private class GetUserClientInfo extends AsyncTask<URL, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(URL... urls) {
            userClient = UserClientService.GetUserInfoByID(userClient.ID);
            userClient.JWT = user.getString("token");
            return null;
        }
    }
    private void InititializeViewComponents(){
        recyclerView = (ListView) findViewById(R.id.productsRecyclerView);
        productArrayAdapter = new ProductArrayAdapter(this, productList);
        recyclerView.setAdapter(productArrayAdapter);
    }
}