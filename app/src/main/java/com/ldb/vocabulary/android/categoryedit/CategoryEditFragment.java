package com.ldb.vocabulary.android.categoryedit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
 * Created by lsp on 2016/9/26.
 */
public class CategoryEditFragment extends Fragment implements CategoryEditContract.View{

    private static final String TAG = CategoryEditFragment.class.getSimpleName();

    private static final int REQUEST_IMAGE = 0;

    private CategoryEditContract.Presenter mPresenter;
    private ImageView mImageView;
    private String mImagePath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.category_edit_fragment, container, false);

        ((Button) view.findViewById(R.id.image_select))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        mImageView = (ImageView) view.findViewById(R.id.image_for_upload);

        ((Button) view.findViewById(R.id.image_upload))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String categoryName =
                                ((EditText) view.findViewById(R.id.category_name)).getText().toString();
                        mPresenter.uploadCategory(categoryName, mImagePath);
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
    public void setPresenter(CategoryEditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private void showFileChooser(){
        Intent intent = new Intent(Intent.ACTION_PICK ,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_PICK); // ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_IMAGE);
    }

}
