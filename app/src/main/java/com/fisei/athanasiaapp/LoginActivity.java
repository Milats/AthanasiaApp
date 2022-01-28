package com.fisei.athanasiaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fisei.athanasiaapp.objects.UserClient;
import com.fisei.athanasiaapp.services.UserClientService;

import org.json.JSONObject;

import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwdEditText;
    private Button loginButton;
    private Button signUpButton;
    private TextView warningTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitializeViewComponents();
    }
    private void Login(){
            warningTextView.setText("");
            LoginTask loginTask = new LoginTask();
            loginTask.execute();
    }
    private class LoginTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... urls) {
            UserClient user = UserClientService.Login(emailEditText.getText().toString(), passwdEditText.getText().toString());
            if(user.JWT != null){
                StartMainActivity(user);
            } else {
                warningTextView.setText("Invalid email or password");
            }
            return null;
        }

    }
    private void StartMainActivity(UserClient userLogged){
        Intent loginSuccesful = new Intent(this, MainActivity.class);
        loginSuccesful.putExtra("id", userLogged.ID);
        loginSuccesful.putExtra("token", userLogged.JWT);
        startActivity(loginSuccesful);
    }
    private void InitializeViewComponents(){
        emailEditText = findViewById(R.id.editTextEmailLogin);
        passwdEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(loginButtonClicked);
        signUpButton = findViewById(R.id.btnSignUp);
        warningTextView = findViewById(R.id.textViewLoginFailed);
    }
    private final View.OnClickListener loginButtonClicked = view -> Login();
}