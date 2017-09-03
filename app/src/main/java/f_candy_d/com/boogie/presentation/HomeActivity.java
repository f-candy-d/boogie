package f_candy_d.com.boogie.presentation;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Px;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.util.Log;
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

    // For ItemDecorations
    private DividerItemDecoration mDividerItemDecoration;
    private SpacerItemDecoration mSpacerItemDecoration;
    @Px private int mItemSideSpace = 0;
    @Px private int mItemGroupTopSpace = 0;
    @Px private int mItemGroupBottomSpace = 0;

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
        final float density = getResources().getDisplayMetrics().density;
        mItemSideSpace = (int) (12 * density);
        mItemGroupTopSpace = (int) (8 * density);
        mItemGroupBottomSpace = mItemGroupTopSpace;

        mSimpleTaskGroupAdapter = new SimpleTaskGroupAdapter();
        mSimpleTaskGroupAdapter.setHeaderTitle("Header title");

        mDividerItemDecoration = new DividerItemDecoration(null,
                getResources().getDrawable(R.drawable.simple_divider, null));
        mDividerItemDecoration.setCallback(new DividerItemDecoration.Callback() {
            @Override
            public boolean drawDividerAboveItem(int adapterPosition) {
                return (mSimpleTaskGroupAdapter.getFirstItemPosition() < adapterPosition &&
                        adapterPosition < mSimpleTaskGroupAdapter.getItemCount());
            }
        });

        mSpacerItemDecoration = new SpacerItemDecoration(this, null);
        mSpacerItemDecoration.setContext(this);
        mSpacerItemDecoration.setCallback(new SpacerItemDecoration.Callback() {
            @Override
            public void getInsertedSpaceAroundItem(int adapterPosition, Rect output) {
                output.left = mItemSideSpace;
                output.right = mItemSideSpace;
                if (adapterPosition == 0) {
                    output.top = mItemGroupTopSpace;
                } else if (adapterPosition == mSimpleTaskGroupAdapter.getItemCount() - 1) {
                    output.bottom = mItemGroupBottomSpace;
                }
            }
        });
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWhatAddDialog();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mSimpleTaskGroupAdapter);
        recyclerView.addItemDecoration(mSpacerItemDecoration);
        recyclerView.addItemDecoration(mDividerItemDecoration);
    }

    private void showWhatAddDialog() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        int cx = (int) (fab.getX() + fab.getWidth() / 2);
        int cy = (int) fab.getY();
        new WhatAddDialog(this)
                .setOnSelectionChosenListener(new WhatAddDialog.OnSelectionChosenListener() {
                    @Override
                    public void onSelectionChosen(WhatAddDialog.Selection selection) {
                    }
                })
                .setupDialogRevealAnim(cx, cy, 400, 400)
                .getDialog()
                .show();
    }

}
