package com.ldb.vocabulary.android.register;

import com.ldb.vocabulary.android.base.BasePresenter;
import com.ldb.vocabulary.android.base.BaseView;

/**
 * Created by lsp on 2016/9/16.
 */
public class RegisterContract{

    interface View extends BaseView<Presenter> {
        void showError(String error);
        void showMessage(String message);
        void onRequestCheckCode(boolean isOk, String message);
        void onRegister(boolean isOk, String message);
    }

    interface Presenter extends BasePresenter{
        void getPhoneCheckCode(String phoneNumber);
        void register(String phoneNumber, String checkCode);
    }
}
