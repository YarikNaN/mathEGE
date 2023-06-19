package com.dragikgames.mathege;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class theordetails extends AppCompatActivity {


    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theordetails);


        // Получаем параметры из Intent
        taskId = getIntent().getIntExtra("id", 0);

        // Получаем данные задачи из базы данных
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"TaskName", "TaskDescription", "TaskImage"};
        String selection = "TaskId=?";
        String[] selectionArgs = {String.valueOf(taskId)};
        Cursor cursor = db.query("Tasks", projection, selection, selectionArgs, null, null, null);
        String taskName = "";
        String taskDescription = "";
        byte[] taskImage = null;
        if (cursor != null && cursor.moveToFirst()) {
            int taskImageIndex = cursor.getColumnIndexOrThrow("TaskImage");
            taskImage = cursor.getBlob(taskImageIndex);
        }
        if (cursor != null) {
            cursor.close();
        }


        ImageView taskImageView = findViewById(R.id.task_image);
        if (taskImage != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(taskImage, 0, taskImage.length);
            taskImageView.setImageBitmap(bitmap);
        } else {
            taskImageView.setVisibility(View.GONE);
            Log.d("TaskDetailsActivity", "No image found for taskId=" + taskId);
        }
    }
}