package com.boost.leodev.socialslogin.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.boost.leodev.socialslogin.R;
import com.boost.leodev.socialslogin.event.EventMainChangeFragment;
import com.boost.leodev.socialslogin.mvp.presenters.MainPresenter;
import com.boost.leodev.socialslogin.mvp.views.MainView;
import com.boost.leodev.socialslogin.ui.fragments.LoginFragment;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    @InjectPresenter
    MainPresenter mPresenter;

    private static final int LAYOUT = R.layout.activity_main;
    private static final int CONTAINER = R.id.fl_main_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        changeFragment(new EventMainChangeFragment(LoginFragment.newInstance()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    public void changeFragment(EventMainChangeFragment changeFragment){
        getSupportFragmentManager().beginTransaction()
                .replace(CONTAINER, changeFragment.getFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
