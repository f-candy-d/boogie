package f_candy_d.com.boogie.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.domain.structure.Task;
import f_candy_d.com.boogie.domain.structure.TaskType;
import f_candy_d.com.boogie.utils.QuantizableHelper;

public class EditTaskActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "extra_task_id";
    public static final String EXTRA_TASK_TYPE = "extra_task_type";

    @Nullable
    static Bundle makeExtras(Task task) {
        if (task != null && task.type != null) {
            Bundle extras = new Bundle();
            extras.putLong(EXTRA_TASK_ID, task.id);
            extras.putInt(EXTRA_TASK_TYPE, task.type.quantize());
            return extras;
        }

        return null;
    }

    @Nullable
    static Bundle makeExtras(TaskType taskType) {
        if (taskType != null) {
            Bundle extra = new Bundle();
            extra.putInt(EXTRA_TASK_TYPE, taskType.quantize());
            return extra;
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        long taskId = intent.getLongExtra(EXTRA_TASK_ID, DbContract.NULL_ID);
        TaskType taskType = QuantizableHelper.convertFromEnumClass(TaskType.class, intent.getIntExtra(EXTRA_TASK_TYPE, -1));

        setContentView(R.layout.activity_edit_task);
        initUI();
        launchFragmentForType(taskType, taskId);
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void launchFragmentForType(TaskType type, long taskId) {
        if (type == null) {
            throw new NullPointerException();
        }

        EditTaskFragment fragment = null;
        switch (type) {
            case EVENT:
                fragment = EditEventTaskFragment.newInstance(taskId);
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, null).commit();
        }
    }
}
