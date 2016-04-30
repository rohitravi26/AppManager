package com.rahul.appmanager.Fragment;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahul.appmanager.BuildConfig;
import com.rahul.appmanager.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rahul on 30 Apr 2016.
 */
public class SystemOverviewFragment extends Fragment{
    TextView manuFactTV, brandTV, modelTV, boardTV, hardwareTV, serialTV, androiIdTV, displayTV, resolutionTV, densityTV, versionTV, versionNameTV, apiLevelTV, fingerprintTV, buildIdTV, buildTimeTV;
    View layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_system_overview, container, false);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
        try {
            setTextViews();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void setTextViews() throws PackageManager.NameNotFoundException {
        manuFactTV.setText(Build.MANUFACTURER);
        modelTV.setText(Build.MODEL);
        brandTV.setText(Build.BRAND);
        serialTV.setText(Build.SERIAL);
        hardwareTV.setText(Build.HARDWARE);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
//        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
//        double screenInches = Math.sqrt(x+y);
        displayTV.setText(BuildConfig.FLAVOR);
        boardTV.setText(Build.BOARD);
        androiIdTV.setText(Settings.Secure.getString(getActivity().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        densityTV.setText(getResources().getDisplayMetrics().densityDpi + " ppi");
        resolutionTV.setText(getResources().getDisplayMetrics().widthPixels + " x " + getResources().getDisplayMetrics().heightPixels + " pixels");
        versionTV.setText(Build.VERSION.RELEASE);
        versionNameTV.setText(getVersionName(Build.VERSION.SDK_INT));
        apiLevelTV.setText(Build.VERSION.SDK);
        fingerprintTV.setText(Build.FINGERPRINT);
        buildIdTV.setText(Build.ID);
        buildTimeTV.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date(Build.TIME)));
    }


    private String getVersionName(int sdkInt) {
        switch (sdkInt){
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
                return "ICE_CREAM_SANDWICH";
            case Build.VERSION_CODES.JELLY_BEAN:
                return "JELLY_BEAN";
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
                return "ICE_CREAM_SANDWICH_MR1";
            case Build.VERSION_CODES.JELLY_BEAN_MR1:
                return "JELLY_BEAN_MR1";
            case Build.VERSION_CODES.JELLY_BEAN_MR2:
                return "JELLY_BEAN_MR2";
            case Build.VERSION_CODES.KITKAT:
                return "KITKAT";
            case Build.VERSION_CODES.KITKAT_WATCH:
                return "KITKAT_WATCH";
            case Build.VERSION_CODES.LOLLIPOP:
                return "LOLLIPOP";
            case Build.VERSION_CODES.LOLLIPOP_MR1:
                return "LOLLIPOP_MR1";
            case Build.VERSION_CODES.M:
                return "MARSHMALLOW";
        }
        return "N/A";
    }

    private void initializeView() {
        manuFactTV = (TextView) layout.findViewById(R.id.manufacture_tv);
        boardTV = (TextView) layout.findViewById(R.id.board_tv);
        modelTV = (TextView) layout.findViewById(R.id.model_tv);
        brandTV = (TextView) layout.findViewById(R.id.brand_tv);
        hardwareTV = (TextView) layout.findViewById(R.id.hardware_tv);
        serialTV = (TextView) layout.findViewById(R.id.serial_tv);
        androiIdTV = (TextView) layout.findViewById(R.id.android_id_tv);
        displayTV = (TextView) layout.findViewById(R.id.display_tv);
        resolutionTV = (TextView) layout.findViewById(R.id.screen_reso_tv);
        densityTV = (TextView) layout.findViewById(R.id.density_tv);
        versionTV = (TextView) layout.findViewById(R.id.version_tv);
        versionNameTV = (TextView) layout.findViewById(R.id.version_name_tv);
        apiLevelTV = (TextView) layout.findViewById(R.id.api_level_tv);
        fingerprintTV = (TextView) layout.findViewById(R.id.fingerprint_tv);
        buildIdTV = (TextView) layout.findViewById(R.id.build_id_tv);
        buildTimeTV = (TextView) layout.findViewById(R.id.build_time_tv);
    }
}
