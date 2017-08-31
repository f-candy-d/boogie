package f_candy_d.com.boogie.domain.structure;

import java.util.Calendar;

import f_candy_d.com.boogie.data_store.EventTableContract;
import f_candy_d.com.boogie.data_store.SqlDbContract;
import f_candy_d.com.boogie.utils.DayOfWeek;
import f_candy_d.com.boogie.utils.InstantTime;
import f_candy_d.com.boogie.utils.Month;
import f_candy_d.com.boogie.utils.TermInterface;

/**
 * Created by daichi on 17/08/30.
 */

public class Event extends TermContent {

    private static final String DEFAULT_NAME = null;
    private static final String DEFAULT_NOTE = null;
    private static final int DEFAULT_YEAR = -1;
    private static final Month DEFAULT_MONTH = null;
    private static final int DEFAULT_DAY_OF_MONTH = -1;
    private static final InstantTime DEFAULT_TIME = null;
    private static final Repetition DEFAULT_REPETITION = null;

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

    public Event() {
        mId = SqlDbContract.NULL_ID;
        mName = DEFAULT_NAME;
        mName = DEFAULT_NOTE;
        mStartYear = DEFAULT_YEAR;
        mStartMonth = DEFAULT_MONTH;
        mStartDayOfMonth = DEFAULT_DAY_OF_MONTH;
        mStartTime = DEFAULT_TIME;
        mEndYear = DEFAULT_YEAR;
        mEndMonth = DEFAULT_MONTH;
        mEndDayOfMonth = DEFAULT_DAY_OF_MONTH;
        mEndTime = DEFAULT_TIME;
        mRepetition = DEFAULT_REPETITION;
    }

    @Override
    ContentCategory getContentCategory() {
        return ContentCategory.EVENT;
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
        return DayOfWeek.from(getStartDate());
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
                mStartMonth.getValue(),
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
        return DayOfWeek.from(getStartDate());
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
                mEndMonth.getValue(),
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
