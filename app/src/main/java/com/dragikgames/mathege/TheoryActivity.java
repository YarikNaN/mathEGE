package com.dragikgames.mathege;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class TheoryActivity extends AppCompatActivity {
    private List<Pair<Integer, String>> taskThemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);

        // Получаем список задач
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        taskThemes = dbHelper.getTaskThemes();

        // Создаем кнопки для каждой задачи
        LinearLayout layout = findViewById(R.id.layoutTheoryTest);
        for (int i = 19; i < 24 && i < taskThemes.size(); i++) {
            Button button = new Button(this);
            Pair<Integer, String> taskTheme = taskThemes.get(i);
            button.setText(taskTheme.second);

//            button.setBackgroundColor(Color.rgb(255, 165, 0));

            button.setBackground(getResources().getDrawable(R.drawable.rounded_button_background));;
            int width = getResources().getDisplayMetrics().widthPixels;
            int buttonWidth = (int) (width * 0.8);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    buttonWidth, ViewGroup.LayoutParams.WRAP_CONTENT);


            button.setLayoutParams(layoutParams);







            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Открываем новое активити при нажатии на кнопку
                    Intent intent = new Intent(TheoryActivity.this, TheorAct.class);
                    intent.putExtra("id", taskTheme.first);
                    intent.putExtra("name", taskTheme.second);
                    startActivity(intent);
                }
            });
            layout.addView(button);
            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) button.getLayoutParams();
// Устанавливаем отступ снизу на 16dp
            layoutParams1.setMargins(0, 26, 0, 0 );
        }
    }
}




























/*    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);

        dbHelper = new DatabaseHelper(this);

        Button btnTheorygeom = (Button) findViewById(R.id.geometry_theory);
        btnTheorygeom.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

    }*/
