package com.fisei.athanasiaapp.services;

import android.util.Log;

import com.fisei.athanasiaapp.utilities.URLs;
import com.fisei.athanasiaapp.objects.UserClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UserClientService {

    public static UserClient Login(String email, String passwd){
        UserClient user = new UserClient();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(URLs.LOGIN);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoInput(true);
            String jsonInput = "{\"Email\": \"" + email +
                    "\",\"Password\": \"" + passwd + "\"}";
            Log.d("Debug", jsonInput);
            try(OutputStream os = connection.getOutputStream()){
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();
            if (responseCode == HttpURLConnection.HTTP_OK){
                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {

                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                JSONObject data = new JSONObject(response.toString()).getJSONObject("data");
                user.ID = data.getInt("id");
                user.JWT = data.getString("token");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return user;
    }
    public static UserClient GetUserInfoByID(int id){
        UserClient user = new UserClient();
        HttpURLConnection connection = null;
        try{
            URL url = new URL(URLs.CLIENT_BY_ID + id);
            connection = (HttpURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();
            if(responseCode == HttpURLConnection.HTTP_OK){
                try(BufferedReader br = new BufferedReader( new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
                JSONObject data = new JSONObject(response.toString());
                JSONArray userData = data.getJSONArray("data");
                user.ID = userData.getJSONObject(0).getInt("id");
                user.Name = userData.getJSONObject(0).getString("name");
                user.Email = userData.getJSONObject(0).getString("email");
                user.Cedula = userData.getJSONObject(0).getString("cedula");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return user;
    }
}