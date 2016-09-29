package com.ldb.vocabulary.android.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.data.local.LocalDataSource;
import com.ldb.vocabulary.android.data.remote.RemoteDataSource;
import com.ldb.vocabulary.android.utils.DateUtils;
import com.ldb.vocabulary.android.utils.DeviceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsp on 2016/9/17.
 */
public class Repository {

    private static final int MSG_CHECK_CODE = 0;
    private static final int MSG_REGISTER = 1;
    private static final int MSG_LOGIN = 2;

    public static Repository INSTANCE = null;

    private LocalDataSource mLocalDataSource;
    private RemoteDataSource mRemoteDataSource;

    private Repository(LocalDataSource localDataSource,
                       RemoteDataSource remoteDataSource){
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static Repository getInstance(LocalDataSource localDataSource,
                                         RemoteDataSource remoteDataSource){
        if(INSTANCE == null){
            INSTANCE = new Repository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * 请求短信验证码
     * @param context
     * @param phoneNumber
     * @param type
     * @param callback
     */
    public void requestPhoneCheckCode(@NonNull final Context context, String phoneNumber, String type,
                                      final RequestCallback.RequestCheckCodeCallback callback){
        if (!DeviceUtil.isNetworkConnected(context)) {
            callback.onError(context.getResources().getString(R.string.network_not_connected));
        }else {
            mRemoteDataSource.requestPhoneCheckCode(context, phoneNumber, type, new RequestCallback() {
                @Override
                public void onResult(boolean isOk, String response) {
                    if(isOk){
                        try {
                            JSONObject result = new JSONObject(response);
                            int code = result.getInt("code");
                            StringBuilder message = new StringBuilder(result.getString("message"));
                            if(code == 400 && result.has("error")){
                                JSONObject errorJson = result.getJSONObject("error");
                                if(errorJson.has("phonenumber")){
                                    message.append( ", " + errorJson.get("phonenumber"));
                                }
                            }
                            if(code == 0){
                                callback.onSuccess(message.toString(), result.getString("checkcode"));
                            }else{
                                callback.onError(message.toString());
                            }
                        } catch (JSONException e) {
                            callback.onError(
                                    context.getResources().getString(R.string.parse_data_error));
                        }

                    }else {
                        callback.onError(context.getResources().getString(R.string.request_error));
                    }
                }
            });
        }
    }

    /**
     * 注册
     * @param context
     * @param phoneNumber
     * @param checkCode
     * @param callback
     */
    public void register(@NonNull final Context context, String phoneNumber,
                         String checkCode, final RequestCallback callback){
        if (!DeviceUtil.isNetworkConnected(context)) {
            callback.onResult(false,
                    context.getResources().getString(R.string.network_not_connected));
        }else {
            mRemoteDataSource.register(context, phoneNumber, checkCode, new RequestCallback() {
                @Override
                public void onResult(boolean isOk, String response) {
                    if(isOk){
                        try {
                            JSONObject result = new JSONObject(response);
                            int code = result.getInt("code");
                            StringBuilder message = new StringBuilder(result.getString("message"));
                            //注册信息格式错误
                            if(code == 400 && result.has("error")){
                                JSONObject errorJson = result.getJSONObject("error");
                                if(errorJson.has("username")){
                                    message.append( ", " + errorJson.get("username"));
                                }
                                if(errorJson.has("password")){
                                    message.append( ", " + errorJson.get("password"));
                                }
                                if(errorJson.has("confirmpwd")){
                                    message.append( ", " + errorJson.get("confirmpwd"));
                                }
                                if(errorJson.has("email")){
                                    message.append( ", " + errorJson.get("email"));
                                }
                            }
                            callback.onResult(code == 0, message.toString());
                        } catch (JSONException e) {
                            callback.onResult(false,
                                    context.getResources().getString(R.string.parse_data_error));
                        }

                    }else {
                        callback.onResult(false,
                                context.getResources().getString(R.string.request_error));
                    }
                }
            });
        }
    }

    /**
     * 登录
     * @param context
     * @param phoneNumber
     * @param checkCode
     * @param callback
     */
    public void login(@NonNull final Context context, String phoneNumber,
                      String checkCode, final RequestCallback.RequestLoginCallback callback){
        if (!DeviceUtil.isNetworkConnected(context)) {
            callback.onError(context.getResources().getString(R.string.network_not_connected));
        }else {
            mRemoteDataSource.login(context, phoneNumber, checkCode, new RequestCallback() {
                @Override
                public void onResult(boolean isOk, String response) {
                    if(isOk){
                        try {
                            JSONObject result = new JSONObject(response);
                            int code = result.getInt("code");
                            StringBuilder message = new StringBuilder(result.getString("message"));
                            Account account = null;
                            if(code == 0){
                                JSONObject accountJson = result.getJSONObject("account");
                                account = new Account();
                                if(accountJson.has("username")){
                                    // TODO 用户名
                                    account.setUsername(accountJson.getString("username"));
                                }
                                if(accountJson.has("phonenumber")){
                                    // TODO　手机号
                                    account.setPhoneNumber(accountJson.getString("phonenumber"));
                                }
                                if(accountJson.has("email")){
                                    // TODO　邮箱
                                    account.setEmail(accountJson.getString("email"));
                                }
                                if(accountJson.has("token")){
                                    // TODO　令牌
                                    account.setToken(accountJson.getString("token"));
                                }
                                if(accountJson.has("state")){
                                    // TODO　状态
                                    account.setState(accountJson.getString("state"));
                                }
                                if(accountJson.has("registertime")){
                                    // TODO　注册时间
                                    try {
                                        account.setRegisterTime(DateUtils.parseDateTime(
                                                accountJson.getString("registertime")));
                                    } catch (ParseException e) {
                                        message.append(context.getResources()
                                                .getString(R.string.parse_register_date_error));
                                    }
                                }
                            }
                            if(code == 0){
                                callback.onSuccess(message.toString(), account);
                            }else{
                                callback.onError(message.toString());
                            }
                        } catch (JSONException e) {
                            callback.onError(
                                    context.getResources().getString(R.string.parse_data_error));
                        }

                    }else {
                        callback.onError(context.getResources().getString(R.string.request_error));
                    }
                }
            });
        }
    }

    /**
     * 获取词汇列表
     * @param context
     * @param page
     * @param sort
     * @param sortType
     * @param callback
     */
    public void getCategoryList(@NonNull final Context context, int page, String sort, String sortType,
                                final RequestCallback.RequestCategoryListCallback callback){
        if (!DeviceUtil.isNetworkConnected(context)) {
            callback.onError(context.getResources().getString(R.string.network_not_connected));
        }else {
            mRemoteDataSource.getCategoryList(context, page, sort, sortType, new RequestCallback() {
                @Override
                public void onResult(boolean isOk, String response) {
                    if(isOk){
                        try {
                            JSONObject result = new JSONObject(response);
                            int code = result.getInt(CommunicationContract.KEY_CODE);
                            StringBuilder message = new StringBuilder(
                                    result.getString(CommunicationContract.KEY_MESSAGE));
                            List<Category> categoryList = new ArrayList<Category>();
                            if(code == 0){
                                JSONArray categoryArray = result.getJSONArray(
                                        CommunicationContract.KEY_CATEGORY_LIST);
                                JSONObject jsonObject = new JSONObject();

                                for(int i = 0; i < categoryArray.length(); i++){
                                    jsonObject = categoryArray.getJSONObject(i);
                                    Category category = new Category();
                                    category.setId(jsonObject.getString(
                                            CommunicationContract.KEY_CATEGORY_ID));
                                    category.setName(jsonObject.getString(
                                            CommunicationContract.KEY_CATEGORY_NAME));
                                    category.setImage(jsonObject.getString(
                                            CommunicationContract.KEY_CATEGORY_IMAGE));
                                    category.setFavoriteCount(jsonObject.getInt(
                                            CommunicationContract.KEY_CATEGORY_FAVORITE_COUNT));
                                    category.setWordCount(jsonObject.getInt(
                                            CommunicationContract.KEY_CATEGORY_WORD_COUNT));
                                    categoryList.add(category);
                                }
                            }
                            if(code == 0){
                                callback.onSuccess(message.toString(), categoryList);
                            }else{
                                callback.onError(message.toString());
                            }
                        } catch (JSONException e) {
                            callback.onError(
                                    context.getResources().getString(R.string.parse_data_error));
                        }

                    }else {
                        callback.onError(context.getResources().getString(R.string.request_error));
                    }
                }
            });
        }
    }

    public void getImageForView(@NonNull final Context context, String url, ImageView imageView, int maxWidth,
                                int maxHeight, int defaultImage, int errorImage,
                                final RequestCallback callback){
        if (!DeviceUtil.isNetworkConnected(context)) {
            callback.onResult(false,
                    context.getResources().getString(R.string.network_not_connected));
        }else {
            mRemoteDataSource.getImageForView(context, url, imageView, maxWidth, maxHeight, defaultImage, errorImage);
        }

    }

    public void getVocabularyList(@NonNull final Context context, String categoryId, int page,
                                  final RequestCallback.RequestVocabularyListCallback callback){
        if (!DeviceUtil.isNetworkConnected(context)) {
            callback.onError(context.getResources().getString(R.string.network_not_connected));
        }else {
            mRemoteDataSource.getVocabularyList(context, categoryId, page, new RequestCallback() {
                @Override
                public void onResult(boolean isOk, String response) {
                    if(isOk){
                        try {
                            JSONObject result = new JSONObject(response);
                            int code = result.getInt(CommunicationContract.KEY_CODE);
                            StringBuilder message = new StringBuilder(
                                    result.getString(CommunicationContract.KEY_MESSAGE));
                            List<Vocabulary> vocabularyList = new ArrayList<Vocabulary>();
                            if(code == 0){
                                if(result.has(CommunicationContract.KEY_VOCABULARY_LIST)) {
                                    JSONArray categoryArray = result.getJSONArray(
                                            CommunicationContract.KEY_VOCABULARY_LIST);
                                    JSONObject jsonObject = new JSONObject();

                                    for (int i = 0; i < categoryArray.length(); i++) {
                                        jsonObject = categoryArray.getJSONObject(i);
                                        Vocabulary vocabulary = new Vocabulary();
                                        vocabulary.setId(jsonObject.getString(
                                                CommunicationContract.KEY_VOCABULARY_ID));
                                        vocabulary.setName(jsonObject.getString(
                                                CommunicationContract.KEY_VOCABULARY_NAME));
                                        vocabulary.setImage(jsonObject.getString(
                                                CommunicationContract.KEY_VOCABULARY_IMAGE));
                                        ;
                                        vocabularyList.add(vocabulary);
                                    }
                                }
                            }
                            if(code == 0){
                                callback.onSuccess(message.toString(), vocabularyList);
                            }else{
                                callback.onError(message.toString());
                            }
                        } catch (JSONException e) {
                            callback.onError(
                                    context.getResources().getString(R.string.parse_data_error));
                        }

                    }else {
                        callback.onError(context.getResources().getString(R.string.request_error));
                    }
                }
            });
        }
    }

    public void postCategory(@NonNull final Context context, List<PostParam> category,
                             final RequestCallback callback){
        if (!DeviceUtil.isNetworkConnected(context)) {
            callback.onResult(false, context.getResources().getString(R.string.network_not_connected));
        }else {
            mRemoteDataSource.postCategory(context, category, new RequestCallback() {
                @Override
                public void onResult(boolean isOk, String response) {
                    if(isOk){
                        // TODO 解析数据

                    }else{
                        callback.onResult(false, response);
                    }
                }
            });
        }
    }
}
