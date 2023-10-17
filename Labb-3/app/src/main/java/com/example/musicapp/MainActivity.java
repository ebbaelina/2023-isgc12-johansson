//https://www.c-sharpcorner.com/article/how-to-be-working-with-multiple-activities-and-navigate-the-activities-in-androi/

package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //deklarera variabler
    Button btnMainActivity;
    EditText userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialisera variablerna
        btnMainActivity = findViewById(R.id.btnMainActivity);
        userInput = findViewById(R.id.userInput);

            //lyssnare på knapp som anropar second activity
            btnMainActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //hämtar ut innehåll i input
                    String artistSearch = userInput.getText().toString();

                    //rensar inputfält
                    userInput.getText().clear();

                    Intent Second = new Intent(MainActivity.this, SecondActivity.class);
                    Second.putExtra("input-key", artistSearch);
                    startActivity(Second);
                }
            });


    }
}