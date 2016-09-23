package com.ldb.vocabulary.android.login;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.data.Injection;

public class LoginActivity extends AppCompatActivity {

    private LoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginFragment fragment =
                (LoginFragment) fragmentManager.findFragmentById(R.id.login_container);
        if (fragment == null) {
            fragment = LoginFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.login_container, fragment)
                    .commit();
        }

        mPresenter = new LoginPresenter(this,
                Injection.provideVocabularyRepository(this), fragment);
    }
}
