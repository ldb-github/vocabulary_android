package com.ldb.vocabulary.android.categoryedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ldb.vocabulary.android.R;

public class CategoryEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new CategoryEditFragment()).commit();
    }
}
