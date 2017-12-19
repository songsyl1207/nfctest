package com.song.nfctest;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import java.io.IOException;


public class ReadActivity extends Activity {
    private NfcAdapter mAdapter;
    private TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         textView = new TextView(this);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        setContentView(textView);

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        resolveIntent(getIntent());

        if (getCallingActivity() == null){
            textView.append("\ncalled by self");
        }else {
            textView.append("\ncalled by other app");
        }

        textView.append("\n\n\n\n\n\ntap your nfc tag to phone to continue ");
    }
    void resolveIntent(Intent intent) {
        printLog("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Parcelable p = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (p == null) {
            return;
        }
        Tag nfcTag = (Tag) p;
        final NfcB nfcb = NfcB.get(nfcTag);
        try {
            nfcb.connect();
            printLog("----------- complete with  read logic----- ");
            textView.append("\n\n\n\n\n\n get nfc event !!!");
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                nfcb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter == null) return;
        mAdapter.enableForegroundDispatch(this,
                PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0),
                new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)},
                new String[][]{new String[]{NfcB.class.getName()}});
    }

    @Override
    public void onNewIntent(Intent intent) {
        resolveIntent(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAdapter == null) return;
        mAdapter.disableForegroundDispatch(this);
    }
    void printLog(String message) {
        Log.e("krik", message);
    }
}