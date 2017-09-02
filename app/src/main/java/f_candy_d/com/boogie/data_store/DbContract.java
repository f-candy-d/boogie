package f_candy_d.com.boogie.data_store;

import android.support.annotation.NonNull;

import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;

/**
 * Created by daichi on 17/08/30.
 */

final public class DbContract {

    public static final String DATABASE_NAME = "boogie_contents_database";
    public static final int DATABASE_VERSION = 1;
    public static final long NULL_ID = -1;

    @NonNull
    public static SqliteTableUtils.TableSource[] getTableSourses() {
        return new SqliteTableUtils.TableSource[] {
                EventTableContract.getTableSource(),
                ContentsRelationTableContract.getTableSource(),
                ContentUidTableContract.getTableSource()
        };

    }
}
