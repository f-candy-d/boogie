package f_candy_d.com.boogie.presentation;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.data_store.SqlDbContract;
import f_candy_d.com.boogie.data_store.SqliteDatabaseOpenHelperImpl;
import f_candy_d.com.boogie.domain.DomainDirector;
import f_candy_d.com.boogie.domain.service.EventEntityRwService;
import f_candy_d.com.boogie.domain.structure.Event;
import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;
import f_candy_d.com.boogie.utils.InstantTime;
import f_candy_d.com.boogie.utils.Month;
import f_candy_d.com.boogie.utils.Time;

public class HomeActivity extends AppCompatActivity {

    private enum RequiredService {
        EVENT_RW_SERVICE
    }

    private DomainDirector<RequiredService> mDomainDirector;
    private HomeContentsItemManager mItemManager;

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

        HomeContentsAdapter adapter = new HomeContentsAdapter();
        adapter.addTerm(Time.TIME_MORNING_START, Time.TIME_AFTERNOON_START, "Morning");
        adapter.addTerm(Time.TIME_AFTERNOON_START, Time.TIME_EVENING_START, "Afternoon");
        adapter.addTerm(Time.TIME_EVENING_START, Time.TIME_NIGHT_START, "Evening");
        adapter.addTerm(Time.TIME_NIGHT_START, Time.TIME_MORNING_START, "night");

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

        adapter.addContents(events);
        adapter.invalidateTermsAndContents();
        adapter.showEntryPointsOnTop(true);

        adapter.addEntryPoint(0, "EVENTS", 1);
        adapter.addEntryPoint(0, "TODO", 2);
        adapter.addEntryPoint(0, "PLAN", 3);
        adapter.addEntryPoint(0, "SCHEDULE", 4);

        mItemManager = new HomeContentsItemManager(adapter,
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_home);
        recyclerView.setAdapter(mItemManager.getAdapter());
        recyclerView.setLayoutManager(mItemManager.getLayoutManager());
    }
}
