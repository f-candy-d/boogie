package f_candy_d.com.boogie.infra;

import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import f_candy_d.com.boogie.infra.sqlite.SqlQuery;
import f_candy_d.com.boogie.infra.sqlite.SqlWhere;

/**
 * Created by daichi on 17/08/30.
 */

public interface SqlRepository {

    /**
     * Insert a row to into the database.
     * @param entity Initial column values for the row
     * @return SQLiteDatabase.insert()
     */
    long insert(@NonNull SqlEntity entity);

    /**
     * Update the row of a specific entity in the database.
     * @param entity New column values.
     *               This object must has a pair of the following key & value:
     *               KEY   : android.provider.BaseColumns._ID
     *               VALUE : Row's ID of a specific entity
     *
     * @return True if updating is successful, false otherwise
     */
    boolean update(@NonNull SqlEntity entity);

    /**
     * Update rows which meet a passed sql WHERE clause in the database.
     * @param entity New column values.
     * @param where Sql WHERE clause
     * @return SQLiteDatabase.update()
     */
    int updateIf(@NonNull SqlEntity entity, @NonNull SqlWhere where);

    /**
     * Delete the row of a specific entity in the database.
     * @param entity An entity which will be deleted.
     *               This object must has a pair of the following key & value:
     *               KEY   : android.provider.BaseColumns._ID
     *               VALUE : Row's ID of a specific entity
     *
     * @return True if deleting is successful, false otherwise
     */
    boolean delete(@NonNull SqlEntity entity);

    /**
     * Delete rows which meet a passed sql WHERE clause in the database.
     * @param where Sql WHERE clause
     * @return SQLiteDatabase.delete()
     */
    int deleteIf(@NonNull SqlWhere where);

    /**
     * Find rows for a passed query.
     * @param query Conditions for finding rows
     * @return An array which contains results of finding, or an empty array
     */
    @NonNull
    SqlEntity[] select(@NonNull SqlQuery query);
}
