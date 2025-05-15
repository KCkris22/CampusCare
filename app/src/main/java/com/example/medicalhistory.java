package com.example.campuscare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class MedicalHistory extends AppCompatActivity {

    EditText editTextName, editTextBirthDate, editTextMedicalCondition, editTextSymptoms, editTextAllergies, editTextMedications;
    Spinner spinnerBloodType;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextBirthDate = findViewById(R.id.editTextBirthDate);
        editTextMedicalCondition = findViewById(R.id.editTextMedicalCondition);
        editTextSymptoms = findViewById(R.id.editTextSymptoms);
        editTextAllergies = findViewById(R.id.editTextAllergies);
        editTextMedications = findViewById(R.id.editTextMedications);
        spinnerBloodType = findViewById(R.id.spinnerBloodType);
        btnSave = findViewById(R.id.btnSave);

        // Setup spinner for blood type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.blood_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodType.setAdapter(adapter);

        // Handle Save button click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collect input values
                String name = editTextName.getText().toString();
                String birthDate = editTextBirthDate.getText().toString();
                String bloodType = spinnerBloodType.getSelectedItem().toString();
                String medicalCondition = editTextMedicalCondition.getText().toString();
                String symptoms = editTextSymptoms.getText().toString();
                String allergies = editTextAllergies.getText().toString();
                String medications = editTextMedications.getText().toString();

                // Pass data to MedicalInformation activity
                Intent intent = new Intent(MedicalHistory.this, MedicalInformation.class);
                intent.putExtra("name", name);
                intent.putExtra("birthDate", birthDate);
                intent.putExtra("bloodType", bloodType);
                intent.putExtra("medicalCondition", medicalCondition);
                intent.putExtra("symptoms", symptoms);
                intent.putExtra("allergies", allergies);
                intent.putExtra("medications", medications);
                startActivity(intent);
            }
        });
    }
}
