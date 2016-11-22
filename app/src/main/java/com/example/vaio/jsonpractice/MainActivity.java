package com.example.vaio.jsonpractice;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.vaio.adapter.ListViewAdapter;
import com.example.vaio.object.ItemObject;
import com.example.vaio.object.JsonParser;
import com.example.vaio.object.MyDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener, SearchView.OnQueryTextListener {
    private static final String TAG = "MainActivity";
    private ListView listView;
    private ListViewAdapter adapter;
    public static final int WHAT = 1;
    private ArrayList<ItemObject> arrItemObject = new ArrayList<>();
    private ArrayList<ItemObject> arrItemObjectTmp = new ArrayList<>();
    private ActionBar actionBar;
    private SearchView searchView;
    private MyDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new MyDatabase(this);
        if(!isNetworkAvailable(this)){
            arrItemObject = database.getData();
            arrItemObjectTmp.addAll(arrItemObject);
        }else {
            initData();
        }

        initViews();
        customActionBar();
    }
    public boolean isNetworkAvailable(final Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
    private void initData(){
        JsonParser jsonParser = new JsonParser(handler);
        jsonParser.execute("http://tigia.hunggiasaigon.com/");
    }
    private void initViews() {
        actionBar = getSupportActionBar();
        listView = (ListView) findViewById(R.id.lvMain);
        adapter = new ListViewAdapter(this, arrItemObjectTmp);
        listView.setAdapter(adapter);
    }

    private void customActionBar() {
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setCustomView(R.layout.action_bar);
        searchView = (SearchView) actionBar.getCustomView().findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.saveOffline);
        menuItem.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT) {
                arrItemObject.addAll((Collection<? extends ItemObject>) msg.obj);
                arrItemObjectTmp.addAll((Collection<? extends ItemObject>) msg.obj);
//                database.insertData(arrItemObject);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        database.clearData();
        database.insertData(arrItemObject);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        arrItemObjectTmp.clear();
        arrItemObjectTmp.addAll(arrItemObject);
        if(newText.isEmpty()){
            adapter.notifyDataSetChanged();
            return false;
        }
        for (int i = arrItemObjectTmp.size() - 1; i >= 0; i--) {
            if(!arrItemObjectTmp.get(i).toString().contains(newText)){
                arrItemObjectTmp.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
        return true;
    }
}
