package f_candy_d.com.boogie.domain.structure;

import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.utils.InstantDate;

/**
 * Created by daichi on 17/09/03.
 */

public class Task implements Cloneable {

    public long id;
    public String title;
    public InstantDate dateTermStart;
    public InstantDate dateTermEnd;
    public boolean isDone;
    public boolean doThroughoutTerm;
    public TaskType type;

    public Task() {
        init();
    }

    public Task(Task task) {
        if (task != null) {
            this.id = task.id;
            this.title = task.title;
            this.isDone = task.isDone;
            this.doThroughoutTerm = task.doThroughoutTerm;
            this.type = task.type;

            this.dateTermStart = (task.dateTermStart != null)
                    ? new InstantDate(task.dateTermStart)
                    : null;

            this.dateTermEnd = (task.dateTermEnd != null)
                    ? new InstantDate(task.dateTermEnd)
                    : null;
        } else {
            init();
        }
    }

    public void init() {
        id = DbContract.NULL_ID;
        title = null;
        dateTermStart = null;
        dateTermEnd = null;
        isDone = false;
        doThroughoutTerm = false;
        type = null;
    }

    public String toSummary() {
        return title;
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        Task task = (Task) super.clone();

        this.dateTermStart = (task.dateTermStart != null)
                ? new InstantDate(task.dateTermStart)
                : null;

        this.dateTermEnd = (task.dateTermEnd != null)
                ? new InstantDate(task.dateTermEnd)
                : null;

        return task;
    }
}
