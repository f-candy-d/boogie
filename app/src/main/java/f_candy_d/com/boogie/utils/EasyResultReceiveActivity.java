package f_candy_d.com.boogie.utils;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by daichi on 9/6/17.
 */

abstract public class EasyResultReceiveActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1000;

    final protected void startActivityForResult(Intent intent, OnActivityResultListener resultListener) {
        if (resultListener != null) {
            ActivityResultReceiver.getInstance().setListener(resultListener);
        }

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        OnActivityResultListener listener = ActivityResultReceiver.getInstance().getListener();
        if (listener != null) {
            final boolean isOk = (resultCode == RESULT_OK);
            listener.onResult(isOk, data);
            ActivityResultReceiver.getInstance().setListener(null);
        }
    }

    /**
     * OnActivityResultListener interface
     */
    protected interface OnActivityResultListener {
        void onResult(boolean isOk, @Nullable Intent data);
    }

    /**
     * This is singleton class
     */
    private static class ActivityResultReceiver {

        private static final ActivityResultReceiver mInstance = new ActivityResultReceiver();
        OnActivityResultListener mListener;

        static ActivityResultReceiver getInstance() {
            return mInstance;
        }

        void setListener(OnActivityResultListener listener) {
            mListener = listener;
        }

        @Nullable
        OnActivityResultListener getListener() {
            return mListener;
        }
    }
}
