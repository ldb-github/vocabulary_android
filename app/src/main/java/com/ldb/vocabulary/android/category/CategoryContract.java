package com.ldb.vocabulary.android.category;

import android.widget.ImageView;

import com.ldb.vocabulary.android.base.BasePresenter;
import com.ldb.vocabulary.android.base.BaseView;
import com.ldb.vocabulary.android.data.Category;

import java.util.List;

/**
 * Created by lsp on 2016/9/22.
 */
public interface CategoryContract {

    interface View extends BaseView<Presenter>{
        void updateDataSet(List<Category> categoryList);
        void notifyError(String error);
    }

    interface Presenter extends BasePresenter{


        void getCategoryList(int page, String sort, String sortType);

        void getImageForView(String url, ImageView imageView, int maxWidth, int maxHeight);

    }
}
