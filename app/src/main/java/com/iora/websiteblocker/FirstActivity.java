package com.iora.websiteblocker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.iora.websiteblocker.fragment.Agreement;
import com.iora.websiteblocker.helper.Preferance;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Preferance preferance = new Preferance(getApplicationContext());
        try {
            if (Settings.Secure.getInt(this.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED) != 1) {
                preferance.setPermission(false);
                preferance.setActivity(false);
            }
            if (preferance.getActivity()) {
                Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                startActivity(intent);
                finish();
            } else {
                setContentView(R.layout.activity_first);
                Fragment fragment = new Agreement();
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.fragmentFirst, fragment).commit();
            }
        } catch (Exception ex) {
            Log.d("IORA", "onCreate: " + ex.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = new Agreement();
        Preferance preferance = new Preferance(getApplicationContext());
        if (preferance.getFragment()) {
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right, R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragmentFirst, fragment).commit();

        } else {
            super.onBackPressed();
        }

    }
}
