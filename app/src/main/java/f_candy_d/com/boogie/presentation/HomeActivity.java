package f_candy_d.com.boogie.presentation;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.data_store.SqliteDatabaseOpenHelperImpl;
import f_candy_d.com.boogie.domain.DomainDirector;
import f_candy_d.com.boogie.domain.service.TaskRWService;
import f_candy_d.com.boogie.domain.structure.Task;
import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;
import f_candy_d.com.boogie.utils.*;

public class HomeActivity extends EasyResultReceiveActivity {

    private DomainDirector mDomainDirector;

    private SimpleTaskGroupAdapter mSimpleTaskGroupAdapter;

    // For ItemDecorations
    private f_candy_d.com.boogie.utils.DividerItemDecoration mDividerItemDecoration;
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
        mDomainDirector = new DomainDirector(this);
        mDomainDirector.addService(new TaskRWService());

        final float density = getResources().getDisplayMetrics().density;
        mItemSideSpace = (int) (12 * density);
        mItemGroupTopSpace = (int) (8 * density);
        mItemGroupBottomSpace = mItemGroupTopSpace;

        mSimpleTaskGroupAdapter = new SimpleTaskGroupAdapter();
        mSimpleTaskGroupAdapter.setHeaderTitle("Today's Tasks");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        final long today = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        final long nextDay = calendar.getTimeInMillis();

        ArrayList<Task> tasksInTerm =
                mDomainDirector.getService(TaskRWService.class).getTasksInTerm(today, nextDay);
        for (Task task : tasksInTerm) {
            mSimpleTaskGroupAdapter.addTask(task);
        }

        mDividerItemDecoration = new f_candy_d.com.boogie.utils.DividerItemDecoration(
                null, getResources().getDrawable(R.drawable.simple_divider, null));
        mDividerItemDecoration.setCallback(new f_candy_d.com.boogie.utils.DividerItemDecoration.Callback() {
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
                output.top = mItemGroupTopSpace;
                if (adapterPosition == mSimpleTaskGroupAdapter.getItemCount() - 1) {
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
//        recyclerView.addItemDecoration(mDividerItemDecoration);

        ArrayList<InnerListAdapter> innerListAdapters = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            innerListAdapters.add(new InnerListAdapter());
        }
        OuterListAdapter outerListAdapter = new OuterListAdapter(this, innerListAdapters);
        recyclerView.setAdapter(outerListAdapter);
    }

    private void showWhatAddDialog() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        int cx = (int) (fab.getX() + fab.getWidth() / 2);
        int cy = (int) fab.getY();
        new WhatAddDialog(this)
                .setOnSelectionChosenListener(new WhatAddDialog.OnSelectionChosenListener() {
                    @Override
                    public void onSelectionChosen(WhatAddDialog.Selection selection, Dialog dialog) {
                        dialog.dismiss();
                        onLaunchAddNewTaskScreen(selection);
                    }
                })
                .setupDialogRevealAnim(cx, cy, 400, 500)
                .getDialog()
                .show();
    }

    private void onLaunchAddNewTaskScreen(WhatAddDialog.Selection selection) {
        switch (selection) {
            case ADD_EVENT:
                startActivityForResult(EventFormActivity.makeIntent(this),
                        new OnActivityResultListener() {
                            @Override
                            public void onResult(boolean isOk, @Nullable Intent data) {
                                if (isOk) {
                                    Log.d("mylog", "activity result id -> " + EventFormActivity.getTaskIdFromIntent(data));
                                } else {
                                    Log.d("mylog", "actibity result failed...");
                                }
                            }
                        });
        }
    }
}
