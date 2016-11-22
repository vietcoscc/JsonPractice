package com.example.vaio.object;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by vaio on 11/21/2016.
 */

public class MyDatabase {
    public static final String TB_NAME = "tigiatable";
    public static final String DB_NAME = "tigia.sqlite";
    public static final String PATH = Environment.getDataDirectory() + "/data/com.example.vaio.jsonpractice/databases/" + DB_NAME;
    private Context context;
    private SQLiteDatabase database;
    private ArrayList<ItemObject> arrItemObject = new ArrayList<>();

    public MyDatabase(Context context) {
        this.context = context;
//        this.arrItemObject = arrItemObject;
        copyDatabase();
    }

    private void copyDatabase() {
        File file = new File(PATH);
        if (file.exists()) {
            return;
        }
        File parentFile = file.getParentFile();
        parentFile.mkdirs();
        try {
            InputStream inputStream = context.getAssets().open(DB_NAME);
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int count = inputStream.read(b);
            while (count != -1) {
                outputStream.write(b, 0, count);
                count = inputStream.read(b);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openDatabase() {
        database = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    private void closeDatabase() {
        database.close();
    }

    public ArrayList<ItemObject> getData() {
        openDatabase();
        Cursor cursor = database.query(TB_NAME, null, null, null, null, null, null);
        int typeIndex = cursor.getColumnIndex(JsonParser.TYPE);
        int imageurlIndex = cursor.getColumnIndex(JsonParser.IMAGE_URL);
        int muatienmatIndex = cursor.getColumnIndex(JsonParser.MUA_TIEN_MAT);
        int bantienmatIndex = cursor.getColumnIndex(JsonParser.BAN_TIEN_MAT);
        int muackIndex = cursor.getColumnIndex(JsonParser.MUA_CK);
        int banckIndex = cursor.getColumnIndex(JsonParser.BAN_CK);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String type = cursor.getString(typeIndex);
            String imageurl = cursor.getString(imageurlIndex);
            String muatienmat = cursor.getString(muatienmatIndex);
            String muack = cursor.getString(muackIndex);
            String bantienmat = cursor.getString(bantienmatIndex);
            String banck = cursor.getString(banckIndex);
            ItemObject itemObject = new ItemObject(type, imageurl, muatienmat, muack, bantienmat, banck);
            arrItemObject.add(itemObject);
            cursor.moveToNext();
        }
        closeDatabase();
        return arrItemObject;
    }

    public void insertData(ArrayList<ItemObject> arrItemObject) {
        openDatabase();
        for (int i = 0; i < arrItemObject.size(); i++) {
            ItemObject itemObject = arrItemObject.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put(JsonParser.TYPE, itemObject.getType());
            contentValues.put(JsonParser.IMAGE_URL, itemObject.getImageurl());
            contentValues.put(JsonParser.MUA_TIEN_MAT, itemObject.getMuatienmat());
            contentValues.put(JsonParser.BAN_TIEN_MAT, itemObject.getBantienmat());
            contentValues.put(JsonParser.MUA_CK, itemObject.getMuack());
            contentValues.put(JsonParser.BAN_CK, itemObject.getBanck());
            database.insert(TB_NAME, null, contentValues);
        }

        closeDatabase();
    }

    public void clearData() {
        openDatabase();
        database.delete(TB_NAME, null, null);
        closeDatabase();
    }
}
