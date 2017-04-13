package com.boost.leodev.socialslogin.helpers.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.boost.leodev.socialslogin.Constants;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FacebookLoginHelper extends LoginHelper {
    private CallbackManager mCallbackManager;

    public FacebookLoginHelper(Fragment fragment, LoginResultCallback callback) {
        super(fragment, callback);
    }

    @Override
    public void init() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(mFragment,
                Arrays.asList("public_profile", "email"));
    }

    @Override
    public void doLogin() {
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                mCallback.onResultSuccess(getUserFromFacebook(object));
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,birthday,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                mCallback.onError("Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                mCallback.onError(error.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FacebookSdk.isFacebookRequestCode(requestCode)){
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public int getSocialsId() {
        return Constants.FACEBOOK_HELPER;
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
