package f_candy_d.com.boogie.utils;

import android.support.annotation.NonNull;

/**
 * Created by daichi on 17/08/31.
 */

public class TimeTerm {

    // Includes start time, exclude end time
    @NonNull private InstantTime mStartTime;
    @NonNull private InstantTime mEndTime;
    private int mTag;

    public TimeTerm() {
        mStartTime = new InstantTime();
        mEndTime = new InstantTime();
    }

    public void setStartTimeSinceMidnightInMinutes(int timeSinceMidnightInMinutes) {
        mStartTime.setTimeSinceMidnightInMinutes(timeSinceMidnightInMinutes);
    }

    public void setEndTimeSinceMidnightInMinutes(int timeSinceMidnightInMinutes) {
        mEndTime.setTimeSinceMidnightInMinutes(timeSinceMidnightInMinutes);
    }

    public boolean containsTime(int timeSinceMidnightInMinutes) {
        final int startTime = mStartTime.getTimeSinceMidnightInMinutes();
        final int endTime = mEndTime.getTimeSinceMidnightInMinutes();

        if (startTime <= endTime) {
            return (startTime <= timeSinceMidnightInMinutes &&
                    timeSinceMidnightInMinutes < endTime);

        } else {
            // When the term is across midnight
            return ((startTime <= timeSinceMidnightInMinutes &&
                    timeSinceMidnightInMinutes < Time.END_OF_DAY) ||
                    (Time.START_OF_DAY <= timeSinceMidnightInMinutes &&
                    timeSinceMidnightInMinutes < endTime));
        }
    }

    public boolean containsTime(InstantTime time) {
        return (time != null && containsTime(time.getTimeSinceMidnightInMinutes()));
    }

    public InstantTime getStartTime() {
        return mStartTime;
    }

    public InstantTime getEndTime() {
        return mEndTime;
    }

    public int getTag() {
        return mTag;
    }

    public void setTag(int tag) {
        mTag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeTerm timeTerm = (TimeTerm) o;

        if (mTag != timeTerm.mTag) return false;
        if (!mStartTime.equals(timeTerm.mStartTime)) return false;
        return mEndTime.equals(timeTerm.mEndTime);

    }

    @Override
    public int hashCode() {
        int result = mStartTime.hashCode();
        result = 31 * result + mEndTime.hashCode();
        result = 31 * result + mTag;
        return result;
    }
}
