package com.iora.websiteblocker.fragment.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iora.websiteblocker.R;
import com.iora.websiteblocker.adapter.AppAdapter;
import com.iora.websiteblocker.databasehelper.DatabaseHelper;
import com.iora.websiteblocker.model.AppBlocked;

import java.util.List;

public class ApplicationBlocked extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.application_blocked,container,false);

        ListView listView=(ListView) view.findViewById(R.id.application_blocked_fragment_listView);
        final DatabaseHelper databaseHelper=DatabaseHelper.getInctance(getContext());
        final List<AppBlocked> appBlockeds=databaseHelper.getAllAppBlocked();
        final AppAdapter appAdapter=new AppAdapter(getContext(),appBlockeds);
        listView.setAdapter(appAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AppBlocked appBlocked=appBlockeds.get(position);
                if (appBlocked.getIsBlocked()){
                    appBlocked.setIsBlocked(false);
                    appBlockeds.get(position).setIsBlocked(false);
                    databaseHelper.updateAppBlocked(appBlocked);
                }else{
                    appBlockeds.get(position).setIsBlocked(true);
                    appBlocked.setIsBlocked(true);
                    databaseHelper.updateAppBlocked(appBlocked);
                }
                appAdapter.updateAppBlocked(appBlockeds);
            }
        });

        return view;
    }
}
