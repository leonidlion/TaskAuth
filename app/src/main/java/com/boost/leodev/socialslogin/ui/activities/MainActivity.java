package com.boost.leodev.socialslogin.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.boost.leodev.socialslogin.R;
import com.boost.leodev.socialslogin.Utils;
import com.boost.leodev.socialslogin.mvp.presenters.MainPresenter;
import com.boost.leodev.socialslogin.mvp.views.MainView;
import com.boost.leodev.socialslogin.ui.fragments.LoginFragment;
import com.boost.leodev.socialslogin.ui.fragments.UserProfileFragment;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    @InjectPresenter
    MainPresenter mPresenter;

    private static final String TAG = "MainActivity";
    private static final int LAYOUT = R.layout.activity_main;
    private static final int CONTAINER = R.id.fl_main_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        if (!TextUtils.isEmpty(Utils.getUserToken(this))){
            changeFragment(UserProfileFragment.newInstance(Utils.getUserToken(this)));
        }else {
            changeFragment(LoginFragment.newInstance());
        }
    }

    @Override
    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(CONTAINER, fragment)
                .commit();
    }
}
