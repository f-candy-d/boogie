package f_candy_d.com.boogie.data_store;

import android.provider.BaseColumns;

import java.util.Calendar;

import f_candy_d.com.boogie.structure.TaskType;
import f_candy_d.com.boogie.infra.sql.SqliteColumnDataType;
import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;

/**
 * Created by daichi on 17/09/03.
 */

public class TaskTableRule implements BaseColumns {

    public static final String TABLE_NAME = "task";

    /**
     * Columns
     */
    public static final String _TITLE = "title";
    public static final String _DATE_TERM_START = "date_term_start";
    public static final String _DATE_TERM_END = "date_term_end";
    public static final String _IS_DONE = "is_done";
    public static final String _DO_THROUGHOUT_TERM = "do_throughout_term";
    public static final String _TYPE = "type";

    /**
     * Table definition
     */
    static SqliteTableUtils.TableSource getTableSource() {
        return new SqliteTableUtils.TableSource(TABLE_NAME)
                .put(_ID, SqliteColumnDataType.INTEGER_PK, false)
                .put(_TITLE, SqliteColumnDataType.TEXT, false)
                .put(_DATE_TERM_START, SqliteColumnDataType.INTEGER, false)
                .put(_DATE_TERM_END, SqliteColumnDataType.INTEGER, false)
                .put(_IS_DONE, SqliteColumnDataType.INTEGER, false)
                .put(_DO_THROUGHOUT_TERM, SqliteColumnDataType.INTEGER, false)
                .put(_TYPE, SqliteColumnDataType.INTEGER, false);
    }

    /**
     * Default values of columns
     */

    public static long defaultId() {
        return DbContract.NULL_ID;
    }

    public static String defaultTitle() {
        return "(No Title)";
    }

    public static long defaultDateTermStart() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static long defaultDateTermEnd() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static boolean defaultIsDone() {
        return false;
    }

    public static boolean defaultDoThroughoutTerm() {
        return false;
    }

    public static TaskType defaultType() {
        return TaskType.NONE;
    }

    public enum ValidationErrorCode {
        TITLE_ERROR, // 'title' is not able to be an empty text
        DATE_ERROR, // 'dateTermEnd' must be greater than or equals to 'dateTermStart'
        TYPE_ERROR, // 'type' is not able to be a null
    }

    /**
     * Data validations
     */

    public static ValidationErrorCode isTitleValid(String title) {
        return (title == null) ? ValidationErrorCode.TITLE_ERROR : null;
    }

    /**
     * @param dateTermStart in millis
     * @param dateTermEnd in millis
     */
    public static ValidationErrorCode isDateValid(long dateTermStart, long dateTermEnd) {
        return (dateTermStart > dateTermEnd) ? ValidationErrorCode.DATE_ERROR : null;
    }

    public static ValidationErrorCode isTypeValid(TaskType type) {
        return (type == null || type == TaskType.NONE)
                ? ValidationErrorCode.TYPE_ERROR
                : null;
    }
}
