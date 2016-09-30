package com.ldb.vocabulary.android.categoryedit;

import com.ldb.vocabulary.android.base.BasePresenter;
import com.ldb.vocabulary.android.base.BaseView;
import com.ldb.vocabulary.android.data.Category;
import com.ldb.vocabulary.android.data.PostParam;

import java.util.List;

/**
 * Created by lsp on 2016/9/29.
 */
public interface CategoryEditContract {

    interface View extends BaseView<Presenter> {
        /**
         * 新增词汇类别回调
         * @param isOk
         * @param message
         */
        void onUploadCategory(boolean isOk, String message);
    }

    interface Presenter extends BasePresenter {
        /**
         * 新增词汇类别
         * @param categoryName
         * @param imagePath
         */
        void uploadCategory(String categoryName, String imagePath);
    }
}
