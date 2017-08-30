package f_candy_d.com.boogie.utils;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by daichi on 17/08/30.
 */

public class InstantTime implements Comparable<InstantTime> {

    // 24h format
    private int mHourOfDay;
    private int mMinute;

    public InstantTime() {
        this(Calendar.getInstance());
    }

    public InstantTime(Calendar calendar) {
        this(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    public InstantTime(int hourOfDay, int minute) {
        set(hourOfDay, minute);
    }

    public InstantTime(int timeSinceMidnightInMinutes) {
        setTimeSinceMidnightInMinutes(timeSinceMidnightInMinutes);
    }

    public void now() {
        Calendar calendar = Calendar.getInstance();
        mHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
    }

    public void startOfDay() {
        set(Time.START_OF_DAY_IN_HOUR, Time.ZERO_MINUTES);
    }

    public void endOfDay() {
        set(Time.END_OF_DAY_IN_HOURS - 1, Time.HOUR_IN_MINUTES - 1);
    }

    public int getTimeSinceMidnightInMinutes() {
        return Time.toTimeSinceMidnightInMinutes(mHourOfDay, mMinute);
    }

    public void set(int hourOfDay, int minute) {
        if (Time.isTimeValied(hourOfDay, minute)) {
            mHourOfDay = hourOfDay;
            mMinute = minute;

        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setTimeSinceMidnightInMinutes(int timeSinceMidnightInMinutes) {
        final int h = timeSinceMidnightInMinutes / Time.HOUR_IN_MINUTES;
        final int m = timeSinceMidnightInMinutes - h;
        set(h, m);
    }

    public void addHourOfDay(int amount) {
        mHourOfDay = (mHourOfDay + amount) % (Time.END_OF_DAY_IN_HOURS);
    }

    public void addMinute(int amount) {
        mMinute = (mMinute + amount) % (Time.HOUR_IN_MINUTES);
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
