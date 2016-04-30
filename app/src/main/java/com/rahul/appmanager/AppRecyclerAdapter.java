package com.rahul.appmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 27 Apr 2016.
 */
public class AppRecyclerAdapter extends RecyclerView.Adapter<AppRecyclerAdapter.CustomViewHolder>{

    Context mContext;
    LayoutInflater inflater;
    List<AppInfo> list;
    public AppRecyclerAdapter(Context mContext, List<AppInfo> list){
        inflater = LayoutInflater.from(mContext);
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_apps, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final AppInfo info = list.get(position);
        holder.appName.setText(info.getAppName());
        holder.packageName.setText(info.getPackageName());
        holder.appImage.setImageDrawable(info.getAppImage());
        holder.version.setText("v " + info.getVersion());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> list = new ArrayList<String>();
                try {
                    String[] permissions = mContext.getPackageManager().getPackageInfo(info.getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
                    if (permissions != null){
                        for (String s: permissions) {
                            if (s.startsWith("android."))
                                list.add(s);
                        }
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (list.size() == 0)
                    list.add("No permission required");
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Permissions");
                final CharSequence options[] = new CharSequence[list.size()];
                list.toArray(options);
                builder.setItems(options,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext.getApplicationContext(), Constants.permissionDescription.get(list.get(which).split("\\.")[2]), Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                builder.create().show();
            }
        });
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
