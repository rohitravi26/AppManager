package com.rahul.appmanager.Activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.rahul.appmanager.R;
import com.rahul.appmanager.UserAppInfo;
import com.rahul.appmanager.UserAppRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SystemAppActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    PackageManager mPackageManager;
    List<UserAppInfo> appList;
    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        appList = new ArrayList<>();
        mPackageManager = getPackageManager();
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                retrieveAppInfo();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Collections.sort(appList, new Comparator<UserAppInfo>() {
                    @Override
                    public int compare(UserAppInfo lhs, UserAppInfo rhs) {
                        return lhs.getAppName().compareTo(rhs.getAppName());
                    }
                });
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(new UserAppRecyclerAdapter(getApplicationContext(), appList));
                mProgressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    private void retrieveAppInfo() {
        for (PackageInfo packageInfo : mPackageManager.getInstalledPackages(PackageManager.GET_META_DATA)){
            UserAppInfo userAppInfo = new UserAppInfo();
            userAppInfo.setAppName(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
            userAppInfo.setPackageName(packageInfo.packageName);
            userAppInfo.setVersion(packageInfo.versionName);
            userAppInfo.setAppImage(packageInfo.applicationInfo.loadIcon(getPackageManager()));
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1)
                appList.add(userAppInfo);
        }
    }
}
