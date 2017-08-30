package f_candy_d.com.boogie.utils;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by daichi on 17/08/30.
 */

public enum DayOfWeek implements Quantizable {
    ;


    @Override
    public int quantize() {
        return 0;
    }

    public static DayOfWeek from(@NonNull Calendar calendar) {
        return null;
    }
}
