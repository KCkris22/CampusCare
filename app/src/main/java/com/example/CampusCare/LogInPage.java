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

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

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

        Email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        LogIn = findViewById(R.id.login_button);
        Signup = findViewById(R.id.signup_link);

        Signup.setOnClickListener(v -> {
            startActivity(new Intent(LogInPage.this, SignUpPage.class));
        });

        LogIn.setOnClickListener(v -> {
            String emailStr = Email.getText().toString().trim();
            String passStr = password.getText().toString().trim();

            if (emailStr.isEmpty()) {
                Email.setError("Please enter your email");
                return;
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                Email.setError("Please enter a valid email");
                return;
            }

            if (passStr.isEmpty()) {
                password.setError("Please enter your password");
                return;
            } else if (passStr.length() < 8) {
                password.setError("Password must be at least 8 characters");
                return;
            }

            if (emailStr.equals("admin@gmail.com") && passStr.equals("admin1234")) {
                Toast.makeText(this, "Admin Login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogInPage.this, HomePage.class));
            } else if (isNetworkAvailable()) {
                loginUser(emailStr, passStr);
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser(String emailStr, String passStr) {
        StringRequest request = new StringRequest(Request.Method.POST, endpoints.LOGIN,
                response -> {
                    if (response.equals("success")) {
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInPage.this, HomePage.class));
                    } else {
                        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("email", emailStr);
                map.put("Password", passStr);
                return map;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
