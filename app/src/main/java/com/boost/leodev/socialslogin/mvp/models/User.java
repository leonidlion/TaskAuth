package com.boost.leodev.socialslogin.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable{
    private String mName;
    private String mEmail;
    private String mPhotoUri;
    private String mBirthDay;

    public User(){}

    protected User(Parcel in) {
        mName = in.readString();
        mEmail = in.readString();
        mPhotoUri = in.readString();
        mBirthDay = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhotoUri() {
        return mPhotoUri;
    }

    public void setPhotoUri(String photoUri) {
        mPhotoUri = photoUri;
    }

    public String getBirthDay() {
        return mBirthDay;
    }

    public void setBirthDay(String birthDay) {
        mBirthDay = birthDay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mEmail);
        dest.writeString(mPhotoUri);
        dest.writeString(mBirthDay);
    }
}
