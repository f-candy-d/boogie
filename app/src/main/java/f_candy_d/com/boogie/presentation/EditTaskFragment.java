package f_candy_d.com.boogie.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.domain.structure.Task;

/**
 * Created by daichi on 17/09/03.
 */

abstract class EditTaskFragment extends Fragment {

    /**
     * Use this in a factory method.
     * Pass a returned value to #setArguments().
     */
    static Bundle makeArgs(long taskId) {
        Bundle args = new Bundle();
        args.putLong(EditTaskActivity.EXTRA_TASK_ID, taskId);
        return args;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        if (getArguments() != null) {
            onPrepareToEdit(getArguments().getLong(EditTaskActivity.EXTRA_TASK_ID, DbContract.NULL_ID));
        } else {
            onPrepareToEdit(DbContract.NULL_ID);
        }
    }

    /**
     * Called in #onCreate().
     * @param taskId DbContract.NULL_ID or passed mId of a task entity
     */
    abstract protected void onPrepareToEdit(long taskId);

    /**
     * Called in #onCreate(). After call this method, #prepareToEdit() will be called.
     * Prepare something for #prepareToEdit() in this method.
     */
    abstract protected void init();

    /**
     * Interaction Interface
     */
    interface InteractListener {
        void onFinishEditingTask(Task task);
        void onCancelled();
    }
}
