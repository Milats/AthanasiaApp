package com.fisei.athanasiaapp.ui;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fisei.athanasiaapp.R;
import com.fisei.athanasiaapp.adapters.ShopItemArrayAdapter;
import com.fisei.athanasiaapp.objects.AthanasiaGlobal;
import com.fisei.athanasiaapp.objects.Product;
import com.fisei.athanasiaapp.objects.ShopCartItem;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShopCartFragment extends Fragment {

    private List<Product> productList = new ArrayList<>();
    private ShopItemArrayAdapter itemArrayAdapter;
    private ListView listView;

    public ShopCartFragment() {
    }
    public static ShopCartFragment newInstance(String param1, String param2) {
        ShopCartFragment fragment = new ShopCartFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        List<ShopCartItem> list = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_shop_cart, container, false);
        listView = (ListView) view.findViewById(R.id.listViewShopCartFragment);
        itemArrayAdapter = new ShopItemArrayAdapter(getContext(), list);
        itemArrayAdapter.clear();
        list = AthanasiaGlobal.SHOPPING_CART;
        itemArrayAdapter.addAll(list);
        //itemArrayAdapter.notifyDataSetChanged();
        listView.setAdapter(itemArrayAdapter);

        return view;
    }
}