package com.example.zunnorain.telcard_khokha.OperatorActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.zunnorain.telcard_khokha.Classes.OperatorClass;
import com.example.zunnorain.telcard_khokha.R;

import java.util.ArrayList;
import java.util.List;

public class OperatorActivity extends AppCompatActivity {

    private RecyclerView comp_recycler_view;
    private OperatorAdapter cAdapter;
    private List<OperatorClass> arr = new ArrayList<OperatorClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comapny__menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Operator");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arr= getArr();
        comp_recycler_view =(RecyclerView)findViewById(R.id.comp_recycler_view);
        cAdapter = new OperatorAdapter(arr,getApplicationContext(),this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        comp_recycler_view.setLayoutManager(mLayoutManager);
        comp_recycler_view.setItemAnimator(new DefaultItemAnimator());
        comp_recycler_view.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private List<OperatorClass> getArr() {
        List<OperatorClass> list = new ArrayList<>();
        list.add(new OperatorClass("Mobilink", R.drawable.jazz, 1));
        list.add(new OperatorClass("Ufone", R.drawable.ufone, 2));
        list.add(new OperatorClass("Warid", R.drawable.warid, 3));
        list.add(new OperatorClass("Telenor", R.drawable.telenor, 4));
        list.add(new OperatorClass("Zong", R.drawable.zong, 5));
        return list;
    }
}
