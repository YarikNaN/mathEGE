package com.dragikgames.mathege;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskAnswer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_answer);

        int taskId = getIntent().getIntExtra("id", 0);
        ImageView taskImageView = findViewById(R.id.answer_image);
        TextView taskNameView = findViewById(R.id.task_answer);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"TaskAnswerText", "AnswerImage"};
        String selection = "TaskId=?";
        String[] selectionArgs = {String.valueOf(taskId)};
        Cursor cursor = db.query("Tasks", projection, selection, selectionArgs, null, null, null);
        String taskName = "";
        byte[] taskImage = null;
        if (cursor != null && cursor.moveToFirst()) {
            int taskAnswerIndex = cursor.getColumnIndexOrThrow("TaskAnswerText");
            taskName = cursor.getString(taskAnswerIndex);
            int taskImageIndex = cursor.getColumnIndexOrThrow("AnswerImage");
            taskImage = cursor.getBlob(taskImageIndex);
        }
        if (cursor != null) {
            cursor.close();
        }


        taskNameView.setText(taskName);

        if (taskImage != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(taskImage, 0, taskImage.length);
            taskImageView.setImageBitmap(bitmap);
        }
        else {
            taskImageView.setVisibility(View.GONE);
            Log.d("TaskDetailsActivity", "No image found for taskId=" + taskId);
        }




    }
}