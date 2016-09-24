package com.ldb.vocabulary.android.vocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.data.Category;
import com.squareup.picasso.Picasso;

/**
 * Created by lsp on 2016/9/24.
 */
public class VocabularyFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";
    private Category mCategory;
    private RecyclerView mVocabularyView;
    private ImageView mCategoryImageView;

    public static VocabularyFragment newInstance(Category category){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_CATEGORY, category);

        VocabularyFragment fragment = new VocabularyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arg = getArguments();
        if(arg != null){
            mCategory = arg.getParcelable(ARG_CATEGORY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vocabulary_fragment, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);

        mCategoryImageView = (ImageView) view.findViewById(R.id.category_image);

        if(mCategory != null){
            collapsingToolbarLayout.setTitle(mCategory.getName());
            Picasso.with(getActivity()).load(mCategory.getImage()).into(mCategoryImageView);
        }


        mVocabularyView = (RecyclerView) view.findViewById(R.id.vocabulary_list);
        mVocabularyView.setHasFixedSize(true);

//        // 导致Toolbar的scroll失效
//        mVocabularyView.setNestedScrollingEnabled(false);

        mVocabularyView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mVocabularyView.setAdapter(new VocabularyAdapter());

        return view;
    }

    private class VocabularyHolder extends RecyclerView.ViewHolder{

        public VocabularyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.vocabulary_item, parent, false));
        }
    }

    private class VocabularyAdapter extends RecyclerView.Adapter<VocabularyHolder>{

        @Override
        public VocabularyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VocabularyHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(VocabularyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }
}
