package com.boost.leodev.socialslogin.event;


import android.support.v4.app.Fragment;

public class EventMainChangeFragment {
    private Fragment mFragment;

    public EventMainChangeFragment(Fragment fragment){
        mFragment = fragment;
    }

    public Fragment getFragment(){
        return mFragment;
    }
}
