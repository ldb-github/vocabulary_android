package com.ldb.vocabulary.android.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.data.Account;
import com.ldb.vocabulary.android.register.RegisterActivity;

/**
 * Created by lsp on 2016/9/17.
 */
public class LoginFragment extends Fragment implements LoginContract.View{

    private static final String TAG = LoginFragment.class.getSimpleName();

    private LoginContract.Presenter mPresenter;

    private EditText mPhoneNumberEdit;
    private EditText mCheckCodeEdit;
    private Button mCheckCodeButton;
    private Button mLoginButton;
    private Button mRegisterButton;

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        mPhoneNumberEdit = (EditText) view.findViewById(R.id.phone_number_edit_text);
        mCheckCodeEdit = (EditText) view.findViewById(R.id.check_code_edit_text);
        mCheckCodeButton = (Button) view.findViewById(R.id.check_code_button);
        mCheckCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getPhoneCheckCode(mPhoneNumberEdit.getText().toString());
            }
        });

        mLoginButton = (Button) view.findViewById(R.id.login_login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login(mPhoneNumberEdit.getText().toString(),
                        mCheckCodeEdit.getText().toString());
            }
        });

        mRegisterButton = (Button) view.findViewById(R.id.login_register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(String error) {
        Snackbar.make(getView().findViewById(R.id.login_snackbar_decor),
                error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(getView().findViewById(R.id.login_snackbar_decor),
                message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRequest(boolean isOk, String message) {
        if(isOk){
            showMessage(message);
        }else{
            showError(message);
        }
    }

    @Override
    public void onLogin(boolean isOk, String message, Account account) {
        if(isOk){
            showMessage(message);
            Intent intent = new Intent();
            intent.putExtra("username", account.getUsername());
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
            // if you don't override onBackPressed() in the activity, then this is ok too.
//            getActivity().onBackPressed();
        }else{
            showError(message);
        }
    }
}
