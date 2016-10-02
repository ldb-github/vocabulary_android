package com.ldb.vocabulary.android.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lsp on 2016/9/22.
 */
public class Category implements Parcelable{

    private String mId;
    private int mSubIndex;
    private String mName;
    private String mImage;
    private String mImageRemote;
    private int mFavoriteCount;
    private int mWordCount;
    private String mLanguage;
    private String mUsername;
    private Date mCreateTime;

    public Category(){
    }

    private Category(Parcel source){
        mId = source.readString();
        mSubIndex = source.readInt();
        mName = source.readString();
        mImage = source.readString();
        mImageRemote = source.readString();
        mFavoriteCount = source.readInt();
        mWordCount = source.readInt();
        mLanguage = source.readString();
        mUsername = source.readString();
        mCreateTime = new Date(source.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeInt(mSubIndex);
        dest.writeString(mName);
        dest.writeString(mImage);
        dest.writeString(mImageRemote);
        dest.writeInt(mFavoriteCount);
        dest.writeInt(mWordCount);
        dest.writeString(mLanguage);
        dest.writeString(mUsername);
        dest.writeLong(mCreateTime.getTime());
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public int getFavoriteCount() {
        return mFavoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        mFavoriteCount = favoriteCount;
    }

    public int getWordCount() {
        return mWordCount;
    }

    public void setWordCount(int wordCount) {
        mWordCount = wordCount;
    }

    public int getSubIndex() {
        return mSubIndex;
    }

    public void setSubIndex(int subIndex) {
        mSubIndex = subIndex;
    }

    public String getImageRemote() {
        return mImageRemote;
    }

    public void setImageRemote(String imageRemote) {
        mImageRemote = imageRemote;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public Date getCreateTime() {
        return mCreateTime;
    }

    public void setCreateTime(Date createTime) {
        mCreateTime = createTime;
    }
}
