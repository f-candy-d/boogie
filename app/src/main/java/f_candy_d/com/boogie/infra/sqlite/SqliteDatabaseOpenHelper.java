package f_candy_d.com.boogie.infra.sqlite;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by daichi on 17/08/30.
 */

public interface SqliteDatabaseOpenHelper {
    SQLiteDatabase openWritableDatabase();
    SQLiteDatabase openReadableDatabase();
}
