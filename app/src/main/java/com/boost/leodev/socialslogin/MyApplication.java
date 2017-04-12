package com.boost.leodev.socialslogin;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by leonid on 12.04.17.
 */

public class MyApplication extends Application {
    private static MyApplication sMyApplication;
    private static GoogleApiClient sGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();
        sMyApplication = this;
        initGoogleApi();
    }

    private void initGoogleApi(){
        GoogleSignInOptions mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        sGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build();
    }

    public static GoogleApiClient getGoogleApiClient() {
        return sGoogleApiClient;
    }

    public static Context getAppContext() {
        return sMyApplication;
    }
}
