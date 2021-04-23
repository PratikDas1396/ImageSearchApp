package com.imagesearch.app.CommonClass;


import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;

import com.imagesearch.app.R;

public class CustomDialog {

    public static Dialog getAllPermissionDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_app_permission);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        return dialog;
    }
}
