package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rl_user, rl_voice, rl_set;
    private ImageView iv_user, iv_voice, iv_set;
    private ViewPager viewPager;

    private Boolean isLogin;

    //创建一个保存Fragment对象的集合
    private List<Fragment> mList = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    viewPager.setCurrentItem(0);


                    break;
                default:
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImmersionBar.with(this).statusBarColor(R.color.barcolor).init();

        isLogin = getIntent().getBooleanExtra("isLogin", false);
        rl_user = findViewById(R.id.rl_user);
        rl_voice = findViewById(R.id.rl_voice);
        rl_set = findViewById(R.id.rl_set);
        iv_user = findViewById(R.id.iv_user);
        iv_voice = findViewById(R.id.iv_voice);
        iv_set = findViewById(R.id.iv_set);
        viewPager = findViewById(R.id.viewpager);

        rl_user.setOnClickListener(this);
        rl_voice.setOnClickListener(this);
        rl_set.setOnClickListener(this);

        //添加fragment对象至集合中
        mList.add(new UserFragment());
        mList.add(new VoiceFragment());
        mList.add(new SetFragment());

        //viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        if (isLogin == true) {
            viewPager.setCurrentItem(1);
        } else {
            viewPager.setCurrentItem(0);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:

                        iv_user.setImageResource(R.mipmap.iv_user_check);
                        iv_voice.setImageResource(R.mipmap.iv_voice_uncheck);
                        iv_set.setImageResource(R.mipmap.iv_set_uncheck);
                        break;
                    case 1:
                        iv_user.setImageResource(R.mipmap.iv_user_uncheck);
                        iv_voice.setImageResource(R.mipmap.iv_voice_check);
                        iv_set.setImageResource(R.mipmap.iv_set_uncheck);
                        break;
                    case 2:
                        iv_user.setImageResource(R.mipmap.iv_user_uncheck);
                        iv_voice.setImageResource(R.mipmap.iv_voice_uncheck);
                        iv_set.setImageResource(R.mipmap.iv_set_check);
                        break;
                    default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_user:
                viewPager.setCurrentItem(0);
                iv_user.setImageResource(R.mipmap.iv_user_check);
                iv_voice.setImageResource(R.mipmap.iv_voice_uncheck);
                iv_set.setImageResource(R.mipmap.iv_set_uncheck);
                break;
            case R.id.rl_voice:
                viewPager.setCurrentItem(1);
                iv_user.setImageResource(R.mipmap.iv_user_uncheck);
                iv_voice.setImageResource(R.mipmap.iv_voice_check);
                iv_set.setImageResource(R.mipmap.iv_set_uncheck);
                break;
            case R.id.rl_set:
                viewPager.setCurrentItem(2);
                iv_user.setImageResource(R.mipmap.iv_user_uncheck);
                iv_voice.setImageResource(R.mipmap.iv_voice_uncheck);
                iv_set.setImageResource(R.mipmap.iv_set_check);
                break;
            default:
        }
    }

    private class MyAdapter extends FragmentPagerAdapter {
        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetPage(MessageEvent s) {
        if (s.isLogin() == false) {
            Log.d("xuwudi", "收到信息了1");
            mHandler.sendEmptyMessage(1);
        }
    }

    public interface checkLogin {
        void check();
    }

}