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
        contact = findViewById(R.id.Contact);  // <- Dapat meron kang EditText para dito sa XML!
        SignUp = findViewById(R.id.SignUp);

        SignUp.setOnClickListener(v -> {
            // Validation checks
            if(email.getText().toString().isEmpty()){
                email.setError("Please enter your email");
            }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                email.setError("Please enter a valid email");
            }
            if(password.getText().toString().isEmpty()) {
                password.setError("Please enter your password");
            }else if(password.getText().toString().length() < 8){
                password.setError("Password must be at least 8 characters");
            }
            if(confirmPassword.getText().toString().isEmpty()) {
                confirmPassword.setError("Please confirm your password");
            }else if(!confirmPassword.getText().toString().equals(password.getText().toString())){
                confirmPassword.setError("Passwords do not match");
            }
            if(name.getText().toString().isEmpty())
                name.setError("Please enter your name");
            if(age.getText().toString().isEmpty())
                age.setError("Please enter your age");
            if(contact.getText().toString().isEmpty())
                contact.setError("Please enter your contact");

            // If all fields are valid
            if (
                    !email.getText().toString().isEmpty() &&
                            android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() &&
                            !password.getText().toString().isEmpty() &&
                            password.getText().toString().length() >= 8 &&
                            !confirmPassword.getText().toString().isEmpty() &&
                            confirmPassword.getText().toString().equals(password.getText().toString()) &&
                            !name.getText().toString().isEmpty() &&
                            !age.getText().toString().isEmpty() &&
                            !contact.getText().toString().isEmpty()
            ) {
                String url = "http://192.168.214.179/FinalProject/signup.php";

                StringRequest request = new StringRequest(Request.Method.POST, url,
                        response -> {
                            if (response.trim().equals("success")) {
                                Toast.makeText(SignUpPage.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpPage.this, LogInPage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SignUpPage.this, "Server Response: " + response, Toast.LENGTH_LONG).show();
                            }
                        },
                        error -> Toast.makeText(SignUpPage.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show()
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> data = new HashMap<>();
                        data.put("name", name.getText().toString());
                        data.put("gender", gender.getText().toString());
                        data.put("age", age.getText().toString());
                        data.put("contact", contact.getText().toString());
                        data.put("email", email.getText().toString());
                        data.put("password", password.getText().toString());
                        data.put("confirmPassword", confirmPassword.getText().toString());
                        return data;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(SignUpPage.this);
                queue.add(request);
            }
        });
    }
}
