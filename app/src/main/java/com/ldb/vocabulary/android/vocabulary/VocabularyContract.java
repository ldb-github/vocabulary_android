package com.ldb.vocabulary.android.vocabulary;

import com.ldb.vocabulary.android.base.BasePresenter;
import com.ldb.vocabulary.android.base.BaseView;
import com.ldb.vocabulary.android.data.Vocabulary;

import java.util.List;

/**
 * Created by lsp on 2016/9/25.
 */
public class VocabularyContract {

    interface View extends BaseView<Presenter>{
        void updateDataSet(List<Vocabulary> vocabularyList);
        void notifyError(String error);
    }

    interface Presenter extends BasePresenter{

        void getVocabularyList(String categoryId, int page);
    }
}
