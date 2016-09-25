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

        /**
         * 获取分类列表
         * @param page 请求页码，从1开始
         * @param sort 排序方式：f收藏量，w词汇量；如果多种混合，则以逗号间隔
         * @param sortType 排序类型：a升序，d降序；如果多种混合，则以逗号间隔，并且与sort一一对应
         */
        void getCategoryList(int page, String sort, String sortType);

        /**
         * 为ImageView获取图片
         * @param url 图片地址
         * @param imageView 展示图片的View
         * @param maxWidth 最大宽度
         * @param maxHeight 最大高度
         */
        void getImageForView(String url, ImageView imageView, int maxWidth, int maxHeight);

    }
}
