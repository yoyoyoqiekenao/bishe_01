package com.example.myapplication;

import android.app.Service;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import java.util.HashMap;
import java.util.Map;

public class VoiceFragment extends Fragment implements View.OnClickListener {

    private ImageView mIvPower, iv_delete, iv_add;
    private SeekBar bubbleSeekBar;
    private boolean isClick = false;
    private int mProgress = 50;
    private SoundPool mSoundPool;
    private HashMap<Integer, Integer> soundId = new HashMap<>();
    private Vibrator vibrator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voice, container, false);
        mIvPower = view.findViewById(R.id.iv_power);
        bubbleSeekBar = view.findViewById(R.id.seekBar);
        iv_delete = view.findViewById(R.id.iv_delete);
        iv_add = view.findViewById(R.id.iv_add);
        // 获得系统的Vibrator实例
        vibrator = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);

        initSoundPool();


        bubbleSeekBar.setProgress(50);
        bubbleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mProgress = seekBar.getProgress();
                if (seekBar.getProgress() == 0) {
                    startVibrator();
                    mSoundPool.play(soundId.get(3), 1, 1, 0, 0, 1);
                } else if (seekBar.getProgress() == 100) {
                    startVibrator();
                    mSoundPool.play(soundId.get(4), 1, 1, 0, 0, 1);
                }
            }
        });


        mIvPower.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        iv_delete.setOnClickListener(this);

        bubbleSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isClick == false) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        return view;
    }

    private void initSoundPool() {
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频数量
            builder.setMaxStreams(1);
            AudioAttributes.Builder attBuilder = new AudioAttributes.Builder();
            attBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attBuilder.build());
            mSoundPool = builder.build();
        } else {
            mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
        }
        soundId.put(1, mSoundPool.load(getContext(), R.raw.close, 1));
        soundId.put(2, mSoundPool.load(getContext(), R.raw.open, 1));
        soundId.put(3, mSoundPool.load(getContext(), R.raw.max, 1));
        soundId.put(4, mSoundPool.load(getContext(), R.raw.min, 1));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_power:
                if (isClick) {
                    isClick = false;
                    mIvPower.setImageResource(R.mipmap.iv_power_close);
                    mSoundPool.play(soundId.get(1), 1, 1, 0, 0, 1);
                    startVibrator();

                } else {
                    isClick = true;
                    mIvPower.setImageResource(R.mipmap.iv_power_open);
                    mSoundPool.play(soundId.get(2), 1, 1, 0, 0, 1);
                    startVibrator();
                }
                break;
            case R.id.iv_delete:
                if (isClick == true) {
                    if (mProgress == 0) {
                        Toast.makeText(getContext(), "已经是最低功率了", Toast.LENGTH_SHORT).show();
                    } else {
                        mProgress = mProgress - 1;
                        bubbleSeekBar.setProgress(mProgress);
                    }
                } else {

                }

                break;
            case R.id.iv_add:
                if (isClick == true) {
                    if (mProgress == 100) {
                        Toast.makeText(getContext(), "已经是最高功率了", Toast.LENGTH_SHORT).show();
                    } else {
                        mProgress = mProgress + 1;
                        bubbleSeekBar.setProgress(mProgress);
                    }
                }

                break;
            default:
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSoundPool != null) {
            mSoundPool.release();
        }
    }

    private void startVibrator() {
        if (vibrator.hasVibrator() == false) {
            Toast.makeText(getContext(), "当前设备没有振动器", Toast.LENGTH_SHORT).show();
            return;
        }
        vibrator.vibrate(new long[]{100, 200, 100, 200}, -1);
    }
}
