package com.example.campuscare;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MedicalInformation extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MedicalHistoryItem> medicalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicalinformation);

        recyclerView = findViewById(R.id.rvMedicalHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medicalList = new ArrayList<>();

        medicalList.add(new MedicalHistoryItem(
                getIntent().getStringExtra("name"),
                getIntent().getStringExtra("birthDate"),
                getIntent().getStringExtra("bloodType"),
                getIntent().getStringExtra("medicalCondition"),
                getIntent().getStringExtra("allergies"),
                getIntent().getStringExtra("medications")
        ));

        MedicalHistoryAdapter adapter = new MedicalHistoryAdapter(medicalList);
        recyclerView.setAdapter(adapter);
    }
}
