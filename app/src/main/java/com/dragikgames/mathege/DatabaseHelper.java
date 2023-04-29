package com.dragikgames.mathege;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "EGEmath.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }


    public Bitmap getImage(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"image"};
        String selection = "name=?";
        String[] selectionArgs = {name};
        Cursor cursor = db.query("tasks", projection, selection, selectionArgs, null, null, null);
        Bitmap bitmap = null;
        if (cursor != null && cursor.moveToFirst()) {
            int imageIndex = cursor.getColumnIndexOrThrow("image");
            byte[] imageBytes = cursor.getBlob(imageIndex);
            bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }
        if (cursor != null) {
            cursor.close();
        }
        return bitmap;
    }




    public List<Pair<Integer, String>> getTaskThemes() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"Id_Task_theme", "Task_theme"};
        Cursor cursor = db.query("TaskThemes", projection, null, null, null, null, null);
        List<Pair<Integer, String>> taskThemes = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndexOrThrow("Id_Task_theme");
                int id = cursor.getInt(idIndex);
                int taskThemeIndex = cursor.getColumnIndexOrThrow("Task_theme");
                String taskTheme = cursor.getString(taskThemeIndex);
                taskThemes.add(new Pair<>(id, taskTheme));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return taskThemes;
    }





    public List<Pair<Integer, String>> getTaskButtons(int taskThemeId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {"TaskId", "TaskName"};
        String selection = "TaskThemeId=?";
        String[] selectionArgs = {String.valueOf(taskThemeId)};
        Cursor cursor = db.query("Tasks", projection, selection, selectionArgs, null, null, null);
        List<Pair<Integer, String>> taskButtons = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndexOrThrow("TaskId");
                int id = cursor.getInt(idIndex);
                int taskNameIndex = cursor.getColumnIndexOrThrow("TaskName");
                String taskName = cursor.getString(taskNameIndex);
                taskButtons.add(new Pair<>(id, taskName));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return taskButtons;
    }

















}