package com.boost.leodev.socialslogin.ui.activities;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.boost.leodev.socialslogin.R;
import com.boost.leodev.socialslogin.event.EventMainChangeFragment;
import com.boost.leodev.socialslogin.mvp.presenters.MainPresenter;
import com.boost.leodev.socialslogin.mvp.views.MainView;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    @InjectPresenter
    MainPresenter mPresenter;

    private static final int LAYOUT = R.layout.activity_main;
    private static final int CONTAINER = R.id.fl_main_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        mPresenter.checkAuthUser();
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
                .setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim)
                .replace(CONTAINER, changeFragment.getFragment()).commit();
    }
}
