package com.example.campuscare;

public class MedicalHistoryItem {
    public String name;
    public String birthDate;
    public String bloodType;
    public String medicalCondition;
    public String allergies;
    public String medications;

    public MedicalHistoryItem(String name, String birthDate, String bloodType,
                              String medicalCondition, String allergies, String medications) {
        this.name = name;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.medicalCondition = medicalCondition;
        this.allergies = allergies;
        this.medications = medications;
    }
}
