package com.yeying.pictureeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

public class EditActivity extends AppCompatActivity {

    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle bundle = this.getIntent().getExtras();
        imagePath = bundle.getString("imagePath");
        Log.d("EditActivity", "image path: " + imagePath);
    }
}