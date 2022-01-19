package com.fisei.athanasiaapp.objects;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Product {
    public final String id;
    public final String name;
    public final String genre;
    public final int quantity;
    public final double unitPrice;
    public final double cost;
    public final String iconURL;

    public Product(String id, String name, String genre, int quantity,
                   double unitPrice, double cost, String iconUrl){
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.cost = cost;
        this.iconURL = iconUrl;
    }
}