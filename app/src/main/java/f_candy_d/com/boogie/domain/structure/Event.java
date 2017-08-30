package f_candy_d.com.boogie.domain.structure;

import java.util.Calendar;

import f_candy_d.com.boogie.utils.DayOfWeek;
import f_candy_d.com.boogie.utils.InstantTime;
import f_candy_d.com.boogie.utils.Month;

/**
 * Created by daichi on 17/08/30.
 */

public class Event extends Content implements TermInterface {

    private long mId;
    private String mName;
    private String mNote;
    private int mStartYear;
    private Month mStartMonth;
    private int mStartDayOfMonth;
    private InstantTime mStartTime;
    private int mEndYear;
    private Month mEndMonth;
    private int mEndDayOfMonth;
    private InstantTime mEndTime;
    private Repetition mRepetition;

    public Event() {}

    @Override
    ContentCategory getContentCategory() {
        return null;
    }

    @Override
    String toSummary() {
        return null;
    }

    /**
     * region; Setters & Getters
     */

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public void setStartYear(int startYear) {
        mStartYear = startYear;
    }

    public void setStartMonth(Month startMonth) {
        mStartMonth = startMonth;
    }

    public void setStartDayOfMonth(int startDayOfMonth) {
        mStartDayOfMonth = startDayOfMonth;
    }

    public void setStartTime(InstantTime startTime) {
        mStartTime = startTime;
    }

    public void setEndYear(int endYear) {
        mEndYear = endYear;
    }

    public void setEndMonth(Month endMonth) {
        mEndMonth = endMonth;
    }

    public void setEndDayOfMonth(int endDayOfMonth) {
        mEndDayOfMonth = endDayOfMonth;
    }

    public void setEndTime(InstantTime endTime) {
        mEndTime = endTime;
    }

    public void setRepetition(Repetition repetition) {
        mRepetition = repetition;
    }

    /**
     * region; TermInterface implementation
     */

    @Override
    public int getStartYear() {
        return mStartYear;
    }

    @Override
    public Month getStartMonth() {
        return mStartMonth;
    }

    @Override
    public int getStartDayOfMonth() {
        return mStartDayOfMonth;
    }

    @Override
    public DayOfWeek getStartDayOfWeek() {
        return null;
    }

    @Override
    public InstantTime getStartTime() {
        return mStartTime;
    }

    @Override
    public Calendar getStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                mStartYear,
                mStartMonth.value(),
                mStartDayOfMonth,
                mStartTime.hourOfDay(),
                mStartTime.minute());

        return calendar;
    }

    @Override
    public int getEndYear() {
        return mEndYear;
    }

    @Override
    public Month getEndMonth() {
        return mEndMonth;
    }

    @Override
    public int getEndDayOfMonth() {
        return mEndDayOfMonth;
    }

    @Override
    public DayOfWeek getEndDayOfWeek() {
        return null;
    }

    @Override
    public InstantTime getEndTime() {
        return mEndTime;
    }

    @Override
    public Calendar getEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                mEndYear,
                mEndMonth.value(),
                mEndDayOfMonth,
                mEndTime.hourOfDay(),
                mEndTime.minute());

        return calendar;
    }

    @Override
    public Repetition getRepetition() {
        return mRepetition;
    }
}
