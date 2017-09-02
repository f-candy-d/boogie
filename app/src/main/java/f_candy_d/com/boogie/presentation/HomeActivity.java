package f_candy_d.com.boogie.presentation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.domain.DomainDirector;
import f_candy_d.com.boogie.domain.service.EventEntityRwService;
import f_candy_d.com.boogie.domain.structure.Event;
import f_candy_d.com.boogie.domain.usecase.TranslateActivityUseCase;
import f_candy_d.com.boogie.utils.InstantTime;
import f_candy_d.com.boogie.utils.MultiSpanMergeAdapter;
import f_candy_d.com.boogie.utils.Month;
import f_candy_d.com.boogie.utils.StaggeredGridLayoutItemSpanSizeController;
import f_candy_d.com.boogie.utils.Time;

public class HomeActivity extends AppCompatActivity
        implements EntryPointAdapter.OnEntryPointClickListener {

    private enum RequiredService {
        EVENT_RW_SERVICE
    }

    private static final int ENTRY_POINT_SCHEDULE = 0;

    private DomainDirector<RequiredService> mDomainDirector;
    private StaggeredGridLayoutItemSpanSizeController mItemSpanSizeController;
    private TermScheduleAdapter mTermScheduleAdapter;
    private EntryPointAdapter mEntryPointAdapter;
    private MultiSpanMergeAdapter mMergeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        initUI();

//        SqliteTableUtils.resetTable(new SqliteDatabaseOpenHelperImpl(this).openWritableDatabase(), SqlDbContract.getTableSourses());
//
//        Event event = new Event();
//        event.setName("event name");
//        event.setNote("event note");
//        event.setRepetition(Event.Repetition.ONE_TIME);
//        event.setStartYear(2017);
//        event.setStartMonth(Month.APRIL);
//        event.setStartDayOfMonth(5);
//        event.setStartTime(new InstantTime(7, 36));
//        event.setEndYear(2017);
//        event.setEndMonth(Month.APRIL);
//        event.setEndDayOfMonth(5);
//        event.setEndTime(new InstantTime(14, 33));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        mDomainDirector = new DomainDirector<>(this, RequiredService.class);
        mDomainDirector.addService(RequiredService.EVENT_RW_SERVICE, new EventEntityRwService());

        mTermScheduleAdapter = new TermScheduleAdapter();
        mTermScheduleAdapter.addTerm(Time.TIME_MORNING_START, Time.TIME_AFTERNOON_START, "Morning");
        mTermScheduleAdapter.addTerm(Time.TIME_AFTERNOON_START, Time.TIME_EVENING_START, "Afternoon");
        mTermScheduleAdapter.addTerm(Time.TIME_EVENING_START, Time.TIME_NIGHT_START, "Evening");
        mTermScheduleAdapter.addTerm(Time.TIME_NIGHT_START, Time.TIME_MORNING_START, "night");

        Event event = new Event();
        event.setName("event name");
        event.setNote("event note");
        event.setRepetition(Event.Repetition.ONE_TIME);
        event.setStartYear(2017);
        event.setStartMonth(Month.APRIL);
        event.setStartDayOfMonth(5);
        event.setStartTime(new InstantTime(0, 0));
        event.setEndYear(2017);
        event.setEndMonth(Month.APRIL);
        event.setEndDayOfMonth(5);
        event.setEndTime(new InstantTime(2, 50));

        ArrayList<Event> events = new ArrayList<>();
        for (int i = 1; i < 24; i += 2) {
            Event ev = copyEvent(event);
            ev.setName(ev.getName() + String.valueOf(i));
            ev.getStartTime().addHourOfDay(i);
            ev.getEndTime().addHourOfDay(i);
            events.add(ev);
        }

        mTermScheduleAdapter.addContents(events);
        mTermScheduleAdapter.invalidateTermsAndContents();
        mTermScheduleAdapter.setItemFullSpan(true);

        mEntryPointAdapter = new EntryPointAdapter();
        mEntryPointAdapter.setOnEntryPointClickListener(this);
        mEntryPointAdapter.setItemFullSpan(false);
        mEntryPointAdapter.addEntryPoint(0, "Schedule", ENTRY_POINT_SCHEDULE);
        mEntryPointAdapter.addEntryPoint(0, "TODO List", 2);
        mEntryPointAdapter.addEntryPoint(0, "Projects", 3);
        mEntryPointAdapter.addEntryPoint(0, "Settings", 4);

        mItemSpanSizeController = new StaggeredGridLayoutItemSpanSizeController(2, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_home);
        recyclerView.setLayoutManager(mItemSpanSizeController.getLayoutManager());

        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());

        mMergeAdapter = new MultiSpanMergeAdapter();
        View header = inflater.inflate(R.layout.list_item_header, recyclerView, false);
        ((TextView) header.findViewById(R.id.list_item_header_title)).setText("Contents");
        mMergeAdapter.addView(header, true);
        mMergeAdapter.addAdapter(mEntryPointAdapter, false);
        header = inflater.inflate(R.layout.list_item_header, recyclerView, false);
        ((TextView) header.findViewById(R.id.list_item_header_title)).setText("Today's Schedule Summary");
        mMergeAdapter.addView(header, true);
        mMergeAdapter.addAdapter(mTermScheduleAdapter, true);

        mItemSpanSizeController.registerAdapter(mMergeAdapter);

        recyclerView.setAdapter(mMergeAdapter);
    }

    private Event copyEvent(Event e) {
        Event event = new Event();
        event.setName(e.getName());
        event.setNote(e.getNote());
        event.setRepetition(e.getRepetition());
        event.setStartYear(e.getStartYear());
        event.setStartMonth(e.getStartMonth());
        event.setStartDayOfMonth(e.getStartDayOfMonth());
        event.setStartTime(new InstantTime(e.getStartTime().getTimeSinceMidnightInMinutes()));
        event.setEndYear(e.getEndYear());
        event.setEndMonth(e.getEndMonth());
        event.setEndDayOfMonth(e.getEndDayOfMonth());
        event.setEndTime(new InstantTime(e.getEndTime().getTimeSinceMidnightInMinutes()));

        return event;
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * region; EntryPointAdapter.OnEntryPointClickListener
     */
    @Override
    public void onEntryPointClicked(int tag) {
       switch (tag) {
           case ENTRY_POINT_SCHEDULE:
               TranslateActivityUseCase.translate(this, ScheduleViewerActivity.class);
       }
    }
}
