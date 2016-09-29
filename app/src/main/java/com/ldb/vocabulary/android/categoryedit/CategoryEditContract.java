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
//        void uploadCategory(List<Category> categoryList);
//        void notifyError(String error);
    }

    interface Presenter extends BasePresenter {

        void uploadCategory(String categoryName, String imagePath);
    }
}
