package f_candy_d.com.listcardview;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by daichi on 17/08/29.
 */

final public class ItemViewManager<VH extends ItemViewManager.ItemViewHolder> {

    abstract public static class ItemViewHolder {

        private int viewType;
        private View itemView;

        public ItemViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }

    public interface Callback<VH extends ItemViewHolder> {
        int getCardItemCount(int cardType);
        int getCardItemViewType(int cardType, int itemIndex);
        ItemViewHolder onCreateCardItemViewHolder(int viewType, ListCardView parent);
        void onBindItemViewHolder(VH holder, int cardType, int index);
    }

    private static final int VIEW_TAG_ITEM_VIEW_HOLDER = -1;

    @NonNull
    private Callback<VH> mCallback;
    @NonNull private ItemViewRecycler mRecycler;

    public ItemViewManager(@NonNull Callback<VH> callback) {
        mCallback = callback;
        mRecycler = new ItemViewRecycler();
    }

    @SuppressWarnings("unchecked cast")
    public void setupCardItems(ListCardView cardView, int cardType) {
        if (cardView == null) {
            return;
        }

        final int requiredItemCount = mCallback.getCardItemCount(cardType);
        final int itemCount = cardView.getItemCount();

        int viewType;
        View itemView;
        ItemViewHolder viewHolder;

        for (int i = 0; i < itemCount; ++i) {
            viewHolder = (ItemViewHolder) cardView.getItemAt(i).getTag(VIEW_TAG_ITEM_VIEW_HOLDER);
            viewType = mCallback.getCardItemViewType(cardType, i);

            if (i < requiredItemCount) {
                if (viewHolder.viewType != viewType) {
                    itemView = replaceCardItemViewAt(i, viewType, cardView);
                    mCallback.onBindItemViewHolder(
                            (VH) itemView.getTag(VIEW_TAG_ITEM_VIEW_HOLDER), cardType, i);

                } else {
                    mCallback.onBindItemViewHolder((VH) viewHolder, cardType, i);
                }

            } else {
                // When the number of existing items in the card is
                // greater than that of required items...
                mRecycler.recycleItemView(viewHolder.itemView, viewHolder.viewType);
            }
        }

        if (itemCount > requiredItemCount) {
            // Remove unnecessary items in the card
            cardView.removeItemsFromBack(itemCount - requiredItemCount);
        }

        // When the number of existing items in the card is
        // less than that of required items...
        for (int i = itemCount; i < requiredItemCount; ++i) {
            viewType = mCallback.getCardItemViewType(cardType, i);
            itemView = getItemViewForType(viewType, cardView);
            cardView.addItem(itemView);
            mCallback.onBindItemViewHolder(
                    (VH) itemView.getTag(VIEW_TAG_ITEM_VIEW_HOLDER), cardType, i);
        }
    }

    /**
     *
     * @param index
     * @param viewType
     * @param cardView
     * @return Replaced, old item view
     */
    private View replaceCardItemViewAt(int index, int viewType, ListCardView cardView) {
        View itemView = getItemViewForType(viewType, cardView);
        itemView = cardView.replaceItemAt(itemView, index);
        ItemViewHolder viewHolder = (ItemViewHolder) itemView.getTag(VIEW_TAG_ITEM_VIEW_HOLDER);
        mRecycler.recycleItemView(viewHolder.itemView, viewHolder.viewType);
        return itemView;
    }

    private View getItemViewForType(int viewType, ListCardView cardView) {
        View itemView = mRecycler.getItemViewIfUnusedExist(viewType);
        // If a cached & unused view for 'viewType' does not exist, create new one
        if (itemView == null) {
            ItemViewHolder viewHolder = mCallback.onCreateCardItemViewHolder(viewType, cardView);
            viewHolder.viewType = viewType;
            viewHolder.itemView.setTag(VIEW_TAG_ITEM_VIEW_HOLDER, viewHolder);
            mRecycler.cacheItemViewAndUseImmediately(viewHolder.itemView, viewType);
            itemView = viewHolder.itemView;

        }

        return itemView;
    }
}