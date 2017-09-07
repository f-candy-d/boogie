package f_candy_d.com.boogie.utils;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import f_candy_d.com.boogie.R;

/**
 * Created by daichi on 9/7/17.
 */

class OuterListAdapter extends RecyclerView.Adapter<OuterListAdapter.OuterListViewHolder> {

    private Context mContext;
    private ArrayList<InnerListAdapter> mAdapters;
    private RecyclerView mParentRecyclerView;

    OuterListAdapter(Context context, RecyclerView recyclerView) {
        mContext = context;
        mAdapters = new ArrayList<>();
        mParentRecyclerView = recyclerView;
    }

    public void addAdapter(InnerListAdapter adapter) {
        adapter.setParentAdapter(this);
        adapter.setPositionInParent(mAdapters.size());
        mAdapters.add(adapter);
    }

    @Override
    public OuterListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_contains_listview, parent, false);

        return new OuterListViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(OuterListViewHolder holder, int position) {
        InnerListAdapter innerAdapter = mAdapters.get(position);
        innerAdapter.setParentRecyclerView(holder.recyclerView);
        holder.recyclerView.setAdapter(innerAdapter);
    }

    @Override
    public int getItemCount() {
        return mAdapters.size();
    }

    public void onInnerItemRemoved(final InnerListAdapter innerAdapter) {
        if (innerAdapter.getItemCount() == 0) {
            // update positions
            mAdapters.remove(innerAdapter.getPositionInParent());
            for (int i = 0; i < mAdapters.size(); ++i) {
                mAdapters.get(i).setPositionInParent(i);
            }
            notifyItemRemoved(innerAdapter.getPositionInParent());

        } else {
            ((DefaultItemAnimator) mParentRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            notifyItemChanged(innerAdapter.getPositionInParent());
        }
    }

    static final class OuterListViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;

        OuterListViewHolder(final View view, Context context) {
            super(view);
            recyclerView = view.findViewById(R.id.recyclerview_inside_cardview);
            // Enable touch event of items in the nested recycler view, and disable scrolling of it.
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    final int swipedPosition = viewHolder.getAdapterPosition();
                    ((InnerListAdapter) recyclerView.getAdapter()).removeItem(swipedPosition);
                }
            };

            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);
        }
    }
}
