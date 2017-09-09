package f_candy_d.com.boogie.v_vm_vmc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by daichi on 9/9/17.
 */

abstract public class ActivityViewModel {

    private VMPartnerActivity mPartnerActivity;

    /**
     * This will be only called in #onCreate() of a partner activity
     */
    void setPartnerActivity(VMPartnerActivity partnerActivity) {
        mPartnerActivity = partnerActivity;
    }

    /**
     * This will be only used by VMController class
     */
    VMPartnerActivity getPartnerActivity() {
        return mPartnerActivity;
    }

    /**
     * The following methods will be called
     * in the Activity-lifecycle methods
     */

    protected void onCreate(@Nullable Bundle savedInstanceState) {}

    protected void onStart() {}

    protected void onRestart() {}

    protected void onResume() {}

    protected void onPause() {}

    protected void onStop() {}

    protected void onDestroy() {}

    protected void onSaveInstanceState(Bundle outState) {}

    protected void onResultReturned(int requestCode, int resultCode, Intent data) {}

    /**
     * Methods for screen transition
     */

    protected void translateVm(Class<ActivityViewModel> modelClass, Bundle extras) {
        VMController.translateVm(this, modelClass, extras);
    }

    protected void translateVmForResult
            (int requestCode, Class<ActivityViewModel> modelClass, Bundle extras) {

        VMController.translateVmForResult(requestCode, this, modelClass, extras);
    }
}
