package com.example.zunnorain.telcard_khokha.PurchaseToBeDetails;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zunnorain.telcard_khokha.Classes.Item;
import com.example.zunnorain.telcard_khokha.DBHandler.DBHandler;
import com.example.zunnorain.telcard_khokha.OperatorActivity.OperatorActivity;
import com.example.zunnorain.telcard_khokha.R;
import com.example.zunnorain.telcard_khokha.Session.SessionManager;

public class PurchaseDeatils extends AppCompatActivity implements View.OnClickListener{


    public static int  cindex=0;
    public static int qindex=0;
    public String [] qty = {"1","2","3","4","5"};
    public String [] cat = {"100","300","500","1000"};
    public int [] catint = {105,315,525,1050};

    String operatorName="";
    int operatorLogo=0;

    private TextInputLayout moperatorlayout ;

    private Spinner quantity_spinner;
    private Spinner category_spinner;

    private TextView total_price;
    private TextView per_price;

    private Button add_cart_button;

    SessionManager sessionManager;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_deatils);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Buy Cards");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moperatorlayout=(TextInputLayout)findViewById(R.id.operatorinputfield);
        total_price=(TextView)findViewById(R.id.dialog_total_price);
        per_price=(TextView)findViewById(R.id.per_card_price);

        add_cart_button=(Button)findViewById(R.id.add_cart_btn);

        sessionManager= new SessionManager(getApplicationContext());
        dbHandler = new DBHandler(this);

        quantity_spinner=(Spinner)findViewById(R.id.spinner_quantity);
        category_spinner=(Spinner)findViewById(R.id.spinner_category);

        moperatorlayout.getEditText().setOnClickListener(this);
        add_cart_button.setOnClickListener(this);


        quantity_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                setQindex(position);
                setTotalPrice(total_price);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setCindex(position);
                setTotalPrice(total_price);
                setPriceDialog(per_price);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        ArrayAdapter<CharSequence> quantity_Adapter = new ArrayAdapter<CharSequence>(this,R.layout.spinner_text, qty );
        quantity_Adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        quantity_spinner.setAdapter(quantity_Adapter);

        ArrayAdapter<CharSequence> category_Adapter = new ArrayAdapter<CharSequence>(this,R.layout.spinner_text, cat );
        category_Adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        category_spinner.setAdapter(category_Adapter);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==69)
        {
            if(resultCode==RESULT_OK)
            {
                operatorName = data.getStringExtra("operatorName");
                operatorLogo = data.getIntExtra("operatorLogo",0);
                moperatorlayout.getEditText().setText("Operator - " + operatorName);
                moperatorlayout.getEditText().setCursorVisible(false);
                moperatorlayout.getEditText().setClickable(true);
                setTotalPrice(total_price);
                setPriceDialog(per_price);
            }
        }
    }

    private   void setCindex(int position )
    {
        cindex=position;
    }
    private void setQindex(int position )
    {
        qindex=position;
    }
    private void setPriceDialog(TextView priceText)
    {
        if(!TextUtils.isEmpty(operatorName))
            priceText.setText("*Rs "+catint[cindex]+" per card");
    }
    private   void setTotalPrice(TextView totalPrice )
    {
        if(!TextUtils.isEmpty(operatorName))
            totalPrice.setText(""+(catint[cindex]*(qindex+1)));
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i==R.id.operatorEdittext)
        {
            moperatorlayout.setError(null);
            Intent intent=new Intent(PurchaseDeatils.this,OperatorActivity.class);
            startActivityForResult(intent,69);
        }
        if(i==R.id.add_cart_btn)
            addToCartFunction();
    }

    private void addToCartFunction() {
        if(TextUtils.isEmpty(operatorName))
        {
            moperatorlayout.setError("Select the operator to add item to cart");
            return;
        }
        else {
            Item item = new Item(operatorLogo,operatorName,(qindex+1),cat[cindex],(catint[cindex]*(qindex+1)));
            dbHandler.addItem(item);
            Toast.makeText(this,"Item Addded to Cart",Toast.LENGTH_SHORT).show();
            finish();
        }

    }


}
