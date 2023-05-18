package com.dragikgames.mathege;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class TaskActivity extends AppCompatActivity {
    private List<Pair<Integer, String>> taskButtons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Получаем параметры из Intent
        int taskThemeId = getIntent().getIntExtra("id", 0);

        // Получаем список задач для данной темы
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        taskButtons = dbHelper.getTaskButtons(taskThemeId);

        // Создаем кнопки для каждой задачи
        LinearLayout layout = findViewById(R.id.layout_task_buttons);




        for (Pair<Integer, String> taskButton : taskButtons) {
            Button button = new Button(this);
            button.setText(taskButton.second);
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
                    Intent intent = new Intent(TaskActivity.this, TaskDetailsActivity.class);
                    intent.putExtra("id", taskButton.first);
                    intent.putExtra("name", taskButton.second);
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

