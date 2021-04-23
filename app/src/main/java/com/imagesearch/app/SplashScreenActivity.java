package com.imagesearch.app;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.imagesearch.app.CommonClass.AppPermission;
import com.imagesearch.app.CommonClass.CustomDialog;

public class SplashScreenActivity extends AppCompatActivity {

    public static final int READ_FILE_PERMISSION_CODE = 0x30;

    public static final int SCREEN_WAIT_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        if (AppPermission.checkAppPermissionsGranted(this)) {
            runMainApp();
        } else {
            if (AppPermission.shouldShowRequestPermissionRationale(this)) {
                Dialog dialog = CustomDialog.getAllPermissionDialog(this);
                Button btnOk = (Button) dialog.findViewById(R.id.btnGrantPermission);
                btnOk.setOnClickListener(v -> {
                    dialog.dismiss();
                    AppPermission.requestAppPermission(this, READ_FILE_PERMISSION_CODE);
                });
                dialog.show();
            }
            else{
                AppPermission.requestAppPermission(this, READ_FILE_PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        runMainApp();
    }

    private void runMainApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SCREEN_WAIT_TIME);
    }
}
