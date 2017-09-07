package f_candy_d.com.boogie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import f_candy_d.com.boogie.R;

/**
 * Created by daichi on 9/7/17.
 */

public class InnerListAdapter extends RecyclerView.Adapter<InnerListAdapter.InnerListViewHolder> {

    private ArrayList<String> mData;
    private OuterListAdapter mParentAdapter;
    private int mPositionInParent;
    private RecyclerView mParentRecyclerView;

    public InnerListAdapter() {
        mData = new ArrayList<>();
        for (int i = 0; i < 7; ++i) {
            String str = "item position = " + String.valueOf(i);
            mData.add(str);
        }
    }

    public OuterListAdapter getParentAdapter() {
        return mParentAdapter;
    }

    public void setParentAdapter(OuterListAdapter parentAdapter) {
        mParentAdapter = parentAdapter;
    }

    public int getPositionInParent() {
        return mPositionInParent;
    }

    public void setPositionInParent(int positionInParent) {
        mPositionInParent = positionInParent;
    }

    public RecyclerView getParentRecyclerView() {
        return mParentRecyclerView;
    }

    public void setParentRecyclerView(RecyclerView parentRecyclerView) {
        mParentRecyclerView = parentRecyclerView;
    }

    @Override
    public InnerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_schedule_summary, parent, false);
        return new InnerListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InnerListViewHolder holder, int position) {
        holder.title.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        mParentAdapter.onInnerItemRemoved(this);
    }

    static class InnerListViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        InnerListViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.list_item_schedule_summary_title);
        }
    }
}
