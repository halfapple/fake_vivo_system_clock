package com.app.clock;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;

public class ClockActivity extends AppCompatActivity {

    private VivoClockView clockView;
    private TextView mTimeTv;

    private Calendar now;
    private StringBuilder sb = new StringBuilder();

    private int hour;
    private int min;
    private int sec;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.clock_layout);

        clockView = (VivoClockView)findViewById(R.id.vivo_clock_view);
        mTimeTv = (TextView)findViewById(R.id.current_time);

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case 0:

                        now = Calendar.getInstance();
                        hour = now.get(Calendar.HOUR);
                        min = now.get(Calendar.MINUTE);
                        sec = now.get(Calendar.SECOND);

                        clockView.setTime(hour, min, sec);

                        sb.delete(0, sb.length());
                        sb.append(hour)
                                .append(":")
                                .append(min)
                                .append(":")
                                .append(sec);
                        mTimeTv.setText(sb.toString());

                        sendEmptyMessageDelayed(0, 1000);
                        break;
                }

            }
        };

        handler.sendEmptyMessage(0);
    }


}
