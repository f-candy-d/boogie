package f_candy_d.com.boogie.domain.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.data_store.ExactTermTaskTable;
import f_candy_d.com.boogie.domain.structure.ExactTermTask;
import f_candy_d.com.boogie.infra.SqlEntity;

/**
 * Created by daichi on 17/09/03.
 */

public class ExactTermTaskEntityRWService extends TaskEntityRWService<ExactTermTask> {

    public ExactTermTaskEntityRWService() {}

    @Override
    public long insertTask(ExactTermTask task) {
        onServiceStart();

        // TODO; Check data validation
        SqlEntity entity = makeSqlEntityFromExactTermTask(task, false);
        final long id = getSqlRepository().insert(entity);

        return (id != -1) ? id : DbContract.NULL_ID;
    }

    @Nullable
    @Override
    public ExactTermTask findTaskById(long id) {
        onServiceStart();

        SqlEntity entity = getSqlRepository().selectRowById(ExactTermTaskTable.TABLE_NAMEE, id);
        if (entity != null) {
            return makeExactTermTaskFromSqlEntity(entity);
        }
        return null;
    }

    @Override
    public boolean updateTask(ExactTermTask task) {
        onServiceStart();
        // TODO; Check data validation
        SqlEntity entity = makeSqlEntityFromExactTermTask(task, true);
        return getSqlRepository().update(entity);
    }

    @Override
    public boolean deleteTask(ExactTermTask task) {
        return getSqlRepository().delete(task.id, ExactTermTaskTable.TABLE_NAMEE);
    }

    @NonNull
    private ExactTermTask makeExactTermTaskFromSqlEntity(@NonNull SqlEntity entity) {
        ExactTermTask task = new ExactTermTask();
        super.makeTaskFromSqlEntity(task, entity);
        return task;
    }

    @NonNull
    private SqlEntity makeSqlEntityFromExactTermTask(@NonNull ExactTermTask task, boolean includeId) {
        SqlEntity entity = super.makeSqlEntityFromTask(task, includeId);
        entity.setTableName(ExactTermTaskTable.TABLE_NAMEE);
        return entity;
    }
}
