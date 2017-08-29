package f_candy_d.com.boogie.infra;

import android.support.annotation.NonNull;

import java.util.Collection;

import f_candy_d.com.boogie.infra.sqlite.SqlQuery;
import f_candy_d.com.boogie.infra.sqlite.SqlWhere;

/**
 * Created by daichi on 17/08/30.
 */

public interface SqlRepository {

    /**
     * Insert a row into the database.
     * @param entity Initial column values for the row
     * @return SQLiteDatabase.insert()
     */
    long insert(@NonNull SqlEntity entity);

    /**
     * Insert rows into the database.
     * @param entities Entities witch have initial column values for rows
     * @return An array that contains SQLiteDatabase.insert() or -1
     */
    @NonNull
    long[] insert(@NonNull Collection<SqlEntity> entities);

    /**
     * Update the row of a specific entity in the database.
     * @param entity New column values.
     *               This object must has a pair of the following column & value:
     *               COLUMN   : android.provider.BaseColumns._ID
     *               VALUE    : Row's ID of a specific entity
     *
     * @return True if updating is successful, or false if an error occur
     */
    boolean update(@NonNull SqlEntity entity);

    /**
     * Update rows which meet a passed sql WHERE clause in the database.
     * @param entity New column values
     * @param where Sql WHERE clause
     * @return SQLiteDatabase.update()
     */
    int updateIf(@NonNull String table, @NonNull SqlEntity entity, @NonNull SqlWhere where);

    /**
     * Delete the row of a specific entity in the database.
     * @param entity An entity which will be deleted.
     *               This object must has a pair of the following column & value:
     *               COLUMN  : android.provider.BaseColumns._ID
     *               VALUE   : Row's ID of a specific entity
     *
     * @return True if deleting is successful, false otherwise
     */
    boolean delete(@NonNull SqlEntity entity);

    /**
     * Delete rows which meet a passed sql WHERE clause in the database.
     * @param where Sql WHERE clause
     * @return SQLiteDatabase.delete()
     */
    int deleteIf(@NonNull String table, @NonNull SqlWhere where);

    /**
     * Find rows for a passed query.
     * @param query Conditions for finding rows
     * @return An array which contains results of finding, or an empty array
     */
    @NonNull
    SqlEntity[] select(@NonNull SqlQuery query);
}
