package f_candy_d.com.boogie.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.domain.structure.TermContent;
import f_candy_d.com.boogie.utils.Time;
import f_candy_d.com.boogie.utils.TimeTerm;
import f_candy_d.com.listcardview.ItemViewManager;
import f_candy_d.com.listcardview.ListCardView;

/**
 * Created by daichi on 17/08/31.
 */

public class HomeContentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ItemViewManager.Callback<HomeContentsAdapter.ItemViewHolder> {

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {

        ScheduleViewHolder(View view) {
            super(view);
        }
    }

    static class EntryPointViewHolder extends RecyclerView.ViewHolder {

        EntryPointViewHolder(View view) {
            super(view);
        }
    }

    static class ItemViewHolder extends ItemViewManager.ItemViewHolder {

        TextView title;

        ItemViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.list_item_schedule_summary_title);
        }
    }

    private static final int CARD_TYPE_MORNING_SCHEDULE = 0;
    private static final int CARD_TYPE_AFTERNOON_SCHEDULE = 1;
    private static final int CARD_TYPE_EVENING_SCHEDULE = 2;
    private static final int CARD_TYPE_MNIGHT_SCHEDULE = 3;
    private static final int SCHEDULE_CARD_COUNT = 4;

    private Map<TimeTerm, ArrayList<TermContent>> mTermContentsMap;

    public HomeContentsAdapter() {
        TimeTerm morning = new TimeTerm();
        TimeTerm afternoon = new TimeTerm();
        TimeTerm eveninig = new TimeTerm();
        TimeTerm night = new TimeTerm();

        morning.setStartTimeSinceMidnightInMinutes(Time.TIME_MORNING_START);
        morning.setEndTimeSinceMidnightInMinutes(Time.TIME_AFTERNOON_START);
        afternoon.setStartTimeSinceMidnightInMinutes(Time.TIME_AFTERNOON_START);
        afternoon.setEndTimeSinceMidnightInMinutes(Time.TIME_EVENING_START);
        eveninig.setStartTimeSinceMidnightInMinutes(Time.TIME_EVENING_START);
        eveninig.setEndTimeSinceMidnightInMinutes(Time.TIME_NIGHT_START);
        night.setStartTimeSinceMidnightInMinutes(Time.TIME_NIGHT_START);
        night.setEndTimeSinceMidnightInMinutes(Time.TIME_MORNING_START);

        mTermContentsMap = new HashMap<>();
        mTermContentsMap.put(morning, new ArrayList<TermContent>());
        mTermContentsMap.put(afternoon, new ArrayList<TermContent>());
        mTermContentsMap.put(eveninig, new ArrayList<TermContent>());
        mTermContentsMap.put(night, new ArrayList<TermContent>());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mTermContentsMap.size();
    }

    public void addContents(Collection<TermContent> contents) {
        for (TermContent content : contents) {
            for (TimeTerm term : mTermContentsMap.keySet()) {
                if (term.containsTime(content.getStartTime())) {
                    mTermContentsMap.get(term).add(content);
                    break;
                }
            }
        }
    }

    public void notifyContentsInserted() {

    }

    /**
     * region; ItemViewManager.Callback implementation
     */

    @Override
    public int getCardItemCount(int cardType) {
        switch (cardType) {
            case CARD_TYPE_MORNING_SCHEDULE:
                return
        }
    }

    @Override
    public int getCardItemViewType(int cardType, int itemIndex) {
        return 0;
    }

    @Override
    public ItemViewManager.ItemViewHolder onCreateCardItemViewHolder(int viewType, ListCardView parent) {
        View view = parent.createItemView(R.layout.general_list_card_view_in_list_view);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder holder, int cardType, int index) {

    }
}
