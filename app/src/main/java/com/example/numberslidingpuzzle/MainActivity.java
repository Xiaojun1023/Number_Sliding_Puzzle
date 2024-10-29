package com.example.numberslidingpuzzle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button startButton;
    private SharedPreferences sharedPreferences;
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        startButton = findViewById(R.id.startButton);
        sharedPreferences = getSharedPreferences("PuzzleGameStats", Context.MODE_PRIVATE);

        startButton.setOnClickListener(view -> {
            playerName = nameInput.getText().toString();
            if (!playerName.isEmpty()) {
                int timesCompleted = sharedPreferences.getInt(playerName + "_timesCompleted", 0);
                long bestTime = sharedPreferences.getLong(playerName + "_bestTime", Long.MAX_VALUE);

                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("playerName", playerName);
                intent.putExtra("timesCompleted", timesCompleted);
                intent.putExtra("bestTime", bestTime);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            }
        });
    }
}