package com.example.zunnorain.telcard_khokha.HistoryActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.zunnorain.telcard_khokha.Classes.HistoryItem;
import com.example.zunnorain.telcard_khokha.Classes.Item;
import com.example.zunnorain.telcard_khokha.DialogHelper.DialogHelper;
import com.example.zunnorain.telcard_khokha.R;
import com.example.zunnorain.telcard_khokha.Session.SessionManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryActivity extends DialogHelper {

    private RecyclerView recyclerView;
    private List<HistoryItem> list;
    private HistoryAdapter hAdapter;
    SessionManager sessionManager;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History Book");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders");
        sessionManager = new SessionManager(getApplicationContext());
        list=new ArrayList<>();

        showProgressDialog();

        list = getListData();
        recyclerView =(RecyclerView)findViewById(R.id.history_recycler_view);
        hAdapter = new HistoryAdapter(list,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(hAdapter);
        hAdapter.notifyDataSetChanged();

    }

    public List<HistoryItem> getListData() {
        final List<HistoryItem> hlist=new ArrayList<>();
        hlist.clear();
        showProgressDialog();
        final String phone = sessionManager.getSessionValues().getPhone();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(phone).exists())
                {
                    DataSnapshot userdatasnapshot= dataSnapshot.child(phone);
                    for (DataSnapshot snapshot : userdatasnapshot.getChildren())
                    {
                        HistoryItem historyItem = snapshot.getValue(HistoryItem.class);
                        hlist.add(historyItem);
                    }

                    hideProgressDialog();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return hlist;
    }
}
