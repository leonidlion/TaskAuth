package com.boost.leodev.socialslogin.helpers.profile;


import com.boost.leodev.socialslogin.mvp.models.User;
import com.facebook.login.LoginManager;

public class FacebookUserProfileHelper implements UserProfileHelper {

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public void logout() {
        LoginManager.getInstance().logOut();
    }
}
