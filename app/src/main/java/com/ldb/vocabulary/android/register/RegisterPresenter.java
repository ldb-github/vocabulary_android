package com.ldb.vocabulary.android.register;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.data.Repository;
import com.ldb.vocabulary.android.data.RequestCallback;

/**
 * Created by lsp on 2016/9/18.
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    private Context mContext;
    private RegisterContract.View mView;
    private Repository mRepository;

    public RegisterPresenter(@NonNull Context context, Repository repository,
                             RegisterContract.View view) {
        mContext = context;
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getPhoneCheckCode(String phoneNumber) {
        mRepository.requestPhoneCheckCode(mContext, phoneNumber, "re",
                new RequestCallback.RequestCheckCodeCallback() {
            @Override
            public void onSuccess(String message, String checkCode) {
                // 可以保存验证码，注册提交时可作检查

                mView.onRequestCheckCode(true,
                        mContext.getResources().getString(R.string.request_check_code_message));
            }

            @Override
            public void onError(String error) {
                mView.onRequestCheckCode(false, (error != null ? error
                        : mContext.getResources().getString(R.string.request_check_code_error)));
            }
        });
    }

    @Override
    public void register(String phoneNumber, String checkCode) {
        mRepository.register(mContext, phoneNumber, checkCode, new RequestCallback() {
            @Override
            public void onResult(boolean isOk, String response) {
                mView.onRegister(isOk, response);
            }
        });

    }

    @Override
    public void start() {

    }
}
