package com.yeying.pictureeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class SplashActivity extends AppCompatActivity {
    private ImageView mImageSplash;
    private TextView mTextBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView() {
        mImageSplash = findViewById(R.id.splash_image);
        mTextBottom = findViewById(R.id.splash_text);
        initImage();
    }

    private void initImage(){
        mImageSplash.setImageDrawable(getResources().getDrawable(R.drawable.splash_img_default));
        ScaleAnimation scaleAnim = new ScaleAnimation(
                1.0f,
                1.2f,
                1.0f,
                1.2f,
                Animation.RELATIVE_TO_PARENT,
                0.5f,
                Animation.RELATIVE_TO_PARENT,
                0.5f);
        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                goToMainActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mImageSplash.startAnimation(scaleAnim);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}