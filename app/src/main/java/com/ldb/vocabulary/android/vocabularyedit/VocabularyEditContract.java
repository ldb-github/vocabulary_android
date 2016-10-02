package com.ldb.vocabulary.android.vocabularyedit;

import com.ldb.vocabulary.android.base.BasePresenter;
import com.ldb.vocabulary.android.base.BaseView;

/**
 * Created by lsp on 2016/10/2.
 */
public class VocabularyEditContract {
    interface View extends BaseView<Presenter> {
        /**
         * 新增词汇回调
         * @param isOk
         * @param message
         */
        void onUploadVocabulary(boolean isOk, String message);
    }

    interface Presenter extends BasePresenter {
        /**
         * 新增词汇
         * @param vocabularyName
         * @param imagePath
         * @param categoryId
         * @param index
         */
        void uploadVocabulary(String vocabularyName, String imagePath, String categoryId, int index);
    }
}
