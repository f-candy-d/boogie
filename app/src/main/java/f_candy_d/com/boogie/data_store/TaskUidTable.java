package f_candy_d.com.boogie.data_store;

import android.provider.BaseColumns;

import f_candy_d.com.boogie.infra.sql.SqliteColumnDataType;
import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;

/**
 * Created by daichi on 17/08/30.
 *
 * The row ID of this table means the UID of a content
 * witch is able to be got by its ID & table name.
 */

public class TaskUidTable implements BaseColumns {

    public static final String TABLE_NAME = "content_uid";

    /**
     * Columns
     */

    public static final String _CONTENT_TABLE_NAME = "content_table_name";
    public static final String _CONTENT_ID = "content_id";

    public static SqliteTableUtils.TableSource getTableSource() {
        return new SqliteTableUtils.TableSource(TABLE_NAME)
                .put(_ID, SqliteColumnDataType.INTEGER_PK, false)
                .put(_CONTENT_TABLE_NAME, SqliteColumnDataType.TEXT, false)
                .put(_CONTENT_ID, SqliteColumnDataType.INTEGER, false);
    }
}
