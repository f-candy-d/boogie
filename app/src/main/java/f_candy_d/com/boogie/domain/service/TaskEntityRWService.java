package f_candy_d.com.boogie.domain.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Calendar;

import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.data_store.TaskTable;
import f_candy_d.com.boogie.domain.SqlRepositoryUser;
import f_candy_d.com.boogie.domain.structure.Task;
import f_candy_d.com.boogie.domain.structure.TaskType;
import f_candy_d.com.boogie.infra.SqlEntity;
import f_candy_d.com.boogie.infra.SqlRepository;
import f_candy_d.com.boogie.utils.InstantDate;

/**
 * Created by daichi on 17/09/03.
 */

public class TaskEntityRWService extends Service implements SqlRepositoryUser {

    private SqlRepository mSqlRepository;

    public long insertTask(Task task) {
        onServiceStart();

        // TODO; Check data validation
        SqlEntity entity = makeSqlEntityFromTask(task, false);
        final long id = mSqlRepository.insert(entity);

        return (id != -1) ? id : DbContract.NULL_ID;
    }

    @Nullable
    public Task findTaskById(long id) {
        onServiceStart();

        SqlEntity entity = mSqlRepository.selectRowById(TaskTable.TABLE_NAME, id);
        if (entity != null) {
            return makeTaskFromSqlEntity(entity);
        }
        return null;
    }

    public boolean updateTask(Task task) {
        onServiceStart();
        // TODO; Check data validation
        SqlEntity entity = makeSqlEntityFromTask(task, true);
        return mSqlRepository.update(entity);
    }

    public boolean deleteTask(Task task) {
        return mSqlRepository.delete(task.id, TaskTable.TABLE_NAME);
    }

    @Override
    boolean isReady() {
        return (mSqlRepository != null);
    }

    @Override
    final public void setSqlRepository(@NonNull SqlRepository repository) {
        mSqlRepository = repository;
    }

    @NonNull
    private SqlEntity makeSqlEntityFromTask(@NonNull Task task, boolean includeId) {
        SqlEntity entity = new SqlEntity(TaskTable.TABLE_NAME);

        if (includeId) {
            entity.put(TaskTable._ID, task.id);
        }
        entity.put(TaskTable._DATE_TERM_END, task.dateTermEnd.asCalendar());
        entity.put(TaskTable._DATE_TERM_START, task.dateTermStart.asCalendar());
        entity.put(TaskTable._TITLE, task.title);
        entity.put(TaskTable._IS_DONE, task.isDone);
        entity.put(TaskTable._DO_THROUGHOUT_TERM, task.doThroughoutTerm);
        entity.put(TaskTable._TYPE, task.type);

        return entity;
    }

    @NonNull
    private Task makeTaskFromSqlEntity(@NonNull SqlEntity entity) {
        Task task = new Task();
        
        task.id = entity.getLongOrDefault(TaskTable._ID, task.id);
        task.title = entity.getStringOrDefault(TaskTable._TITLE, task.title);
        task.isDone = entity.getBooleanOrDefault(TaskTable._IS_DONE, task.isDone);
        task.doThroughoutTerm = entity.getBooleanOrDefault(TaskTable._DO_THROUGHOUT_TERM, task.doThroughoutTerm);
        task.type = entity.getQuantizableEnumOrDefault(TaskTable._TYPE, TaskType.class, task.type);

        Calendar date = entity.getCalendarOrDefault(TaskTable._DATE_TERM_END, null);
        task.dateTermEnd = (date != null) ? new InstantDate(date) : task.dateTermEnd;

        date = entity.getCalendarOrDefault(TaskTable._DATE_TERM_START, null);
        task.dateTermStart= (date != null) ? new InstantDate(date) : task.dateTermStart;

        return task;
    }
}
