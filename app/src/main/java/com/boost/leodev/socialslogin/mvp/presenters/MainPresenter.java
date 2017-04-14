package com.boost.leodev.socialslogin.mvp.presenters;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.boost.leodev.socialslogin.Constants;
import com.boost.leodev.socialslogin.MyApplication;
import com.boost.leodev.socialslogin.event.EventMainChangeFragment;
import com.boost.leodev.socialslogin.helpers.login.FacebookLoginHelper;
import com.boost.leodev.socialslogin.helpers.login.GoogleLoginHelper;
import com.boost.leodev.socialslogin.mvp.views.MainView;
import com.boost.leodev.socialslogin.ui.fragments.LoginFragment;
import com.boost.leodev.socialslogin.ui.fragments.UserProfileFragment;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    public void checkAuthUser(){
        if (!checkFacebookUser() && !checkGoogleUser()){
            getViewState().changeFragment(new EventMainChangeFragment(LoginFragment.newInstance()));
        }
    }

    public void onResume(){
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    public void onPause(){
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onChangeFragment(EventMainChangeFragment changeFragment){
        getViewState().changeFragment(changeFragment);
    }

    private boolean checkFacebookUser(){
        if (AccessToken.getCurrentAccessToken() != null){
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            if (object != null){
                                getViewState().changeFragment(new EventMainChangeFragment(
                                        UserProfileFragment.newInstance(
                                                FacebookLoginHelper.getUserFromFacebook(object),
                                                Constants.FACEBOOK_HELPER)));
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,birthday,picture");
            request.setParameters(parameters);
            request.executeAsync();
            return true;
        }
        return false;
    }

    private boolean checkGoogleUser(){
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(MyApplication.getGoogleApiClient());
        if (opr.isDone()){
            GoogleSignInResult result = opr.get();
            MyApplication.getGoogleApiClient().connect();
            getViewState().changeFragment(new EventMainChangeFragment(
                    UserProfileFragment.newInstance(
                            GoogleLoginHelper.getUserFromGoogle(
                                    result.getSignInAccount()), Constants.GOOGLE_HELPER)));
            return true;
        }
        return false;
    }
}
