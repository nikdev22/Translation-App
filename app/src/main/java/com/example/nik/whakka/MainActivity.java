package com.example.nik.whakka;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.translate.AmazonTranslateAsyncClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

//import com.google.cloud.translate.Translate;
//import com.google.cloud.translate.TranslateOptions;
//import com.google.cloud.translate.Translation;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {
    Spinner spinnerLeft,spinnerRight;
    private static final String API_KEY = "YOUR_API_KEY";
//    Translate translate;
    TextView resultView;
    AWSCredentials awsCredentials;
    AmazonTranslateAsyncClient translateAsyncClient;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinnerLeft = findViewById(R.id.spinnerLeft);
        spinnerRight = findViewById(R.id.spinnerRight);
        resultView = findViewById(R.id.resultView);
        editText = findViewById(R.id.editText);
//        TranslateOptions options = TranslateOptions.newBuilder()
//                .setApiKey(API_KEY)
//                .build();
//        translate = options.getService();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        awsCredentials = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return "YOUR_API_KEY";
            }

            @Override
            public String getAWSSecretKey() {
                return "YOUR_API_KEY";
            }
        };

         translateAsyncClient = new AmazonTranslateAsyncClient(awsCredentials);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent cameraIntent = new Intent(MainActivity.this,CameraActivity.class);
            cameraIntent.putExtra("Key",1);
            startActivity(cameraIntent);
        } else if(id == R.id.nav_gallery) {
            Intent objectFinder = new Intent(MainActivity.this,CameraActivity.class);
            objectFinder.putExtra("Key",2);
            startActivity(objectFinder);
        }else{

        }


//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void convertText(View view){
        String spinnerLeftValue = (String) spinnerLeft.getSelectedItem();
        String spinnerRightValue = (String) spinnerRight.getSelectedItem();
        String targetLanguage = "fr";
//        final Translation translation =
//                translate.translate("Hello World",
//                        Translate.TranslateOption.targetLanguage("de"));
//        resultView.setText(translation.getTranslatedText());
        switch (spinnerRightValue){
            case "French":
                  targetLanguage = "fr";
                break;
            case "Chinese":
                targetLanguage = "zh-CN";
                break;
            case "Spanish":
                targetLanguage = "es";
                break;
            case "Turkish":
                targetLanguage = "tr";
                break;
            case "Russian":
                targetLanguage = "ru";
                break;
            case "German":
                targetLanguage = "de";
                break;

        }
        String textToTranslate = String.valueOf(editText.getText());
        TranslateTextRequest translateTextRequest = new TranslateTextRequest()
                .withText(textToTranslate)
                .withSourceLanguageCode("en")
                .withTargetLanguageCode(targetLanguage);
        translateAsyncClient.translateTextAsync(translateTextRequest, new AsyncHandler<TranslateTextRequest, TranslateTextResult>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onSuccess(TranslateTextRequest request, TranslateTextResult translateTextResult) {
//                Log.d(LOG_TAG, "Original Text: " + request.getText());
//                Log.d(this, "Translated Text: " + translateTextResult.getTranslatedText());
                resultView.setText(translateTextResult.getTranslatedText());
            }
        });


    }
}
