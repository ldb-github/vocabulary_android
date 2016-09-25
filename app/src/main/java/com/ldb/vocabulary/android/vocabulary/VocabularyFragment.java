package com.ldb.vocabulary.android.vocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.category.CategoryContract;
import com.ldb.vocabulary.android.data.Category;
import com.ldb.vocabulary.android.data.Vocabulary;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsp on 2016/9/24.
 */
public class VocabularyFragment extends Fragment implements VocabularyContract.View {

    private static final String TAG = VocabularyFragment.class.getSimpleName();

    private static final String ARG_CATEGORY = "category";

    private VocabularyContract.Presenter mPresenter;
    private Category mCategory;
    private RecyclerView mVocabularyView;
    private List<Vocabulary> mVocabularyList = new ArrayList<>();
    private int mPage;
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

        setHasOptionsMenu(true);

        Bundle arg = getArguments();
        if(arg != null){
            mCategory = arg.getParcelable(ARG_CATEGORY);
        }
        mPage = 1;
        if(mCategory != null){
            mPresenter.getVocabularyList(mCategory.getId(), mPage);
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
        mVocabularyView.setAdapter(new VocabularyAdapter(mVocabularyList));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void updateDataSet(List<Vocabulary> vocabularyList) {
        mVocabularyList.addAll(vocabularyList);
        if(mVocabularyView != null) {
            mVocabularyView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void notifyError(String error) {
        showError(error);
    }

    @Override
    public void setPresenter(VocabularyContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showError(String error){
        Snackbar.make(getView().findViewById(R.id.coordinator),
                error, Snackbar.LENGTH_SHORT).show();
    }

    private class VocabularyHolder extends RecyclerView.ViewHolder{

        private Vocabulary mVocabulary;

        private ImageView mVocabularyImage;
        private TextView mVocabularyName;

        public VocabularyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.vocabulary_item, parent, false));
            mVocabularyImage = (ImageView) itemView.findViewById(R.id.vocabulary_image);
            mVocabularyName = (TextView) itemView.findViewById(R.id.vocabulary_name);
        }

        public void bindItem(Vocabulary vocabulary){
            mVocabulary = vocabulary;
            mVocabularyName.setText(mVocabulary.getName());
            if(mVocabulary.getImage() != null && !mVocabulary.getImage().trim().isEmpty()) {
                Picasso.with(getActivity()).load(mVocabulary.getImage()).into(mVocabularyImage);
            }
        }
    }

    private class VocabularyAdapter extends RecyclerView.Adapter<VocabularyHolder>{

        private List<Vocabulary> mVocabularyList;

        public VocabularyAdapter(List<Vocabulary> vocabularyList){
            mVocabularyList = vocabularyList;
        }

        @Override
        public VocabularyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VocabularyHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(VocabularyHolder holder, int position) {
            holder.bindItem(mVocabularyList.get(position));
        }

        @Override
        public int getItemCount() {
            return mVocabularyList.size();
        }
    }
}
