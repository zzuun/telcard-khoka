package com.example.zunnorain.telcard_khokha.BankDetailsActivity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.zunnorain.telcard_khokha.Classes.HistoryItem;
import com.example.zunnorain.telcard_khokha.Classes.Item;
import com.example.zunnorain.telcard_khokha.DBHandler.DBHandler;
import com.example.zunnorain.telcard_khokha.OptionsActivity.OptionsActivity;
import com.example.zunnorain.telcard_khokha.R;
import com.example.zunnorain.telcard_khokha.Session.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankDeatailsActivity extends AppCompatActivity implements View.OnClickListener{

    private android.support.v7.widget.Toolbar toolbar;
    private TextInputLayout debitLayout;

    private Button continueButton;
    private String debitNumber;

    DBHandler dbHandler;
    private List<Item> list;
    DatabaseReference databaseReference ;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_deatails);

        toolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Debit Card Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        debitLayout =(TextInputLayout)findViewById(R.id.debitinputfield);
        continueButton=(Button)findViewById(R.id.continue_button);

        dbHandler= new DBHandler(getApplicationContext());
        list= new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Orders");
        sessionManager= new SessionManager(getApplicationContext());

        debitLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                debitLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        continueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i==R.id.continue_button)
        {
            debitNumber=debitLayout.getEditText().getText().toString().trim();
            if(validateNumber(debitNumber))
            {
                addToHistoyTable();
            }
        }
        if(i==R.id.debitinputfield)
        {

        }
    }

    private void addToHistoyTable() {
        list =dbHandler.fetchAllItems();
        final Date date = new Date();
        DateFormat dateFormat= new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        final String stringDate=dateFormat.format(date);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 int i = 1;
                for(Item item : list)
                {
                    String ii = Integer.toString(i);
                    HistoryItem historyItem= new HistoryItem(item.getComp_logo(),item.getComp_name(),stringDate,item.getQty(),item.getCat(),item.getPrice());
                    databaseReference.child(sessionManager.getSessionValues().getPhone()).child(stringDate+ii).setValue(historyItem);
                    i++;
                }
                dbHandler.clearCart();
                Intent intent = new Intent(BankDeatailsActivity.this, OptionsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean validateNumber(String debitNumber) {
        if(TextUtils.isEmpty(debitNumber)) {
            debitLayout.setError("Must enter debit card number to proceed");
            return false;
        }
        if(debitNumber.length()<16) {
            debitLayout.setError("Enter complete debit card number");
            return false;
        }
        return true;
    }
}
