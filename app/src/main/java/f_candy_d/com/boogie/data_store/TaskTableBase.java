package f_candy_d.com.boogie.data_store;

import android.provider.BaseColumns;

import f_candy_d.com.boogie.infra.sql.SqliteColumnDataType;
import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;

/**
 * Created by daichi on 17/09/02.
 */

public abstract class TaskTableBase implements BaseColumns {

    public static final String _TITLE = "_title";
    public static final String _NOTE = "_note";
    public static final String _TERM_START_DATE = "_term_start_date";
    public static final String _TERM_END_DATE = "_term_end_date";
    public static final String _PRIORITY = "_priority";

    static SqliteTableUtils.TableSource getBaseTableSource() {
        return new SqliteTableUtils.TableSource(null)
                .put(_ID, SqliteColumnDataType.INTEGER_PK, false)
                .put(_TITLE, SqliteColumnDataType.TEXT, false)
                .put(_NOTE, SqliteColumnDataType.TEXT, true)
                .put(_TERM_START_DATE, SqliteColumnDataType.INTEGER, false)
                .put(_TERM_END_DATE, SqliteColumnDataType.INTEGER, false)
                .put(_PRIORITY, SqliteColumnDataType.INTEGER, false);
    }
}