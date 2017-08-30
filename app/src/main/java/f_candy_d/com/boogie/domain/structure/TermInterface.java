package f_candy_d.com.boogie.domain.structure;

import java.util.Calendar;

import f_candy_d.com.boogie.utils.DayOfWeek;
import f_candy_d.com.boogie.utils.InstantTime;
import f_candy_d.com.boogie.utils.Month;

/**
 * Created by daichi on 17/08/30.
 */

public interface TermInterface {

    public enum Repetition {
        ONE_DAY,
        EVERY_DAY,
        WEEEKLY,
        EVERY_MONTH,
        EVERY_YEAR,
        CUSTOM
    }

    int getStartYear();
    Month getStartMonth();
    int getStartDayOfMonth();
    DayOfWeek getStartDayOfWeek();
    InstantTime getStartTime();
    Calendar getStartDate();

    int getEndYear();
    Month getEndMonth();
    int getEndDayOfMonth();
    DayOfWeek getEndDayOfWeek();
    InstantTime getEndTime();
    Calendar getEndDate();

    Repetition getRepetition();
}
