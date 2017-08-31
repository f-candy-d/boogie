package f_candy_d.com.boogie.presentation;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

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
    private HomeContentsAdapter mAdapter;

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

        mAdapter = new HomeContentsAdapter();
        mAdapter.addTerm(Time.TIME_MORNING_START, Time.TIME_AFTERNOON_START, "Morning");
        mAdapter.addTerm(Time.TIME_AFTERNOON_START, Time.TIME_EVENING_START, "Afternoon");
        mAdapter.addTerm(Time.TIME_EVENING_START, Time.TIME_NIGHT_START, "Evening");
        mAdapter.addTerm(Time.TIME_NIGHT_START, Time.TIME_MORNING_START, "night");
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
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
