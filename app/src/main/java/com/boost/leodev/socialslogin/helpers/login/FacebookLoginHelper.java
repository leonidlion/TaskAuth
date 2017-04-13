package com.boost.leodev.socialslogin.helpers.login;


import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

public class FacebookLoginHelper implements LoginHelper {
    private CallbackManager mCallbackManager;

    @Override
    public void init() {

    }

    @Override
    public void doLogin() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FacebookSdk.isFacebookRequestCode(requestCode)){
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
