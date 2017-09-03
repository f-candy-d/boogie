package f_candy_d.com.boogie.domain.structure;

import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.utils.InstantDate;

/**
 * Created by daichi on 17/09/03.
 */

public abstract class Task {

    public static final int DEFAULT_PRIORITY = 0;

    public long id;
    public String title;
    public String note;
    public InstantDate termStartDate;
    public InstantDate termEndDate;
    public int priority;

    public Task() {
        init();
    }

    public Task(Task task) {
        if (task != null) {
            id = task.id;
            title = task.title;
            note = task.note;
            termStartDate = new InstantDate(task.termStartDate);
            termEndDate = new InstantDate(task.termEndDate);
            priority = task.priority;
        }
    }

    public void init() {
        id = DbContract.NULL_ID;
        title = null;
        note = null;
        termStartDate = null;
        termEndDate = null;
        priority = DEFAULT_PRIORITY;
    }

    abstract public TaskType getType();
    abstract public String toSummary();
}
