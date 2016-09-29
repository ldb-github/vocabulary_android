package com.ldb.vocabulary.android.categoryedit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ldb.vocabulary.android.data.CommunicationContract;
import com.ldb.vocabulary.android.data.PostParam;
import com.ldb.vocabulary.android.data.Repository;
import com.ldb.vocabulary.android.data.RequestCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsp on 2016/9/29.
 */
public class CategoryEditPresenter implements CategoryEditContract.Presenter {

    private static final String TAG = CategoryEditPresenter.class.getSimpleName();

    private Context mContext;
    private Repository mRepository;
    private CategoryEditContract.View mView;

    public CategoryEditPresenter(@NonNull Context context, Repository repository,
                                 CategoryEditContract.View view){
        mContext = context;
        mRepository = repository;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void uploadCategory(String categoryName, String imagePath) {
        List<PostParam> postParams = new ArrayList<>();
        PostParam param = new PostParam();
        param.setFile(false);
        param.setValue(categoryName);
        param.setFieldName(CommunicationContract.KEY_CATEGORY_NAME);
        postParams.add(param);
        if(imagePath != null && !imagePath.trim().isEmpty()) {
            param = new PostParam();
            param.setFile(true);
            File image = new File(imagePath);
            param.setData(image);
            param.setFileName(image.getName());
            // TODO 这里需要更好的方法来自动判断文件的mimeType.
            param.setMimeType("image/jpeg");
            param.setFieldName(CommunicationContract.KEY_CATEGORY_IMAGE);
            postParams.add(param);
        }
        mRepository.postCategory(mContext, postParams, new RequestCallback() {
            @Override
            public void onResult(boolean isOk, String response) {
                if(isOk){
                    Log.d(TAG, "Success to upload");
                }else{
                    Log.d(TAG, "Fail to upload");
                }
            }
        });
    }

    @Override
    public void start() {
    }
}
