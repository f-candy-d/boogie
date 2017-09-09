package f_candy_d.com.boogie.v_vm_vmc;

import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daichi on 9/9/17.
 */

class VMController {

    // {@key:ViewModelClass.class.getName()  @value:ViewClass.class}
    private final static Map<String, Class<? extends VMPartnerActivity>> VM_V_PAIRING_MAP;

    static {
        VM_V_PAIRING_MAP = new HashMap<>();

        // Pairing of view & view-model
    }

    private VMController() {}

    public static void translateVm
            (ActivityViewModel from, Class<? extends ActivityViewModel> to, Bundle extras) {

        Intent intent = makeIntent(from, to, extras);
        from.getPartnerActivity().startActivity(intent);
    }

    public static void translateVmForResult
            (int requestCode, ActivityViewModel from, Class<? extends ActivityViewModel> to, Bundle extras) {

        Intent intent = makeIntent(from, to, extras);
        from.getPartnerActivity().startActivityForResult(intent, requestCode);
    }

    private static Intent makeIntent
            (ActivityViewModel from, Class<? extends ActivityViewModel> to, Bundle extras) {

        Class<?> viewClass = VM_V_PAIRING_MAP.get(to.getName());
        if (viewClass == null) {
            throw new IllegalArgumentException(
                    "Pairing of view and view-model (" + to.getName() + ") does not exist!");
        }

        Intent intent = new Intent(from.getPartnerActivity(), viewClass);
        if (extras != null) {
            intent.putExtras(extras);
        }

        return intent;
    }
}
