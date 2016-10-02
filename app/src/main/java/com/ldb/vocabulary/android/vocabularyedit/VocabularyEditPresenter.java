package com.ldb.vocabulary.android.vocabularyedit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ldb.vocabulary.android.data.CommunicationContract;
import com.ldb.vocabulary.android.data.PostParam;
import com.ldb.vocabulary.android.data.Repository;
import com.ldb.vocabulary.android.data.RequestCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsp on 2016/10/2.
 */
public class VocabularyEditPresenter implements VocabularyEditContract.Presenter{
    private static final String TAG = VocabularyEditPresenter.class.getSimpleName();

    private Context mContext;
    private Repository mRepository;
    private VocabularyEditContract.View mView;

    public VocabularyEditPresenter(@NonNull Context context, Repository repository,
                                   VocabularyEditContract.View view){
        mContext = context;
        mRepository = repository;
        mView = view;

        mView.setPresenter(this);
    }
    @Override
    public void uploadVocabulary(String vocabularyName, String imagePath, String categoryId, int index) {
        List<PostParam> postParams = new ArrayList<>();
        // 词汇名称
        PostParam param = new PostParam();
        param.setFile(false);
        param.setValue(vocabularyName);
        param.setFieldName(CommunicationContract.KEY_VOCABULARY_NAME);
        postParams.add(param);
        // 词汇图片
        if(imagePath != null && !imagePath.trim().isEmpty()) {
            param = new PostParam();
            param.setFile(true);
            File image = new File(imagePath);
            param.setData(image);
            param.setFileName(image.getName());
            // TODO 这里需要更好的方法来自动判断文件的mimeType.
            param.setMimeType("image/jpeg");
            param.setFieldName(CommunicationContract.KEY_VOCABULARY_IMAGE);
            postParams.add(param);
        }
        // 词汇类别id
        param = new PostParam();
        param.setFile(false);
        param.setValue(categoryId);
        param.setFieldName(CommunicationContract.KEY_CATEGORY_ID);
        postParams.add(param);
        // 词汇类别index
        param = new PostParam();
        param.setFile(false);
        param.setValue(index + "");
        param.setFieldName(CommunicationContract.KEY_CATEGORY_INDEX);
        postParams.add(param);

        mRepository.postVocabulary(mContext, postParams, new RequestCallback() {
            @Override
            public void onResult(boolean isOk, String response) {
                mView.onUploadVocabulary(isOk, response);
            }
        });
    }

    @Override
    public void start() {

    }
}
