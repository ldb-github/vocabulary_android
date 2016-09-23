package com.ldb.vocabulary.android.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ldb.vocabulary.android.data.local.LocalDataSourceSqlite;
import com.ldb.vocabulary.android.data.remote.RemoteDataSourceVolley;

/**
 * Created by lsp on 2016/9/17.
 */
public class Injection {

    public static Repository provideVocabularyRepository(@NonNull Context context){
        if(context == null){
            throw new NullPointerException();
        }
        return Repository.getInstance(
                LocalDataSourceSqlite.getInstance(context),
                RemoteDataSourceVolley.getInstance());
    }
}
