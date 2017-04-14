package com.boost.leodev.socialslogin.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.boost.leodev.socialslogin.mvp.models.User;

public interface UserProfileView extends MvpView {
    void showUserData(User user);
    void onLogout();
    void showMessage(String message);
    void startFilePickerIntent();
}
