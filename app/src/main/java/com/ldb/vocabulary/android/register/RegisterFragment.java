package com.ldb.vocabulary.android.register;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.login.LoginActivity;

public class RegisterFragment extends Fragment implements RegisterContract.View {

    private static final String TAG = RegisterFragment.class.getSimpleName();

    private RegisterContract.Presenter mPresenter;

    private EditText mPhoneNumberEdit;
    private EditText mCheckCodeEdit;
    private Button mCheckCodeButton;
    private Button mRegisterButton;

    public RegisterFragment() {

    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        mPhoneNumberEdit = (EditText) view.findViewById(R.id.phone_number_edit_text);
        mCheckCodeEdit = (EditText) view.findViewById(R.id.check_code_edit_text);
        mCheckCodeButton = (Button) view.findViewById(R.id.check_code_button);
        mCheckCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getPhoneCheckCode(mPhoneNumberEdit.getText().toString());
            }
        });

        mRegisterButton = (Button) view.findViewById(R.id.register_register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mPresenter.register(mPhoneNumberEdit.getText().toString(),
                       mCheckCodeEdit.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError(String error) {
        Snackbar.make(getView().findViewById(R.id.register_snackbar_decor),
                error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(getView().findViewById(R.id.register_snackbar_decor),
                message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestCheckCode(boolean isOk, String message) {
        if(isOk){
            showMessage(message);
        }else{
            showError(message);
        }
    }

    @Override
    public void onRegister(boolean isOk, String message) {
        if(isOk){
            showMessage(message);
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            Log.d(TAG, "finish activity");
            getActivity().finish();
        }else{
            showError(message);
        }
    }
}
