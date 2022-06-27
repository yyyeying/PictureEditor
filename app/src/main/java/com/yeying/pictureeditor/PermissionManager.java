package com.yeying.pictureeditor;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class PermissionManager {
    // public static final int EXTERNAL_STORAGE_REQUEST = 1;

    public static void checkReadExternalStoragePermission(Activity activity) {
        if (PackageManager.PERMISSION_GRANTED
                != ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setTitle("存储读取权限不可用")
                    .setMessage("是否立即开启存储读取权限")
                    .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(
                                    activity,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    RequestCode.EXTERNAL_STORAGE_REQUEST);
                        }
                    })
                    .setNegativeButton("放弃", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(activity, "您暂时无法使用此功能", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }).show();
        } else {
            return;
        }
    }
}
