package com.ldb.vocabulary.android.vocabularyedit;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.categoryedit.CategoryEditFragment;
import com.ldb.vocabulary.android.data.Injection;
import com.ldb.vocabulary.android.vocabulary.VocabularyActivity;

public class VocabularyEditActivity extends AppCompatActivity {

    private static final String EXTRA_CATEGORY_ID = "category_id";
    private static final String EXTRA_CATEGORY_INDEX = "category_index";

    private String mCategoryId;
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocabulary_edit_activity);

        Intent intent = getIntent();
        if(intent != null){
            mCategoryId = intent.getStringExtra(EXTRA_CATEGORY_ID);
            mIndex = intent.getIntExtra(EXTRA_CATEGORY_INDEX, 0);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        VocabularyEditFragment fragment =
                (VocabularyEditFragment) fragmentManager.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = VocabularyEditFragment.newInstance(mCategoryId, mIndex);
            fragmentManager
                    .beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
        new VocabularyEditPresenter(this, Injection.provideVocabularyRepository(this), fragment);
    }

    public static Intent newIntent(Context context, String categoryId, int index){
        Intent intent = new Intent(context, VocabularyEditActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        intent.putExtra(EXTRA_CATEGORY_INDEX, index);
        return intent;
    }
}
