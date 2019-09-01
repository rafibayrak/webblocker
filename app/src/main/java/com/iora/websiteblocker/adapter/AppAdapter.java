package com.iora.websiteblocker.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.iora.websiteblocker.R;
import com.iora.websiteblocker.databasehelper.DatabaseHelper;
import com.iora.websiteblocker.model.AppBlocked;

import java.util.List;

public class AppAdapter extends ArrayAdapter {
    private LayoutInflater inflater;

    private final Context _context;
    private List<AppBlocked> _appBlockeds;
    public AppAdapter(Context context, List<AppBlocked> appBlockeds) {
        super(context, 0, appBlockeds);
        _context = context;
        _appBlockeds = appBlockeds;

    }

    @Override
    public int getCount() {
        return _appBlockeds.size();

    }

    @Override
    public AppBlocked getItem(int position) {

        return _appBlockeds.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView tvAppName;
        ImageView imageViewApp;
        Switch appIsBlockedSwitch;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder=new ViewHolder();
            inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.app_adapter, null);
            holder.tvAppName = convertView.findViewById(R.id.tvAppName);
            holder.imageViewApp = convertView.findViewById(R.id.imageViewApp);
            holder.appIsBlockedSwitch=convertView.findViewById(R.id.app_adapter_switch);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }
        try {
            final AppBlocked appBlocked = _appBlockeds.get(position);
            Drawable icon = _context.getPackageManager().getApplicationIcon(appBlocked.getPackageName());
            holder.imageViewApp.setImageDrawable(icon);
            holder.tvAppName.setText(appBlocked.getName());
            holder.appIsBlockedSwitch.setChecked(appBlocked.getIsBlocked());

        } catch (Exception ex) {
            System.out.println();
        }
        return convertView;
    }

    public void updateAppBlocked(List<AppBlocked> appBlockeds){
        _appBlockeds=appBlockeds;
        notifyDataSetChanged();
    }
}
