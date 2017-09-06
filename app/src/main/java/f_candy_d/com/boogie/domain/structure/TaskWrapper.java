package f_candy_d.com.boogie.domain.structure;

import android.support.annotation.NonNull;

/**
 * Created by daichi on 9/6/17.
 */

abstract public class TaskWrapper {

    @NonNull private Task mTask;

    public TaskWrapper() {
        this(new Task());
    }

    public TaskWrapper(@NonNull TaskWrapper wrapper) {
        this(wrapper.getTask());
    }

    public TaskWrapper(@NonNull Task task) {
        setTask(task);
    }

    final public void setTask(@NonNull Task task) {
        try {
            mTask = task.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        formatTask(mTask);
    }

    @NonNull
    final public Task getTask() {
        return mTask;
    }

    abstract void formatTask(@NonNull Task task);
}
