package f_candy_d.com.boogie.view_model;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

import f_candy_d.com.boogie.business_logic.TaskIOLogic;
import f_candy_d.com.boogie.utils.PartsOfDay;
import f_candy_d.com.boogie.structure.Task;
import f_candy_d.com.boogie.utils.Group;
import f_candy_d.com.boogie.v_vm_vmc.ActivityViewModel;

/**
 * Created by daichi on 9/8/17.
 */

public class HomeViewModel extends ActivityViewModel {

    public interface RequestListener {
        void initUI();
        void showUpcomingTasksAsGroup(ArrayList<Group<Task>> taskGroup);
    }

    private RequestListener mRequestListener;
    private TaskIOLogic mTaskIOLogic;

    public HomeViewModel(Context context, RequestListener requestListener) {
        mRequestListener = requestListener;
        mTaskIOLogic = new TaskIOLogic(context);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestListener.initUI();
        showUpcomingTasksAsGroup();
    }

    public ArrayList<Group<Task>> showUpcomingTasksAsGroup() {
        final int groupCount = 4; // Morning, Afternoon, Evening, Night
        ArrayList<Group<Task>> groups = new ArrayList<>(groupCount);

        Calendar base = Calendar.getInstance();
        PartsOfDay.Parts part = PartsOfDay.getCurrentPartOfDay();
        long start = PartsOfDay.getNearestStartDatetime(base, part);

        for (int i = 0; i < groupCount; ++i) {
            part = part.nextPart();
            base.setTimeInMillis(start);
            long end = PartsOfDay.getNextStartDatetime(base, part);

            ArrayList<Task> result = mTaskIOLogic.getTasksInTerm(start, end);
            Group<Task> group = new Group<>(null, result);

            group.setName(part.previousPart().toString());
            groups.add(group);
            start = end;
        }

        return groups;
    }
}
