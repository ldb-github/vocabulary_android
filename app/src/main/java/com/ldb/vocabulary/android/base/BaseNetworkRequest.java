package com.ldb.vocabulary.android.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.ldb.vocabulary.android.data.PostParam;
import com.ldb.vocabulary.android.data.RequestCallback;

import java.util.List;

/**
 * Created by lsp on 2016/9/22.
 */
public interface BaseNetworkRequest {

    /**
     * 字符类型请求
     * @param context
     * @param url
     * @param callback
     */
    void stringRequest(@NonNull Context context, String url, RequestCallback callback);

    /**
     * 图片类型请求
     * @param context
     * @param url
     * @param imageView
     * @param maxWidth
     * @param maxHeight
     * @param defaultImage
     * @param errorImage
     */
    void imageRequest(@NonNull Context context, String url, ImageView imageView, int maxWidth, int maxHeight,
                      int defaultImage, int errorImage);


    void postRequest(@NonNull Context context, String url, List<PostParam> postParams, RequestCallback callback);
}
