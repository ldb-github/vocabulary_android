package com.ldb.vocabulary.android.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lsp on 2016/9/22.
 */
public class Category implements Parcelable{

    private String mId;
    private String mName;
    private String mImage;
    private int mFavoriteCount;
    private int mWordCount;

    public Category(){
    }

    private Category(Parcel source){
        mId = source.readString();
        mName = source.readString();
        mImage = source.readString();
        mFavoriteCount = source.readInt();
        mWordCount = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mImage);
        dest.writeInt(mFavoriteCount);
        dest.writeInt(mWordCount);
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
}
