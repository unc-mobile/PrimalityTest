package com.example.servicepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Intent string for our ResultReceiver.
    private static final String COMPUTED_PRIMALITY = "com.example.servicepractice.COMPUTED";

    private static final String LOG_TAG = "PRIMALITY_MAIN";

    private TextView mResult;
    private Button mCheck;
    private EditText mNumber;

    private ResultReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Primality");

        mResult = findViewById(R.id.result);
        mCheck = findViewById(R.id.check);
        mNumber = findViewById(R.id.number);
        mNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mResult.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mReceiver = new ResultReceiver();
        registerReceiver(mReceiver, new IntentFilter(COMPUTED_PRIMALITY));
    }

    private int getNumber() {
        Editable editable = mNumber.getText();
        if (editable == null) {
            return PrimalityTest.INVALID;
        }

        String text = editable.toString();
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return PrimalityTest.INVALID;
        }
    }

    public void onClick(View view) {
        if (view == mCheck) {
            mCheck.setEnabled(false);
            mResult.setText("Calculating...");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                    new Intent(COMPUTED_PRIMALITY), 0);
            Intent intent = new Intent(this, PrimalityTest.class);
            intent.putExtra(PrimalityTest.PENDING_INTENT, pendingIntent);
            intent.putExtra(PrimalityTest.EXTRA_NUMBER, getNumber());
            startService(intent);
        }
    }

    private void setResult(boolean prime) {
        mResult.setText(prime ? "YES!" : "NO!");
    }

    private class ResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = getResultCode();
            Log.d(LOG_TAG, "onReceive\tresultCode: " + resultCode);
            MainActivity.this.setResult(resultCode == PrimalityTest.RESULT_PRIME);
            mCheck.setEnabled(true);
        }
    }
}
