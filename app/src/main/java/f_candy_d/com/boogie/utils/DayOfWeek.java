package f_candy_d.com.boogie.utils;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by daichi on 17/08/30.
 */

public enum DayOfWeek implements Quantizable {

    MONDAY {@Override public int quantize() {return Calendar.MONDAY;}},
    TUESDAY {@Override public int quantize() {return Calendar.TUESDAY;}},
    WEDNESDAY {@Override public int quantize() {return Calendar.WEDNESDAY;}},
    THURSDAY {@Override public int quantize() {return Calendar.THURSDAY;}},
    FRIDAY {@Override public int quantize() {return Calendar.FRIDAY;}},
    SATURDAY {@Override public int quantize() {return Calendar.SATURDAY;}},
    SUNDAY {@Override public int quantize() {return Calendar.SUNDAY;}};

    public static DayOfWeek from(@NonNull Calendar calendar) {
        DayOfWeek[] dayOfWeeks = values();
        final int dow = calendar.get(Calendar.DAY_OF_WEEK);
        for (DayOfWeek dayOfWeek : dayOfWeeks) {
            if (dayOfWeek.quantize() == dow) {
                return dayOfWeek;
            }
        }
        return null;
    }
}
