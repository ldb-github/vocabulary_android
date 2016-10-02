package com.ldb.vocabulary.android.categoryedit;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.data.Injection;
import com.ldb.vocabulary.android.login.LoginFragment;

public class CategoryEditActivity extends AppCompatActivity {

    private static final String TAG = CategoryEditActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_edit_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        CategoryEditFragment fragment =
                (CategoryEditFragment) fragmentManager.findFragmentById(R.id.login_container);
        if (fragment == null) {
            fragment = CategoryEditFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }

        new CategoryEditPresenter(this, Injection.provideVocabularyRepository(this), fragment);
    }
}
