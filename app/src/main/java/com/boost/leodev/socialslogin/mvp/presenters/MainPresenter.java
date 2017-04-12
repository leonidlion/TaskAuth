package com.boost.leodev.socialslogin.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.boost.leodev.socialslogin.event.EventMainChangeFragment;
import com.boost.leodev.socialslogin.mvp.views.MainView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    public void onResume(){
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    public void onPause(){
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onChangeFragment(EventMainChangeFragment changeFragment){
        getViewState().changeFragment(changeFragment);
    }
}
