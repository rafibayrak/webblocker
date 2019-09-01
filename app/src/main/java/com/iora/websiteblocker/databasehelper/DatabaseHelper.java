package com.iora.websiteblocker.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iora.websiteblocker.model.AppBlocked;
import com.iora.websiteblocker.model.AppHistory;
import com.iora.websiteblocker.model.TimeSetting;
import com.iora.websiteblocker.model.WebBlocked;
import com.iora.websiteblocker.model.WebHistory;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static volatile DatabaseHelper _DatabaseHelper;
    private static final String DATABASE_NAME = "ioraWebBlocker.db";
    private static final int version = 1;
    private static SQLiteDatabase _db;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
        if (_DatabaseHelper != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static DatabaseHelper getInctance(Context context) {
        if (_DatabaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (_DatabaseHelper == null) {
                    _DatabaseHelper = new DatabaseHelper(context.getApplicationContext());
                    _db = _DatabaseHelper.getWritableDatabase();
                }
            }
        }
        return _DatabaseHelper;

    }

    public static String TABLE_WEBHISTORY = "web_history";
    public static String TABLE_APPHISTORY = "app_history";
    public static String TABLE_SETTINGS = "iora_settings";
    public static String TABLE_TIMESETTINGS = "time_settings";
    public static String TABLE_BLOCKED_APP = "app_blocked";
    public static String TABLE_BLOCKED_WEB = "web_blocked";

    private static String webHistoryColumn =
            "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "UrlLink text," +
                    "CreationTime text)";
    private static String appHistoryColumn =
            "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Name text," +
                    "PackageName text," +
                    "CreationTime text)";
    private static String timeSettingsColumn =
            "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "StartTime text," +
                    "EndTime text," +
                    "CreationTime text)";
    private static String webBlockedColumn =
            "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "UrlLink text," +
                    "IsBlocked boolean," +
                    "CreationTime text)";
    private static String appBlockedColumn =
            "(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Name text," +
                    "PackageName text," +
                    "IsBlocked boolean," +
                    "CreationTime text)";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_APPHISTORY + " " + appHistoryColumn);
        sqLiteDatabase.execSQL("create table " + TABLE_BLOCKED_APP + " " + appBlockedColumn);
        sqLiteDatabase.execSQL("create table " + TABLE_BLOCKED_WEB + " " + webBlockedColumn);
        sqLiteDatabase.execSQL("create table " + TABLE_WEBHISTORY + " " + webHistoryColumn);
        sqLiteDatabase.execSQL("create table " + TABLE_TIMESETTINGS + " " + timeSettingsColumn);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_APPHISTORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOCKED_APP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOCKED_WEB);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WEBHISTORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMESETTINGS);
        onCreate(sqLiteDatabase);
    }

    public void insertWebBlocked(WebBlocked webBlocked) {
        ContentValues content = new ContentValues();
        content.put("UrlLink", webBlocked.getUrlLink());
        content.put("IsBlocked", webBlocked.getIsBlocked());
        content.put("CreationTime", webBlocked.getCreationTime());
        _db.insert(DatabaseHelper.TABLE_APPHISTORY, null, content);
    }

    public void insertAppBlocked(AppBlocked appBlocked) {
        ContentValues content = new ContentValues();
        content.put("Name", appBlocked.getName());
        content.put("PackageName", appBlocked.getPackageName());
        content.put("IsBlocked", appBlocked.getIsBlocked());
        content.put("CreationTime", appBlocked.getCreationTime());
        _db.insert(DatabaseHelper.TABLE_BLOCKED_APP, null, content);
    }

    public void insertAppHistory(AppHistory app) {
        ContentValues content = new ContentValues();
        content.put("Name", app.getName());
        content.put("PackageName", app.getPackageName());
        content.put("CreationTime", app.getCreationTime());
        _db.insert(DatabaseHelper.TABLE_APPHISTORY, null, content);
    }

    public void insertTimeSetting(TimeSetting timeSetting) {
        ContentValues content = new ContentValues();
        content.put("StartTime", timeSetting.getStartTime());
        content.put("EndTime", timeSetting.getEndTime());
        content.put("CreationTime", timeSetting.getCreationTime());
        _db.insert(DatabaseHelper.TABLE_APPHISTORY, null, content);
    }

    public void insertWebHistory(WebHistory webHistory) {
        ContentValues content = new ContentValues();
        content.put("UrlLink", webHistory.getUrlLink());
        content.put("CreationTime", webHistory.getCreationTime());
        _db.insert(DatabaseHelper.TABLE_APPHISTORY, null, content);
    }

    public List<WebBlocked> getAllWebBlocked() {
        Cursor cursor = _db.rawQuery("Select * from " + TABLE_BLOCKED_WEB, null);
        List<WebBlocked> webBlockeds = new ArrayList<>();
        WebBlocked webBlocked;
        while (cursor.moveToNext()) {
            webBlocked = new WebBlocked();
            webBlocked.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            webBlocked.setCreationTime(cursor.getLong(cursor.getColumnIndex("CreationTime")));
            webBlocked.setUrlLink(cursor.getString(cursor.getColumnIndex("UrlLink")));
            webBlocked.setIsBlocked(cursor.getInt(cursor.getColumnIndex("IsBlocked")) != 0);
            webBlockeds.add(webBlocked);
        }
        cursor.close();
        return webBlockeds;
    }

    public List<AppBlocked> getAllAppBlocked() {
        Cursor cursor = _db.rawQuery("Select * from " + TABLE_BLOCKED_APP, null);
        List<AppBlocked> appBlockeds = new ArrayList<>();
        AppBlocked appBlocked;
        while (cursor.moveToNext()) {
            appBlocked = new AppBlocked();
            appBlocked.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            appBlocked.setCreationTime(cursor.getLong(cursor.getColumnIndex("CreationTime")));
            appBlocked.setPackageName(cursor.getString(cursor.getColumnIndex("PackageName")));
            appBlocked.setName(cursor.getString(cursor.getColumnIndex("Name")));
            appBlocked.setIsBlocked(cursor.getInt(cursor.getColumnIndex("IsBlocked")) != 0);
            appBlockeds.add(appBlocked);
        }
        cursor.close();
        return appBlockeds;
    }

    public List<AppHistory> getAllAppHistory() {
        Cursor cursor = _db.rawQuery("Select * from " + TABLE_APPHISTORY, null);
        List<AppHistory> appHistories = new ArrayList<>();
        AppHistory appHistory;
        while (cursor.moveToNext()) {
            appHistory = new AppHistory();
            appHistory.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            appHistory.setCreationTime(cursor.getLong(cursor.getColumnIndex("CreationTime")));
            appHistory.setName(cursor.getString(cursor.getColumnIndex("Name")));
            appHistory.setPackageName(cursor.getString(cursor.getColumnIndex("PackageName")));
            appHistories.add(appHistory);
        }
        cursor.close();
        return appHistories;
    }

    public List<TimeSetting> getAllTimeSettings() {
        Cursor cursor = _db.rawQuery("Select * from " + TABLE_TIMESETTINGS, null);
        List<TimeSetting> timeSettings = new ArrayList<>();
        TimeSetting timeSetting;
        while (cursor.moveToNext()) {
            timeSetting = new TimeSetting();
            timeSetting.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            timeSetting.setCreationTime(cursor.getLong(cursor.getColumnIndex("CreationTime")));
            timeSetting.setStartTime(cursor.getLong(cursor.getColumnIndex("StartTime")));
            timeSetting.setEndTime(cursor.getLong(cursor.getColumnIndex("EndTime")));
            timeSettings.add(timeSetting);
        }
        cursor.close();
        return timeSettings;
    }

    public List<WebHistory> getAllWebHistory() {
        Cursor cursor = _db.rawQuery("Select * from " + TABLE_WEBHISTORY, null);
        List<WebHistory> webHistories = new ArrayList<>();
        WebHistory webHistory;
        while (cursor.moveToNext()) {
            webHistory = new WebHistory();
            webHistory.setId(cursor.getInt(cursor.getColumnIndex("Id")));
            webHistory.setCreationTime(cursor.getLong(cursor.getColumnIndex("CreationTime")));
            webHistory.setUrlLink(cursor.getString(cursor.getColumnIndex("UrlLink")));
            webHistories.add(webHistory);
        }
        cursor.close();
        return webHistories;
    }

    public void updateWebBlocked(WebBlocked webBlocked) {
        ContentValues content = new ContentValues();
        content.put("UrlLink", webBlocked.getUrlLink());
        content.put("IsBlocked", webBlocked.getIsBlocked());
        content.put("CreationTime", webBlocked.getCreationTime());
        _db.update(DatabaseHelper.TABLE_APPHISTORY, content, "Id=" + webBlocked.getId(), null);
    }

    public void updateAppBlocked(AppBlocked appBlocked) {
        ContentValues content = new ContentValues();
        content.put("Name", appBlocked.getName());
        content.put("PackageName", appBlocked.getPackageName());
        content.put("IsBlocked", appBlocked.getIsBlocked());
        content.put("CreationTime", appBlocked.getCreationTime());
        _db.update(DatabaseHelper.TABLE_BLOCKED_APP, content, "Id=" + appBlocked.getId(), null);
    }

    public void updateTimeSetting(TimeSetting timeSetting) {
        ContentValues content = new ContentValues();
        content.put("StartTime", timeSetting.getStartTime());
        content.put("EndTime", timeSetting.getEndTime());
        content.put("CreationTime", timeSetting.getCreationTime());
        _db.update(DatabaseHelper.TABLE_APPHISTORY, content, "Id=" + timeSetting.getId(), null);
    }

    public void deleteWebBlocked(int id) {
        _db.delete(DatabaseHelper.TABLE_APPHISTORY, "Id=" + id, null);
    }

    public void deleteAppBlocked(int id) {
        _db.insert(DatabaseHelper.TABLE_BLOCKED_APP, "Id=" + id, null);
    }

    public void deleteAppHistory(int id) {
        _db.delete(DatabaseHelper.TABLE_APPHISTORY, "Id=" + id, null);
    }

    public void deleteTimeSetting(int id) {
        _db.delete(DatabaseHelper.TABLE_APPHISTORY, "Id=" + id, null);
    }

    public void deleteWebHistory(int id) {
        _db.delete(DatabaseHelper.TABLE_APPHISTORY, "Id=" + id, null);
    }

    public WebBlocked getWebBlocked(int id) {
        Cursor cursor = _db.rawQuery("Select * from " + TABLE_BLOCKED_WEB + " where Id=" + id, null);
        WebBlocked webBlocked;
        webBlocked = new WebBlocked();
        webBlocked.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        webBlocked.setCreationTime(cursor.getLong(cursor.getColumnIndex("CreationTime")));
        webBlocked.setUrlLink(cursor.getString(cursor.getColumnIndex("UrlLink")));
        webBlocked.setIsBlocked(cursor.getInt(cursor.getColumnIndex("IsBlocked")) != 0);
        cursor.close();
        return webBlocked;
    }

    public AppBlocked getAppBlocked(int id) {
        Cursor cursor = _db.rawQuery("Select * from " + TABLE_BLOCKED_APP + " where Id=" + id, null);

        AppBlocked appBlocked;
        appBlocked = new AppBlocked();
        appBlocked.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        appBlocked.setCreationTime(cursor.getLong(cursor.getColumnIndex("CreationTime")));
        appBlocked.setPackageName(cursor.getString(cursor.getColumnIndex("PackageName")));
        appBlocked.setName(cursor.getString(cursor.getColumnIndex("Name")));
        appBlocked.setIsBlocked(cursor.getInt(cursor.getColumnIndex("IsBlocked")) != 0);
        cursor.close();
        return appBlocked;
    }

    public AppBlocked getAppBlockedPackageName(String packageName) {
        Cursor cursor = _db.rawQuery("Select * from " + TABLE_BLOCKED_APP + " where PackageName='" + packageName+"'", null);
        AppBlocked appBlocked;
        appBlocked = new AppBlocked();
        cursor.moveToNext();
        appBlocked.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        appBlocked.setCreationTime(cursor.getLong(cursor.getColumnIndex("CreationTime")));
        appBlocked.setPackageName(cursor.getString(cursor.getColumnIndex("PackageName")));
        appBlocked.setName(cursor.getString(cursor.getColumnIndex("Name")));
        appBlocked.setIsBlocked(cursor.getInt(cursor.getColumnIndex("IsBlocked")) != 0);
        cursor.close();
        return appBlocked;
    }

    public AppHistory getAppHistory(int id) {
        Cursor cursor = _db.rawQuery("Select * from " + TABLE_APPHISTORY + " where Id=" + id, null);

        AppHistory appHistory;
        appHistory = new AppHistory();
        appHistory.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        appHistory.setCreationTime(cursor.getLong(cursor.getColumnIndex("CreationTime")));
        appHistory.setName(cursor.getString(cursor.getColumnIndex("Name")));
        appHistory.setPackageName(cursor.getString(cursor.getColumnIndex("PackageName")));

        cursor.close();
        return appHistory;
    }

    public TimeSetting getTimeSettings(int id) {
        Cursor cursor = _db.rawQuery("Select * from " + TABLE_TIMESETTINGS + " where Id=" + id, null);
        TimeSetting timeSetting;

        timeSetting = new TimeSetting();
        timeSetting.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        timeSetting.setCreationTime(cursor.getLong(cursor.getColumnIndex("CreationTime")));
        timeSetting.setStartTime(cursor.getLong(cursor.getColumnIndex("StartTime")));
        timeSetting.setEndTime(cursor.getLong(cursor.getColumnIndex("EndTime")));

        cursor.close();
        return timeSetting;
    }

    public WebHistory getWebHistory(int id) {
        Cursor cursor = _db.rawQuery("Select * from " + TABLE_WEBHISTORY + " where Id=" + id, null);

        WebHistory webHistory;
        webHistory = new WebHistory();
        webHistory.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        webHistory.setCreationTime(cursor.getLong(cursor.getColumnIndex("CreationTime")));
        webHistory.setUrlLink(cursor.getString(cursor.getColumnIndex("UrlLink")));

        cursor.close();
        return webHistory;
    }


}
