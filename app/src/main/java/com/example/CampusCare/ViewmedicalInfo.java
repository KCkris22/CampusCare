package com.example.CampusCare;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewmedicalInfo extends AppCompatActivity {

    TextView fullname, dob, bloodType, medicalConditions, allergies, medications;
    String Name;
    // Assuming you pass patientId to fetch data from the server

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmedicalinfo);

        // Initialize the TextViews
        fullname = findViewById(R.id.fullname);
        dob = findViewById(R.id.dob);
        bloodType = findViewById(R.id.bloodType);
        medicalConditions = findViewById(R.id.medicalConditions);
        allergies = findViewById(R.id.allergies);
        medications = findViewById(R.id.medications);

        // Get the patient ID passed from the previous activity
        Name = getIntent().getStringExtra("Name");

        // Fetch the medical info for this patient
        fetchMedicalInfo();
    }

    private void fetchMedicalInfo() {// Update with your endpoint

        StringRequest request = new StringRequest(Request.Method.POST, endpoints.MedicalInfo,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        // Set the data to TextViews
                        fullname.setText(obj.getString("name"));
                        dob.setText(obj.getString("dob"));
                        bloodType.setText(obj.getString("bloodType"));
                        medicalConditions.setText(obj.getString("medicalConditions"));
                        allergies.setText(obj.getString("allergies"));
                        medications.setText(obj.getString("medications"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(this, "Error fetching details", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Name", Name); // Send the patientId to the server
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

}
