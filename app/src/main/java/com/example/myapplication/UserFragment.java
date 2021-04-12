package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.security.PrivateKey;

import static android.content.Context.MODE_PRIVATE;

public class UserFragment extends Fragment implements View.OnClickListener {

    private EditText ed_username, ed_password;
    private TextView tv_login, tv_name;
    private LinearLayout ll_qq, ll_wx;
    private RelativeLayout rl_login;
    private ImageView ivHead;
    private Activity mActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ed_username = view.findViewById(R.id.ed_username);
        ed_password = view.findViewById(R.id.ed_password);
        tv_login = view.findViewById(R.id.tv_login);
        ll_qq = view.findViewById(R.id.ll_qq);
        ll_wx = view.findViewById(R.id.ll_wx);
        rl_login = view.findViewById(R.id.rl_login);
        ivHead = view.findViewById(R.id.iv_heads);
        tv_name = view.findViewById(R.id.tv_name);


        tv_login.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                if (TextUtils.isEmpty(ed_username.getText())) {
                    Toast.makeText(getContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ed_password.getText())) {
                    Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                ivHead.setVisibility(View.VISIBLE);
                tv_name.setVisibility(View.VISIBLE);
                tv_name.setText(ed_username.getText().toString());
                rl_login.setVisibility(View.GONE);

                SharedPreferences sp = getContext().getSharedPreferences("isLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isLogin", true);
                editor.putString("name", ed_username.getText().toString());
                editor.commit();
                break;
            default:
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sp = getContext().getSharedPreferences("isLogin", MODE_PRIVATE);
        if (sp.getBoolean("isLogin", false) == false) {
            rl_login.setVisibility(View.VISIBLE);
            ivHead.setVisibility(View.GONE);
            tv_name.setVisibility(View.GONE);
        } else {
            rl_login.setVisibility(View.GONE);
            ivHead.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            tv_name.setText(sp.getString("name", ""));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    public void checkLogin() {
        SharedPreferences sp = mActivity.getSharedPreferences("isLogin", MODE_PRIVATE);
        if (sp.getBoolean("isLogin", false) == false) {
            rl_login.setVisibility(View.VISIBLE);
            ivHead.setVisibility(View.GONE);
            tv_name.setVisibility(View.GONE);
        } else {
            rl_login.setVisibility(View.GONE);
            ivHead.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            tv_name.setText(sp.getString("name", ""));
        }
    }


}
