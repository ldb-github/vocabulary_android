package com.ldb.vocabulary.android.register;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.data.Injection;
import com.ldb.vocabulary.android.login.LoginContract;
import com.ldb.vocabulary.android.login.LoginFragment;
import com.ldb.vocabulary.android.login.LoginPresenter;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private RegisterContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Log.d(TAG, "onCreate");
        FragmentManager fragmentManager = getSupportFragmentManager();
        RegisterFragment fragment =
                (RegisterFragment) fragmentManager.findFragmentById(R.id.register_container);
        if (fragment == null) {
            fragment = RegisterFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.register_container, fragment)
                    .commit();
        }

        mPresenter = new RegisterPresenter(this,
                Injection.provideVocabularyRepository(this), fragment);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
