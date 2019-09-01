package com.iora.websiteblocker;

import android.app.Application;
import android.content.Intent;

import com.iora.websiteblocker.service.IoraService;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent=new Intent(getApplicationContext(), IoraService.class);
        startService(intent);
    }
}
