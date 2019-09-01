package com.iora.websiteblocker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;

import com.iora.websiteblocker.databasehelper.DatabaseHelper;
import com.iora.websiteblocker.model.AppBlocked;

import java.sql.SQLOutput;
import java.util.Calendar;

public class ApplicationState extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            DatabaseHelper databaseHelper = DatabaseHelper.getInctance(context);
            String packageName=intent.getPackage();
            if (packageName==null || packageName.isEmpty()){

            }else{
                AppBlocked appBlocked = databaseHelper.getAppBlockedPackageName(packageName);
                databaseHelper.deleteAppBlocked(appBlocked.getId());
            }
        }
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            try {
                DatabaseHelper databaseHelper = DatabaseHelper.getInctance(context);
                AppBlocked appBlocked = new AppBlocked();
                appBlocked.setPackageName(intent.getPackage());
                ApplicationInfo app = context.getPackageManager().getApplicationInfo(intent.getPackage(), 0);
                appBlocked.setName(app.name);
                appBlocked.setIsBlocked(false);
                appBlocked.setCreationTime(Calendar.getInstance().getTimeInMillis());
                databaseHelper.insertAppBlocked(appBlocked);
            } catch (Exception ex) {
                System.out.println("ApplicationState =>>>>>>>>>>>" + ex.getMessage());
            }
        }
    }
}
