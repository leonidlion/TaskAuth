package com.boost.leodev.socialslogin.helpers.profile;


import com.boost.leodev.socialslogin.mvp.models.User;

public interface UserProfileHelper {
    User getUser();
    void logout();
}
