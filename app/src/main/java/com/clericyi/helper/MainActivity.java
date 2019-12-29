package com.clericyi.helper;


import android.os.Bundle;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clericyi.basehelper.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private static final String TAG = "wangyi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List list = new ArrayList<Integer>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        Adapter adapter = new Adapter(getBaseContext(), list);
        RecyclerView recyclerView = findViewById(R.id.table);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
