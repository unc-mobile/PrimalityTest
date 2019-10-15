package com.example.servicepractice;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class PrimalityTest extends Service {
    // Key to retrieve the number to test.
    public static final String EXTRA_NUMBER = "com.example.servicepractice.NUMBER";

    // Key to retrieve the intent to send when the task completes.
    public static final String PENDING_INTENT = "com.example.servicepractice.PENDING";

    // Default integer, in case the Intent does not contain EXTRA_NUMBER.
    public static final int INVALID = -1;

    // Result codes to send back to the calling Activity.
    public static final int RESULT_NOT_PRIME = 1;
    public static final int RESULT_PRIME = 2;

    private static final String LOG_TAG = "PRIMALITY";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
