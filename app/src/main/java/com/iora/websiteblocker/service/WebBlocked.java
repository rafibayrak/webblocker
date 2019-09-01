package com.iora.websiteblocker.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.iora.websiteblocker.helper.Preferance;

public class WebBlocked extends AccessibilityService {
    private Preferance _preferance;

    @Override
    protected void onServiceConnected() {
        _preferance = new Preferance(getApplicationContext());
        _preferance.setPermission(true);
        System.out.println("Start Service");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (_preferance.getIoraOnOff()) {
            try {

                if (AccessibilityEvent.eventTypeToString(event.getEventType()).contains("WINDOW")) {
                    AccessibilityNodeInfo nodeInfo = event.getSource();
                    dfs(nodeInfo);
                }

            } catch (Exception ex) {
                System.out.println("WebBlocked service ERROR: "+ex.getMessage());
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    public void dfs(AccessibilityNodeInfo info) {
        if (info == null)
            return;
        if (info.getText() != null && info.getText().length() > 0)
            System.out.println(info.getText() + " class: " + info.getClassName());
        for (int i = 0; i < info.getChildCount(); i++) {
            AccessibilityNodeInfo child = info.getChild(i);
            dfs(child);
            if (child != null) {
                child.recycle();
            }
        }
    }
}
