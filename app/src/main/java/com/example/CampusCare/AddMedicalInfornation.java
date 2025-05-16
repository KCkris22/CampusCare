// AddMedicalInfornation.java
package com.example.campuscare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddMedicalInfornation extends AppCompatActivity {

    EditText etPatientName, etDOB, etBloodType, etMedicalConditions, etAllergies, etMedications;
    Button btnUploadFile, btnSave;
    private static final int PICK_FILE_REQUEST = 1;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmedicalinfornation);

        etPatientName = findViewById(R.id.etPatientName);
        etDOB = findViewById(R.id.etDOB);
        etBloodType = findViewById(R.id.etBloodType);
        etMedicalConditions = findViewById(R.id.etMedicalConditions);
        etAllergies = findViewById(R.id.etAllergies);
        etMedications = findViewById(R.id.etMedications);
        btnUploadFile = findViewById(R.id.btnUploadFile);
        btnSave = findViewById(R.id.btnSave);

        etDOB.setOnClickListener(v -> showDatePicker());
        btnUploadFile.setOnClickListener(v -> openFilePicker());

        btnSave.setOnClickListener(v -> {
            String name = etPatientName.getText().toString().trim();
            String dob = etDOB.getText().toString().trim();
            String bloodType = etBloodType.getText().toString().trim();
            String medicalConditions = etMedicalConditions.getText().toString().trim();
            String allergies = etAllergies.getText().toString().trim();
            String medications = etMedications.getText().toString().trim();

            if (name.isEmpty() || dob.isEmpty()) {
                Toast.makeText(this, "Please enter Name and Date of Birth.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, MedicalInformation.class);
            intent.putExtra("name", name);
            intent.putExtra("birthDate", dob);
            intent.putExtra("bloodType", bloodType);
            intent.putExtra("medicalCondition", medicalConditions);
            intent.putExtra("allergies", allergies);
            intent.putExtra("medications", medications);
            startActivity(intent);
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int year, int month, int dayOfMonth) ->
                        etDOB.setText(dayOfMonth + "/" + (month + 1) + "/" + year),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null) {
            fileUri = data.getData();
            Toast.makeText(this, "File selected: " + fileUri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
        }
    }
}
