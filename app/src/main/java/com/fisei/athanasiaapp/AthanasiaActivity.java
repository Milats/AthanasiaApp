package com.fisei.athanasiaapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.fisei.athanasiaapp.objects.Product;
import com.fisei.athanasiaapp.objects.UserClient;
import com.fisei.athanasiaapp.services.UserClientService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.fisei.athanasiaapp.databinding.ActAthanasiaBinding;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AthanasiaActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActAthanasiaBinding binding;

    private Bundle user = new Bundle();
    public static UserClient userClient = new UserClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user = getIntent().getExtras();
        super.onCreate(savedInstanceState);

        binding = ActAthanasiaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarAthanasia.toolbar);

        binding.appBarAthanasia.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_shop, R.id.nav_orders).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_athanasia);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        GetUserClientInfoTask getUserClientInfo = new GetUserClientInfoTask();
        getUserClientInfo.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.athanasia, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_athanasia);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private class GetUserClientInfoTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... urls) {
            userClient = UserClientService.GetUserInfoByID(user.getInt("id"));
            userClient.JWT = user.getString("token");
            return null;
        }
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            //Las vistas no pueden ser editadas en un hilo diferente al main
            ShowUserInfo();
        }
    }
    private void ShowUserInfo(){
        TextView userName = (TextView) findViewById(R.id.textViewUserName);
        userName.setText(userClient.Name);
        TextView userEmail = (TextView) findViewById(R.id.textViewUserEmail);
        userEmail.setText(userClient.Email);
    }
}