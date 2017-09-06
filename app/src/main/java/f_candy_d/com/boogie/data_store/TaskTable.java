package f_candy_d.com.boogie.data_store;

import android.provider.BaseColumns;

import f_candy_d.com.boogie.infra.sql.SqliteColumnDataType;
import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;

/**
 * Created by daichi on 17/09/03.
 */

public class TaskTable implements BaseColumns {

    public static final String TABLE_NAME = "task";

    public static final String _TITLE = "mTitle";
    public static final String _DATE_TERM_START = "date_term_start";
    public static final String _DATE_TERM_END = "date_term_end";
    public static final String _IS_DONE = "is_done";
    public static final String _DO_THROUGHOUT_TERM = "do_throughout_term";
    public static final String _TYPE = "type";

    public static SqliteTableUtils.TableSource getTableSource() {
        return new SqliteTableUtils.TableSource(TABLE_NAME)
                .put(_ID, SqliteColumnDataType.INTEGER_PK, false)
                .put(_TITLE, SqliteColumnDataType.TEXT, false)
                .put(_DATE_TERM_START, SqliteColumnDataType.INTEGER, false)
                .put(_DATE_TERM_END, SqliteColumnDataType.INTEGER, false)
                .put(_IS_DONE, SqliteColumnDataType.INTEGER, false)
                .put(_DO_THROUGHOUT_TERM, SqliteColumnDataType.INTEGER, false)
                .put(_TYPE, SqliteColumnDataType.INTEGER, false);
    }
}
