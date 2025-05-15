package com.example.CampusCare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUpPage extends AppCompatActivity {

    Button SignUp;
    EditText email, password, confirmPassword, name, age, gender, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // Connect EditTexts
        email = findViewById(R.id.email);
        password = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.ConfirmPassword);
        name = findViewById(R.id.Name);
        age = findViewById(R.id.Age);
        gender = findViewById(R.id.Gender);
        contact = findViewById(R.id.Contact);
        SignUp = findViewById(R.id.SignUp);

        SignUp.setOnClickListener(v -> {
            String Email = email.getText().toString();
            String Password = password.getText().toString();
            String ConfirmPassword= confirmPassword.getText().toString();
            String Name = name.getText().toString();
            String Age = age.getText().toString();
            String Gender = gender.getText().toString();
            String Contact = contact.getText().toString();
            SignUp(Email, Password, ConfirmPassword, Name, Age, Gender, Contact);

        });
    }

    private void SignUp(String Email, String Password, String ConfirmPassword, String Name, String Age, String Gender, String Contact) {
        StringRequest request = new StringRequest(Request.Method.POST, endpoints.SIGNUP,
                response -> {
                    Toast.makeText(this, "Sign Up successfully : " + response, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpPage.this, LogInPage.class);
                    intent.putExtra("Name", Name);
                    startActivity(intent);
                },
                error -> Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("Email", Email);
                map.put("Password", Password);
                map.put("ConfirmPassword", ConfirmPassword);
                map.put("Name", Name);
                map.put("Age", Age);
                return map;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}
