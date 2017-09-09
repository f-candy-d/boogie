package f_candy_d.com.boogie.utils;

import java.util.Calendar;

/**
 * Created by daichi on 9/9/17.
 */

public class CalendarUtil {

    private CalendarUtil() {}

    /**
     * Check only YYYY/MM/DD
     */
    public static boolean isSameDate(Calendar cal1, Calendar cal2) {
        return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Check only hh:mm:ss
     */
    public static boolean isSameTime(Calendar cal1, Calendar cal2) {
        return (cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY) &&
                cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE) &&
                cal1.get(Calendar.SECOND) == cal2.get(Calendar.SECOND));
    }
}
