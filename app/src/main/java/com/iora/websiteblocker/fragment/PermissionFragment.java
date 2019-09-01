package com.iora.websiteblocker.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iora.websiteblocker.ActivityMain;
import com.iora.websiteblocker.R;
import com.iora.websiteblocker.databasehelper.DatabaseHelper;
import com.iora.websiteblocker.helper.Preferance;
import com.iora.websiteblocker.model.AppBlocked;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PermissionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Preferance preferance = new Preferance(getContext());
        preferance.setFragment(true);
        return inflater.inflate(R.layout.permission, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.btnContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferance preferance = new Preferance(getContext());
                if (!preferance.getPermission()) {
                    Intent openSettings = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    openSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(openSettings);
                } else {
                    preferance.setActivity(true);

                    ArrayList<AppBlocked> appBlockeds=GetApplicationList(getContext());

                    for (AppBlocked item: appBlockeds){
                        DatabaseHelper.getInctance(getContext()).insertAppBlocked(item);
                    }

                    Intent intent = new Intent(getContext(), ActivityMain.class);
                    startActivity(intent);
                    getActivity().finish();
                   /* Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    List<ResolveInfo> pkgAppsList = getContext().getPackageManager().queryIntentActivities(mainIntent, 0);
                    PackageManager packageManager = getContext().getPackageManager();
                    List<PackageInfo> packs = packageManager.getInstalledPackages(0);


                    try {
                        for (PackageInfo item : packs) {
                            if (((isSystemPackage(item) == false) && item.applicationInfo.packageName!="com.iora.websiteblocker") || systemPackage(item.packageName)) {
                                AppBlocked appBlocked = new AppBlocked();
                                String appName = item.applicationInfo.loadLabel(packageManager).toString();
                                appBlocked.setName(appName);
                                appBlocked.setPackageName(item.applicationInfo.packageName);
                                appBlocked.setCreationTime(Calendar.getInstance().getTimeInMillis());
                                appBlocked.setIsBlocked(false);
                                DatabaseHelper.getInctance(getContext()).insertAppBlocked(appBlocked);

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                }
            }
        });
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }

    private boolean systemPackage(String packageName){
        boolean value= packageName =="com.google.android.youtube"
                || packageName =="com.android.settings"
                || packageName =="com.google.android.music"
                || packageName =="com.android.chrome"
                || packageName =="com.google.android.calendar"
                || packageName =="com.android.phone"
                || packageName =="com.google.android.GoogleCamera"
                || packageName =="com.android.settings" ;
        return value;
    }

    public ArrayList<AppBlocked> GetApplicationList(Context context) {
        AppBlocked applicationModels;
        ArrayList<AppBlocked> appModelsArrayList = new ArrayList<>();
        List<ApplicationInfo> packages;
        PackageManager pm = context.getPackageManager();
        packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo packageInfo : packages) {
            if (null != pm.getLaunchIntentForPackage(packageInfo.packageName)) {
                applicationModels = new AppBlocked();
                applicationModels.setName(String.valueOf(packageInfo.loadLabel(pm)));
                applicationModels.setPackageName(packageInfo.packageName);
                applicationModels.setIsBlocked(false);
                if (!applicationModels.getName().equals(getString(R.string.app_name))) {
                    System.out.println("package name: " + applicationModels.getPackageName());
                    appModelsArrayList.add(applicationModels);
                }
            }
        }
        for (int i = 0; i < appModelsArrayList.size(); i++) {
            for (int j = i + 1; j < appModelsArrayList.size(); j++) {
                if (appModelsArrayList.get(i).getPackageName().equals(appModelsArrayList.get(j).getPackageName())) {
                    appModelsArrayList.remove(j);
                    j--;
                }
            }
        }
        return appModelsArrayList;
    }
}
