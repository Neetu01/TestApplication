package com.example.administrator.myapplication.Model;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.administrator.myapplication.APIinterface.IUser;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 6/5/2019.
 */

public class User implements IUser {

    String email;
    String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean IsValidatedata() {
        //Check email is empty
        //check email is matches pattern
        //check password length
        return !TextUtils.isEmpty(getEmail()) && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches() && getPassword().length()>6;
    }
}
