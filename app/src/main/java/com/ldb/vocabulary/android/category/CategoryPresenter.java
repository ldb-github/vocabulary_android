package com.ldb.vocabulary.android.category;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.ldb.vocabulary.android.data.Category;
import com.ldb.vocabulary.android.data.Repository;
import com.ldb.vocabulary.android.data.RequestCallback;

import java.util.List;

/**
 * Created by lsp on 2016/9/22.
 */
public class CategoryPresenter implements CategoryContract.Presenter {

    private static final String TAG = CategoryPresenter.class.getSimpleName();

    private Context mContext;
    private Repository mRepository;
    private CategoryContract.View mView;

    public CategoryPresenter(Context context, Repository repository, CategoryContract.View view){
        mContext = context;
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        getCategoryList(1, "f", "a");
    }

    @Override
    public void getCategoryList(int page, String sort, String sortType) {
        mRepository.getCategoryList(mContext, page, sort, sortType,
                new RequestCallback.RequestCategoryListCallback() {
            @Override
            public void onSuccess(String message, List<Category> categoryList) {
                mView.updateDataSet(categoryList);
            }

            @Override
            public void onError(String error) {
                mView.notifyError(error);
            }
        });
    }

    @Override
    public void getImageForView(String url, ImageView imageView, int maxWidth, int maxHeight) {
        Log.d(TAG, "url:" + url);
        mRepository.getImageForView(mContext, url, imageView, maxWidth, maxHeight, 0, 0,
                new RequestCallback() {
                    @Override
                    public void onResult(boolean isOk, String response) {
                        if(!isOk){
                            mView.notifyError(response);
                        }
                    }
                });
    }
}
