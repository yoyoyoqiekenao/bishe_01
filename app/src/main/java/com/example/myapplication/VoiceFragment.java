package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xw.repo.BubbleSeekBar;

public class VoiceFragment extends Fragment implements View.OnClickListener {

    private ImageView mIvPower, iv_delete, iv_add;
    private BubbleSeekBar bubbleSeekBar;
    private boolean isClick = false;
    private int mProgress = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice, container, false);
        mIvPower = view.findViewById(R.id.iv_power);
        bubbleSeekBar = view.findViewById(R.id.seekBar);
        iv_delete = view.findViewById(R.id.iv_delete);
        iv_add = view.findViewById(R.id.iv_add);

        bubbleSeekBar.setProgress(0);
        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                mProgress = progress;
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });

        mIvPower.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_power:
                if (isClick) {
                    isClick = false;
                    mIvPower.setImageResource(R.mipmap.iv_power_close);
                } else {
                    isClick = true;
                    mIvPower.setImageResource(R.mipmap.iv_power_open);
                }
                break;
            case R.id.iv_delete:
                if (mProgress == 0) {
                    Toast.makeText(getContext(), "已经是最低音量了", Toast.LENGTH_SHORT).show();
                } else {
                    mProgress = mProgress - 1;
                    bubbleSeekBar.setProgress(mProgress);
                }
                break;
            case R.id.iv_add:
                if (mProgress == 100) {
                    Toast.makeText(getContext(), "已经是最高音量了", Toast.LENGTH_SHORT).show();
                } else {
                    mProgress = mProgress + 1;
                    bubbleSeekBar.setProgress(mProgress);
                }
                break;
            default:
        }
    }
}
