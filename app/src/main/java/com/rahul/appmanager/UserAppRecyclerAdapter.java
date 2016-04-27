package com.rahul.appmanager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rahul on 27 Apr 2016.
 */
public class UserAppRecyclerAdapter extends RecyclerView.Adapter<UserAppRecyclerAdapter.CustomViewHolder>{

    Context mContext;
    LayoutInflater inflater;
    List<UserAppInfo> list;
    public UserAppRecyclerAdapter(Context mContext, List<UserAppInfo> list){
        inflater = LayoutInflater.from(mContext);
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_user_app, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        UserAppInfo info = list.get(position);
        holder.appName.setText(info.getAppName());
        holder.packageName.setText(info.getPackageName());
        holder.appImage.setImageDrawable(info.getAppImage());
        holder.version.setText("v "+info.getVersion());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView appName, packageName, version;
        ImageView appImage;
        public CustomViewHolder(View itemView) {
            super(itemView);
            appName = (TextView) itemView.findViewById(R.id.app_name);
            appImage = (ImageView) itemView.findViewById(R.id.image_app);
            packageName = (TextView) itemView.findViewById(R.id.package_name);
            version = (TextView) itemView.findViewById(R.id.version);
        }
    }
}
