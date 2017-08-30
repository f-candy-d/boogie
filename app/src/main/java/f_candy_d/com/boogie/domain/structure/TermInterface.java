package f_candy_d.com.boogie.domain.structure;

import java.util.Calendar;

import f_candy_d.com.boogie.utils.DayOfWeek;
import f_candy_d.com.boogie.utils.InstantTime;
import f_candy_d.com.boogie.utils.Month;

/**
 * Created by daichi on 17/08/30.
 */

public interface TermInterface {

    int getStartYear();
    Month getStartMonth();
    int getStartDayOfMonth();
    DayOfWeek getStartDow();
    InstantTime getStartTime();
    Calendar getStartDate();

    int getEndYear();
    Month getEndMonth();
    int getEndDayOfMonth();
    DayOfWeek getEndDow();
    InstantTime getEndTime();
    Calendar getEndDate();
}
