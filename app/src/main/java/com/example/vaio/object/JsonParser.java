package com.example.vaio.object;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.vaio.jsonpractice.MainActivity;
import com.example.vaio.object.ItemObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


/**
 * Created by vaio on 11/20/2016.
 */

public class JsonParser extends AsyncTask<String, Void, ArrayList<ItemObject>> {
    public static final String TAG = "JsonParser";
    public static final String TYPE = "type";
    public static final String IMAGE_URL = "imageurl";
    public static final String MUA_TIEN_MAT = "muatienmat";
    public static final String MUA_CK = "muack";
    public static final String BAN_TIEN_MAT = "bantienmat";
    public static final String BAN_CK = "banck";
    private Handler handler;

    public JsonParser(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected ArrayList<ItemObject> doInBackground(String... params) {
        ArrayList<ItemObject> arrItemObject  = new ArrayList<>();
        String link = params[0];
        try {
//            URL url = new URL(link);
//            URLConnection connection = url.openConnection();
//            InputStream inputStream = connection.getInputStream();
//            StringBuilder builder = new StringBuilder();
//            int c = inputStream.read();
//            while (c != -1) {
//                builder.append((char) c);
//                c = inputStream.read();
//            }
//            Log.e(TAG, builder.toString());
//
//            JSONObject root = new JSONObject(builder.toString());
//            JSONArray array = root.getJSONArray("items");
//            for(int i=0;i<array.length();i++){
//                JSONObject obj  = array.getJSONObject(i);
//                Log.e(TAG,obj.getString("muatienmat"));
//                String type = obj.getString(TYPE);
//                String imageurl = obj.getString(IMAGE_URL);
//                String muatienmat = obj.getString(MUA_TIEN_MAT);
//                String muack = obj.getString(MUA_CK);
//                String bantienmat = obj.getString(BAN_TIEN_MAT);
//                String banck = obj.getString(BAN_CK);
//                ItemObject itemObject = new ItemObject(type,imageurl,muatienmat,muack,bantienmat,banck);
//                arrItemObject.add(itemObject);
//            }
            URL url = new URL(link);
            InputStreamReader inpuStream = new InputStreamReader(url.openStream(),"UTF-8");
            ItemParent itemParent = new Gson().fromJson(inpuStream,ItemParent.class);
            arrItemObject.addAll(itemParent.getItems());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrItemObject;
    }

    @Override
    protected void onPostExecute(ArrayList<ItemObject> itemObjects) {
        super.onPostExecute(itemObjects);
        Message message = new Message();
        message.what = MainActivity.WHAT;
        message.obj = itemObjects;
        handler.sendMessage(message);
    }
}
