package com.ldb.vocabulary.android.categoryedit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.main.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lsp on 2016/9/26.
 */
public class CategoryEditFragment extends Fragment {

    private static final int REQUEST_IMAGE = 0;
    private static final String KEY_IMAGE = "image";
    private static final String KEY_IMAGE_NAME = "name";

    private ImageView mImageView;
    private EditText mImageName;
    private Bitmap mBitmapForUpload;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_edit_fragment, container, false);

        ((Button) view.findViewById(R.id.image_select))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        mImageView = (ImageView) view.findViewById(R.id.image_for_upload);
        mImageName = (EditText) view.findViewById(R.id.image_name);

        ((Button) view.findViewById(R.id.image_upload))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String imageName = mImageName.getText().toString();
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_IMAGE, getStringImage(mBitmapForUpload));
                        map.put(KEY_IMAGE_NAME, imageName);
                        uploadImage(map);
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
                    Uri filePath = data.getData();
                    try {
                        mBitmapForUpload = MediaStore.Images.Media
                                .getBitmap(getActivity().getContentResolver(), filePath);
                        mImageView.setImageBitmap(mBitmapForUpload);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                return;
        }

    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        byte[] imageBytes = out.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void uploadImage(HashMap<String, String> params){
        new ImageUploadTask().execute(params);
    }

    private class ImageUploadTask extends AsyncTask<HashMap<String, String>, Void, String>{
        @Override
        protected String doInBackground(HashMap<String, String>... params) {
            if(params.length <= 0){
                return null;
            }
            RequestHandler request = new RequestHandler();
            String uploadUrl = "http://android.ldb.com:8080/vocabulary/category/add";
            return request.sendPostRequest(uploadUrl, params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null){
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Upload error!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class RequestHandler {

        public String sendPostRequest(String urlStr, HashMap<String, String> postParams){
            URL url;
            StringBuffer sb = new StringBuffer();
            try {
                url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream out = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(getPostDataString(postParams));
                writer.flush();
                writer.close();
                out.close();

                int responseCode = conn.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK){
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String response;
                    while ((response = reader.readLine()) != null){
                        sb.append(response);
                    }
                }
            }catch(MalformedURLException urlE){
                urlE.printStackTrace();
            }catch (IOException ioE){
                ioE.printStackTrace();
            }

            return sb.toString();
        }

        public String getPostDataString(HashMap<String, String> postParams){
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for(Map.Entry<String, String> entry : postParams.entrySet()){
                if(first){
                    first = false;
                }else{
                    sb.append("&");
                }
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
            }
            return sb.toString();
        }
    }

}
