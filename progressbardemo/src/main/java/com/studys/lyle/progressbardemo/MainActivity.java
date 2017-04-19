package com.studys.lyle.progressbardemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTvProgress;
    private ProgressBar mPbProgress;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = mPbProgress.getProgress();
            if (msg.what < 3) {
                mPbProgress.setProgress(++progress);
                mTvProgress.setText("下载倒计时:" + (3 - msg.what - 1) + "秒");
            } else if (msg.what >= 3 && msg.what < 7) {
                mTvProgress.setText("休息倒计时:" + (7 - msg.what - 1) + "秒");
            } else {
                mPbProgress.setProgress(++progress);
                mTvProgress.setText("下载倒计时:" + (12 - msg.what - 1) + "秒");
            }
            if (progress == 8) {
                mTvProgress.setText("下载完成");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initProgress();
    }

    private void initProgress() {
        for (int i = 0; i < 12; i++) {
            Message msg = Message.obtain();
            msg.what = i;
            mHandler.sendMessageDelayed(msg, i * 1000);
        }
    }

    private void initView() {
        mTvProgress = (TextView) findViewById(R.id.tv_progress);
        mPbProgress = (ProgressBar) findViewById(R.id.pb_progress);
    }
}
