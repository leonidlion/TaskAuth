package com.boost.leodev.socialslogin.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.boost.leodev.socialslogin.Constants;
import com.boost.leodev.socialslogin.R;
import com.boost.leodev.socialslogin.event.EventMainChangeFragment;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.boost.leodev.socialslogin.mvp.presenters.LoginPresenter;
import com.boost.leodev.socialslogin.mvp.views.LoginView;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.SignInButton;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends MvpAppCompatFragment implements LoginView{
    private static final int LAYOUT = R.layout.fragment_login;

    @InjectPresenter
    LoginPresenter mPresenter;

    @BindView(R.id.google_login)
    SignInButton mGoogleLogin;
    @BindView(R.id.fb_login)
    ImageButton mFbLogin;


    @OnClick({R.id.google_login, R.id.fb_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.google_login:
                mPresenter.onGoogleClick();
                break;
            case R.id.fb_login:
                mPresenter.onFacebookClick();
                break;
        }
    }

    public static LoginFragment newInstance(){
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
//        mPresenter.checkIsAuth();
        return view;
    }

    @Override
    public void startSignInGoogle(){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mPresenter.getApiClient());
        startActivityForResult(intent, Constants.GOOGLE_REQUEST_CODE);
    }

    @Override
    public void startSignInFacebook(){
        mPresenter.initFacebook();
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("public_profile", "email"));
    }

    @Override
    public void changeFragment(User user){
        EventBus.getDefault().post(new EventMainChangeFragment(UserProfileFragment.newInstance(user)));
    }

    @Override
    public void showError(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            mPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }
}
