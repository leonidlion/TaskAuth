package com.boost.leodev.socialslogin.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.boost.leodev.socialslogin.mvp.views.LoginView;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {
    private static final String TAG = "LoginPresenter";

    public void authWithGoogle(GoogleSignInResult result){
        if (result.isSuccess()){
            GoogleSignInAccount acc = result.getSignInAccount();
            User user = new User();
            user.setName(acc.getDisplayName());
            user.setEmail(acc.getEmail());
            user.setBirthDay(null);
            user.setPhotoUri(acc.getPhotoUrl().toString());
            getViewState().onSuccessSignIn(user);
        }
    }

    public void authWithFacebook() {

    }
}
