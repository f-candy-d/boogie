package f_candy_d.com.boogie;

import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by daichi on 9/8/17.
 */

public class AppTimeDefine {

    /**
     * 1 hour = 60 minutes.
     */
    public static final int HOUR_IN_MINUTES = 60;

    /**
     * The time morning starts in minutes (since midnight).
     * Defined time is 5 am.
     *
     * TIME_MORNING_START <= (Morning Time) < TIME_AFTERNOON_START
     */
    private static final int TIME_MORNING_START = 5 * HOUR_IN_MINUTES;

    /**
     * The time afternoon starts in minutes (since midnight).
     * Defined time is 12 pm.
     *
     * TIME_AFTERNOON_START <= (Afternoon Time) < TIME_EVENING_START
     */
    private static final int TIME_AFTERNOON_START = 12 * HOUR_IN_MINUTES;

    /**
     * The time evening starts in minutes (since midnight).
     * Defined time is 5 pm.
     *
     * TIME_EVENING_START <= (Evening Time) < TIME_NIGHT_START
     */
    private static final int TIME_EVENING_START = 17 * HOUR_IN_MINUTES;

    /**
     * The time night starts in minutes (since midnight).
     * Defined time is 9 pm.
     *
     * TIME_NIGHT_START <= (Night Time) < NEXT_DAY_MIDNIGHT
     * or
     * MIDNIGHT <= (Night Time) < TIME_MORNING_START
     */
    private static final int TIME_NIGHT_START = 21 * HOUR_IN_MINUTES;

    private AppTimeDefine() {}

    /**
     * Flags used in #getNearestDatetime() , #getNextDatetime()
     * and #getPreviousDatetime() as a second parameter
     */
    public static final int MORNING_START = 0;
    public static final int AFTERNOON_START = 1;
    public static final int EVENING_START = 2;
    public static final int NIGHT_START = 3;

    private static int convertFlagToTime(int flag) {
        switch (flag) {
            case MORNING_START: return TIME_MORNING_START;
            case AFTERNOON_START: return TIME_AFTERNOON_START;
            case EVENING_START: return TIME_EVENING_START;
            case NIGHT_START: return TIME_NIGHT_START;
            default: throw new IllegalArgumentException(
                    "Can not understand the flag (= " + String.valueOf(flag) + ")");
        }
    }

    private static int getNextFlag(int flag) {
        switch (flag) {
            case MORNING_START: return AFTERNOON_START;
            case AFTERNOON_START: return EVENING_START;
            case EVENING_START: return NIGHT_START;
            case NIGHT_START: return MORNING_START;
            default: throw new IllegalArgumentException(
                    "Can not understand the flag (= " + String.valueOf(flag) + ")");
        }
    }

    /**
     * @param base Base datetime
     * @param flag One of the above constants (See line 53)
     * @return Time in millis
     */
    public static long getNearestDatetime(Calendar base, int flag) {
        final long baseTime = base.getTimeInMillis();
        final long prevDate = getPreviousDatetime(base, flag);
        final long nextDate = getNextDatetime(base, flag);
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(nextDate);

        return (prevDate <= baseTime && baseTime < getPreviousDatetime(date, getNextFlag(flag)))
                ? prevDate
                : nextDate;
    }

    /**
     *
     * @param base Base datetime
     * @param flag One of the above constants (See line 53)
     * @return Time in millis
     */
    public static long getNextDatetime(Calendar base, int flag) {
        Calendar nextDate = Calendar.getInstance();
        nextDate.setTimeInMillis(base.getTimeInMillis());
        setTimeSinceMidnightInMinutes(nextDate, convertFlagToTime(flag));
        nextDate.set(Calendar.SECOND, 0);

        if (base.after(nextDate)) {
            nextDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        return nextDate.getTimeInMillis();
    }

    /**
     *
     * @param base Base datetime
     * @param flag One of the above constants (See line 53)
     * @return Time in millis
     */
    public static long getPreviousDatetime(Calendar base, int flag) {
        Calendar prevDate = Calendar.getInstance();
        prevDate.setTimeInMillis(base.getTimeInMillis());
        setTimeSinceMidnightInMinutes(prevDate, convertFlagToTime(flag));
        prevDate.set(Calendar.SECOND, 0);

        if (base.before(prevDate)) {
            prevDate.add(Calendar.DAY_OF_MONTH, - 1);
        }

        return prevDate.getTimeInMillis();
    }

    /**
     * Test of the following methods.
     *
     * #getNextDatetime()
     * #getPreviousDatetime()
     * #getNearestDatetime()
     */
    public static void testGetDatetimeMethods() {
        final Calendar baseTime = Calendar.getInstance();
        Log.d("mylog", "base time -> " + DateFormat.format("yyyy-MM-dd hh:mm a", baseTime));
        Calendar tmp = Calendar.getInstance();

        long m = AppTimeDefine.getNextDatetime(baseTime, AppTimeDefine.MORNING_START);
        long a = AppTimeDefine.getNextDatetime(baseTime, AppTimeDefine.AFTERNOON_START);
        long e = AppTimeDefine.getNextDatetime(baseTime, AppTimeDefine.EVENING_START);
        long n = AppTimeDefine.getNextDatetime(baseTime, AppTimeDefine.NIGHT_START);

        tmp.setTimeInMillis(m);
        Log.d("mylog", "next date morning starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(a);
        Log.d("mylog", "next date afternoon starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(e);
        Log.d("mylog", "next date evening starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(n);
        Log.d("mylog", "next date night starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));

        m = AppTimeDefine.getPreviousDatetime(baseTime, AppTimeDefine.MORNING_START);
        a = AppTimeDefine.getPreviousDatetime(baseTime, AppTimeDefine.AFTERNOON_START);
        e = AppTimeDefine.getPreviousDatetime(baseTime, AppTimeDefine.EVENING_START);
        n = AppTimeDefine.getPreviousDatetime(baseTime, AppTimeDefine.NIGHT_START);

        tmp.setTimeInMillis(m);
        Log.d("mylog", "previous date morning starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(a);
        Log.d("mylog", "previous date afternoon starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(e);
        Log.d("mylog", "previous date evening starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(n);
        Log.d("mylog", "previous date night starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));

        m = AppTimeDefine.getNearestDatetime(baseTime, AppTimeDefine.MORNING_START);
        a = AppTimeDefine.getNearestDatetime(baseTime, AppTimeDefine.AFTERNOON_START);
        e = AppTimeDefine.getNearestDatetime(baseTime, AppTimeDefine.EVENING_START);
        n = AppTimeDefine.getNearestDatetime(baseTime, AppTimeDefine.NIGHT_START);

        tmp.setTimeInMillis(m);
        Log.d("mylog", "nearest date morning starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(a);
        Log.d("mylog", "nearest date afternoon starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(e);
        Log.d("mylog", "nearest date evening starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(n);
        Log.d("mylog", "nearest date night starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
    }

    private static void setTimeSinceMidnightInMinutes(Calendar calendar, int timeSinceMidnightInMinutes) {
        int h = timeSinceMidnightInMinutes / HOUR_IN_MINUTES;
        int m = timeSinceMidnightInMinutes - h * HOUR_IN_MINUTES;
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
    }
}
