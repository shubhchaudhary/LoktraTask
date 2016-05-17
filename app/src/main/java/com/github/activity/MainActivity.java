package com.github.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.adapter.ListAdapter;
import com.github.font.DividerItemDecoration;
import com.github.model.ArrayModel;
import com.github.net.ServiceCallBacks;
import com.github.net.ServiceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceCallBacks{
String token="6080cb36eb5b4d30a37dd2b410542aeab6c69ece";
    ServiceManager serviceCall;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceCall=new ServiceManager(MainActivity.this,MainActivity.this);
        serviceCall.getDetails(token);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onRequestComplete(Object data, int caller) {
        if (caller == ServiceCallBacks.GITHUB_CALL) {
            Type listType=new TypeToken<List<ArrayModel>>(){}.getType();
            List<ArrayModel> list=(List<ArrayModel>)new Gson().fromJson(data.toString(),listType);
            ListAdapter adapter=new ListAdapter(this,list);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onError(String errorString, int caller) {

    }

    @Override
    public void onRequestCancel(String errorString, int caller) {

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
