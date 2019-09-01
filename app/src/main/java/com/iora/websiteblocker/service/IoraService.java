package com.iora.websiteblocker.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.iora.websiteblocker.BaseApplication;
import com.iora.websiteblocker.receiver.ApplicationState;

public class IoraService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        ApplicationState receiver=new ApplicationState();
        registerReceiver(receiver,filter);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        BaseApplication baseApplication=new BaseApplication();
    }
}
