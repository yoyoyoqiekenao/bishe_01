package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

public class SetFragment extends Fragment implements View.OnClickListener, PopupWindow.OnDismissListener {
    private TextView tv_exit;

    private RelativeLayout rl_exit, rl_about, rootView;
    private PopupWindow mPop, mPop_delete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, container, false);
        tv_exit = view.findViewById(R.id.tv_exit);
        rl_exit = view.findViewById(R.id.rl_exit);
        rl_about = view.findViewById(R.id.rl_about);
        rootView = view.findViewById(R.id.rootView);

        tv_exit.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit:
                getActivity().finish();
                break;
            case R.id.rl_exit:
                if (getContext().getSharedPreferences("isLogin", Context.MODE_PRIVATE).getBoolean("isLogin", false) == true) {
                    backgroundAlpha(0.5f);
                    showDeletePop();
                } else {

                }


                break;
            case R.id.rl_about:
                backgroundAlpha(0.5f);
                showPop();
                break;
            case R.id.rl_yes:
                SharedPreferences sp = getContext().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isLogin", false);
                editor.putString("name", "");
                editor.commit();
                mPop_delete.dismiss();
                EventBus.getDefault().post(new MessageEvent(false));
                break;
            case R.id.rl_no:
                mPop_delete.dismiss();
                break;
            default:
        }
    }

    private void showDeletePop() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_delete, null);
        mPop_delete = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout rlYes = view.findViewById(R.id.rl_yes);
        RelativeLayout rlNo = view.findViewById(R.id.rl_no);

        rlYes.setOnClickListener(this);
        rlNo.setOnClickListener(this);

        mPop_delete.setAnimationStyle(R.style.popupwindow_anim_style);
        mPop_delete.setFocusable(true);
        mPop_delete.setBackgroundDrawable(new BitmapDrawable());
        mPop_delete.setOutsideTouchable(true);
        mPop_delete.setOnDismissListener(this);
        mPop_delete.showAtLocation(rootView, Gravity.CENTER, 0, 0);
    }

    private void showPop() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_aboput, null);
        mPop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPop.setAnimationStyle(R.style.popupwindow_anim_style);
        mPop.setFocusable(true);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        mPop.setOutsideTouchable(true);
        mPop.setOnDismissListener(this);
        mPop.showAtLocation(rootView, Gravity.CENTER, 0, 0);

    }

    /**
     * 设置添加屏幕的背景透明度 * * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1.0f);
    }
}
