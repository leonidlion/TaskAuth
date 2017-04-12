package com.boost.leodev.socialslogin.mvp.presenters;

import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.boost.leodev.socialslogin.Constants;
import com.boost.leodev.socialslogin.MyApplication;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.boost.leodev.socialslogin.mvp.views.LoginView;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {
    private CallbackManager mCallbackManager;

    public void initFacebook(){
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                getViewState().changeFragment(getUserFromFacebook(object));
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,birthday,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                getViewState().onCancel();
            }

            @Override
            public void onError(FacebookException error) {
                getViewState().showError(error.toString());
            }
        });
    }

    public void onGoogleClick() {
        MyApplication.getGoogleApiClient().connect();
        getViewState().startSignInGoogle();
    }

    public void onFacebookClick() {
        getViewState().startSignInFacebook();
    }

    public GoogleApiClient getApiClient() {
        return MyApplication.getGoogleApiClient();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.GOOGLE_REQUEST_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                getViewState().changeFragment(getUserFromGoogle(result.getSignInAccount()));
            }
        }
        if (FacebookSdk.isFacebookRequestCode(requestCode)){
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private User getUserFromGoogle(GoogleSignInAccount signInAccount){
        User user = new User();
        user.setName(signInAccount.getDisplayName());
        user.setEmail(signInAccount.getEmail());
        user.setPhotoUri(signInAccount.getPhotoUrl().toString());
        return user;
    }

    private User getUserFromFacebook(JSONObject object){
        User user = new User();
        try {
            user.setName(object.getString("name"));
            user.setEmail(object.getString("email"));
            user.setBirthDay(object.getString("birthday"));
            user.setPhotoUri(object.getJSONObject("picture").getJSONObject("data").getString("url"));
        } catch (JSONException e){
            e.printStackTrace();
        }
        return user;
    }
}
