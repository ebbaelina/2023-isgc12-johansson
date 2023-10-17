//https://www.c-sharpcorner.com/article/how-to-be-working-with-multiple-activities-and-navigate-the-activities-in-androi/

package com.example.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class SecondActivity extends MainActivity{
    private final String API_KEY = "d538a1d9647bf180d5b513ae4c4e7abc";
    //deklarera variabler
    Button btnSecondActivity;
    TextView artistTxt;
    TextView relatedArtists1;
    TextView relatedArtists2;
    TextView relatedArtists3;
    TextView relatedArtists4;
    TextView relatedArtists5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //initialisera variablerna
        btnSecondActivity = findViewById(R.id.btnSecondActivity);
        artistTxt = findViewById(R.id.ArtistTxt);
        relatedArtists1 = findViewById(R.id.relatedArtists1);
        relatedArtists2 = findViewById(R.id.relatedArtists2);
        relatedArtists3 = findViewById(R.id.relatedArtists3);
        relatedArtists4 = findViewById(R.id.relatedArtists4);
        relatedArtists5 = findViewById(R.id.relatedArtists5);


        //lyssnare på knapp som anropar main activity
        btnSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent First = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(First);
            }
        });

        Intent ArtistTextIntent = getIntent();

        String inputKeyValue = ArtistTextIntent.getStringExtra("input-key");
        artistTxt.setText(inputKeyValue);

        getData(inputKeyValue);
    }

    private void getData(String inputKeyValue){
        
        //Om det finns mellanslag i input strängen byt ut den mot inget mellanslag
        String noSpace = inputKeyValue.replace(" ", "+");

        URL url;
        String apiUrl = "https://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=" + noSpace + "&api_key=" + API_KEY;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            url = new URL(apiUrl);

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            String tagName;

            String artistMatch1;
            String artistMatch2;
            String artistMatch3;
            String artistMatch4;
            String artistMatch5;
            String inputArtist;
            int currentTag = 0;

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                if (parserEvent == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    if(tagName.equals("similarartists")){
                        inputArtist = parser.getAttributeValue(0);
                        artistTxt.setText(inputArtist);
                    }
                    if (tagName.equals("name")) {
                        currentTag++;
                        if (currentTag == 1) {
                            artistMatch1 = parser.nextText();
                            relatedArtists1.setText(artistMatch1);
                        } else if (currentTag == 2){
                            artistMatch2 = parser.nextText();
                            relatedArtists2.setText(artistMatch2);
                        } else if (currentTag == 3){
                            artistMatch3 = parser.nextText();
                            relatedArtists3.setText(artistMatch3);
                        } else if (currentTag == 4){
                            artistMatch4 = parser.nextText();
                            relatedArtists4.setText(artistMatch4);
                        } else if (currentTag == 5){
                            artistMatch5 = parser.nextText();
                            relatedArtists5.setText(artistMatch5);
                        }
                    }
                }
                parserEvent = parser.next();
            }

        }catch (Exception e){
            String error = String.valueOf(e);
            Log.e("fel:", error);
        }
    }
}
