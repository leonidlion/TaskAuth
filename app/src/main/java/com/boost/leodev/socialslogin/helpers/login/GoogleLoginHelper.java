package com.boost.leodev.socialslogin.helpers.login;


import android.content.Intent;

import com.boost.leodev.socialslogin.Constants;
import com.boost.leodev.socialslogin.MyApplication;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleLoginHelper implements LoginHelper {

    @Override
    public void init() {
        MyApplication.getGoogleApiClient().connect();
    }

    @Override
    public void doLogin() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.GOOGLE_REQUEST_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
//                getViewState().changeFragment(getUserFromGoogle(result.getSignInAccount()));
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

    public boolean isApiClientConnected(){
        return MyApplication.getGoogleApiClient().isConnected();
    }

    public GoogleApiClient getApiClient(){
        return MyApplication.getGoogleApiClient();
    }
}
