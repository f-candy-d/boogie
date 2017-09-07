package f_candy_d.com.boogie.presentation;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import f_candy_d.com.boogie.R;

/**
 * Created by daichi on 9/7/17.
 */

class OuterListAdapter extends RecyclerView.Adapter<OuterListAdapter.OuterListViewHolder> {

    private Context mContext;
    private ArrayList<InnerListAdapter> mAdapters;

    OuterListAdapter(Context context, Collection<InnerListAdapter> adapters) {
        mContext = context;
        mAdapters = new ArrayList<>(adapters);
    }

    @Override
    public OuterListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_contains_listview, parent, false);

        return new OuterListViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(OuterListViewHolder holder, int position) {
        holder.recyclerView.setAdapter(mAdapters.get(position));
    }

    @Override
    public int getItemCount() {
        return mAdapters.size();
    }

    static final class OuterListViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;

        OuterListViewHolder(View view, Context context) {
            super(view);
            recyclerView = view.findViewById(R.id.recyclerview_inside_cardview);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }

                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }
            });
        }
    }
}
