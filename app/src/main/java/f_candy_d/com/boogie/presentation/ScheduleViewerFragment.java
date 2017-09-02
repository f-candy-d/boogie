package f_candy_d.com.boogie.presentation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import f_candy_d.com.recyclerviewfragment.RecyclerViewFragment;

/**
 * Created by daichi on 17/09/02.
 */

public class ScheduleViewerFragment extends RecyclerViewFragment {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 50;
    }

    @NonNull
    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    /**
     * ViewHolder class
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View view) {
            super(view);
        }
    }
}
