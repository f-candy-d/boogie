package f_candy_d.com.boogie.data_store;

import android.provider.BaseColumns;

import f_candy_d.com.boogie.infra.sql.SqliteColumnDataType;
import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;

/**
 * Created by daichi on 17/08/30.
 *
 * The row ID of this table means the UID of a task
 * witch is able to be got by its ID & table name.
 */

public class TaskUidTable implements BaseColumns {

    public static final String TABLE_NAME = "task_uid";

    /**
     * Columns
     */

    public static final String _TASK_TABLE_NAME = "task_table_name";
    public static final String _TASK_ID = "task_id";

    public static SqliteTableUtils.TableSource getTableSource() {
        return new SqliteTableUtils.TableSource(TABLE_NAME)
                .put(_ID, SqliteColumnDataType.INTEGER_PK, false)
                .put(_TASK_TABLE_NAME, SqliteColumnDataType.TEXT, false)
                .put(_TASK_ID, SqliteColumnDataType.INTEGER, false);
    }
}
