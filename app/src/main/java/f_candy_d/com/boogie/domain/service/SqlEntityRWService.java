package f_candy_d.com.boogie.domain.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.domain.SqlRepositoryUser;
import f_candy_d.com.boogie.domain.structure.SqlEntityObject;
import f_candy_d.com.boogie.infra.SqlEntity;
import f_candy_d.com.boogie.infra.SqlRepository;

/**
 * Created by daichi on 9/7/17.
 */

public class SqlEntityRWService extends Service implements SqlRepositoryUser {

    private SqlRepository mSqlRepository;

    public long insert(SqlEntityObject entityObject) {
        onServiceStart();

        Log.d("mylog", entityObject.toSqlEntity(false).getValueMap().toString());
        if (!entityObject.isValid()) {
            return DbContract.NULL_ID;
        }

        SqlEntity entity = entityObject.toSqlEntity(false);
        final long id = mSqlRepository.insert(entity);

        return (id != -1) ? id : DbContract.NULL_ID;
    }

    @Nullable
    SqlEntity findById(long id, @NonNull String table) {
        onServiceStart();
        return mSqlRepository.selectRowById(table, id);
    }

    public boolean update(SqlEntityObject entityObject) {
        onServiceStart();

        if (!entityObject.isValid()) {
            return false;
        }

        SqlEntity entity = entityObject.toSqlEntity(true);
        return mSqlRepository.update(entity);
    }

    public boolean delete(SqlEntityObject entityObject) {
        return mSqlRepository.delete(entityObject.id, entityObject.getTableName());
    }

    @Override
    boolean isReady() {
        return (mSqlRepository != null);
    }

    @Override
    final public void setSqlRepository(@NonNull SqlRepository repository) {
        mSqlRepository = repository;
    }

    SqlRepository getSqlRepository() {
        return mSqlRepository;
    }

}
