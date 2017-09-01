package f_candy_d.com.boogie.presentation;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.domain.structure.TermContent;
import f_candy_d.com.boogie.utils.InstantTime;
import f_candy_d.com.boogie.utils.Time;
import f_candy_d.com.listcardview.ItemViewManager;
import f_candy_d.com.listcardview.ListCardView;

/**
 * Created by daichi on 17/08/31.
 */

public class HomeContentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ItemViewManager.Callback<HomeContentsAdapter.ListCardItemViewHolder> {

    public interface RequestListener {
        void onManageItemSpanSize(View itemView, boolean isFullSpan);
    }

    private static final int VIEW_TYPE_TERM_SCHEDULE = 0;
    private static final int VIEW_TYPE_ENTRY_POINT = 1;

    private ArrayList<EntryPoint> mEntryPoints;
    private ArrayList<Term> mTerms;
    private ArrayList<TermContent> mContents;
    private ItemViewManager<ListCardItemViewHolder> mItemViewManager;
    private boolean mShowEntryPointsOnTop = false;
    private RequestListener mRequestListener;

    public HomeContentsAdapter() {
        mEntryPoints = new ArrayList<>();
        mTerms = new ArrayList<>();
        mContents = new ArrayList<>();
        mItemViewManager = new ItemViewManager<>(this);
        mRequestListener = null;
    }

    private int getLocalPositionInTerms(int absolutePosition) {
        final int position = (mShowEntryPointsOnTop)
                ? absolutePosition - mEntryPoints.size()
                : absolutePosition;

        return (0 <= position && position < mTerms.size())
                ? position
                : -1;
    }

    private int getLocalPositionInEntryPoints(int absolutePosition) {
        final int position = (mShowEntryPointsOnTop)
                ? absolutePosition
                : absolutePosition - mTerms.size();

        return (0 <= position && position < mEntryPoints.size())
                ? position
                : -1;
    }

    public void setRequestListener(RequestListener requestListener) {
        mRequestListener = requestListener;
    }

    public void showEntryPointsOnTop(boolean showEntryPointsOnTop) {
        mShowEntryPointsOnTop = showEntryPointsOnTop;
    }

    /**
     * region; For term-schedule card
     */

    public void addTerm(int startTimeSinceMidnightInMinutes, int endTimeSinceMidnightInMinutes, String name, int index) {
        Term term = new Term();
        term.startTime = new InstantTime(startTimeSinceMidnightInMinutes);
        term.endTime = new InstantTime(endTimeSinceMidnightInMinutes);
        term.name = name;
        term.contents = new ArrayList<>();
        mTerms.add(index, term);
    }

    public void addTerm(int startTimeSinceMidnightInMinutes, int endTimeSinceMidnightInMinutes, String name) {
        addTerm(startTimeSinceMidnightInMinutes, endTimeSinceMidnightInMinutes, name, mTerms.size());
    }

    public void addContent(TermContent content) {
        mContents.add(content);
    }

    public <T extends TermContent> void addContents(Collection<T> contents) {
        mContents.addAll(contents);
    }

    public void invalidateTermsAndContents() {
        // Reset term's contents
        for (int i = 0; i < mTerms.size(); ++i) {
            Term term = mTerms.get(i);
            term.termIndex = i;
            term.contents.clear();

            for (TermContent content : mContents) {
                if (isTimeInTerm(term, content.getStartTime())) {
                    term.contents.add(content);
                }
            }
        }
    }

    private boolean isTimeInTerm(Term term, InstantTime time) {
        final int startTime = term.startTime.getTimeSinceMidnightInMinutes();
        final int endTime = term.endTime.getTimeSinceMidnightInMinutes();
        final int t = time.getTimeSinceMidnightInMinutes();

        if (startTime <= endTime) {
            return (startTime <= t && t < endTime);

        } else {
            // When the term is across midnight
            return ((startTime <= t && t < Time.END_OF_DAY) ||
                    (Time.START_OF_DAY <= t && t < endTime));
        }
    }

    /**
     * region; For entry-point card
     */

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

    /**
     * region; Abstract methods implementation
     */

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_TERM_SCHEDULE: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.general_list_card_view_in_list_view, parent, false);
                return new TermScheduleViewHolder(view);
            }

            case VIEW_TYPE_ENTRY_POINT:
            {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.entry_point_card, parent, false);
                return new EntryPointViewHolder(view);
            }

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_TERM_SCHEDULE:
                if (mRequestListener != null) {
                    mRequestListener.onManageItemSpanSize(holder.itemView, true);
                }

                Term term = mTerms.get(getLocalPositionInTerms(position));
                ListCardView card = ((TermScheduleViewHolder) holder).card;
                card.setHeaderText(term.name);
                mItemViewManager.setupCardItems(card, term.termIndex);
                break;

            case VIEW_TYPE_ENTRY_POINT:
                if (mRequestListener != null) {
                    mRequestListener.onManageItemSpanSize(holder.itemView, false);
                }

                EntryPoint entryPoint = mEntryPoints.get(getLocalPositionInEntryPoints(position));
                ((EntryPointViewHolder) holder).title.setText(entryPoint.title);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTerms.size() + mEntryPoints.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mShowEntryPointsOnTop) {
            return (position < mEntryPoints.size())
                    ? VIEW_TYPE_ENTRY_POINT
                    : VIEW_TYPE_TERM_SCHEDULE;

        } else {
            return (position < mTerms.size())
                    ? VIEW_TYPE_TERM_SCHEDULE
                    : VIEW_TYPE_ENTRY_POINT;
        }
    }

    /**
     * region; ItemViewManager.Callback implementation
     */

    @Override
    public int getCardItemCount(int termId) {
        return mTerms.get(termId).contents.size();
    }

    @Override
    public int getCardItemViewType(int termId, int itemIndex) {
        // Nothing to do for now...
        return 0;
    }

    @Override
    public ItemViewManager.ItemViewHolder onCreateCardItemViewHolder(int viewType, ListCardView parent) {
        View view = parent.createItemView(R.layout.list_item_schedule_summary);
        return new ListCardItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ListCardItemViewHolder holder, int termId, int index) {
        holder.title.setText(mTerms.get(termId).contents.get(index).toSummary());
    }

    /**
     * Term structure
     */
    private static class Term {
        int termIndex;
        String name = null;
        InstantTime startTime = null;
        InstantTime endTime = null;
        ArrayList<TermContent> contents = null;
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
     * ViewHolder for RecyclerView
     */

    private static class TermScheduleViewHolder extends RecyclerView.ViewHolder {

        ListCardView card;

        TermScheduleViewHolder(View view) {
            super(view);
            card = view.findViewById(R.id.general_list_card_view);
        }
    }

    private static class EntryPointViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        EntryPointViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.entry_point_card_title);
        }
    }

    /**
     * ListCardItemViewHolder for ListCardView
     */

    static class ListCardItemViewHolder extends ItemViewManager.ItemViewHolder {

        TextView title;

        ListCardItemViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.list_item_schedule_summary_title);
        }
    }
}
