package com.ldb.vocabulary.android.categoryedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.data.Injection;

public class CategoryEditActivity extends AppCompatActivity {

    private static final String TAG = CategoryEditActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_edit_activity);

        CategoryEditFragment fragment = new CategoryEditFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment).commit();

        new CategoryEditPresenter(this, Injection.provideVocabularyRepository(this), fragment);
    }
}
