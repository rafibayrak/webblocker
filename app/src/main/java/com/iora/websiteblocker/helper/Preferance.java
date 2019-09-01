package com.iora.websiteblocker.helper;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Preferance {
    private Context _context;
    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    String KEY_FRAGMENT="whichFragment";
    String KEY_PERMISSION="permission";
    String KEY_ACTIVITY="activity";
    String KEY_IORA_ON_OR_OFF="IoraOnorOff";

    public Preferance(Context context) {
        pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setFragment(boolean value2){
        editor.putBoolean(KEY_FRAGMENT,value2);
        editor.commit();
    }

    public boolean getFragment(){
        return pref.getBoolean(KEY_FRAGMENT,false);
    }

    public void setPermission(boolean value2){
        editor.putBoolean(KEY_PERMISSION,value2);
        editor.commit();
    }

    public boolean getPermission(){
        return pref.getBoolean(KEY_PERMISSION,false);
    }

    public void setActivity(boolean value2){
        editor.putBoolean(KEY_ACTIVITY,value2);
        editor.commit();
    }

    public boolean getActivity(){
        return pref.getBoolean(KEY_ACTIVITY,false);
    }

    public void setIoraOnOff(boolean value2){
        editor.putBoolean(KEY_IORA_ON_OR_OFF,value2);
        editor.commit();
    }

    public boolean getIoraOnOff(){
        return pref.getBoolean(KEY_IORA_ON_OR_OFF,false);
    }

}
