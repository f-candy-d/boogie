package f_candy_d.com.boogie;

import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;

import static f_candy_d.com.boogie.AppConstants.HOUR_IN_MINUTES;

/**
 * Created by daichi on 9/8/17.
 */

final public class PartsOfDay {

    private PartsOfDay() {}

    public enum Flags {
        /**
         * The time morning starts in minutes (since midnight).
         * Defined time is 4 am.
         */
        MORNING_START(4 * HOUR_IN_MINUTES),

        /**
         * The time afternoon starts in minutes (since midnight).
         * Defined time is 12 pm.
         */
        AFTERNOON_START(12 * HOUR_IN_MINUTES),

        /**
         * The time evening starts in minutes (since midnight).
         * Defined time is 5 pm.
         */
        EVENING_START(17 * HOUR_IN_MINUTES),

        /**
         * The time night starts in minutes (since midnight).
         * Defined time is 9 pm.
         */
        NIGHT_START(21 * HOUR_IN_MINUTES);

        final private int mTime;

        Flags(int timeSinceMidnightInMinutes) {
            mTime = timeSinceMidnightInMinutes;
        }

        int getTimeSinceMidnightInMinutes() {
            return mTime;
        }

        Flags nextFlag() {
            switch (this) {
                case MORNING_START: return AFTERNOON_START;
                case AFTERNOON_START: return EVENING_START;
                case EVENING_START: return NIGHT_START;
                case NIGHT_START: return MORNING_START;
            }
            return null;
        }
    }

    public static long getNearestDatetime(Calendar base, Flags flag) {
        final long baseTime = base.getTimeInMillis();
        final long prevDate = getPreviousDatetime(base, flag);
        final long nextDate = getNextDatetime(base, flag);
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(nextDate);

        return (prevDate <= baseTime && baseTime < getPreviousDatetime(date, flag.nextFlag()))
                ? prevDate
                : nextDate;
    }

    public static long getNextDatetime(Calendar base, Flags flag) {
        Calendar nextDate = Calendar.getInstance();
        nextDate.setTimeInMillis(base.getTimeInMillis());
        setTimeSinceMidnightInMinutes(nextDate, flag.getTimeSinceMidnightInMinutes());
        nextDate.set(Calendar.SECOND, 0);

        if (base.after(nextDate)) {
            nextDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        return nextDate.getTimeInMillis();
    }

    public static long getPreviousDatetime(Calendar base, Flags flag) {
        Calendar prevDate = Calendar.getInstance();
        prevDate.setTimeInMillis(base.getTimeInMillis());
        setTimeSinceMidnightInMinutes(prevDate, flag.getTimeSinceMidnightInMinutes());
        prevDate.set(Calendar.SECOND, 0);

        if (base.before(prevDate)) {
            prevDate.add(Calendar.DAY_OF_MONTH, - 1);
        }

        return prevDate.getTimeInMillis();
    }

    private static void setTimeSinceMidnightInMinutes(Calendar calendar, int timeSinceMidnightInMinutes) {
        int h = timeSinceMidnightInMinutes / HOUR_IN_MINUTES;
        int m = timeSinceMidnightInMinutes - h * HOUR_IN_MINUTES;
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);
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

        long m = PartsOfDay.getNextDatetime(baseTime, PartsOfDay.Flags.MORNING_START);
        long a = PartsOfDay.getNextDatetime(baseTime, PartsOfDay.Flags.AFTERNOON_START);
        long e = PartsOfDay.getNextDatetime(baseTime, PartsOfDay.Flags.EVENING_START);
        long n = PartsOfDay.getNextDatetime(baseTime, PartsOfDay.Flags.NIGHT_START);

        tmp.setTimeInMillis(m);
        Log.d("mylog", "next date morning starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(a);
        Log.d("mylog", "next date afternoon starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(e);
        Log.d("mylog", "next date evening starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(n);
        Log.d("mylog", "next date night starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));

        m = PartsOfDay.getPreviousDatetime(baseTime, PartsOfDay.Flags.MORNING_START);
        a = PartsOfDay.getPreviousDatetime(baseTime, PartsOfDay.Flags.AFTERNOON_START);
        e = PartsOfDay.getPreviousDatetime(baseTime, PartsOfDay.Flags.EVENING_START);
        n = PartsOfDay.getPreviousDatetime(baseTime, PartsOfDay.Flags.NIGHT_START);

        tmp.setTimeInMillis(m);
        Log.d("mylog", "previous date morning starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(a);
        Log.d("mylog", "previous date afternoon starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(e);
        Log.d("mylog", "previous date evening starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(n);
        Log.d("mylog", "previous date night starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));

        m = PartsOfDay.getNearestDatetime(baseTime, PartsOfDay.Flags.MORNING_START);
        a = PartsOfDay.getNearestDatetime(baseTime, PartsOfDay.Flags.AFTERNOON_START);
        e = PartsOfDay.getNearestDatetime(baseTime, PartsOfDay.Flags.EVENING_START);
        n = PartsOfDay.getNearestDatetime(baseTime, PartsOfDay.Flags.NIGHT_START);

        tmp.setTimeInMillis(m);
        Log.d("mylog", "nearest date morning starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(a);
        Log.d("mylog", "nearest date afternoon starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(e);
        Log.d("mylog", "nearest date evening starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(n);
        Log.d("mylog", "nearest date night starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
    }
}
