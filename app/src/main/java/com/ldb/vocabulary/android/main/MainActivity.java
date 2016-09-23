package com.ldb.vocabulary.android.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.category.CategoryFragment;
import com.ldb.vocabulary.android.category.CategoryPresenter;
import com.ldb.vocabulary.android.collection.CollectionFragment;
import com.ldb.vocabulary.android.data.Injection;
import com.ldb.vocabulary.android.mine.MineFragment;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mCategoryButton;
    private Button mCollectionButton;
    private Button mMineButton;

    private CategoryFragment mCategoryFragment;
    private CollectionFragment mCollectionFragment;
    private MineFragment mMineFragment;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();

        mCategoryButton = (Button) findViewById(R.id.main_category_button);
        mCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCategoryFragment == null){
                    mCategoryFragment = CategoryFragment.newInstance();
                    new CategoryPresenter(MainActivity.this,
                            Injection.provideVocabularyRepository(MainActivity.this), mCategoryFragment);
                }
                switchFragment(mCategoryFragment);
                mCurrentFragment = mCategoryFragment;
            }
        });

        mCollectionButton = (Button) findViewById(R.id.main_collection_button);
        mCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCollectionFragment == null){
                    mCollectionFragment = CollectionFragment.newInstance();
                }
                switchFragment(mCollectionFragment);
                mCurrentFragment = mCollectionFragment;
            }
        });

        mMineButton = (Button) findViewById(R.id.main_mine_button);
        mMineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMineFragment == null){
                    mMineFragment = MineFragment.newInstance();
                }
                switchFragment(mMineFragment);
                mCurrentFragment = mMineFragment;
            }
        });

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void init(){
        setDefaultFragment();
    }

    private void setDefaultFragment(){
        if(mCategoryFragment == null){
            mCategoryFragment = CategoryFragment.newInstance();
            new CategoryPresenter(MainActivity.this,
                    Injection.provideVocabularyRepository(MainActivity.this), mCategoryFragment);
        }
        switchFragment(mCategoryFragment);
        mCurrentFragment = mCategoryFragment;
    }

    private void switchFragment(Fragment oldFragment, Fragment newFragment){
        if(newFragment == null){
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(!newFragment.isAdded()){
            transaction.add(R.id.container, newFragment);
        }
        if(oldFragment != null) {
            transaction.hide(oldFragment);
        }
        transaction.show(newFragment);
        transaction.commit();
    }

    private void switchFragment(Fragment newFragment){
        switchFragment(mCurrentFragment, newFragment);
    }

}
