package com.ldb.vocabulary.android.vocabulary;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ldb.vocabulary.android.data.Repository;
import com.ldb.vocabulary.android.data.RequestCallback;
import com.ldb.vocabulary.android.data.Vocabulary;

import java.util.List;

/**
 * Created by lsp on 2016/9/25.
 */
public class VocabularyPresenter implements VocabularyContract.Presenter{

    private Context mContext;
    private Repository mRepository;
    private VocabularyContract.View mView;

    public VocabularyPresenter(@NonNull Context context, Repository repository,
                               VocabularyContract.View view){
        mContext = context;
        mRepository = repository;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void getVocabularyList(String categoryId, int categoryIndex, int page) {
        mRepository.getVocabularyList(mContext, categoryId, categoryIndex,
                page, null, new RequestCallback.RequestVocabularyListCallback() {
            @Override
            public void onSuccess(String message, List<Vocabulary> vocabularyList) {
                mView.updateDataSet(vocabularyList);
            }

            @Override
            public void onError(String error) {
                mView.notifyError(error);
            }
        });
    }

    @Override
    public void start() {

    }
}
