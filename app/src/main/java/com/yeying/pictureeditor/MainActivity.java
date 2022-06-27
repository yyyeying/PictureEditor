package com.yeying.pictureeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mBtnEdit;
    private TextView mTextTop;

    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTextTop = (TextView) findViewById(R.id.main_top);
        mBtnEdit = (Button) findViewById(R.id.button_edit);
        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionManager.checkReadExternalStoragePermission(MainActivity.this);
                goToAlbumn();
                // goToEditAvtivity();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("MainActivity", "permissions: " + permissions[0] + ", result: " + grantResults[0]);
        switch (requestCode) {
            case RequestCode.EXTERNAL_STORAGE_REQUEST:
                if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this, "获取存储权限成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "获取存储权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void goToAlbumn() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RequestCode.IMAGE_REQUEST);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        Log.d("MainActivity", "image path: " + path);
        return path;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MainActivity", "request code: " + requestCode + ", result code: " + requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCode.IMAGE_REQUEST:
                if (resultCode == RESULT_OK) {
                    handleImage(data);
                    goToEditAvtivity();
                }
        }
    }

    private void handleImage(Intent intent) {
        Uri uri = intent.getData();
        Log.d("MainAvtivity", "image uri: " + uri.toString());
        if (DocumentsContract.isDocumentUri(this, uri)) {
            Log.d("MainAvtivity", "uri is document");
            String docId = DocumentsContract.getDocumentId(uri);
        } else if (uri.getScheme().equals("content")) {
            Log.d("MainActivity", "uri is content");
            imagePath = getImagePath(uri, null);
        } else if (uri.getScheme().equals("file")){
            imagePath = uri.getPath();
        }
    }

    private void goToEditAvtivity() {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("imagePath", imagePath);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}