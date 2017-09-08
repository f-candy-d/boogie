package f_candy_d.com.boogie.presentation;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import f_candy_d.com.boogie.PartsOfDay;
import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.domain.DomainDirector;
import f_candy_d.com.boogie.domain.service.TaskRWService;
import f_candy_d.com.boogie.domain.structure.Task;
import f_candy_d.com.boogie.utils.*;

public class HomeActivity extends EasyResultReceiveActivity {

    private DomainDirector mDomainDirector;
    private OuterListAdapter mTaskGroupsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        init();
        initUI();
        PartsOfDay.testGetDatetimeMethods();
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
    }

    private void initRecyclerView() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.MINUTE, Time.TIME_MORNING_START);
        final long todayMorning = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        final long nextDayMorning = calendar.getTimeInMillis();

        ArrayList<Task> tasksInOneDay = mDomainDirector.getService(TaskRWService.class)
                .getTasksInTerm(todayMorning, nextDayMorning);
        Log.d("mylog", "get -> " + tasksInOneDay.size());

        // Local class
        class TimeRange {
            private String name;
            private int start;
            private int end;

            private TimeRange(String n, int s, int e) { name = n; start = s; end = e; }

            private boolean isInRange(int timeSinceMidnightInMinutes) {
                if (start <= end) {
                    return (start <= timeSinceMidnightInMinutes && timeSinceMidnightInMinutes < end);
                } else {
                    return ((start <= timeSinceMidnightInMinutes && timeSinceMidnightInMinutes < Time.NEXT_DAY_MIDNIGHT) ||
                            (Time.MIDNIGHT <= timeSinceMidnightInMinutes && timeSinceMidnightInMinutes < end));
                }
            }
        }

        ArrayList<TimeRange> ranges = new ArrayList<>();

        // Morning
        ranges.add(new TimeRange("Morning", Time.TIME_MORNING_START, Time.TIME_AFTERNOON_START));
        // Afternoon
        ranges.add(new TimeRange("Afternoon", Time.TIME_AFTERNOON_START, Time.TIME_EVENING_START));
        // Evening
        ranges.add(new TimeRange("Evening", Time.TIME_EVENING_START, Time.TIME_NIGHT_START));
        // Night
        ranges.add(new TimeRange("Night", Time.TIME_NIGHT_START, Time.TIME_MORNING_START));

        mTaskGroupsAdapter = new OuterListAdapter(this);
        ArrayList<Task> tasks = new ArrayList<>();
        Iterator<Task> iterator = tasksInOneDay.iterator();

        for (TimeRange range : ranges) {
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (range.isInRange(task.dateTermStart.getTimeOfDaySinceMidnightInMinutes())) {
                    tasks.add(task);
                    iterator.remove();
                }
            }

            SimpleTaskGroupAdapter innerAdapter = new SimpleTaskGroupAdapter(tasks);
            innerAdapter.setHeaderTitle(range.name);
            mTaskGroupsAdapter.addAdapter(innerAdapter);
            tasks.clear();
        }

//        mDividerItemDecoration = new f_candy_d.com.boogie.utils.DividerItemDecoration(
//                null, getResources().getDrawable(R.drawable.simple_divider, null));
//        mDividerItemDecoration.setCallback(new f_candy_d.com.boogie.utils.DividerItemDecoration.Callback() {
//            @Override
//            public boolean drawDividerAboveItem(int adapterPosition) {
//                return (mSimpleTaskGroupAdapter.getFirstItemPosition() < adapterPosition &&
//                        adapterPosition < mSimpleTaskGroupAdapter.getItemCount());
//            }
//        });

        final float density = getResources().getDisplayMetrics().density;
        final int itemSideSpace = (int) (16 * density);
        final int itemGroupTopSpace = (int) (8 * density);
        final int itemGroupBottomSpace = (int) (8 * density);

        SpacerItemDecoration spacerItemDecoration =
                new SpacerItemDecoration(this, new SpacerItemDecoration.Callback() {
                    @Override
                    public void getInsertedSpaceAroundItem(int adapterPosition, Rect output) {
                        if (adapterPosition == 0) {
                            output.top = itemGroupTopSpace * 10;
                        } else {
                            output.top = itemGroupTopSpace;
                        }

                        if (adapterPosition == 10 - 1) {
                            output.bottom = itemGroupBottomSpace * 4;
                        } else {
                            output.bottom = itemGroupBottomSpace;
                        }

                        output.left = itemSideSpace;
                        output.right = itemSideSpace;
                    }
                });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mTaskGroupsAdapter);
        recyclerView.addItemDecoration(spacerItemDecoration);
//        recyclerView.addItemDecoration(mDividerItemDecoration);
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

        initRecyclerView();
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
