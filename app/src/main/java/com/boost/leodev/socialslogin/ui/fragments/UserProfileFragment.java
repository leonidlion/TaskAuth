package com.boost.leodev.socialslogin.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.boost.leodev.socialslogin.R;
import com.boost.leodev.socialslogin.mvp.models.User;
import com.boost.leodev.socialslogin.mvp.presenters.UserProfilePresenter;
import com.boost.leodev.socialslogin.mvp.views.UserProfileView;
import com.bumptech.glide.Glide;

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

    @OnClick(R.id.btn_out)
    public void onClick(){
        mPresenter.logOut();
    }

    private static final String TAG = "UserProfileFragment";
    private static final int LAYOUT = R.layout.fragment_user_profile;
    private static final String ARGS_USER = "ARGS_USER";

    public static UserProfileFragment newInstance(User user){
        Bundle args = new Bundle();
        args.putParcelable(ARGS_USER, user);
        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);

        User user = getArguments().getParcelable(ARGS_USER);

        if (user != null) {
            mUserName.setText(user.getName());
            mUserEmail.setText(user.getEmail());
            mBirthDay.setText(user.getBirthDay());
            Log.d(TAG, "onCreateView: " + user.getPhotoUri());
            Glide.with(this)
                    .load(Uri.parse(user.getPhotoUri()))
                    .into(mProfileIcon);
        }

        return view;
    }
}
