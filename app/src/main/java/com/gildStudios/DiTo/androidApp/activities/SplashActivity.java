package com.gildStudios.DiTo.androidApp.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.gildStudios.DiTo.androidApp.R;
import com.gildStudios.DiTo.androidApp.adapters.SplashListenerAdapter;
import com.gildStudios.DiTo.androidApp.services.OnAppKilled;
import com.github.alexjlockwood.kyrie.KyrieDrawable;

public class SplashActivity extends AppCompatActivity {
    private final static int SPLASH_waitingTime = 1750; // This is 1.75 seconds
    private final static String CHANNEL_ID = "notifChannel";

    private KyrieDrawable splashAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        OnAppKilled.enqueueWork(this, new Intent());

        final ImageView tV = findViewById(R.id.welcomeMessage);
        final ImageView iV = findViewById(R.id.splashLogo);

        splashAnimation = KyrieDrawable.create(this, R.drawable.transition_splash);
        iV.setImageDrawable(splashAnimation);
        splashAnimation.addListener(new SplashListenerAdapter(tV,this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        splashAnimation.start();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
