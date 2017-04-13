package com.boost.leodev.socialslogin.helpers.login;


import android.content.Intent;
import android.support.v4.app.Fragment;

import com.boost.leodev.socialslogin.Constants;
import com.boost.leodev.socialslogin.MyApplication;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public class GoogleLoginHelper extends LoginHelper {

    public GoogleLoginHelper(Fragment fragment, LoginResultCallback callback) {
        super(fragment, callback);
    }

    @Override
    public void init() {
        if (!MyApplication.getGoogleApiClient().isConnected())
            MyApplication.getGoogleApiClient().connect();
    }

    @Override
    public void doLogin() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(MyApplication.getGoogleApiClient());
        mFragment.startActivityForResult(intent, Constants.GOOGLE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.GOOGLE_REQUEST_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                mCallback.onResultSuccess(getUserFromGoogle(result.getSignInAccount()));
            }
        }
    }

    private User getUserFromGoogle(GoogleSignInAccount signInAccount){
        User user = new User();
        user.setName(signInAccount.getDisplayName());
        user.setEmail(signInAccount.getEmail());
        user.setPhotoUri(signInAccount.getPhotoUrl().toString());
        return user;
    }
}
