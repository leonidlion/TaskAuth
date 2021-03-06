package com.boost.leodev.socialslogin.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.boost.leodev.socialslogin.event.EventMainChangeFragment;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void changeFragment(EventMainChangeFragment changeFragment);
}
