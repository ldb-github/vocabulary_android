package com.ldb.vocabulary.android.login;

import com.ldb.vocabulary.android.base.BasePresenter;
import com.ldb.vocabulary.android.base.BaseView;
import com.ldb.vocabulary.android.data.Account;

/**
 * Created by lsp on 2016/9/17.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter>{
        void showError(String error);
        void showMessage(String message);
        void onRequest(boolean isOk, String message);
        void onLogin(boolean isOk, String message, Account account);
    }

    interface Presenter extends BasePresenter{
        void getPhoneCheckCode(String phoneNumber);
        void login(String phoneNumber, String checkCode);
    }
}
