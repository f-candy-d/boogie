package f_candy_d.com.boogie.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import f_candy_d.com.boogie.R;

/**
 * Created by daichi on 9/7/17.
 */

public class InnerListAdapter extends RecyclerView.Adapter<InnerListAdapter.InnerListViewHolder> {

    @Override
    public InnerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_schedule_summary, parent, false);
        return new InnerListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InnerListViewHolder holder, int position) {
        holder.title.setText("item position = " + String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    static class InnerListViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        InnerListViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.list_item_schedule_summary_title);
        }
    }
}
