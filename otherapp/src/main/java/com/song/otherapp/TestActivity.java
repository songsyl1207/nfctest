package com.song.otherapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 112 on 2017/12/15.
 */

public class TestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);

        Button button = new Button(this);
        button.setText("call main app");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApp();
            }
        });
        linearLayout.addView(button);
        setContentView(linearLayout);
    }

    private void callApp() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.song.nfctest", "com.song.nfctest.ReadActivity"));
        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1001) {
            boolean result = data.getBooleanExtra("result", false);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
