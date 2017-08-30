package f_candy_d.com.boogie.utils;

import java.util.Calendar;

/**
 * Created by daichi on 17/08/30.
 */

public interface TermInterface {

    enum Repetition implements Quantizable {
        ONE_DAY {@Override public int quantize() {return 0;}},
        EVERY_DAY {@Override public int quantize() {return 1;}},
        WEEEKLY {@Override public int quantize() {return 2;}},
        EVERY_MONTH {@Override public int quantize() {return 3;}},
        EVERY_YEAR {@Override public int quantize() {return 4;}},
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
