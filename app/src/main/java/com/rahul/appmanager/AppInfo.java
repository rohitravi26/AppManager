package com.rahul.appmanager;

import android.graphics.drawable.Drawable;

/**
 * Created by Rahul on 27 Apr 2016.
 */
public class AppInfo {
    String appName, packageName, version;

    public Drawable getAppImage() {
        return appImage;
    }

    public void setAppImage(Drawable appImage) {

        this.appImage = appImage;
    }

    Drawable appImage;

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppName() {

        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVersion() {
        return version;
    }
}
