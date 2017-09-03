package f_candy_d.com.boogie.domain.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Calendar;

import f_candy_d.com.boogie.data_store.TaskTableBase;
import f_candy_d.com.boogie.domain.SqlRepositoryUser;
import f_candy_d.com.boogie.domain.structure.Task;
import f_candy_d.com.boogie.infra.SqlEntity;
import f_candy_d.com.boogie.infra.SqlRepository;
import f_candy_d.com.boogie.utils.InstantDate;

/**
 * Created by daichi on 17/09/03.
 */

abstract class TaskEntityRWService<T extends Task> extends Service implements SqlRepositoryUser {

    private SqlRepository mSqlRepository;

    abstract public long insertTask(T task);

    @Nullable
    abstract public T findTaskById(long id);

    abstract public boolean updateTask(T task);

    abstract public boolean deleteTask(T task);

    @Override
    boolean isReady() {
        return (mSqlRepository != null);
    }

    @Override
    final public void setSqlRepository(@NonNull SqlRepository repository) {
        mSqlRepository = repository;
    }

    protected SqlRepository getSqlRepository() {
        return mSqlRepository;
    }

    @NonNull
    final protected SqlEntity makeSqlEntityFromTask(@NonNull Task task, boolean includeId) {
        SqlEntity entity = new SqlEntity();

        if (includeId) {
            entity.put(TaskTableBase._ID, task.id);
        }
        entity.put(TaskTableBase._PRIORITY, task.priority);
        entity.put(TaskTableBase._TERM_END_DATE, task.termEndDate.asCalendar());
        entity.put(TaskTableBase._TERM_START_DATE, task.termStartDate.asCalendar());
        entity.put(TaskTableBase._NOTE, task.note);
        entity.put(TaskTableBase._TITLE, task.title);

        return entity;
    }

    @NonNull
    final protected void makeTaskFromSqlEntity(@NonNull Task output, @NonNull SqlEntity entity) {
        output.id = entity.getLongOrDefault(TaskTableBase._ID, output.id);
        output.priority = entity.getIntOrDefault(TaskTableBase._PRIORITY, output.priority);

        Calendar date = entity.getCalendarOrDefault(TaskTableBase._TERM_END_DATE, null);
        output.termEndDate = (date != null) ? new InstantDate(date) : null;

        date = entity.getCalendarOrDefault(TaskTableBase._TERM_START_DATE, null);
        output.termStartDate = (date != null) ? new InstantDate(date) : null;

        output.note = entity.getStringOrDefault(TaskTableBase._NOTE, null);
        output.title = entity.getStringOrDefault(TaskTableBase._TITLE, null);
    }
}
