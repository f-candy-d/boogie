package f_candy_d.com.boogie.presentation;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.utils.EditableItemSpanSizeAdapter;

/**
 * Created by daichi on 17/09/01.
 */

public class EntryPointAdapter extends EditableItemSpanSizeAdapter<EntryPointAdapter.EntryPointViewHolder> {

    public interface OnEntryPointClickListener {
        void onEntryPointClicked(int tag);
    }

    private ArrayList<EntryPoint> mEntryPoints;
    private boolean mIsItemFullSpan = true;
    private OnEntryPointClickListener mOnEntryPointClickListener;

    public EntryPointAdapter() {
        mEntryPoints = new ArrayList<>();
    }

    public void setOnEntryPointClickListener(OnEntryPointClickListener onEntryPointClickListener) {
        mOnEntryPointClickListener = onEntryPointClickListener;
    }

    public void addEntryPoint(@DrawableRes int drawable, String title, int tag, int index) {
        EntryPoint entryPoint = new EntryPoint();
        entryPoint.drawable = drawable;
        entryPoint.title = title;
        entryPoint.tag = tag;
        mEntryPoints.add(index, entryPoint);
    }

    public void addEntryPoint(@DrawableRes int drawable, String title, int tag) {
        addEntryPoint(drawable, title, tag, mEntryPoints.size());
    }

    public void setItemFullSpan(boolean itemFullSpan) {
        mIsItemFullSpan = itemFullSpan;
    }

    /**
     * region; Abstract methods implementation
     */

    @Override
    public EntryPointViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_point_card, parent, false);
        return new EntryPointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EntryPointViewHolder holder, int position) {
        setupItemSpanSize(holder.itemView, mIsItemFullSpan);
        EntryPoint entryPoint = mEntryPoints.get(position);
        holder.title.setText(entryPoint.title);
        holder.setOnClickListener(mOnEntryPointClickListener, entryPoint.tag);
    }

    @Override
    public int getItemCount() {
        return mEntryPoints.size();
    }

    /**
     * EntryPoint structure
     */
    private static class EntryPoint {
        int tag;
        @DrawableRes int drawable;
        String title = null;
    }

    /**
     * ViewHolder
     */
    static class EntryPointViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        EntryPointViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.entry_point_card_title);
        }

        void setOnClickListener(final OnEntryPointClickListener listener, final int tag) {
            super.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onEntryPointClicked(tag);
                    }
                }
            });
        }
    }
}
