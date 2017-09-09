package f_candy_d.com.boogie.utils;

import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;

import static f_candy_d.com.boogie.AppConstants.HOUR_IN_MINUTES;

/**
 * Created by daichi on 9/8/17.
 */

final public class PartsOfDay {

    private PartsOfDay() {}

    public enum Parts {
        /**
         * The time morning starts in minutes (since midnight).
         * Defined time is 4 am.
         */
        MORNING(4 * HOUR_IN_MINUTES),

        /**
         * The time afternoon starts in minutes (since midnight).
         * Defined time is 12 pm.
         */
        AFTERNOON(12 * HOUR_IN_MINUTES),

        /**
         * The time evening starts in minutes (since midnight).
         * Defined time is 5 pm.
         */
        EVENING(17 * HOUR_IN_MINUTES),

        /**
         * The time night starts in minutes (since midnight).
         * Defined time is 9 pm.
         */
        NIGHT(21 * HOUR_IN_MINUTES);

        final private int mTime;

        Parts(int timeSinceMidnightInMinutes) {
            mTime = timeSinceMidnightInMinutes;
        }

        int getStartTimeSinceMidnightInMinutes() {
            return mTime;
        }

        public Parts nextPart() {
            switch (this) {
                case MORNING: return AFTERNOON;
                case AFTERNOON: return EVENING;
                case EVENING: return NIGHT;
                case NIGHT: return MORNING;
            }
            throw new IllegalStateException();
        }

        public Parts previousPart() {
            switch (this) {
                case MORNING: return NIGHT;
                case AFTERNOON: return MORNING;
                case EVENING: return AFTERNOON;
                case NIGHT: return EVENING;
            }
            throw new IllegalStateException();
        }
    }

    public static long getNearestStartDatetime(Calendar base, Parts flag) {
        final long baseTime = base.getTimeInMillis();
        final long prevDate = getPreviousStartDatetime(base, flag);
        final long nextDate = getNextStartDatetime(base, flag);
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(nextDate);

        return (prevDate <= baseTime && baseTime < getPreviousStartDatetime(date, flag.nextPart()))
                ? prevDate
                : nextDate;
    }

    public static long getNextStartDatetime(Calendar base, Parts flag) {
        Calendar nextDate = Calendar.getInstance();
        nextDate.setTimeInMillis(base.getTimeInMillis());
        setTimeSinceMidnightInMinutes(nextDate, flag.getStartTimeSinceMidnightInMinutes());
        nextDate.set(Calendar.SECOND, 0);

        if (!base.before(nextDate)) {
            nextDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        return nextDate.getTimeInMillis();
    }

    public static long getPreviousStartDatetime(Calendar base, Parts flag) {
        Calendar prevDate = Calendar.getInstance();
        prevDate.setTimeInMillis(base.getTimeInMillis());
        setTimeSinceMidnightInMinutes(prevDate, flag.getStartTimeSinceMidnightInMinutes());
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
     * #getNextStartDatetime()
     * #getPreviousStartDatetime()
     * #getNearestStartDatetime()
     */
    public static void testGetDatetimeMethods() {
        final Calendar baseTime = Calendar.getInstance();
        Log.d("mylog", "base time -> " + DateFormat.format("yyyy-MM-dd hh:mm a", baseTime));
        Calendar tmp = Calendar.getInstance();

        long m = PartsOfDay.getNextStartDatetime(baseTime, Parts.MORNING);
        long a = PartsOfDay.getNextStartDatetime(baseTime, Parts.AFTERNOON);
        long e = PartsOfDay.getNextStartDatetime(baseTime, Parts.EVENING);
        long n = PartsOfDay.getNextStartDatetime(baseTime, Parts.NIGHT);

        tmp.setTimeInMillis(m);
        Log.d("mylog", "next date morning starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(a);
        Log.d("mylog", "next date afternoon starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(e);
        Log.d("mylog", "next date evening starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(n);
        Log.d("mylog", "next date night starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));

        m = PartsOfDay.getPreviousStartDatetime(baseTime, Parts.MORNING);
        a = PartsOfDay.getPreviousStartDatetime(baseTime, Parts.AFTERNOON);
        e = PartsOfDay.getPreviousStartDatetime(baseTime, Parts.EVENING);
        n = PartsOfDay.getPreviousStartDatetime(baseTime, Parts.NIGHT);

        tmp.setTimeInMillis(m);
        Log.d("mylog", "previous date morning starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(a);
        Log.d("mylog", "previous date afternoon starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(e);
        Log.d("mylog", "previous date evening starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(n);
        Log.d("mylog", "previous date night starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));

        m = PartsOfDay.getNearestStartDatetime(baseTime, Parts.MORNING);
        a = PartsOfDay.getNearestStartDatetime(baseTime, Parts.AFTERNOON);
        e = PartsOfDay.getNearestStartDatetime(baseTime, Parts.EVENING);
        n = PartsOfDay.getNearestStartDatetime(baseTime, Parts.NIGHT);

        tmp.setTimeInMillis(m);
        Log.d("mylog", "nearest date morning starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(a);
        Log.d("mylog", "nearest date afternoon starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(e);
        Log.d("mylog", "nearest date evening starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
        tmp.setTimeInMillis(n);
        Log.d("mylog", "nearest date night starts -> " + DateFormat.format("yyyy-MM-dd hh:mm a", tmp));
    }

    public static Parts getCurrentPartOfDay() {
        Calendar now = Calendar.getInstance();
        final int time = now.get(Calendar.HOUR_OF_DAY) * HOUR_IN_MINUTES + now.get(Calendar.MINUTE);

        if (Parts.MORNING.getStartTimeSinceMidnightInMinutes() <= time &&
                time < Parts.AFTERNOON.getStartTimeSinceMidnightInMinutes()) {
            return Parts.MORNING;

        } else if (Parts.AFTERNOON.getStartTimeSinceMidnightInMinutes() <= time &&
                time < Parts.EVENING.getStartTimeSinceMidnightInMinutes()) {
            return Parts.AFTERNOON;

        } else if (Parts.EVENING.getStartTimeSinceMidnightInMinutes() <= time &&
                time < Parts.NIGHT.getStartTimeSinceMidnightInMinutes()) {
            return Parts.EVENING;

        } else {
            return Parts.NIGHT;
        }
    }

}
