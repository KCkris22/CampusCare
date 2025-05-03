package com.example.CampusCare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MessagesPage extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_messages);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Toast.makeText(MessagesPage.this, "Home selected", Toast.LENGTH_SHORT).show();
                // Navigate to HomePage
                Intent intent = new Intent(MessagesPage.this, HomePage.class);
                startActivity(intent);
                return true;

            } else if (id == R.id.nav_history) {
                Toast.makeText(MessagesPage.this, "History selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MessagesPage.this, HistoryPage.class);
                startActivity(intent);
                return true;

            } else if (id == R.id.nav_messages) {
                Toast.makeText(MessagesPage.this, "Messages selected", Toast.LENGTH_SHORT).show();
                return true;

            } else if (id == R.id.nav_profile) {
                Toast.makeText(MessagesPage.this, "Profile selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MessagesPage.this, ProfilePage.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}