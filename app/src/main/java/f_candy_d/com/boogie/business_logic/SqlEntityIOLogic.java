package f_candy_d.com.boogie.business_logic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.structure.SqlEntityObject;
import f_candy_d.com.boogie.infra.SqlEntity;
import f_candy_d.com.boogie.infra.SqlRepository;

/**
 * Created by daichi on 9/9/17.
 */

public class SqlEntityIOLogic {

    private SqlRepository mSqlRepository;

    public SqlEntityIOLogic(Context context) {
        mSqlRepository = ImplAdoption.createSqlRepositoryImpl(context);
    }

    public long insert(SqlEntityObject entityObject) {
        if (!entityObject.isValid()) {
            return DbContract.NULL_ID;
        }

        SqlEntity entity = entityObject.toSqlEntity(false);
        final long id = mSqlRepository.insert(entity);

        return (id != -1) ? id : DbContract.NULL_ID;
    }

    @Nullable
    SqlEntity findById(long id, @NonNull String table) {
        return mSqlRepository.selectRowById(table, id);
    }

    public boolean update(SqlEntityObject entityObject) {
        if (!entityObject.isValid()) {
            return false;
        }

        SqlEntity entity = entityObject.toSqlEntity(true);
        return mSqlRepository.update(entity);
    }

    public boolean delete(SqlEntityObject entityObject) {
        return mSqlRepository.delete(entityObject.id, entityObject.getTableName());
    }

    protected SqlRepository getSqlRepository() {
        return mSqlRepository;
    }
}
