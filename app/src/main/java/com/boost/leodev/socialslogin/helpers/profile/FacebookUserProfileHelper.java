package com.boost.leodev.socialslogin.helpers.profile;


import com.facebook.login.LoginManager;

public class FacebookUserProfileHelper implements UserProfileHelper {

    @Override
    public void logout() {
        LoginManager.getInstance().logOut();
    }
}
