package f_candy_d.com.boogie.structure;

import android.support.annotation.NonNull;

import f_candy_d.com.boogie.infra.SqlEntity;

/**
 * Created by daichi on 9/7/17.
 */

abstract public class SqlEntityObject<E extends Enum<E>> {

    @NonNull private String mTableName;
    public long id;

    SqlEntityObject(@NonNull String tableName) {
        mTableName = tableName;
    }

    @NonNull
    public String getTableName() {
        return mTableName;
    }

    /**
     *
     * @return Error codes
     */
    @NonNull
    abstract public E[] checkValidation();

    public boolean isValid() {
        return (checkValidation().length == 0);
    }

    @NonNull
    abstract public SqlEntity toSqlEntity(boolean includeRowId);

    abstract public void constructFromSqlEntity(SqlEntity entity);
}
