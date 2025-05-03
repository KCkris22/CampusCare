package com.example.CampusCare;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewmedicalInfo extends AppCompatActivity {

    TextView fullname, birthdate, address, contact, past_surgeries, illnesses, hospitalization, chronic_condition, allergies, familymed, currentmed, symptoms, vaccination, mentalhealth, dateText;
    String dateReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmedicalinfo);

        // Find all views
        fullname = findViewById(R.id.fullname);
        birthdate = findViewById(R.id.birthdate);
        address = findViewById(R.id.address);
        contact = findViewById(R.id.contact);
        past_surgeries = findViewById(R.id.past_surgeries);
        illnesses = findViewById(R.id.illnesses);
        hospitalization = findViewById(R.id.hospitalization);
        chronic_condition = findViewById(R.id.chronic_condition);
        allergies = findViewById(R.id.allergies);
        familymed = findViewById(R.id.familymed);
        currentmed = findViewById(R.id.currentmed);
        symptoms = findViewById(R.id.symptoms);
        vaccination = findViewById(R.id.vaccination);
        mentalhealth = findViewById(R.id.mentalhealth);
        dateText = findViewById(R.id.dateText);

        dateReceived = getIntent().getStringExtra("date");

        fetchMedicalInfo();
    }

    private void fetchMedicalInfo() {
        String url = "http://192.168.214.179/FinalProject/viewmedicalinfo.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        fullname.setText(obj.getString("fullname"));
                        birthdate.setText(obj.getString("birthdate"));
                        address.setText(obj.getString("address"));
                        contact.setText(obj.getString("contact"));
                        past_surgeries.setText(obj.getString("past_surgeries"));
                        illnesses.setText(obj.getString("illnesses"));
                        hospitalization.setText(obj.getString("hospitalization"));
                        chronic_condition.setText(obj.getString("chronic_condition"));
                        allergies.setText(obj.getString("allergies"));
                        familymed.setText(obj.getString("familymed"));
                        currentmed.setText(obj.getString("currentmed"));
                        symptoms.setText(obj.getString("symptoms"));
                        vaccination.setText(obj.getString("vaccination"));
                        mentalhealth.setText(obj.getString("mentalhealth"));
                        dateText.setText(obj.getString("Date"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Error fetching details", Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("date", dateReceived);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
