package com.ldb.vocabulary.android.vocabularyedit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ldb.vocabulary.android.R;

import java.io.IOException;

/**
 * Created by lsp on 2016/10/1.
 */
public class VocabularyEditFragment extends Fragment implements VocabularyEditContract.View{
    private static final String TAG = VocabularyEditFragment.class.getSimpleName();

    private static final int REQUEST_IMAGE = 0;
    private static final String ARG_CATEGORY_ID = "category_id";
    private static final String ARG_CATEGORY_INDEX = "category_index";

    private String mCategoryId;
    private int mIndex;
    private VocabularyEditContract.Presenter mPresenter;
    private ImageView mImageView;
    private String mImagePath;

    public static VocabularyEditFragment newInstance(String categoryId, int index){
        Bundle bundle = new Bundle();
        bundle.putString(ARG_CATEGORY_ID, categoryId);
        bundle.putInt(ARG_CATEGORY_INDEX, index);

        VocabularyEditFragment fragment = new VocabularyEditFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arg = getArguments();
        if(arg != null){
            mCategoryId = arg.getString(ARG_CATEGORY_ID);
            mIndex = arg.getInt(ARG_CATEGORY_INDEX);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.vocabulary_edit_fragment, container, false);

//        ((Button) view.findViewById(R.id.image_select))
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showFileChooser();
//                    }
//                });

        mImageView = (ImageView) view.findViewById(R.id.vocabulary_image_for_upload);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        ((Button) view.findViewById(R.id.upload_vocabulary_button))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String categoryName =
                                ((EditText) view.findViewById(R.id.vocabulary_name_edit)).getText().toString();
                        mPresenter.uploadVocabulary(categoryName, mImagePath, mCategoryId, mIndex);
                    }
                });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != Activity.RESULT_OK){
            return;
        }
        switch (requestCode){
            case REQUEST_IMAGE:
                if(data.getData() != null){
                    Uri selectedImage = data.getData();
                    //content://com.android.providers.media.documents/document/image%3A45194
                    Log.d(TAG, selectedImage.toString());
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mImagePath = cursor.getString(columnIndex);
                    cursor.close();

                    try {
                        Bitmap bitmap = MediaStore.Images.Media
                                .getBitmap(getActivity().getContentResolver(), selectedImage);
                        mImageView.setImageBitmap(bitmap);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                return;
        }

    }

    @Override
    public void setPresenter(VocabularyEditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void showFileChooser(){
        Intent intent = new Intent(Intent.ACTION_PICK ,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_PICK); // ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onUploadVocabulary(boolean isOk, String message) {
        if(isOk){
            // TODO 暂时先显示成功，之后应该弹框询问是增加词汇还是返回主界面
            showMessage(message);
        }else {
            showError(message);
        }
    }

    public void showError(String error) {
        Snackbar.make(getView().findViewById(R.id.vocabulary_edit_snackbar_decor),
                error, Snackbar.LENGTH_SHORT).show();
    }

    public void showMessage(String message) {
        Snackbar.make(getView().findViewById(R.id.vocabulary_edit_snackbar_decor),
                message, Snackbar.LENGTH_SHORT).show();
    }
}
