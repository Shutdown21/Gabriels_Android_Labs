package algonquin.cst2335.hube0078;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    ImageView imgView;
    Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );

        Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
        Button loginButton = findViewById(R.id.login_button);
        EditText emailEditText = findViewById(R.id.emailedittext);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        emailEditText.setText(emailAddress);

        loginButton.setOnClickListener(clk-> {
            nextPage.putExtra( "EmailAddress", emailEditText.getText().toString() );
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", emailEditText.getText().toString());
            editor.apply();
            startActivity( nextPage);
        } );


    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.w("MainActivity", "onStart() - The application is now on screen.");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.w("MainActivity", "onResume() - The application is now responding to user input.");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.w("MainActivity", "onPause()- The application no longer responds to user input.");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.w("MainActivity", "onStop() - The application is no longer visible.");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.w("MainActivity", "onDestroy() - Any memory used by the application is freed.");
    }
}