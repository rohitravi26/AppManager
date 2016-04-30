package com.rahul.appmanager.Fragment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rahul.appmanager.R;
import com.rahul.appmanager.AppInfo;
import com.rahul.appmanager.AppRecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SystemAppFragment extends Fragment {

    RecyclerView mRecyclerView;
    PackageManager mPackageManager;
    List<AppInfo> appList;
    ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        appList = new ArrayList<>();
        mPackageManager = getActivity().getPackageManager();
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                retrieveAppInfo();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Collections.sort(appList, new Comparator<AppInfo>() {
                    @Override
                    public int compare(AppInfo lhs, AppInfo rhs) {
                        return lhs.getAppName().compareTo(rhs.getAppName());
                    }
                });
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(new AppRecyclerAdapter(getActivity(), appList));
                mProgressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    private void retrieveAppInfo() {
        for (PackageInfo packageInfo : mPackageManager.getInstalledPackages(PackageManager.GET_META_DATA)){
            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(getActivity().getPackageManager()).toString());
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setVersion(packageInfo.versionName);
            appInfo.setAppImage(packageInfo.applicationInfo.loadIcon(getActivity().getPackageManager()));
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1)
                appList.add(appInfo);
        }
    }
}
