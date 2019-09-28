package com.androidtutorialshub.animatedgradientbackground;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private AnimationDrawable animationDrawable;


    protected AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
    protected AlphaAnimation fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        final TextView InitText = (TextView) findViewById(R.id.InitText);
        fadeIn.setDuration(1200);
        fadeIn.setFillAfter(true);
        InitText.startAnimation(fadeIn);



        final Button HelloButton = (Button) findViewById(R.id.HelloButton);
        HelloButton.setVisibility(View.VISIBLE);
        HelloButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelloButton.setVisibility(View.GONE);
              //  stopButton.setVisibility(View.VISIBLE);
                InitText.startAnimation(fadeOut);
                fadeOut.setDuration(1200);
                fadeOut.setFillAfter(true);
                fadeOut.setStartOffset(1000);
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            animationDrawable.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
        }
    }
}
