package com.boost.leodev.socialslogin.ui.activities;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.boost.leodev.socialslogin.R;
import com.boost.leodev.socialslogin.event.EventMainChangeFragment;
import com.boost.leodev.socialslogin.mvp.presenters.MainPresenter;
import com.boost.leodev.socialslogin.mvp.views.MainView;
import com.boost.leodev.socialslogin.ui.fragments.LoginFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        onChangeFragment(new EventMainChangeFragment(LoginFragment.newInstance()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onChangeFragment(EventMainChangeFragment changeFragment){
        getSupportFragmentManager().beginTransaction()
                .replace(CONTAINER, changeFragment.getFragment())
                .commit();
    }
}
