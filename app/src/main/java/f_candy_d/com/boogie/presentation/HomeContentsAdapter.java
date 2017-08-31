package f_candy_d.com.boogie.presentation;

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

    private static final int VIEW_TYPE_TERM_SCHEDULE = 0;
    private static final int VIEW_TYPE_ENTRY_POINT = 1;

    private ArrayList<Term> mTerms;
    private ArrayList<TermContent> mContents;
    private ItemViewManager<ListCardItemViewHolder> mItemViewManager;

    public HomeContentsAdapter() {
        mTerms = new ArrayList<>();
        mContents = new ArrayList<>();
        mItemViewManager = new ItemViewManager<>(this);
    }

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

    public void removeContent(TermContent content) {
        mContents.remove(content);
    }

    public void removeContents(Collection<TermContent> contents) {
        mContents.removeAll(contents);
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
     * region; Abstract methods implementation
     */

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_TERM_SCHEDULE:
                View view = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.general_list_card_view_in_list_view, parent, false);
                return new TermScheduleViewHolder(view);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_TERM_SCHEDULE:
                Term term = mTerms.get(position);
                ListCardView card = ((TermScheduleViewHolder) holder).card;
                card.setHeaderText(term.name);
                mItemViewManager.setupCardItems(card, term.termIndex);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position < mTerms.size())
                ? VIEW_TYPE_TERM_SCHEDULE
                : VIEW_TYPE_ENTRY_POINT;
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
     * ViewHolder for RecyclerView
     */

    static class TermScheduleViewHolder extends RecyclerView.ViewHolder {

        ListCardView card;

        TermScheduleViewHolder(View view) {
            super(view);
            card = view.findViewById(R.id.general_list_card_view);
        }
    }

    static class EntryPointViewHolder extends RecyclerView.ViewHolder {

        EntryPointViewHolder(View view) {
            super(view);
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
