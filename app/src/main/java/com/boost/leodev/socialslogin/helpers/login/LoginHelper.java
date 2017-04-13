package com.boost.leodev.socialslogin.helpers.login;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.boost.leodev.socialslogin.mvp.models.User;

public abstract class LoginHelper {
    LoginResultCallback mCallback;
    Fragment mFragment;

    public interface LoginResultCallback{
        void onResultSuccess(User user);
        void onError(String message);
    }

    LoginHelper(Fragment fragment, LoginResultCallback callback){
        mFragment= fragment;
        mCallback = callback;
    }

    public abstract void init();
    public abstract void doLogin();
    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);
}
