package com.boost.leodev.socialslogin.helpers.profile;

import com.boost.leodev.socialslogin.MyApplication;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleUserProfileHelper implements UserProfileHelper {

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public void logout() {
        GoogleApiClient googleApiClient = MyApplication.getGoogleApiClient();
        if (googleApiClient.isConnected())
            Auth.GoogleSignInApi.signOut(googleApiClient);
    }
}
