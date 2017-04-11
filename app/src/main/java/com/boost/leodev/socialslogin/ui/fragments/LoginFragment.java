package com.boost.leodev.socialslogin.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.boost.leodev.socialslogin.Constants;
import com.boost.leodev.socialslogin.R;
import com.boost.leodev.socialslogin.event.EventMainChangeFragment;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.boost.leodev.socialslogin.mvp.presenters.LoginPresenter;
import com.boost.leodev.socialslogin.mvp.views.LoginView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends MvpAppCompatFragment implements
        LoginView,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @InjectPresenter
    LoginPresenter mPresenter;

    @BindView(R.id.google_login)
    SignInButton mGoogleLogin;

    @OnClick({R.id.google_login, R.id.fb_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.google_login:
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(intent, Constants.GOOGLE_REQUEST_CODE);
                break;
            case R.id.fb_login:
                break;
        }
    }

    private static final String TAG = "LoginFragment";
    private static final int LAYOUT = R.layout.fragment_login;
    private GoogleApiClient mGoogleApiClient;

    public static LoginFragment newInstance(){
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        if (mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.GOOGLE_REQUEST_CODE){
            mPresenter.authWithGoogle(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
        if (requestCode == Constants.FACEBOOK_REQUEST_CODE){
            mPresenter.authWithFacebook();
        }
    }

    @Override
    public void onSuccessSignIn(User user) {
        EventBus.getDefault().post(new EventMainChangeFragment(UserProfileFragment.newInstance(user)));
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected: ");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: ");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: ");
    }
}
