package f_candy_d.com.boogie;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import f_candy_d.com.boogie.domain.DomainDirector;
import f_candy_d.com.boogie.domain.service.EventEntityRwService;
import f_candy_d.com.boogie.domain.structure.Event;
import f_candy_d.com.boogie.utils.InstantTime;
import f_candy_d.com.boogie.utils.Month;

public class HomeActivity extends AppCompatActivity {

    private enum RequiredService {
        EVENT_RW_SERVICE
    }

    private DomainDirector<RequiredService> mDomainDirector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        initUI();

        Event event = new Event();
        event.setName("event name");
        event.setNote("event note");
        event.setRepetition(Event.Repetition.ONE_TIME);
        event.setStartYear(2017);
        event.setStartMonth(Month.APRIL);
        event.setStartDayOfMonth(5);
        event.setStartTime(new InstantTime(7, 36));
        event.setEndYear(2017);
        event.setEndMonth(Month.APRIL);
        event.setEndDayOfMonth(5);
        event.setEndTime(new InstantTime(14, 33));
        EventEntityRwService service = mDomainDirector.getAndCastService(RequiredService.EVENT_RW_SERVICE, EventEntityRwService.class);
        final long id = service.insertEvent(event);
        event.setId(id);

        Event loaded = service.findEventById(id);
        if (loaded != null && service.checkValidation(event)) {
            Log.d("mylog", "#########################################################");
            Log.d("mylog", "Name::" + event.getName());
            Log.d("mylog", "Note::" + event.getNote());
            Log.d("mylog", "Repetition::" + event.getRepetition().toString());
            Log.d("mylog", "StartDate::" + event.getStartDate().toString());
            Log.d("mylog", "EndDate::" + event.getEndDate().toString());

        } else {
            Log.d("mylog", "Loading Event for id=" + String.valueOf(id) + " failed");
        }
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
}
