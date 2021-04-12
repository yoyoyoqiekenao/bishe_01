package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

public class SetFragment extends Fragment implements View.OnClickListener {
    private TextView tv_exit;

    private RelativeLayout rl_exit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, container, false);
        tv_exit = view.findViewById(R.id.tv_exit);
        rl_exit = view.findViewById(R.id.rl_exit);

        tv_exit.setOnClickListener(this);
        rl_exit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit:
                getActivity().finish();
                break;
            case R.id.rl_exit:
                SharedPreferences sp = getContext().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isLogin", false);
                editor.putString("name", "");
                editor.commit();
                EventBus.getDefault().post(new MessageEvent(false));



                break;
            default:
        }
    }



}
