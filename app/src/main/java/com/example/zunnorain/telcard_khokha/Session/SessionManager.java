package com.example.zunnorain.telcard_khokha.Session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.zunnorain.telcard_khokha.Classes.User;

/**
 * Created by Zunnorain on 13/06/2018.
 */

public class SessionManager {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Context context;

    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_Password="password";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    public void createLoginSession(User user)
    {
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_PHONE,user.getPhone());
        editor.putString(KEY_NAME,user.getUsername());
        editor.putString(KEY_Password,user.getPassword());
        editor.commit();
    }

    public boolean isLoggedIn()
    {
        return sharedPreferences.getBoolean(IS_LOGIN,false);
    }

    public void LogOutSession()
    {
        editor.clear();
        editor.commit();
    }

    public User getSessionValues()
    {
        User user=new User();
        user.setPhone(sharedPreferences.getString(KEY_PHONE,null));
        user.setUsername(sharedPreferences.getString(KEY_NAME,null));
        user.setPassword(sharedPreferences.getString(KEY_Password,null));
        return user;
    }
}
