package com.ldb.vocabulary.android.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.data.Account;
import com.ldb.vocabulary.android.data.Repository;
import com.ldb.vocabulary.android.data.RequestCallback;

/**
 * Created by lsp on 2016/9/17.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private Context mContext;
    private LoginContract.View mView;
    private Repository mRepository;

    public LoginPresenter(@NonNull Context context, Repository repository,
                          LoginContract.View view) {
        mContext = context;
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void getPhoneCheckCode(String phoneNumber) {
        mRepository.requestPhoneCheckCode(mContext, phoneNumber, "lg",
                new RequestCallback.RequestCheckCodeCallback() {
            @Override
            public void onSuccess(String message, String checkCode) {
                // 可以保存验证码，登录提交时可作检查

                mView.onRequest(true,
                        mContext.getResources().getString(R.string.request_check_code_message));
            }

            @Override
            public void onError(String error) {
                mView.onRequest(false, (error != null ? error
                        : mContext.getResources().getString(R.string.request_check_code_error)));
            }
        });
    }

    @Override
    public void login(String phoneNumber, String checkCode) {
        mRepository.login(mContext, phoneNumber, checkCode,
                new RequestCallback.RequestLoginCallback() {
            @Override
            public void onSuccess(String message, Account account) {
                // TODO 对帐户信息进行处理 a、保存到sqlite db。b、保存到preference

                mView.onLogin(true, message, account);
            }

            @Override
            public void onError(String error) {
                mView.onLogin(false, (error != null ? error
                        : mContext.getResources().getString(R.string.request_login_error)), null);
            }
        });
    }

    @Override
    public void start() {

    }
}
