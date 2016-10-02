package com.ldb.vocabulary.android.vocabulary;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.data.Category;
import com.ldb.vocabulary.android.data.Injection;
import com.ldb.vocabulary.android.data.Vocabulary;
import com.squareup.picasso.Picasso;

public class VocabularyActivity extends AppCompatActivity {

    private static final String EXTRA_CATEGORY = "category";

    private Category mCategory;
    private TextView mCategoryNameTextView;
    private ImageView mCategoryImageView;
    private RecyclerView mVocabularyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabulary_activity);

        Intent intent = getIntent();
        if(intent != null){
            mCategory = intent.getParcelableExtra(EXTRA_CATEGORY);
        }

        VocabularyFragment fragment = (VocabularyFragment)
                getSupportFragmentManager().findFragmentById(R.id.container);
        if(fragment == null){
            fragment = VocabularyFragment.newInstance(mCategory);
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        }

        new VocabularyPresenter(this, Injection.provideVocabularyRepository(this), fragment);

    }

    public static Intent newIntent(Context context, Category category){
        Intent intent = new Intent(context, VocabularyActivity.class);
        intent.putExtra(EXTRA_CATEGORY, category);
        return intent;
    }

}
