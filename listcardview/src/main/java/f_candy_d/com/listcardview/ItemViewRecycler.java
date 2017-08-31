package f_candy_d.com.listcardview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by daichi on 17/08/29.
 */

class ItemViewRecycler {

    // { key::view type, value::item view }
    @NonNull
    private SparseArray<ArrayList<View>> mCachedItemViews;
    // { key::view type, value::count of used item views }
    @NonNull private SparseIntArray mUsedItemViewCounts;

    ItemViewRecycler() {
        mCachedItemViews = new SparseArray<>();
        mUsedItemViewCounts = new SparseIntArray();
    }

    void cacheItemView(@NonNull View itemView, int viewType) {
        ArrayList<View> itemViews;
        if ((itemViews = mCachedItemViews.get(viewType, null)) == null) {
            itemViews = new ArrayList<>();
            mCachedItemViews.put(viewType, itemViews);
            mUsedItemViewCounts.put(viewType, 0);
        }

        itemViews.add(itemView);
    }

    void cacheItemViewAndUseImmediately(@NonNull View itemView, int viewType) {
        cacheItemView(itemView, viewType);
        increaseUsedItemCount(viewType, 1);
    }

    @Nullable
    View getItemViewIfUnusedExist(int viewType) {
        ArrayList<View> itemViews = mCachedItemViews.get(viewType, null);
        if (itemViews == null) {
            return null;
        }

        int usedViewCount = mUsedItemViewCounts.get(viewType);
        if (usedViewCount < itemViews.size()) {
            View itemView = itemViews.get(usedViewCount);
            increaseUsedItemCount(viewType, 1);
            return itemView;
        }

        return null;
    }

    void recycleItemView(@NonNull View itemView, int viewType) {
        ArrayList<View> itemViews = mCachedItemViews.get(viewType, null);
        if (itemViews != null) {
            // Move the returned view to the back of the array
            itemViews.remove(itemView);
            itemViews.add(itemView);
            decreaseUsedItemCount(viewType, 1);
        }
    }

    private void increaseUsedItemCount(int viewType, int count) {
        final int usedCount = mUsedItemViewCounts.get(viewType);
        mUsedItemViewCounts.put(viewType, usedCount + count);
    }

    private void decreaseUsedItemCount(int viewType, int count) {
        final int usedCount = mUsedItemViewCounts.get(viewType);
        mUsedItemViewCounts.put(viewType, usedCount - count);
    }
}
