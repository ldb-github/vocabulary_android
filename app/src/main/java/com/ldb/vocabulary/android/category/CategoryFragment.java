package com.ldb.vocabulary.android.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.data.Category;
import com.ldb.vocabulary.android.vocabulary.VocabularyActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsp on 2016/9/17.
 */
public class CategoryFragment extends Fragment implements CategoryContract.View{

    private static final String TAG = CategoryFragment.class.getSimpleName();

    private RecyclerView mCategoryView;
    private CategoryContract.Presenter mPresenter;
    private int mPage;
    private List<Category> mCategoryList = new ArrayList<>();

    public static CategoryFragment newInstance(){
        return new CategoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.category_fragment, container, false);

        mCategoryView = (RecyclerView) view.findViewById(R.id.category_recycle_view);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        mCategoryView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        mCategoryView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mCategoryView.setAdapter(new CategoryAdapter(mCategoryList));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_category, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.category_menu_refresh:
                mPage = 1;
                mPresenter.getCategoryList(mPage, null, null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateDataSet(List<Category> categoryList) {
        mCategoryList.addAll(categoryList);
        if(mCategoryView != null){
            mCategoryView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void notifyError(String error) {
        showError(error);
    }

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void showError(String error){
        Snackbar.make(getView().findViewById(R.id.category_snackbar_decor),
                error, Snackbar.LENGTH_SHORT).show();
    }

    private class CategoryHolder extends RecyclerView.ViewHolder{

        private Category mCategory;
        public ImageView picture;
        public TextView name;
        public CategoryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.category_item, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.tile_picture);
            name = (TextView) itemView.findViewById(R.id.tile_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = VocabularyActivity.newIntent(getActivity(), mCategory);
                    startActivity(intent);
                }
            });
        }
        public void setCategory(Category category){
            mCategory = category;
        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>{

        private List<Category> mCategoryList;

        public CategoryAdapter(List<Category> categoryList){
            mCategoryList = categoryList;
        }

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CategoryHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            Category category = mCategoryList.get(position);
            holder.setCategory(category);
            // 目前图片请求使用的是Volley，但是由于RequestQueue的创建需要Context，而Repository是不会
            // 与特定的Context关联，也就无法为RequestQueue提供固定的Context，这样RequestQueue就只好是局部变量，
            // 通过参数为它提供Context，最终导致无法使用ImageLoader的Cache功能。又不想把Volley直接
            // 搬到View层，所以决定使用Picasso.
//            mPresenter.getImageForView(category.getImage(), holder.picture,
//                    holder.picture.getWidth(), holder.picture.getHeight());
            Picasso.with(getActivity()).load(category.getImage()).into(holder.picture);
            holder.name.setText(category.getName());
        }

        @Override
        public int getItemCount() {
            return mCategoryList.size();
        }
    }
}

