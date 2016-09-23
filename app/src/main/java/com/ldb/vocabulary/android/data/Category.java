package com.ldb.vocabulary.android.data;

/**
 * Created by lsp on 2016/9/22.
 */
public class Category {

    private String mId;
    private String mName;
    private String mImage;
    private int mFavoriteCount;
    private int mWordCount;

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
