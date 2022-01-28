package com.fisei.athanasiaapp.ui;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fisei.athanasiaapp.MainActivity;
import com.fisei.athanasiaapp.R;
import com.fisei.athanasiaapp.adapters.ProductArrayAdapter;
import com.fisei.athanasiaapp.objects.Product;
import com.fisei.athanasiaapp.services.ProductService;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {

    private List<Product> productList = new ArrayList<>();
    private ProductArrayAdapter productArrayAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listViewProductFragment);
        productArrayAdapter = new ProductArrayAdapter(getContext(), productList);
        listView.setAdapter(productArrayAdapter);
        GetProductsTask getProductsTask = new GetProductsTask();
        getProductsTask.execute();

        return view;
    }

     class GetProductsTask extends AsyncTask<URL, Void, JSONObject> {
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
         }
}
}