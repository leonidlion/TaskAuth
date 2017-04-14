package com.boost.leodev.socialslogin.mvp.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.boost.leodev.socialslogin.Constants;
import com.boost.leodev.socialslogin.MyApplication;
import com.boost.leodev.socialslogin.R;
import com.boost.leodev.socialslogin.helpers.profile.FacebookUserProfileHelper;
import com.boost.leodev.socialslogin.helpers.profile.GoogleUserProfileHelper;
import com.boost.leodev.socialslogin.helpers.profile.UserProfileHelper;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.boost.leodev.socialslogin.mvp.views.UserProfileView;


@InjectViewState
public class UserProfilePresenter extends MvpPresenter<UserProfileView> {
    private UserProfileHelper mProfileHelper;

    public void logOut() {
        mProfileHelper.logout();
        getViewState().onLogout();
    }

    public void setProfileHelper(int socialHelperId){
        switch (socialHelperId){
            case Constants.GOOGLE_HELPER:
                mProfileHelper = new GoogleUserProfileHelper();
                break;
            case Constants.FACEBOOK_HELPER:
                mProfileHelper = new FacebookUserProfileHelper();
                break;
        }
    }

    public void getUser(User user) {
        if (user == null) {
            getViewState().showMessage(MyApplication.getAppContext().getString(R.string.error_get_user_data));
            return;
        }
        getViewState().showUserData(user);
    }

    public void startFilePicker(){
        getViewState().startFilePickerIntent();
    }

    public void onActivityResult(String fileUri){

    }

    public void showMessage(String message){
        getViewState().showMessage(message);
    }
}
