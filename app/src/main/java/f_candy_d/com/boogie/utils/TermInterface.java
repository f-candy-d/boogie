package f_candy_d.com.boogie.utils;

import java.util.Calendar;

/**
 * Created by daichi on 17/08/30.
 */

public interface TermInterface {

    enum Repetition implements Quantizable {
        ONE_TIME {@Override public int quantize() {return 0;}},
        DAILY {@Override public int quantize() {return 1;}},
        WEEKLY {@Override public int quantize() {return 2;}},
        MONTHLY {@Override public int quantize() {return 3;}},
        YEARLY {@Override public int quantize() {return 4;}},
        CUSTOM {@Override public int quantize() {return 5;}}
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
