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
                            int code = result.getInt(CommunicationContract.KEY_CODE);
                            StringBuilder message = new StringBuilder(
                                    result.getString(CommunicationContract.KEY_MESSAGE));
                            if(code == CommunicationContract.VALUE_CODE_PARAMSERROR &&
                                    result.has(CommunicationContract.KEY_ERROR)){
                                JSONObject errorJson = result.getJSONObject(CommunicationContract.KEY_ERROR);
                                if(errorJson.has(CommunicationContract.KEY_PHONENUMBER)){
                                    message.append( ", " + errorJson.get(CommunicationContract.KEY_PHONENUMBER));
                                }
                            }
                            if(code == CommunicationContract.VALUE_CODE_OK){
                                callback.onSuccess(message.toString(),
                                        result.getString(CommunicationContract.KEY_CHECKCODE));
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
                            int code = result.getInt(CommunicationContract.KEY_CODE);
                            StringBuilder message = new StringBuilder(
                                    result.getString(CommunicationContract.KEY_MESSAGE));
                            //注册信息格式错误
                            if(code == CommunicationContract.VALUE_CODE_PARAMSERROR
                                    && result.has(CommunicationContract.KEY_ERROR)){
                                JSONObject errorJson = result.getJSONObject(CommunicationContract.KEY_ERROR);
                                if(errorJson.has(CommunicationContract.KEY_USERNAME)){
                                    message.append( ", " + errorJson.get(CommunicationContract.KEY_USERNAME));
                                }
                                if(errorJson.has(CommunicationContract.KEY_PASSWORD)){
                                    message.append( ", " + errorJson.get(CommunicationContract.KEY_PASSWORD));
                                }
                                if(errorJson.has(CommunicationContract.KEY_CONFIRMPWD)){
                                    message.append( ", " + errorJson.get(CommunicationContract.KEY_CONFIRMPWD));
                                }
                                if(errorJson.has(CommunicationContract.KEY_EMAIL)){
                                    message.append( ", " + errorJson.get(CommunicationContract.KEY_EMAIL));
                                }
                            }
                            callback.onResult(
                                    code == CommunicationContract.VALUE_CODE_OK,
                                    message.toString());
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
                            int code = result.getInt(CommunicationContract.KEY_CODE);
                            StringBuilder message = new StringBuilder(result.getString(CommunicationContract.KEY_MESSAGE));
                            Account account = null;
                            if(code == CommunicationContract.VALUE_CODE_OK){
                                JSONObject accountJson = result.getJSONObject("account");
                                account = new Account();
                                if(accountJson.has(CommunicationContract.KEY_USERNAME)){
                                    // TODO 用户名
                                    account.setUsername(accountJson.getString(CommunicationContract.KEY_USERNAME));
                                }
                                if(accountJson.has(CommunicationContract.KEY_PHONENUMBER)){
                                    // TODO　手机号
                                    account.setPhoneNumber(accountJson.getString(CommunicationContract.KEY_PHONENUMBER));
                                }
                                if(accountJson.has(CommunicationContract.KEY_EMAIL)){
                                    // TODO　邮箱
                                    account.setEmail(accountJson.getString(CommunicationContract.KEY_EMAIL));
                                }
                                if(accountJson.has(CommunicationContract.KEY_TOKEN)){
                                    // TODO　令牌
                                    account.setToken(accountJson.getString(CommunicationContract.KEY_TOKEN));
                                }
                                if(accountJson.has(CommunicationContract.KEY_STATE)){
                                    // TODO　状态
                                    account.setState(accountJson.getString(CommunicationContract.KEY_STATE));
                                }
                                if(accountJson.has(CommunicationContract.KEY_REGISTER_TIME)){
                                    // TODO　注册时间
                                    try {
                                        account.setRegisterTime(DateUtils.parseDateTime(
                                                accountJson.getString(CommunicationContract.KEY_REGISTER_TIME)));
                                    } catch (ParseException e) {
                                        message.append(context.getResources()
                                                .getString(R.string.parse_register_date_error));
                                    }
                                }
                            }
                            if(code == CommunicationContract.VALUE_CODE_OK){
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
                            if(code == CommunicationContract.VALUE_CODE_OK){
                                if(result.has(CommunicationContract.KEY_CATEGORY_LIST)) {
                                    JSONArray categoryArray = result.getJSONArray(
                                            CommunicationContract.KEY_CATEGORY_LIST);
                                    JSONObject jsonObject = new JSONObject();
                                    for (int i = 0; i < categoryArray.length(); i++) {
                                        jsonObject = categoryArray.getJSONObject(i);
                                        Category category = new Category();
                                        category.setId(jsonObject.getString(
                                                CommunicationContract.KEY_CATEGORY_ID));
                                        category.setSubIndex(jsonObject.getInt(
                                                CommunicationContract.KEY_CATEGORY_INDEX));
                                        category.setName(jsonObject.getString(
                                                CommunicationContract.KEY_CATEGORY_NAME));
                                        if(jsonObject.has(CommunicationContract.KEY_CATEGORY_IMAGE)) {
                                            category.setImage(jsonObject.getString(
                                                    CommunicationContract.KEY_CATEGORY_IMAGE));
                                        }
                                        if(jsonObject.has(CommunicationContract.KEY_CATEGORY_IMAGE_REMOTE)) {
                                            category.setImageRemote(jsonObject.getString(
                                                    CommunicationContract.KEY_CATEGORY_IMAGE_REMOTE));
                                        }
                                        category.setFavoriteCount(jsonObject.getInt(
                                                CommunicationContract.KEY_CATEGORY_FAVORITE_COUNT));
                                        category.setWordCount(jsonObject.getInt(
                                                CommunicationContract.KEY_CATEGORY_WORD_COUNT));
                                        if(jsonObject.has(CommunicationContract.KEY_CATEGORY_LANGUAGE)) {
                                            category.setLanguage(jsonObject.getString(
                                                    CommunicationContract.KEY_CATEGORY_LANGUAGE));
                                        }
                                        if(jsonObject.has(CommunicationContract.KEY_CATEGORY_CREATER)) {
                                            category.setUsername(jsonObject.getString(
                                                    CommunicationContract.KEY_CATEGORY_CREATER));
                                        }
                                        try {
                                            category.setCreateTime(
                                                    DateUtils.parseDateTime(jsonObject.getString(
                                                            CommunicationContract.KEY_CATEGORY_CREATE_TIME)));
                                        } catch (ParseException e) {
                                            message.append(context.getResources()
                                                    .getString(R.string.parse_category_create_time_error));
                                        }
                                        categoryList.add(category);
                                    }
                                }
                            }
                            if(code == CommunicationContract.VALUE_CODE_OK){
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

    public void getVocabularyList(@NonNull final Context context, String categoryId, int categoryIndex, int page,
                                  String secondLan, final RequestCallback.RequestVocabularyListCallback callback){
        if (!DeviceUtil.isNetworkConnected(context)) {
            callback.onError(context.getResources().getString(R.string.network_not_connected));
        }else {
            mRemoteDataSource.getVocabularyList(context, categoryId, categoryIndex, page, secondLan, new RequestCallback() {
                @Override
                public void onResult(boolean isOk, String response) {
                    if(isOk){
                        try {
                            JSONObject result = new JSONObject(response);
                            int code = result.getInt(CommunicationContract.KEY_CODE);
                            StringBuilder message = new StringBuilder(
                                    result.getString(CommunicationContract.KEY_MESSAGE));
                            List<Vocabulary> vocabularyList = new ArrayList<Vocabulary>();
                            if(code == CommunicationContract.VALUE_CODE_OK){
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
                                        if(jsonObject.has(CommunicationContract.KEY_VOCABULARY_IMAGE)) {
                                            vocabulary.setImage(jsonObject.getString(
                                                    CommunicationContract.KEY_VOCABULARY_IMAGE));
                                        }
                                        ;
                                        vocabularyList.add(vocabulary);
                                    }
                                }
                            }
                            if(code == CommunicationContract.VALUE_CODE_OK){
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
                        try {
                            JSONObject result = new JSONObject(response);
                            int code = result.getInt(CommunicationContract.KEY_CODE);
                            String message = result.getString(CommunicationContract.KEY_MESSAGE);
                            if(code == CommunicationContract.VALUE_CODE_OK){
                                callback.onResult(true, message);
                            }else{
                                callback.onResult(false, message);
                            }
                        }catch (JSONException e){
                            callback.onResult(false,
                                    context.getResources().getString(R.string.parse_data_error));
                        }
                    }else{
                        callback.onResult(false, response);
                    }
                }
            });
        }
    }

    public void postVocabulary(@NonNull final Context context, List<PostParam> category,
                             final RequestCallback callback){
        if (!DeviceUtil.isNetworkConnected(context)) {
            callback.onResult(false, context.getResources().getString(R.string.network_not_connected));
        }else {
            mRemoteDataSource.postVocabulary(context, category, new RequestCallback() {
                @Override
                public void onResult(boolean isOk, String response) {
                    if(isOk){
                        try {
                            JSONObject result = new JSONObject(response);
                            int code = result.getInt(CommunicationContract.KEY_CODE);
                            String message = result.getString(CommunicationContract.KEY_MESSAGE);
                            if(code == CommunicationContract.VALUE_CODE_OK){
                                callback.onResult(true, message);
                            }else{
                                callback.onResult(false, message);
                            }
                        }catch (JSONException e){
                            callback.onResult(false,
                                    context.getResources().getString(R.string.parse_data_error));
                        }
                    }else{
                        callback.onResult(false, response);
                    }
                }
            });
        }
    }
}
