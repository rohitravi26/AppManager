package com.rahul.appmanager.Activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.rahul.appmanager.R;
import com.rahul.appmanager.UserAppInfo;
import com.rahul.appmanager.UserAppRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserAppActivity extends AppCompatActivity {

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
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(new UserAppRecyclerAdapter(getApplicationContext(), appList));
                mProgressBar.setVisibility(View.GONE);
            }
        }.execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void retrieveAppInfo() {
        for (PackageInfo packageInfo : mPackageManager.getInstalledPackages(PackageManager.GET_META_DATA)){
            UserAppInfo userAppInfo = new UserAppInfo();
            userAppInfo.setAppName(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
            userAppInfo.setPackageName(packageInfo.packageName);
            userAppInfo.setVersion(packageInfo.versionName);
            userAppInfo.setAppImage(packageInfo.applicationInfo.loadIcon(getPackageManager()));
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 1)
                appList.add(userAppInfo);
        }
    }
}
