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
        final int testNumber = intent.getIntExtra(EXTRA_NUMBER, INVALID);
        final PendingIntent pendingIntent = intent.getParcelableExtra(PENDING_INTENT);

        Thread testThread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isPrime = true;
                if (testNumber <= 1) {
                    isPrime = false;
                    Log.d(LOG_TAG, "testNumber is " + testNumber + ", so not prime");
                } else {
                    for (int i = 2; i < testNumber; ++i) {
                        if (testNumber % i == 0) {
                            Log.d(LOG_TAG, i + " divides " + testNumber + ", so not prime");
                            isPrime = false;
                            break;
                        }
                    }
                }
                try {
                    int code = isPrime ? RESULT_PRIME : RESULT_NOT_PRIME;
                    Log.d(LOG_TAG, "isPrime: " + isPrime);
                    Log.d(LOG_TAG, "code: " + code);
                    pendingIntent.send(code);
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
                stopSelf();
            }
        });
        testThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
