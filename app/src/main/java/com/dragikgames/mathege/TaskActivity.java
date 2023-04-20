package com.dragikgames.mathege;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class TaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Получаем параметры из Intent
        int id = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");

        // Устанавливаем название активити
        setTitle(name);

        // Получаем текст задачи из базы данных
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"Task_text"};
        String selection = "Id_Task_theme=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query("Tasks", projection, selection, selectionArgs, null, null, null);
        String taskText = "";
        if (cursor != null && cursor.moveToFirst()) {
            int taskTextIndex = cursor.getColumnIndexOrThrow("Task_text");
            taskText = cursor.getString(taskTextIndex);
        }
        if (cursor != null) {
            cursor.close();
        }

        // Отображаем текст задачи
        TextView textView = findViewById(R.id.text_view);
        textView.setText(taskText);
    }
}
