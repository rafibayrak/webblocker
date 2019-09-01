package com.iora.websiteblocker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    @Override
    public void onBackPressed() {
       Intent intent=new Intent(getApplicationContext(),ActivityMain.class);
       startActivity(intent);
       overridePendingTransition( R.anim.enter_left_to_right, R.anim.exit_left_to_right);
    }

}
