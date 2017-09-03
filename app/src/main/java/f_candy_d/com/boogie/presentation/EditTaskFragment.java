package f_candy_d.com.boogie.presentation;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import f_candy_d.com.boogie.domain.structure.Task;

/**
 * Created by daichi on 17/09/03.
 */

abstract class EditTaskFragment extends Fragment {

    protected static final String ARG_TASK_ID = "arg_task_id";

    static Bundle makeArgs(long id) {
        Bundle args = new Bundle();
        args.putLong(ARG_TASK_ID, id);
        return args;
    }

    interface InteractListener {
        void onFinishEditingTask(Task task);
        void onCancelled();
    }
}
