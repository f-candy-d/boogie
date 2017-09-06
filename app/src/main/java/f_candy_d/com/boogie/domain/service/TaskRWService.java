package f_candy_d.com.boogie.domain.service;

import android.support.annotation.Nullable;

import f_candy_d.com.boogie.data_store.TaskTableRule;
import f_candy_d.com.boogie.domain.structure.Task;
import f_candy_d.com.boogie.infra.SqlEntity;

/**
 * Created by daichi on 17/09/03.
 */

public class TaskRWService extends SqlEntityRWService {

    @Nullable
    public Task findTaskById(long id) {
        SqlEntity entity = findById(id, TaskTableRule.TABLE_NAME);
        if (entity != null) {
            return new Task(entity);
        }
        return null;
    }
}
