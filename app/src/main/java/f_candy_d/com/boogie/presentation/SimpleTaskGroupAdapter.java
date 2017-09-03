package f_candy_d.com.boogie.presentation;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.domain.structure.ExactTermTask;
import f_candy_d.com.boogie.domain.structure.Task;

/**
 * Created by daichi on 17/09/03.
 */

class SimpleTaskGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements DividerItemDecoration.Callback {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_TASK = 1;

    private ArrayList<Task> mTasks;

    SimpleTaskGroupAdapter() {
        mTasks = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            ExactTermTask task = new ExactTermTask();
            mTasks.add(task);
        }
    }

    void addTask(Task task) {
        mTasks.add(task);
    }

    void addTasks(Collection<Task> tasks) {
        mTasks.addAll(tasks);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {
            case VIEW_TYPE_HEADER:
                view = inflater.inflate(R.layout.simple_task_adapter_header, parent, false);
                return new HeaderViewHolder(view);

            case VIEW_TYPE_TASK:
                view = inflater.inflate(R.layout.simple_task_adapter_task_item, parent, false);
                return new TaskViewHolder(view);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        // items + header
        return mTasks.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        final int offsetFromFirstItem = getPositionOffsetFromFirstItem(position);
        if (offsetFromFirstItem == -1) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_TASK;
        }
    }

    private int getPositionOffsetFromFirstItem(int position) {
        return position - 1;
    }

    /**
     * DividerItemDecoration.Callback implementation
     */
    @Override
    public boolean drawDividerAboveItem(int itemPosition) {
        return (1 < itemPosition && itemPosition < getItemCount());
    }

    /**
     * TaskViewHolder
     */
    static class TaskViewHolder extends RecyclerView.ViewHolder {

        public TaskViewHolder(View view) {
            super(view);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }
    }
}
