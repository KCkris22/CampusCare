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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmedicalinfo);

        // Match these with your actual XML IDs
        fullname = findViewById(R.id.fullname); // matches
        dob = findViewById(R.id.birthdate); // changed from R.id.dob to R.id.birthdate
        bloodType = findViewById(R.id.address); // assuming bloodType mapped to address field
        medicalConditions = findViewById(R.id.illnesses); // matches illness info
        allergies = findViewById(R.id.past_surgeries); // assuming this represents allergies
        medications = findViewById(R.id.currentmed); // matches current medications

        Name = getIntent().getStringExtra("Name");

        fetchMedicalInfo();
    }

    private void fetchMedicalInfo() {
        StringRequest request = new StringRequest(Request.Method.POST, endpoints.MedicalInfo,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
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
                params.put("Name", Name);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }
}
