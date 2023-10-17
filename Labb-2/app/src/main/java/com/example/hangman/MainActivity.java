package com.example.hangman;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    //deklarera variabler
    //HangmanWord används för att visa vilket ord som ska gissas
    TextView hangmanWordGuess;
    //innehåller alla ord som ska slumpas ut
    String[] hangmanWords = {"husvagn", "minigolf", "tortilla", "bilskola", "zombie", "studsmatta"};
    //för att hämta ut det användaren skrivit i input field
    EditText userInput;
    //Används för att skriva ut de bokstäver användaren gissat fel
    TextView WrongLettersTried;
    //antal liv kvar
    TextView livesLeft;
    Button btnGuess;
    Button btnReset;
    TextView Headline;
    String WrongLetters = " ";
    String livesLeftX = "  X X X X X X X";
    String randomWordWith_ = " ";
    //Vinna/förlora meddelande
    final String WINNINGMESSAGE = "Du vann! Spela igen?";
    final String LOSINGMESSAGE = "Du förlora! Spela igen?";
    final String HEADLINE = "HÄNGA GUBBE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        hangmanWordGuess = findViewById(R.id.hangmanWord);
        userInput = findViewById(R.id.userInput);
        WrongLettersTried = findViewById(R.id.WrongLettersTried);
        livesLeft = findViewById(R.id.livesLeft);
        btnGuess = findViewById(R.id.btnGuess);
        Headline = findViewById(R.id.Headline);
        btnReset = findViewById(R.id.btnReset);

        Random random = new Random();
        //slumpar ut ett index från 0 upp till längden på min string array
        int getRandomIndex = random.nextInt(hangmanWords.length);
        //hämtar ut ordet från den indexplats som slumpats ut
        String randomWord = hangmanWords[getRandomIndex];
        Toast.makeText(this, randomWord, Toast.LENGTH_SHORT).show();

        StartGame(randomWord);
    }

    void StartGame(String randomWord) {

        livesLeft.setText(livesLeftX);

        //byter ut alla bokstäver till _ för att dölja ordet som ska gissas
        for (int i = 0; i < randomWord.length(); i++) {
            randomWordWith_ += randomWord.replace(randomWord, "_ ");
        }
        hangmanWordGuess.setText(randomWordWith_);

        //lyssnare på gissa knappen, hämtar då input från användaren
        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hämtat ut bokstaven från input rutan och sätter i en sträng
                String wordFromInput = userInput.getText().toString();

                //rensar input rutan
                userInput.getText().clear();

                //kollar om randomword strängen innehåller bokstaven från input fältet
                if (randomWord.contains(wordFromInput)) {
                    StringBuilder randomWordUpdate = new StringBuilder(randomWordWith_);


                    for (int i = 0; i < randomWord.length(); i++) {
                        //tar ut aktuell char i strängen
                        char currentChar = randomWord.charAt(i);
                        //kollar om aktuell char är samma som input från användaren
                        //eftersom den bara innehåller en bokstav hämtas första bokstaven i stängen, på plats 0
                        if (currentChar == wordFromInput.charAt(0)) {
                            //eftersom det finns mellanslag mellan "_" så multipliceras index i med 2 för att hitta rätt plats i strängen
                            //https://www.geeksforgeeks.org/stringbuilder-setcharat-in-java-with-examples/
                           randomWordUpdate.setCharAt(i * 2, currentChar);

                        }
                    }

                   randomWordWith_ = randomWordUpdate.toString();
                   hangmanWordGuess.setText(randomWordWith_);

                    checkForWinnOrLoose();

                } else {
                    WrongLetters += wordFromInput + " ";
                    WrongLettersTried.setText(WrongLetters);
                   // removeLivesLeft();

                    if (!livesLeftX.isEmpty()) {
                        livesLeftX = livesLeftX.substring(0, livesLeftX.length() - 2);
                        livesLeft.setText(livesLeftX);

                    }
                    checkForWinnOrLoose();
                }

                btnReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //slumpar ut nytt ord för att skicka till metoden StartGame
                        Random random = new Random();
                        int getRandomIndex = random.nextInt(hangmanWords.length);
                        String randomWord = hangmanWords[getRandomIndex];

                        //återställer alla variabler
                        randomWordWith_ = "";
                        WrongLetters = " ";
                        livesLeftX = "  X X X X X X X";

                        // Skriver ut allt i gui på nytt med återställda variabler
                        hangmanWordGuess.setText(randomWordWith_);
                        WrongLettersTried.setText(WrongLetters);
                        livesLeft.setText(livesLeftX);
                        Headline.setText(HEADLINE);

                        StartGame(randomWord);


                    }
                });

            }

            void checkForWinnOrLoose() {
                if(!randomWordWith_.contains("_")){
                    Headline.setText(WINNINGMESSAGE);
                }

                if(!livesLeftX.contains("X")){
                    Headline.setText(LOSINGMESSAGE);
                }
            }





        });
    }



    }
