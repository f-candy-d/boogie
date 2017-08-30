package f_candy_d.com.boogie.data_store;

import android.provider.BaseColumns;

import f_candy_d.com.boogie.infra.sql.SqliteColumnDataType;
import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;

/**
 * Created by daichi on 17/08/30.
 *
 * When add/remove any columns of this table, edit the following files which depends on this file.
 * * {@link f_candy_d.com.boogie.domain.structure.Event}
 * * {@link f_candy_d.com.boogie.domain.service.EventEntityRwService}
 */

public class EventTableContract implements BaseColumns {

    public static final String TABLE_NAME = "event";

    /**
     * Columns
     */
    public static final String _NAME = "name";
    public static final String _NOTE = "note";
    public static final String _START_YEAR = "start_year";
    public static final String _START_MONTH = "start_month";
    public static final String _START_DAY_OF_MONTH = "start_day_of_month";
    public static final String _START_TIME_SINCE_MIDNIGHT_IN_MINUTES = "start_time_since_midnight_in_minutes";
    public static final String _START_DAY_OF_WEEK = "start_day_of_week";
    public static final String _END_YEAR = "end_year";
    public static final String _END_MONTH = "end_month";
    public static final String _END_DAY_OF_MONTH = "end_day_of_month";
    public static final String _END_TIME_SINCE_MIDNIGHT_IN_MINUTES = "end_time_since_midnight_in_minutes";
    public static final String _END_DAY_OF_WEEK = "end_day_of_week";
    public static final String _REPETITION = "repetition";

    public static SqliteTableUtils.TableSource getTableSource() {
        return new SqliteTableUtils.TableSource(TABLE_NAME)
                .put(_ID, SqliteColumnDataType.INTEGER_PK, false)
                .put(_NAME, SqliteColumnDataType.TEXT, false)
                .put(_NOTE, SqliteColumnDataType.TEXT, true)
                .put(_START_YEAR, SqliteColumnDataType.INTEGER, true)
                .put(_START_MONTH, SqliteColumnDataType.INTEGER, true)
                .put(_START_DAY_OF_MONTH, SqliteColumnDataType.INTEGER, true)
                .put(_START_TIME_SINCE_MIDNIGHT_IN_MINUTES, SqliteColumnDataType.INTEGER, false)
                .put(_START_DAY_OF_WEEK, SqliteColumnDataType.INTEGER, true)
                .put(_END_YEAR, SqliteColumnDataType.INTEGER, true)
                .put(_END_MONTH, SqliteColumnDataType.INTEGER, true)
                .put(_END_DAY_OF_MONTH, SqliteColumnDataType.INTEGER, true)
                .put(_END_TIME_SINCE_MIDNIGHT_IN_MINUTES, SqliteColumnDataType.INTEGER, false)
                .put(_END_DAY_OF_WEEK, SqliteColumnDataType.INTEGER, true)
                .put(_REPETITION, SqliteColumnDataType.INTEGER, false);
    }
}
