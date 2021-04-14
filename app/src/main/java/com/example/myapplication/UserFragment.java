package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.security.PrivateKey;

import static android.content.Context.MODE_PRIVATE;

public class UserFragment extends Fragment implements View.OnClickListener, PopupWindow.OnDismissListener {

    private EditText ed_username, ed_password;
    private TextView tv_login, tv_name;
    private LinearLayout ll_qq, ll_wx;
    private RelativeLayout rl_login, rootView;
    private ImageView ivHead;
    private PopupWindow mPop;
    private int mType;
    private SharedPreferences mSp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ed_username = view.findViewById(R.id.ed_username);
        ed_password = view.findViewById(R.id.ed_password);
        rootView = view.findViewById(R.id.rootView);
        tv_login = view.findViewById(R.id.tv_login);
        ll_qq = view.findViewById(R.id.ll_qq);
        ll_wx = view.findViewById(R.id.ll_wx);
        rl_login = view.findViewById(R.id.rl_login);
        ivHead = view.findViewById(R.id.iv_heads);
        tv_name = view.findViewById(R.id.tv_name);


        tv_login.setOnClickListener(this);
        ll_qq.setOnClickListener(this);
        ll_wx.setOnClickListener(this);


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
            case R.id.ll_qq:
                backgroundAlpha(0.5f);
                showLoginPop(0);
                break;
            case R.id.ll_wx:
                backgroundAlpha(0.5f);
                showLoginPop(1);
                break;
            case R.id.rl_yes:
                if (0 == mType) {
                    SharedPreferences sp1 = getContext().getSharedPreferences("isLogin", MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sp1.edit();
                    editor1.putBoolean("isLogin", true);
                    editor1.putString("name", "QQ");
                    editor1.commit();
                } else if (1 == mType) {
                    SharedPreferences sp1 = getContext().getSharedPreferences("isLogin", MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sp1.edit();
                    editor1.putBoolean("isLogin", true);
                    editor1.putString("name", "WX");
                    editor1.commit();
                }
                mPop.dismiss();
                ivHead.setVisibility(View.VISIBLE);
                tv_name.setVisibility(View.VISIBLE);
                tv_name.setText(ed_username.getText().toString());
                rl_login.setVisibility(View.GONE);
                tv_name.setText(mSp.getString("name", ""));
                break;
            case R.id.rl_no:

                mPop.dismiss();
                break;
            default:
        }
    }

    private void showLoginPop(int type) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_delete, null);
        mPop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout rlYes = view.findViewById(R.id.rl_yes);
        RelativeLayout rlNo = view.findViewById(R.id.rl_no);
        TextView tvContent = view.findViewById(R.id.tv_message);
        mType = type;
        if (0 == type) {
            tvContent.setText("是否使用QQ登陆？");
        } else {
            tvContent.setText("是佛使用微信登陆？");
        }

        rlYes.setOnClickListener(this);
        rlNo.setOnClickListener(this);

        mPop.setAnimationStyle(R.style.popupwindow_anim_style);
        mPop.setFocusable(true);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        mPop.setOutsideTouchable(true);
        mPop.setOnDismissListener(this);
        mPop.showAtLocation(rootView, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();

        mSp = getContext().getSharedPreferences("isLogin", MODE_PRIVATE);
        if (mSp.getBoolean("isLogin", false) == false) {
            rl_login.setVisibility(View.VISIBLE);
            ivHead.setVisibility(View.GONE);
            tv_name.setVisibility(View.GONE);
        } else {
            rl_login.setVisibility(View.GONE);
            ivHead.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            tv_name.setText(mSp.getString("name", ""));
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onDismiss() {
        backgroundAlpha(1.0f);
    }

    /**
     * 设置添加屏幕的背景透明度 * * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }
}
