package com.example.zunnorain.telcard_khokha.OptionsActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zunnorain.telcard_khokha.CartActivity.CartActivity;
import com.example.zunnorain.telcard_khokha.Classes.OperatorClass;
import com.example.zunnorain.telcard_khokha.DBHandler.DBHandler;
import com.example.zunnorain.telcard_khokha.HistoryActivity.HistoryActivity;
import com.example.zunnorain.telcard_khokha.HistoryActivity.HistoryAdapter;
import com.example.zunnorain.telcard_khokha.LoginSignup.LoginActivity;
import com.example.zunnorain.telcard_khokha.PurchaseToBeDetails.PurchaseDeatils;
import com.example.zunnorain.telcard_khokha.R;
import com.example.zunnorain.telcard_khokha.Session.SessionManager;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout shop_layout;
    private RelativeLayout cart_layout;
    private RelativeLayout history_layout;
    private RelativeLayout setting_layout;

    private TextView cart_text;

    DBHandler dbHandler;
    SessionManager sessionManager;

    OperatorClass operatorClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        shop_layout=(RelativeLayout)findViewById(R.id.shop_layout);
        cart_layout=(RelativeLayout)findViewById(R.id.cart_layout);
        history_layout=(RelativeLayout)findViewById(R.id.history_layout);
        setting_layout=(RelativeLayout)findViewById(R.id.setting_layout);

        cart_text= (TextView)findViewById(R.id.cart_text);

        dbHandler=new DBHandler(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        shop_layout.setOnClickListener(this);
        cart_layout.setOnClickListener(this);
        history_layout.setOnClickListener(this);
        setting_layout.setOnClickListener(this);

       // dbHandler.clearCart();
//        addOperators();

    }

    private void addOperators() {

        operatorClass.operatorClassList.add(new OperatorClass("Mobilink", R.drawable.jazz, 1));
        operatorClass.operatorClassList.add(new OperatorClass("Ufone", R.drawable.ufone, 2));
        operatorClass.operatorClassList.add(new OperatorClass("Warid", R.drawable.warid, 3));
        operatorClass.operatorClassList.add(new OperatorClass("Telenor", R.drawable.telenor, 4));
        operatorClass.operatorClassList.add(new OperatorClass("Zong", R.drawable.zong, 5));
    }


    @Override
    protected void onStart() {

        cartDecor();

        super.onStart();
    }

    private void cartDecor() {

        int count =dbHandler.getItemsCount();
        cart_text.setText(""+count);
    }

    @Override
    protected void onResume() {
        cartDecor();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i==R.id.shop_layout)
        {
            startActivity(new Intent(OptionsActivity.this, PurchaseDeatils.class));
        }
        if(i==R.id.cart_layout)
        {
            startActivity(new Intent(OptionsActivity.this, CartActivity.class));
        }
        if(i==R.id.history_layout)
        {
            if(!sessionManager.isLoggedIn())
            {
                Intent intent = new  Intent(OptionsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else
            {
                Intent intent = new  Intent(OptionsActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        }
        if(i==R.id.setting_layout)
        {
            if(!sessionManager.isLoggedIn())
            {
                Intent intent = new  Intent(OptionsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else
            {
                sessionManager.LogOutSession();
            }
        }
    }
}
