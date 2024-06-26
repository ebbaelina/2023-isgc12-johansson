package com.example.hangman;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    //deklarera variabler
    //HangmanWordGuess används för att visa vilket ord som ska gissas
    TextView hangmanWordGuess;
    //innehåller alla ord som ska slumpas ut
    String[] hangmanWords = {"husvagn", "minigolf", "tortilla", "bilskola", "zombie", "studsmatta", "morot"};
    char[] randomWordArray;
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
    String hiddenWord;
    String randomWordWith_ = " ";
    String randomWord;
    //Vinna/förlora meddelande
    final String WIN = "Du vann! Spela igen?";
    final String LOSE = "Du förlora! Spela igen?";
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
        randomWord = hangmanWords[getRandomIndex];
        randomWordArray = randomWord.toCharArray();
        Toast.makeText(this, randomWord, Toast.LENGTH_LONG).show();

        StartGame();
    }

    public void StartGame() {

        //byter ut alla bokstäver till _ för att dölja ordet som ska gissas
        randomWordArray = new char[randomWord.length()];
        Arrays.fill(randomWordArray, '_');


        hiddenWord = new String(randomWordArray).replace("", " ");

        // Uppdaterar TextView för att visa det dolda ordet och liv
        hangmanWordGuess.setText(hiddenWord);
        livesLeft.setText(livesLeftX);

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hämtat ut bokstaven från input rutan och sätter i en sträng
                String wordFromInput = userInput.getText().toString();
                char guessedLetter = wordFromInput.charAt(0);

                //rensar input rutan
                userInput.getText().clear();

                guessWord(guessedLetter);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    public void guessWord(char guessedLetter) {

        boolean letterGuessed = false;

        for (int i = 0; i < randomWord.length(); i++) {
            if (randomWord.charAt(i) == guessedLetter && randomWordArray[i] == '_') {
                randomWordArray[i] = guessedLetter;
                letterGuessed = true;
            }
        }
        if (letterGuessed) {

            hiddenWord = new String(randomWordArray);
            hangmanWordGuess.setText(hiddenWord.replaceAll("", " "));

            checkForWinOrLoss();

        } else {
            //lägger till guessedLetter i WrongLetters strängen och lägger till mellanslag
            WrongLetters += guessedLetter + " ";
            WrongLettersTried.setText(WrongLetters);

            //om man har liv kvar, så tas ett X bort från stängen, plus mellanslaget
            if (!livesLeftX.isEmpty()) {
                livesLeftX = livesLeftX.substring(0, livesLeftX.length() - 2);
                livesLeft.setText(livesLeftX);

                checkForWinOrLoss();

            }
        }
    }

    public void resetGame() {

        //slumpar ut nytt ord för att skicka till metoden StartGame
        Random random = new Random();
        int getRandomIndex = random.nextInt(hangmanWords.length);
        String randomWord = hangmanWords[getRandomIndex];
        btnGuess.setEnabled(true);

        //återställer alla variabler
        randomWordWith_ = "";
        WrongLetters = " ";
        livesLeftX = "  X X X X X X X";

        // Skriver ut allt i gui på nytt med återställda variabler
        hangmanWordGuess.setText(randomWordWith_);
        WrongLettersTried.setText(WrongLetters);
        livesLeft.setText(livesLeftX);
        Headline.setText(HEADLINE);

        StartGame();

    }

    public void checkForWinOrLoss() {
        if(!hiddenWord.contains("_")){
            Headline.setText(WIN);
            btnGuess.setEnabled(false);
        }

        if(!livesLeftX.contains("X")){
            Headline.setText(LOSE);
            btnGuess.setEnabled(false);
        }
    }


}



