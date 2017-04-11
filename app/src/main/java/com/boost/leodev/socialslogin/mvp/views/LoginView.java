package com.boost.leodev.socialslogin.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.boost.leodev.socialslogin.mvp.models.User;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface LoginView extends MvpView {
    void onSuccessSignIn(User user);
    void onError(String message);
}
