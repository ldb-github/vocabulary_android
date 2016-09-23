package com.ldb.vocabulary.android.collection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.mine.MineFragment;

/**
 * Created by lsp on 2016/9/17.
 */
public class CollectionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collection_fragment, container, false);

        return view;
    }

    public static CollectionFragment newInstance(){
        return new CollectionFragment();
    }
}
