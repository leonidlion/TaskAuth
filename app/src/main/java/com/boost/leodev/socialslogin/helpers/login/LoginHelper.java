package com.boost.leodev.socialslogin.helpers.login;

import android.content.Intent;

public interface LoginHelper {
    void init();
    void doLogin();
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
