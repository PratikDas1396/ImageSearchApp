package com.imagesearch.app.CommonClass;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class AppPermission<T> {

    private static String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    //    Call from Activity
    public static boolean checkAppPermissionsGranted(Activity activity) {
        int count = 0;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) == PermissionChecker.PERMISSION_DENIED) {
                count++;
            }
        }
        return (count == 0);
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity) {
        int count = 0;
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                count++;
            }
        }
        return (count == 0);
    }

    public static int requestAppPermission(Activity activity, int requestCode) {
        ArrayList<String> request = new ArrayList<String>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) == PermissionChecker.PERMISSION_DENIED) {
                request.add(permission);
            }
        }
        ActivityCompat.requestPermissions(activity, request.toArray(new String[0]), requestCode);
        return requestCode;
    }
    //    Call from Activity


    // Call from Fragment
    public static boolean checkAppPermissionsGranted(Context context) {
        int count = 0;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_DENIED) {
                count++;
            }
        }
        return (count == 0);
    }

    public static boolean shouldShowRequestPermissionRationale(Fragment fragment) {
        int count = 0;
        for (String permission : permissions) {
            if (fragment.shouldShowRequestPermissionRationale(permission)) {
                count++;
            }
        }
        return (count == 0);
    }

    public static int requestAppPermission(Fragment fragment, Context context, int requestCode) {
        ArrayList<String> request = new ArrayList<String>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_DENIED) {
                request.add(permission);
            }
        }
        fragment.requestPermissions(request.toArray(new String[0]), requestCode);
        return requestCode;
    }
    //Call from Fragment

}
