package com.boost.leodev.socialslogin.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.boost.leodev.socialslogin.MyApplication;
import com.boost.leodev.socialslogin.event.EventMainChangeFragment;
import com.boost.leodev.socialslogin.mvp.views.UserProfileView;
import com.boost.leodev.socialslogin.ui.fragments.LoginFragment;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;


@InjectViewState
public class UserProfilePresenter extends MvpPresenter<UserProfileView> {

    public void logOut() {
        LoginManager.getInstance().logOut();

        GoogleApiClient googleApiClient = MyApplication.getGoogleApiClient();
        if (googleApiClient.isConnected())
            Auth.GoogleSignInApi.signOut(googleApiClient);

        EventBus.getDefault().post(new EventMainChangeFragment(LoginFragment.newInstance()));
    }


}
