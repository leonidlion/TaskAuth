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
import com.boost.leodev.socialslogin.R;
import com.boost.leodev.socialslogin.event.EventMainChangeFragment;
import com.boost.leodev.socialslogin.helpers.login.FacebookLoginHelper;
import com.boost.leodev.socialslogin.helpers.login.GoogleLoginHelper;
import com.boost.leodev.socialslogin.helpers.login.LoginHelper;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.boost.leodev.socialslogin.mvp.presenters.LoginPresenter;
import com.boost.leodev.socialslogin.mvp.views.LoginView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends MvpAppCompatFragment implements LoginView, LoginHelper.LoginResultCallback{
    private static final int LAYOUT = R.layout.fragment_login;

    @InjectPresenter
    LoginPresenter mPresenter;

    @BindView(R.id.google_login)
    ImageButton mGoogleLogin;
    @BindView(R.id.fb_login)
    ImageButton mFbLogin;


    @OnClick({R.id.google_login, R.id.fb_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.google_login:
                mPresenter.setLoginHelper(new GoogleLoginHelper(this, this));
                break;
            case R.id.fb_login:
                mPresenter.setLoginHelper(new FacebookLoginHelper(this, this));
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
        return view;
    }

    @Override
    public void changeFragment(User user, int socialHelperId){
        EventBus.getDefault().post(new EventMainChangeFragment(UserProfileFragment.newInstance(user, socialHelperId)));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            mPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onResultSuccess(User user) {
        mPresenter.onResultSuccess(user);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
