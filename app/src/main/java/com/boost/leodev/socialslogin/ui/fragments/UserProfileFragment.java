package com.boost.leodev.socialslogin.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.boost.leodev.socialslogin.Constants;
import com.boost.leodev.socialslogin.R;
import com.boost.leodev.socialslogin.event.EventMainChangeFragment;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.boost.leodev.socialslogin.mvp.presenters.UserProfilePresenter;
import com.boost.leodev.socialslogin.mvp.views.UserProfileView;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileFragment extends MvpAppCompatFragment implements UserProfileView {
    @InjectPresenter
    UserProfilePresenter mPresenter;

    @BindView(R.id.civ_profile_icon)
    CircleImageView mProfileIcon;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.user_email)
    TextView mUserEmail;
    @BindView(R.id.user_date_birth)
    TextView mBirthDay;
    @BindView(R.id.btn_out)
    ImageButton mLogout;
    @BindView(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    @OnClick(R.id.btn_out)
    public void onClick(){
        mPresenter.logOut();
    }

    private static final int LAYOUT = R.layout.fragment_user_profile;
    private static final String ARGS_USER = "ARGS_USER";
    private static final String ARGS_SOCIAL_HELPER = "ARGS_SOCIAL_HELPER";

    public static UserProfileFragment newInstance(User user, int socialHelper){
        Bundle args = new Bundle();
        args.putParcelable(ARGS_USER, user);
        args.putInt(ARGS_SOCIAL_HELPER, socialHelper);
        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);

        int socialsId = getArguments().getInt(ARGS_SOCIAL_HELPER);

        mPresenter.setProfileHelper(socialsId);
        mPresenter.getUser((User) getArguments().getParcelable(ARGS_USER));

        changeToolbarTitle(socialsId);

        return view;
    }

    private void changeToolbarTitle(int socialsId){
        if(socialsId == Constants.FACEBOOK_HELPER)
            mToolbar.setTitle(getString(R.string.facebook_profile));
        else
            mToolbar.setTitle(getString(R.string.google_profile));
    }

    @Override
    public void showUserData(User user) {
        mUserName.setText(user.getName());
        mUserEmail.setText(user.getEmail());
        mBirthDay.setText(user.getBirthDay());
        Glide.with(this)
                .load(Uri.parse(user.getPhotoUri()))
                .into(mProfileIcon);
    }

    @Override
    public void onLogout() {
        EventBus.getDefault().post(new EventMainChangeFragment(LoginFragment.newInstance()));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
