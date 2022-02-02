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
import com.fisei.athanasiaapp.services.UserAdminService;
import com.fisei.athanasiaapp.services.UserClientService;
import org.json.JSONObject;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwdEditText;
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
    private Boolean LoginAdmin(){
            warningTextView.setText("");
            LoginAdminTask loginAdminTask = new LoginAdminTask();
            loginAdminTask.execute();
        return true;
    }
    private void SignUp(){
        Intent register = new Intent(this, SingUpActivity.class);
        startActivity(register);
    }
    private class LoginTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... urls) {
            UserClient user = UserClientService.Login(emailEditText.getText().toString(), passwdEditText.getText().toString());
            if(user.JWT != null){
                StartAthanasiaActivity(user, false);
            } else {
                warningTextView.setText(R.string.label_wrong_email_password);
            }
            return null;
        }
    }
    private class LoginAdminTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... urls) {
            UserClient user = UserAdminService.Login(emailEditText.getText().toString(), passwdEditText.getText().toString());
            if(user.JWT != null){
                StartAthanasiaActivity(user, true);
            } else {
                warningTextView.setText(R.string.label_wrong_email_password);
            }
            return null;
        }
    }
    private void StartAthanasiaActivity(UserClient userLogged, Boolean admin){
        Intent loginSuccesful = new Intent(this, AthanasiaActivity.class);
        loginSuccesful.putExtra("id", userLogged.ID);
        loginSuccesful.putExtra("token", userLogged.JWT);
        loginSuccesful.putExtra("admin", admin);
        startActivity(loginSuccesful);
    }
    private void InitializeViewComponents(){
        emailEditText = findViewById(R.id.editTextEmailLogin);
        passwdEditText = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(loginButtonClicked);
        loginButton.setOnLongClickListener(loginAdminButtonClicked);
        Button signUpButton = findViewById(R.id.btnRegister);
        signUpButton.setOnClickListener(signUpButtonClicked);
        warningTextView = findViewById(R.id.textViewLoginFailed);
    }
    private final View.OnClickListener loginButtonClicked = view -> Login();
    private final View.OnClickListener signUpButtonClicked = view -> SignUp();
    private final View.OnLongClickListener loginAdminButtonClicked = view -> LoginAdmin();
}