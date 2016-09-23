package com.ldb.vocabulary.android.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ldb.vocabulary.android.R;
import com.ldb.vocabulary.android.login.LoginActivity;

/**
 * Created by lsp on 2016/9/17.
 */
public class MineFragment extends Fragment {

    private static final String TAG = MineFragment.class.getSimpleName();

    private static final int REQUEST_LOGIN = 101;
    private String mUsername = null;
    // 登录状态 0未登录， 1已登录
    private int mLoginState = 0;

    private Button mRLButton;
    private TextView mUsernameTextView;

    public static MineFragment newInstance(){
        return new MineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, container, false);

        mRLButton = (Button) view.findViewById(R.id.mine_login_register);
        mRLButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, REQUEST_LOGIN);
            }
        });

        mUsernameTextView = (TextView) view.findViewById(R.id.mini_username_text_view);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        switch (requestCode){
            case REQUEST_LOGIN:
                Bundle bundle = data.getExtras();
                onLogined(bundle);
                return;

        }
    }

    private void onLogined(Bundle data){
        mUsername = data.getString("username");
        mUsernameTextView.setText(mUsername);
        mLoginState = 1;
        notifyLoginState();
    }

    /**
     * 处理登录状态变化
     */
    private void notifyLoginState(){
        switch (mLoginState){
            case 0:

                return;
            case 1:
                mRLButton.setVisibility(View.GONE);
                mUsernameTextView.setVisibility(View.VISIBLE);
                return;
        }
    }

    /**
     * 通知其他关注登录状态的组件
     */
    private void sendLoginStateBroadcast(){

    }
}
