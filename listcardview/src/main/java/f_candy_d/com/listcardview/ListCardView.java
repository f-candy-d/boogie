package f_candy_d.com.listcardview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by daichi on 17/08/29.
 */

public class ListCardView extends CardView {

    public interface OnItemViewCreatedListener {
        void onItemViewCreated(ListCardView parent, View item, @LayoutRes int layoutXml, int index);
    }

    public interface OnItemClickListener {
        void onItemClick(ListCardView parent, View item);
    }

    // Default values
    private static final boolean SHOW_DIVIDER = true;
    private static final boolean SHOW_DIVIDER_BETWEEN_HEADER_AND_FIRST_ITEM = false;

    // UI
    private LinearLayout mContentsContainer;
    private TextView mHeaderTextView;
    private ArrayList<View> mItemViews;

    // Attributes
    // header
    @ColorInt
    private int mHeaderTextColor;
    @Px
    private int mHeaderTextLeftMargin;
    @Px private int mHeaderTextRightMargin;
    @Px private int mHeaderTextTopMargin;
    @Px private int mHeaderTextBottomMargin;
    @Px private int mHeaderTextSize;
    private String mHeaderText;
    // diviser
    @Px private int mDividerStrokeWidh;
    @ColorInt private int mDividerColor;
    @Px private int mDividerLeftMargin;
    @Px private int mDividerRightMargin;
    private boolean mShowDivider;
    private boolean mShowDividerBetweenHeaderAndFirstItem;
    // items
    @Px private int mItemsTopMargin;
    @Px private int mItemsBottomMargin;
    @Px private int mItemsLeftMargin;
    @Px private int mItemsRightMargin;

    // Misc
    private Context mContext;
    private OnItemViewCreatedListener mOnItemViewCreatedListener;
    private OnItemClickListener mOnItemClickListener;
    private Paint mDividerPaint;

    public ListCardView(Context context) {
        super(context);
        initAttributes(context);
        init(context);
    }

    public ListCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mItemViews = new ArrayList<>();
        mOnItemViewCreatedListener = null;
        mOnItemClickListener = null;

        // Paint object for dividers
        mDividerPaint = new Paint();
        mDividerPaint.setColor(mDividerColor);
        mDividerPaint.setStrokeWidth(mDividerStrokeWidh);

        // Contents container
        mContentsContainer = new LinearLayout(context);
        mContentsContainer.setOrientation(LinearLayout.VERTICAL);
        mContentsContainer.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mContentsContainer.setPadding(0, 0, 0, mItemsBottomMargin);
        this.addView(mContentsContainer);

