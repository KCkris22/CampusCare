package com.example.CampusCare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MedicalInfo extends AppCompatActivity {

    LinearLayout linearLayoutDates;
    TextView noDatesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicalinformation);

        // Initialize the views
        linearLayoutDates = findViewById(R.id.linearLayoutDates);
        noDatesText = findViewById(R.id.noDatesText);

        // Fetch the dates from the server
        fetchDates();
    }

    private void fetchDates() {
        String url = "http://192.168.214.179/FinalProject/displaymedinfo.php"; // Change if needed

        // Create a new StringRequest to fetch data from the URL
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Parse the JSON response
                        JSONArray array = new JSONArray(response);

                        // Check if the array is empty
                        if (array.length() == 0) {
                            noDatesText.setVisibility(View.VISIBLE); // Show "No dates available"
                        } else {
                            noDatesText.setVisibility(View.GONE); // Hide the "No dates" text

                            // Clear any previous views
                            linearLayoutDates.removeAllViews();

                            // Loop through the dates and create TextViews for each one
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String date = object.getString("Date");

                                TextView dateTextView = new TextView(this);
                                dateTextView.setText(date);
                                dateTextView.setTextSize(18);
                                dateTextView.setPadding(10, 10, 10, 10);

                                dateTextView.setOnClickListener(v -> {
                                    // Handle the date click event
                                    String clickedDate = dateTextView.getText().toString();
                                    Toast.makeText(MedicalInfo.this, "Clicked on: " + clickedDate, Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(MedicalInfo.this,ViewmedicalInfo.class);
                                    intent.putExtra("selected_date", clickedDate); // Pass the clicked date
                                    startActivity(intent);
                                });

                                // Add the TextView to the LinearLayout
                                linearLayoutDates.addView(dateTextView);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Error fetching dates", Toast.LENGTH_SHORT).show();
                }
        );

        // Add the request to the request queue to execute it
        Volley.newRequestQueue(this).add(request);
    }
}
