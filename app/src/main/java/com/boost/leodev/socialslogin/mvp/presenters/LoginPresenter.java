package com.boost.leodev.socialslogin.mvp.presenters;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.boost.leodev.socialslogin.helpers.login.LoginHelper;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.boost.leodev.socialslogin.mvp.views.LoginView;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {
    private LoginHelper mLoginHelper;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLoginHelper.onActivityResult(requestCode, resultCode, data);
    }

    public void setLoginHelper(LoginHelper loginHelper) {
        mLoginHelper = loginHelper;
        mLoginHelper.init();
        mLoginHelper.doLogin();
    }

    public void onResultSuccess(User user) {
        getViewState().changeFragment(user);
    }
}
