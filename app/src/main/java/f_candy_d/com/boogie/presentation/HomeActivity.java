package f_candy_d.com.boogie.presentation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.data_store.SqliteDatabaseOpenHelperImpl;
import f_candy_d.com.boogie.domain.DomainDirector;
import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;

public class HomeActivity extends AppCompatActivity {

    private enum RequiredService {
        EVENT_RW_SERVICE
    }

    private static final int ENTRY_POINT_SCHEDULE = 0;

    private DomainDirector<RequiredService> mDomainDirector;
    private SimpleTaskGroupAdapter mSimpleTaskGroupAdapter;
    private f_candy_d.com.boogie.presentation.DividerItemDecoration mDividerItemDecoration;
//    private android.support.v7.widget.DividerItemDecoration mDividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        initUI();
        SqliteTableUtils.resetTable(new SqliteDatabaseOpenHelperImpl(this).openWritableDatabase(), DbContract.getTableSources());
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
        mSimpleTaskGroupAdapter = new SimpleTaskGroupAdapter();
        mDividerItemDecoration = new f_candy_d.com.boogie.presentation.DividerItemDecoration(mSimpleTaskGroupAdapter,
                getResources().getDrawable(R.drawable.simple_divider, null));
//        mDividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        mDividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.simple_divider, null));
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mSimpleTaskGroupAdapter);
        recyclerView.addItemDecoration(mDividerItemDecoration);
    }
}
