package f_candy_d.com.boogie.data_store;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import f_candy_d.com.boogie.infra.sql.SqliteDatabaseOpenHelper;
import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;

/**
 * Created by daichi on 17/08/30.
 */

public class SqliteDatabaseOpenHelperImpl extends SQLiteOpenHelper implements SqliteDatabaseOpenHelper {

    public SqliteDatabaseOpenHelperImpl(Context context) {
        super(context, SqlDbContract.DATABASE_NAME, null, SqlDbContract.DATABASE_VERSION);
    }

    @Override
    public SQLiteDatabase openWritableDatabase() {
        return getWritableDatabase();
    }

    @Override
    public SQLiteDatabase openReadableDatabase() {
        return getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        SqliteTableUtils.TableSource[] tableSources = SqlDbContract.getTableSourses();
        for (SqliteTableUtils.TableSource source : tableSources) {
            SqliteTableUtils.createTable(sqLiteDatabase, source);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
