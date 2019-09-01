package com.iora.websiteblocker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.iora.websiteblocker.fragment.HomeFragment;
import com.iora.websiteblocker.fragment.SecondFragment;
import com.iora.websiteblocker.fragment.ThirtFragment;
import com.iora.websiteblocker.helper.Preferance;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class ActivityMain extends AppCompatActivity {
    private Preferance _preferance;
    Switch _switchEnableDisable;
    MenuItem _switchMenuItem;
    MenuItem _buttonEnableDisableMenuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.navigation_dashboard:
                    fragment = new SecondFragment();
                    break;
                case R.id.navigation_notifications:
                    fragment = new ThirtFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentMain, fragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        _preferance = new Preferance(getApplicationContext());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentMain, homeFragment).commit();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_manu, menu);
        _switchMenuItem =menu.findItem(R.id.switch_enable_disable);
        _buttonEnableDisableMenuItem=menu.findItem(R.id.btnEnableDisable);
        _switchEnableDisable=(Switch) _switchMenuItem.getActionView();
        _switchEnableDisable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    _preferance.setIoraOnOff(true);
                    _buttonEnableDisableMenuItem.setTitle(getString(R.string.iora_enable));
                }else{
                    _preferance.setIoraOnOff(false);
                    _buttonEnableDisableMenuItem.setTitle(getString(R.string.iora_disable));
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnEnableDisable:
                openSwitch();
                break;
            case R.id.btnSettings:
                Intent intent =new Intent(getApplicationContext(),Settings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_right_to_left, R.anim.exit_right_to_left);
                break;
            case R.id.btnRate:
                break;
            case R.id.btnShare:
                break;
            case R.id.btnAbout:
                break;
            case R.id.btnContactUs:
                break;

        }
        return true;
    }

    private void openSwitch() {

        if (!_preferance.getIoraOnOff()){
            _switchEnableDisable.setChecked(true);
            _preferance.setIoraOnOff(true);
            _buttonEnableDisableMenuItem.setTitle(getString(R.string.iora_enable));
        }else{
            _switchEnableDisable.setChecked(false);
            _preferance.setIoraOnOff(false);
            _buttonEnableDisableMenuItem.setTitle(getString(R.string.iora_disable));
        }

    }
}
