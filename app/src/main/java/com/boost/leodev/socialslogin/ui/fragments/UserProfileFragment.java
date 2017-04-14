package com.boost.leodev.socialslogin.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

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
    @BindView(R.id.btn_share_file)
    Button mShareFile;
    @BindView(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    @OnClick({R.id.btn_out, R.id.btn_share_file})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_out:
                mPresenter.logOut();
                break;
            case R.id.btn_share_file:
                checkPermission();
                break;
        }
    }

    private static final int LAYOUT = R.layout.fragment_user_profile;
    private static final String ARGS_USER = "ARGS_USER";
    private static final String ARGS_SOCIAL_HELPER = "ARGS_SOCIAL_HELPER";

    private ViewGroup mRootView;
    private PermissionListener mReadExternalListener;
    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted(PermissionGrantedResponse response) {
            mPresenter.startFilePicker();
        }

        @Override
        public void onPermissionDenied(PermissionDeniedResponse response) {
        }

        @Override
        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
        }
    };


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

        mRootView = (ViewGroup) view;

        changeToolbarTitle(socialsId);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPermissionListener();
    }

    private void initPermissionListener() {
        mReadExternalListener = new CompositePermissionListener(mPermissionListener,
                SnackbarOnDeniedPermissionListener.Builder.with(mRootView, R.string.permission_read)
                        .withOpenSettingsButton(R.string.permission_setting)
                        .build());
    }

    private void checkPermission() {
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(mReadExternalListener)
                .check();
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
    public void startFilePickerIntent(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/* video/*");
        startActivityForResult(intent, Constants.FILE_PICK_REQUEST_CODE);
    }

    @Override
    public void onLogout() {
        EventBus.getDefault().post(new EventMainChangeFragment(LoginFragment.newInstance()));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.FILE_PICK_REQUEST_CODE && resultCode == RESULT_OK){
            if (data.getData() != null){
                postFacebookPhoto(data.getData());
            }else {
                mPresenter.showMessage(getString(R.string.data_null));
            }
        }
    }

    private void postFacebookPhoto(Uri uri){
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            mProfileIcon.setImageBitmap(bitmap);
            ShareDialog dialog = new ShareDialog(this);
            if (ShareDialog.canShow(SharePhotoContent.class)){
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                dialog.show(content);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
