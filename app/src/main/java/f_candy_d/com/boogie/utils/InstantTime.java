package f_candy_d.com.boogie.utils;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by daichi on 17/08/30.
 */

public class InstantTime implements Comparable<InstantTime> {

    static final int MAX_HOUR_OF_DAY = 23;
    static final int MIN_HOUR_OF_DAY = 0;
    static final int MAX_MINUTES = 59;
    static final int MIN_MINUTES = 0;
    // 1 hour = 60 minutes
    static final int HOUR_IN_MINUTES = 60;

    // 24h format
    // MIN_HOUR_OF_DAY <= mHourOfDay <= MAX_HOUR_OF_DAY
    private int mHourOfDay;
    // MIN_MINUTES <= mMinute <= MAX_MINUTES
    private int mMinute;

    InstantTime() {
        this(Calendar.getInstance());
    }

    InstantTime(Calendar calendar) {
        this(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    InstantTime(int hourOfDay, int minute) {
        set(hourOfDay, minute);
    }

    public void now() {
        Calendar calendar = Calendar.getInstance();
        mHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
    }

    public void startOfDay() {
        set(MIN_HOUR_OF_DAY, MIN_MINUTES);
    }

    public void endOfDay() {
        set(MAX_HOUR_OF_DAY, MAX_HOUR_OF_DAY);
    }

    public int getTimeSinceMidnightInMinutes() {
        return mHourOfDay * HOUR_IN_MINUTES + mMinute;
    }

    void set(int hourOfDay, int minute) {
        if (MIN_HOUR_OF_DAY <= hourOfDay && hourOfDay <= MAX_HOUR_OF_DAY &&
                MIN_MINUTES <= minute && minute <= MAX_MINUTES) {
            mHourOfDay = hourOfDay;
            mMinute = minute;

        } else {
            throw new IllegalArgumentException();
        }
    }

    public void addHourOfDay(int amount) {
        mHourOfDay = (mHourOfDay + amount) % (MAX_HOUR_OF_DAY + 1);
    }

    public void addMinute(int amount) {
        mMinute = (mMinute + amount) % (MAX_MINUTES + 1);
    }

    public int hourOfDay() {
        return mHourOfDay;
    }

    public int minute() {
        return mMinute;
    }

    public boolean beforeThan(InstantTime when) {
        return (when != null &&
                (mHourOfDay  < when.hourOfDay() ||
                        (mHourOfDay == when.hourOfDay() && mMinute < when.minute())));
    }

    public boolean afterThan(InstantTime when) {
        return (!beforeThan(when) && !equals(when));
    }

    @Override
    public int compareTo(@NonNull InstantTime instantTime) {
        if (afterThan(instantTime)) {
            return 1;
        } else if (equals(instantTime)) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstantTime that = (InstantTime) o;

        if (mHourOfDay != that.mHourOfDay) return false;
        return mMinute == that.mMinute;

    }

    @Override
    public int hashCode() {
        int result = mHourOfDay;
        result = 31 * result + mMinute;
        return result;
    }
 }
