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

public class TaskDetailsActivity extends AppCompatActivity {
    private EditText answerEditText;
    private Button checkAnswerButton;
    private DatabaseReference mDatabase;
    private int taskId;
    private int userAnswer;
    String taskIdString;

    //Снизу тест
    private String userId="Егор";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        answerEditText = findViewById(R.id.answer_edit_text);
        checkAnswerButton = findViewById(R.id.check_answer_button);


        mDatabase = FirebaseDatabase.getInstance().getReference();


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
            int taskNameIndex = cursor.getColumnIndexOrThrow("TaskName");
            taskName = cursor.getString(taskNameIndex);
            int taskDescriptionIndex = cursor.getColumnIndexOrThrow("TaskDescription");
            taskDescription = cursor.getString(taskDescriptionIndex);
            int taskImageIndex = cursor.getColumnIndexOrThrow("TaskImage");
            taskImage = cursor.getBlob(taskImageIndex);
        }
        if (cursor != null) {
            cursor.close();
        }

        // Отображаем данные задачи
        TextView taskNameView = findViewById(R.id.task_name);
        taskNameView.setText(taskName);
        TextView taskDescriptionView = findViewById(R.id.task_description);
        taskDescriptionView.setText(taskDescription);
        ImageView taskImageView = findViewById(R.id.task_image);
        if (taskImage != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(taskImage, 0, taskImage.length);
            taskImageView.setImageBitmap(bitmap);
        }
        else {
            taskImageView.setVisibility(View.GONE);
            Log.d("TaskDetailsActivity", "No image found for taskId=" + taskId);
        }





        checkAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 userAnswer = 0;
                try {
                    userAnswer = Integer.parseInt(answerEditText.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(TaskDetailsActivity.this, "Введите число", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Получаем параметры из Intent
                 taskId = getIntent().getIntExtra("id", 0);
                 taskIdString = String.valueOf(taskId);

                // Получаем данные задачи из базы данных
                DatabaseHelper dbHelper = new DatabaseHelper(TaskDetailsActivity.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String[] projection = {"RightAnswer"};
                String selection = "TaskId=?";
                String[] selectionArgs = {String.valueOf(taskId)};
                Cursor cursor = db.query("Tasks", projection, selection, selectionArgs, null, null, null);
                int rightAnswer=0;
                if (cursor != null && cursor.moveToFirst()) {
                    rightAnswer = cursor.getInt(cursor.getColumnIndexOrThrow("RightAnswer"));
                }
                if (cursor != null) {
                    cursor.close();
                }

                Log.d("TaskDetailsActivity", "userAnswer = " + userAnswer);
                Log.d("TaskDetailsActivity", "rightAnswer = " + rightAnswer);
                // Сравниваем ответ пользователя и правильный ответ
                if (userAnswer == rightAnswer) {
                    Toast.makeText(TaskDetailsActivity.this, "Верно!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TaskDetailsActivity.this, "Неверно!", Toast.LENGTH_SHORT).show();
                }

                ContentValues values = new ContentValues();
                values.put("UserText", userAnswer);
                int count = db.update("Tasks", values, selection, selectionArgs);
              /*  if (count > 0) {
                    Toast.makeText(TaskDetailsActivity.this, "Ответ сохранен!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TaskDetailsActivity.this, "Ошибка сохранения ответа!", Toast.LENGTH_SHORT).show();
                }*/

                writeNewUser();
            }
        });



        TextView myTextView = findViewById(R.id.bottom_text_view);
        myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // переход к новой активности
                Intent intent = new Intent(TaskDetailsActivity.this, TaskAnswer.class);
                intent.putExtra("id", taskId);
                startActivity(intent);
            }
        });
    }

    public void writeNewUser() {
        User user = new User(1,taskId,userAnswer);


        mDatabase.child("users").child(userId).child(taskIdString).setValue(user);
    }
}
