package com.example.zunnorain.telcard_khokha.LoginSignup;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zunnorain.telcard_khokha.Classes.User;
import com.example.zunnorain.telcard_khokha.DialogHelper.DialogHelper;
import com.example.zunnorain.telcard_khokha.R;

import com.example.zunnorain.telcard_khokha.Session.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class LoginActivity extends DialogHelper implements View.OnClickListener{

    private DatabaseReference mDatabaseReference;

    SessionManager sessionManager;

    private TextInputLayout mPhoneInputLayout;
    private TextInputLayout mPasswordLayout;
    private TextView signup_instead;
    private android.support.v7.widget.Toolbar toolbar;

    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

        sessionManager= new SessionManager(this);

        signup_instead=(TextView)findViewById(R.id.instead_signup);
        mPhoneInputLayout = (TextInputLayout)findViewById(R.id.phoneinputfield);
        mPasswordLayout=(TextInputLayout)findViewById(R.id.passwordinputfield);

        signup_instead.setOnClickListener(this);

        mPhoneInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPhoneInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPasswordLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mPasswordLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        mLoginButton= findViewById(R.id.login_activity_button);

        mLoginButton.setOnClickListener(this);
    }

    private void signIn(final String phone, final String password) {
        if (!validateForm(phone,password)) {
            return;
        }
        showProgressDialog();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                hideProgressDialog();
                if(dataSnapshot.child(phone).exists())
                {
                   User user = dataSnapshot.child(phone).getValue(User.class);
                   if (user.getPassword().equals(password))
                   {
                      sessionManager.createLoginSession(user);
                       /*Intent intent =new Intent(LoginActivity.this,OperatorActivity.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(intent);*/
                       finish();

                   }
                   else
                       mPasswordLayout.setError("Enter correct password");
                   return;
                }
                else {

                    mPhoneInputLayout.setError("This is not a registered number");
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private boolean validateForm(String phone , String password) {
        if (TextUtils.isEmpty(phone)) {
            mPhoneInputLayout.setError("Mobile number is required");
            return  false;
        } else if(phone.length()!=11) {
            mPhoneInputLayout.setError("You must enter complete valid number");
            return  false;
        } else if (phone.charAt(0)!= '0'||phone.charAt(1)!='3'||(phone.charAt(2)<'0'||phone.charAt(2)>'4')) {
            mPhoneInputLayout.setError("Not a valid  Number");
            return false;
        } else
        {
            for (int i = 0;i<11;i++)
            {
                if (phone.charAt(i)<'0'||phone.charAt(i)>'9')
                {
                    mPhoneInputLayout.setError("Number cannot contain characters other than (0-9)");
                    return false;
                }
            }
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordLayout.setError("Password is required");
            return false;
        }
        else if(password.length()<6) {
            mPasswordLayout.setError("Password must be atleast 6 characters long");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i==R.id.login_activity_button)
        {
            String phone = mPhoneInputLayout.getEditText().getText().toString().trim();
            String password=mPasswordLayout.getEditText().getText().toString().trim();
            signIn(phone,password);
        }
        if(i==R.id.instead_signup)
        {
            startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            finish();
        }
    }
}
