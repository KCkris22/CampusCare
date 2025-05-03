package com.example.CampusCare;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LogInPage extends AppCompatActivity {

    Button LogIn;
    EditText Email, password;
    TextView Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);

        Signup = findViewById(R.id.signUpLink);

        Signup.setOnClickListener(v -> {
            Intent intent = new Intent(LogInPage.this, SignUpPage.class);
            startActivity(intent);
        });
        LogIn = findViewById(R.id.LogIn);

        LogIn.setOnClickListener(v -> {
            String emailStr = Email.getText().toString().trim();
            String passStr = password.getText().toString().trim();

            if (emailStr.isEmpty()) {
                Email.setError("Please enter your email");
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                Email.setError("Please enter a valid email");
            }

            if (passStr.isEmpty()) {
                password.setError("Please enter your password");
            } else if (passStr.length() < 8) {
                password.setError("Password must be at least 8 characters");
            }

            if (
                    !emailStr.isEmpty() &&
                            android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches() &&
                            !passStr.isEmpty() && passStr.length() >= 8
            ) {
                // 🛰 Send to server
                if (Email.equals("admin@gmail.com") && password.equals("admin1234")){
                    Toast.makeText(LogInPage.this, "Admin Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogInPage.this, HomePage.class));
                }else if (isNetworkAvailable()) {
                    Toast.makeText(LogInPage.this, "Internet connection available", Toast.LENGTH_SHORT).show();
                    String url = getNetworkType();

                    StringRequest request = new StringRequest(Request.Method.POST, url,
                            response -> {
                                if (response.equals("success")) {
                                    Toast.makeText(LogInPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LogInPage.this, HomePage.class));
                                } else {
                                    Toast.makeText(LogInPage.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                    if (passStr != response) {
                                        Toast.makeText(LogInPage.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                    }
                                    if (emailStr != response){
                                        Toast.makeText(LogInPage.this, "Incorrect email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            error -> {
                                if (error instanceof com.android.volley.TimeoutError) {
                                    Toast.makeText(LogInPage.this, "Request timed out. Please try again.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LogInPage.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> data = new HashMap<>();
                            data.put("email", emailStr);
                            data.put("Password", passStr);
                            return data;
                        }
                    };

                    // Increase timeout for the request
                    int timeoutMs = 10000; // 10 seconds timeout
                    RetryPolicy policy = new DefaultRetryPolicy(
                            timeoutMs,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    );
                    request.setRetryPolicy(policy);

                    // Add request to the queue
                    RequestQueue queue = Volley.newRequestQueue(LogInPage.this);
                    queue.add(request);
                } else {
                    Toast.makeText(LogInPage.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    // Check if network is available
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    // Get network type (Wi-Fi or Mobile Data)
    private String getNetworkType() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return "http://192.168.214.179/FinalProject/login.php";
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return "https://abcd1234.ngrok.io/FinalProject/login.php";
            }
        }
        return "No Network";
    }
}
