package com.boost.leodev.socialslogin.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.boost.leodev.socialslogin.R;
import com.boost.leodev.socialslogin.mvp.presenters.LoginPresenter;
import com.boost.leodev.socialslogin.mvp.views.LoginView;


public class LoginFragment extends MvpAppCompatFragment implements LoginView {
    @InjectPresenter
    LoginPresenter mPresenter;

    private static final String TAG = "LoginFragment";
    private static final int LAYOUT = R.layout.fragment_login;


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
        return view;
    }

    @Override
    public void onSuccessSignIn() {

    }

    @Override
    public void onError(String message) {

    }
}