        // Header
        mHeaderTextView = new TextView(context);
        mHeaderTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mHeaderTextSize);
        mHeaderTextView.setTextColor(mHeaderTextColor);
        mHeaderTextView.setText(mHeaderText);
        mHeaderTextView.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, mItemsTopMargin);
        mHeaderTextView.setLayoutParams(layoutParams);
        mHeaderTextView.setPadding(
                mHeaderTextLeftMargin,
                mHeaderTextTopMargin,
                mHeaderTextRightMargin,
                mHeaderTextBottomMargin);
        mContentsContainer.addView(mHeaderTextView);
    }

    private void initAttributes(Context context) {
        mHeaderTextColor = getColorFromResources(context, R.color.categoryCardViewHeaderTextColor);
        mHeaderTextLeftMargin = getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_header_text_left_margin);
        mHeaderTextRightMargin = getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_header_text_right_margin);
        mHeaderTextTopMargin = getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_header_text_top_margin);
        mHeaderTextBottomMargin = getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_header_text_bottom_margin);
        mHeaderTextSize = getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_header_text_size);
        mHeaderText = null;

        mDividerStrokeWidh = getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_divider_stroke_width);
        mDividerColor = getColorFromResources(context, R.color.categoryCardViewDividerColor);
        mDividerLeftMargin = getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_divider_left_margin);
        mDividerRightMargin = getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_divider_right_margin);
        mShowDivider = SHOW_DIVIDER;
        mShowDividerBetweenHeaderAndFirstItem = SHOW_DIVIDER_BETWEEN_HEADER_AND_FIRST_ITEM;

        mItemsTopMargin = getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_items_top_margin);
        mItemsBottomMargin = getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_items_bottom_margin);
        mItemsLeftMargin = getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_items_left_margin);
        mItemsRightMargin = getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_items_right_margin);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ListCardView, 0, 0);
        try {
            mHeaderTextColor = a.getColor(R.styleable.ListCardView_headerTextColor,
                    getColorFromResources(context, R.color.categoryCardViewHeaderTextColor));
            mHeaderTextLeftMargin = a.getDimensionPixelSize(R.styleable.ListCardView_headerTextLeftMargin,
                    getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_header_text_left_margin));
            mHeaderTextRightMargin = a.getDimensionPixelSize(R.styleable.ListCardView_headerTextRightMargin,
                    getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_header_text_right_margin));
            mHeaderTextTopMargin = a.getDimensionPixelSize(R.styleable.ListCardView_headerTextTopMargin,
                    getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_header_text_top_margin));
            mHeaderTextBottomMargin = a.getDimensionPixelSize(R.styleable.ListCardView_headerTextBottomMargin,
                    getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_header_text_bottom_margin));
            mHeaderTextSize = a.getDimensionPixelSize(R.styleable.ListCardView_headerTextSize,
                    getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_header_text_size));
            mHeaderText = a.getString(R.styleable.ListCardView_headerText);

            mDividerStrokeWidh = a.getDimensionPixelSize(R.styleable.ListCardView_dividerStrokeWidth,
                    getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_divider_stroke_width));
            mDividerColor = a.getColor(R.styleable.ListCardView_dividerColor,
                    getColorFromResources(context, R.color.categoryCardViewDividerColor));
            mDividerLeftMargin = a.getDimensionPixelSize(R.styleable.ListCardView_dividerLeftMargin,
                    getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_divider_left_margin));
            mDividerRightMargin = a.getDimensionPixelSize(R.styleable.ListCardView_dividerRightMargin,
                    getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_divider_right_margin));
            mShowDivider = a.getBoolean(R.styleable.ListCardView_showDivider,
                    SHOW_DIVIDER);
            mShowDividerBetweenHeaderAndFirstItem = a.getBoolean(R.styleable.ListCardView_showDividerBetweenHeaderAndFirstItem,
                    SHOW_DIVIDER_BETWEEN_HEADER_AND_FIRST_ITEM);

            mItemsTopMargin = a.getDimensionPixelSize(R.styleable.ListCardView_itemsTopMargin,
                    getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_items_top_margin));
            mItemsBottomMargin = a.getDimensionPixelSize(R.styleable.ListCardView_itemsBottomMargin,
                    getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_items_bottom_margin));
            mItemsLeftMargin = a.getDimensionPixelSize(R.styleable.ListCardView_itemsLeftMargin,
                    getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_items_left_margin));
            mItemsRightMargin = a.getDimensionPixelSize(R.styleable.ListCardView_itemsRightMargin,
                    getDimensionPixelSizeFromResources(context, R.dimen.category_card_view_items_right_margin));

        } finally {
            a.recycle();
        }
    }

    public void setOnItemViewCreatedListener(OnItemViewCreatedListener onItemViewCreatedListener) {
        mOnItemViewCreatedListener = onItemViewCreatedListener;
    }

    /**
     * @param layoutXml A resource id of the item's layout xml
     */
    public void addItem(@LayoutRes int layoutXml) {
        View itemView = createItemView(layoutXml);
        addItem(itemView);
        // Notify a listener that a new item view is created
        if (mOnItemViewCreatedListener != null) {
            mOnItemViewCreatedListener.onItemViewCreated(this, itemView, layoutXml, mItemViews.size() - 1);
        }

    }

    /**
     *
     * @param layoutXml A resource id of the item's layout xml
     * @param itemCount The number of views which will be inflated
     */
    public void addItems(@LayoutRes int layoutXml, int itemCount) {
        for (int i = 0; i < itemCount; ++i) {
            addItem(layoutXml);
        }
    }

    public void addItem(View itemView) {
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(ListCardView.this, view);
                }
            }
        });

        mItemViews.add(itemView);
        mContentsContainer.addView(itemView);
    }

    public void removeItem(View view) {
        mItemViews.remove(view);
        mContentsContainer.removeView(view);
        // Re-draw dividers
        invalidate();
    }

    public void removeAllItems() {
        for (View item : mItemViews) {
            mContentsContainer.removeView(item);
        }
        mItemViews.clear();
        // Re-draw dividers
        invalidate();

    }

    public View removeItem(int index) {
        View item = mItemViews.get(index);
        removeItem(item);
        return item;
    }

    void removeItemsFromBack(int count) {
        View view;
        for (int i = 0; i < count; ++i) {
            view = mItemViews.remove(mItemViews.size() - 1);
            mContentsContainer.removeView(view);
        }
        invalidate();
    }

    public View getItemAt(int index) {
        return mItemViews.get(index);
    }

    View replaceItemAt(View itemView, int index) {
        View replaced = mItemViews.set(index, itemView);
        index = mContentsContainer.indexOfChild(replaced);
        mContentsContainer.removeViewAt(index);
        mContentsContainer.addView(itemView, index);

        return replaced;
    }

    public View createItemView(@LayoutRes int layoutXml) {
        View itemView = inflate(mContext, layoutXml, null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(mItemsLeftMargin, 0, mItemsRightMargin, 0);
        itemView.setLayoutParams(layoutParams);
        return itemView;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mShowDivider) {
            return;
        }

        // Line's start & stop y position
        int lineY = mHeaderTextView.getHeight() + mItemsTopMargin;
        // Line's start & stop x position
        final int lineStartX = mDividerLeftMargin;
        final int lineStopX = canvas.getWidth() - mDividerRightMargin;

        for (int i = 0; i < mItemViews.size(); ++i) {

            if (i != 0 || mShowDividerBetweenHeaderAndFirstItem) {
                canvas.drawLine(lineStartX, lineY, lineStopX, lineY, mDividerPaint);
            }

            // Calculate next line's Y
            if (i + 1 < mItemViews.size()) {
                lineY += mItemViews.get(i + 1).getHeight();
            }
        }
    }

    @Px
    private static int getDimensionPixelSizeFromResources(Context context, @DimenRes int id) {
        return context.getResources().getDimensionPixelSize(id);
    }

    @ColorInt
    private static int getColorFromResources(Context context, @ColorRes int id) {
        return ContextCompat.getColor(context, id);
    }

    public void setHeaderText(String headerText) {
        mHeaderText = headerText;
        mHeaderTextView.setText(mHeaderText);
    }

    public void setHeaderTextColor(int headerTextColor) {
        mHeaderTextColor = headerTextColor;
        mHeaderTextView.setTextColor(mHeaderTextColor);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public int getItemCount() {
        return mItemViews.size();
    }
}
