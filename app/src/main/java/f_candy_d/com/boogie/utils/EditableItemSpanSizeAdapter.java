package f_candy_d.com.boogie.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by daichi on 17/09/01.
 */

abstract public class EditableItemSpanSizeAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private OnSetupItemSpanSizeListener mOnSetupItemSpanSizeListener;

    public OnSetupItemSpanSizeListener getOnSetupItemSpanSizeListener() {
        return mOnSetupItemSpanSizeListener;
    }

    public void setOnSetupItemSpanSizeListener(OnSetupItemSpanSizeListener onSetupItemSpanSizeListener) {
        mOnSetupItemSpanSizeListener = onSetupItemSpanSizeListener;
    }

    protected void setupItemSpanSize(View itemView, boolean requestFullSpan) {
        if (mOnSetupItemSpanSizeListener != null) {
            mOnSetupItemSpanSizeListener.onSetupItemSpanSize(itemView, requestFullSpan);
        }
    }
}
