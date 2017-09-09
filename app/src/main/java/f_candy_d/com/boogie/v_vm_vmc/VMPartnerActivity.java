package f_candy_d.com.boogie.v_vm_vmc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by daichi on 9/9/17.
 */

abstract public class VMPartnerActivity extends AppCompatActivity {
    
    private ActivityViewModel mViewModel;
    
    abstract protected ActivityViewModel onCreateViewModel();
    
    protected ActivityViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = onCreateViewModel();
        if (mViewModel != null) {
            mViewModel.setPartnerActivity(this);
            mViewModel.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mViewModel != null) {
            mViewModel.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mViewModel != null) {
            mViewModel.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mViewModel != null) {
            mViewModel.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mViewModel != null) {
            mViewModel.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mViewModel != null) {
            mViewModel.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            mViewModel.onDestroy();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mViewModel != null) {
            mViewModel.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mViewModel != null) {
            mViewModel.onResultReturned(requestCode, resultCode, data);
        }
    }
}
