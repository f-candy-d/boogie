package f_candy_d.com.boogie.utils;

import java.util.Calendar;

/**
 * Created by daichi on 17/09/03.
 *
 * Helper class for {@link java.util.Calendar}
 */

public class InstantDate {

    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private int mDayOfWeek;
    private int mHourOfDay;
    private int mMinute;
    private int mMillisecond;

    public InstantDate() {
        set(Calendar.getInstance());
    }

    public InstantDate(Calendar calendar) {
        set(calendar);
    }

    public InstantDate(int timeInMillis) {

    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getDayOfMonth() {
        return mDayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        mDayOfMonth = dayOfMonth;
    }

    public int getDayOfWeek() {
        return mDayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        mDayOfWeek = dayOfWeek;
    }

    public int getHourOfDay() {
        return mHourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        mHourOfDay = hourOfDay;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        this.mMinute = minute;
    }

    public int getMillisecond() {
        return mMillisecond;
    }

    public void setMillisecond(int millisecond) {
        this.mMillisecond = millisecond;
    }

    public void set(int timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        set(calendar);
    }

    public void set(Calendar calendar) {
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        mHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        mMillisecond = calendar.get(Calendar.MILLISECOND);
    }

    public int getTimeSinceMidnightInMinutes() {
        return mHourOfDay * Time.HOUR_IN_MINUTES + mMinute;
    }
}
