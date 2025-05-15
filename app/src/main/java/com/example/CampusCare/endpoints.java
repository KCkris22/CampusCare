package com.example.CampusCare;

public class endpoints {
    private static final String BASE_URL = "http://192.168.0.107/FinalProject/Final%20act/"; // Replace with your WAMP IP & folder


    public static final String AddMedicalInformation = BASE_URL + "user.php?action=create";
    public static final String SIGNUP = BASE_URL + "user.php?action=read_all";
    public static final String MedicalInfo = BASE_URL + "user.php?action=read_one";
    public static final String UPDATE = BASE_URL + "user.php?action=update";
    public static final String DELETE = BASE_URL + "user.php?action=delete";
}

