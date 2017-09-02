package f_candy_d.com.recyclerviewfragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by daichi on 17/08/29.
 */

class RecyclerViewFragmentAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    interface DispatchMethodCallListener<VH extends RecyclerView.ViewHolder> {
        VH onCreateViewHolder(ViewGroup parent, int viewType);
        void onBindViewHolder(VH holder, int position);
        int getItemCount();
        int getItemViewType(int position);
    }

    @NonNull
    private DispatchMethodCallListener<VH> mDispatchMethodCallListener;

    RecyclerViewFragmentAdapter(@NonNull DispatchMethodCallListener<VH> dispatchMethodCallListener) {
        mDispatchMethodCallListener = dispatchMethodCallListener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return mDispatchMethodCallListener.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        mDispatchMethodCallListener.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mDispatchMethodCallListener.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mDispatchMethodCallListener.getItemViewType(position);
    }
}
