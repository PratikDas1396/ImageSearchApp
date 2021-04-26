package com.imagesearch.app.CommonClass;


import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;

import com.imagesearch.app.R;

public class CustomDialog {

    public static Dialog getAllPermissionDialog(Context context) {
        return getDialog(context, R.layout.dialog_app_permission, false);
    }

    public static Dialog getLoadingDialog(Context context) {
        return getDialog(context, R.layout.dialog_loader, false);
    }

    private static Dialog getDialog(Context context, int layout, boolean setCancelable){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(setCancelable);
        return dialog;
    }
}
