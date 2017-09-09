package f_candy_d.com.boogie.business_logic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import f_candy_d.com.boogie.data_store.TaskTableRule;
import f_candy_d.com.boogie.structure.Task;
import f_candy_d.com.boogie.infra.SqlEntity;
import f_candy_d.com.boogie.infra.sql.SqlBetweenExpr;
import f_candy_d.com.boogie.infra.sql.SqlLogicExpr;
import f_candy_d.com.boogie.infra.sql.SqlQuery;

/**
 * Created by daichi on 9/9/17.
 */

public class TaskIOLogic extends SqlEntityIOLogic {

    public TaskIOLogic(Context context) {
        super(context);
    }

    @Nullable
    public Task findTaskById(long id) {
        SqlEntity entity = findById(id, TaskTableRule.TABLE_NAME);
        if (entity != null) {
            return new Task(entity);
        }
        return null;
    }

    @NonNull
    public ArrayList<Task> getTasksInTerm(long dateTermStart, long dateTermEnd) {
        SqlBetweenExpr between1 =
                new SqlBetweenExpr(TaskTableRule._DATE_TERM_START)
                        .setRange(dateTermStart, dateTermEnd)
                        .setRangeBoundaries(false, true);

        SqlBetweenExpr between2 =
                new SqlBetweenExpr(TaskTableRule._DATE_TERM_END)
                        .setRange(dateTermStart, dateTermEnd)
                        .setRangeBoundaries(false, true);

        SqlLogicExpr where = new SqlLogicExpr(between1).or(between2);

        SqlQuery query = new SqlQuery();
        query.setSelection(where);
        query.putTables(TaskTableRule.TABLE_NAME);

        SqlEntity[] results = getSqlRepository().select(query);
        ArrayList<Task> tasks = new ArrayList<>(results.length);

        for (SqlEntity entity : results) {
            tasks.add(new Task(entity));
        }

        return tasks;
    }
}
