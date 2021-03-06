package f_candy_d.com.boogie.data_store;

import android.provider.BaseColumns;

import f_candy_d.com.boogie.infra.sql.SqliteColumnDataType;
import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;

/**
 * Created by daichi on 17/08/30.
 */

public class TasksRelationTable implements BaseColumns {

    public static final String TABLE_NAME = "tasks_relation";

    /**
     * Columns
     */

    public static final String _ONE_UID = "one_uid";
    public static final String _OTHER_UID = "other_uid";

    public static SqliteTableUtils.TableSource getTableSource() {
        return new SqliteTableUtils.TableSource(TABLE_NAME)
                .put(_ID, SqliteColumnDataType.INTEGER_PK, false)
                .put(_ONE_UID, SqliteColumnDataType.INTEGER, false)
                .put(_OTHER_UID, SqliteColumnDataType.INTEGER, false);
    }
}
