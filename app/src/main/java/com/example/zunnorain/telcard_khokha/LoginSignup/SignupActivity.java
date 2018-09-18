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
import com.example.zunnorain.telcard_khokha.OperatorActivity.OperatorActivity;
import com.example.zunnorain.telcard_khokha.R;
import com.example.zunnorain.telcard_khokha.Session.SessionManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

    public class SignupActivity extends DialogHelper implements View.OnClickListener{


    private DatabaseReference mDatabaseReference;

    SessionManager sessionManager;

    private TextInputLayout mPhoneInputLayout;
    private TextInputLayout mUsernameLayout;
    private TextInputLayout mPasswordLayout;
    private android.support.v7.widget.Toolbar toolbar;
    private TextView login_instead;


    private Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        toolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

        sessionManager=new SessionManager(this);

        login_instead=(TextView)findViewById(R.id.instead_login);
        mPhoneInputLayout = (TextInputLayout)findViewById(R.id.phoneinputfield);
        mUsernameLayout=(TextInputLayout)findViewById(R.id.nameinputfield);
        mPasswordLayout=(TextInputLayout)findViewById(R.id.passwordinputfield);

        login_instead.setOnClickListener(this);

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

        mUsernameLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mUsernameLayout.setError(null);
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

        mSignupButton=findViewById(R.id.signup_activity_button);

        mSignupButton.setOnClickListener(this);

    }


    private void createAccount(final String phone, final String username , final String password) {
        if (!validateForm(phone , username , password)) {
            return;
        }

        showProgressDialog();

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(phone).exists())
                {
                    hideProgressDialog();
                    mPhoneInputLayout.setError("Number is  already registered");
                    return;
                }
                else {
                    User user = new User( username,phone,password);
                    mDatabaseReference.child(phone).setValue(user);
                    hideProgressDialog();
                    sessionManager.createLoginSession(user);
                    finish();
                    //updateActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void updateActivity() {
        Intent intent = new Intent(SignupActivity.this, OperatorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


    private boolean validateForm(String phone , String username , String password) {

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

        if (TextUtils.isEmpty(username)) {
            mUsernameLayout.setError("User name is required");
            return false;
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
        if (i==R.id.signup_activity_button)
        {
            String phone = mPhoneInputLayout.getEditText().getText().toString().trim();
            String username = mUsernameLayout.getEditText().getText().toString().trim();
            String password= mPasswordLayout.getEditText().getText().toString().trim();

            createAccount(phone,username,password);

        }
        if(i==R.id.instead_login)
        {
            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            finish();
        }
    }
}
