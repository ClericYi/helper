package com.clericyi.helper;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clericyi.basehelper.BaseActivity;
import com.clericyi.basehelper.ui.LeadBaseActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends LeadBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup group = findViewById(R.id.linear);
        addCirclePoint(group,10);
    }
}
