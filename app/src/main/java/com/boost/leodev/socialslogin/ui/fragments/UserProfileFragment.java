package com.boost.leodev.socialslogin.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.boost.leodev.socialslogin.R;
import com.boost.leodev.socialslogin.mvp.presenters.UserProfilePresenter;
import com.boost.leodev.socialslogin.mvp.views.UserProfileView;


public class UserProfileFragment extends MvpAppCompatFragment implements UserProfileView {
    @InjectPresenter
    UserProfilePresenter mPresenter;

    private static final String TAG = "UserProfileFragment";
    private static final int LAYOUT = R.layout.fragment_user_profile;

    public static UserProfileFragment newInstance(String userToken){
        Bundle args = new Bundle();
        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        return view;
    }
}
