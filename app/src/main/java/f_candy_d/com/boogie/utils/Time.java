package f_candy_d.com.boogie.utils;

/**
 * Created by daichi on 17/08/30.
 */

final public class Time {

    private Time() {}

    /**
     * 1 hour = 60 minutes.
     */
    public static final int HOUR_IN_MINUTES = 60;

    /**
     * o minutes.
     */
    public static final int ZERO_MINUTES = 0;

    /**
     * The start time of a day since midnight in minutes.
     * Defined time is midnight of a day.
     *
     * START_OF_DAY <= t < END_OF_DAY
     * (t := Specific time of a day since midnight in minutes)
     */
    public static final int START_OF_DAY = 0;

    /**
     * The start time of a day since midnight in hours.
     * Defined time is midnight of a day.
     *
     * START_OF_DAY_IN_HOURS <= t < END_OF_DAY_IN_HOURS
     * (t := Specific time of a day since midnight in hours)
     */
    public static final int START_OF_DAY_IN_HOUR = 0;

    /**
     * The end time of a day since midnight in minutes.
     * Defined time is midnight of next day.
     *
     * START_OF_DAY <= t < END_OF_DAY
     * (t := Specific time of a day since midnight in minutes)
     */
    public static final int END_OF_DAY = 24 * HOUR_IN_MINUTES;

    /**
     * The end time of a day since midnight in minutes.
     * Defined time is midnight of next day.
     *
     * START_OF_DAY_IN_HOURS <= t < END_OF_DAY_IN_HOURS
     * (t := Specific time of a day since midnight in hours)
     */
    public static final int END_OF_DAY_IN_HOURS = 24;

    /**
     * The time morning starts in minutes (since midnight).
     * Defined time is 5 am.
     *
     * TIME_MORNING_START <= (Morning Time) < TIME_AFTERNOON_START
     */
    public static final int TIME_MORNING_START = 5 * HOUR_IN_MINUTES;

    /**
     * The time afternoon starts in minutes (since midnight).
     * Defined time is 12 pm.
     *
     * TIME_AFTERNOON_START <= (Afternoon Time) < TIME_EVENING_START
     */
    public static final int TIME_AFTERNOON_START = 12 * HOUR_IN_MINUTES;

    /**
     * The time evening starts in minutes (since midnight).
     * Defined time is 5 pm.
     *
     * TIME_EVENING_START <= (Evening Time) < TIME_NIGHT_START
     */
    public static final int TIME_EVENING_START = 17 * HOUR_IN_MINUTES;

    /**
     * The time night starts in minutes (since midnight).
     * Defined time is 9 pm.
     *
     * TIME_NIGHT_START <= (Night Time) < END_OF_DAY
     * or
     * START_OF_DAY <= (Night Time) < TIME_MORNING_START
     */
    public static final int TIME_NIGHT_START = 21 * HOUR_IN_MINUTES;

    /**
     * Check validation of the passed time(hh:mm).
     * @param hourOfDay 24h format
     * @param minute 0 min ~ 59 min
     * @return True if the passed time is valid, false otherwise
     */
    public static boolean isTimeValied(int hourOfDay, int minute) {
        return (START_OF_DAY_IN_HOUR <= hourOfDay && hourOfDay < END_OF_DAY_IN_HOURS &&
                ZERO_MINUTES <= minute && minute < HOUR_IN_MINUTES);
    }

    /**
     * Check validation of the passed time.
     * @param timeSinceMidnightInMinutes Time since midnight in minutes
     * @return True if the passed time is valid, false otherwise
     */
    public static boolean isTimeValied(int timeSinceMidnightInMinutes) {
        return (START_OF_DAY <= timeSinceMidnightInMinutes &&
                timeSinceMidnightInMinutes < END_OF_DAY);
    }

    /**
     * Convert the passed time (hh:mm) to the time since midnight in minutes.
     * Do not check the validation of the time.
     * @param hourOfDay Hour of a day (24h format)
     * @param minute Minutes
     * @return
     */
    public static int toTimeSinceMidnightInMinutes(int hourOfDay, int minute) {
        return hourOfDay * HOUR_IN_MINUTES + minute;
    }
}
