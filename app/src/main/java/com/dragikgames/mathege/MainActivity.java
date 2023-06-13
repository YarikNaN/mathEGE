package com.dragikgames.mathege;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnTheory = (Button) findViewById(R.id.b_Theory);
        btnTheory.setOnClickListener(this);


        Button btnPractice = (Button) findViewById(R.id.b_Practice);
        btnPractice.setOnClickListener(this);

//        Button btnSettings = (Button) findViewById(R.id.b_Settings);
//        btnSettings.setOnClickListener(this);
    }

    public void onClick(View v){

        switch (v.getId()){

            case R.id.b_Theory:
                finish();
                startActivity(new Intent(MainActivity.this, TheoryActivity.class));
                break;

            case R.id.b_Practice:
                finish();
                startActivity(new Intent(MainActivity.this, PracticeActivity.class));
                break;

//            case R.id.b_Settings:
//                finish();
//                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
//                break;
        }
    }
}