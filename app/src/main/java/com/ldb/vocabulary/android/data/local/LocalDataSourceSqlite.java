package com.ldb.vocabulary.android.data.local;

import android.content.Context;

import com.ldb.vocabulary.android.data.DataSource;

/**
 * Created by lsp on 2016/9/17.
 */
public class LocalDataSourceSqlite implements LocalDataSource {

    private static LocalDataSourceSqlite INSTANCE = null;

    private Context mContext;

    private LocalDataSourceSqlite(Context context){

    }

    public static LocalDataSourceSqlite getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new LocalDataSourceSqlite(context);
        }
        return INSTANCE;
    }

}
