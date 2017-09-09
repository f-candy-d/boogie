package f_candy_d.com.boogie.view;

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

import f_candy_d.com.boogie.view_model.HomeViewModel;
import f_candy_d.com.boogie.utils.PartsOfDay;
import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.domain.DomainDirector;
import f_candy_d.com.boogie.domain.service.TaskRWService;
import f_candy_d.com.boogie.structure.Task;
import f_candy_d.com.boogie.utils.*;

public class HomeActivity extends EasyResultReceiveActivity {

    private DomainDirector mDomainDirector;
    private OuterListAdapter mTaskGroupsAdapter;
    private HomeViewModel mIODirector;
    private MergeAdapter mMergeAdapter;

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
        Log.d("mylog", "curren part -> " + PartsOfDay.getCurrentPartOfDay().toString());
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
        mIODirector = new HomeViewModel(this);
    }

    private void initRecyclerView() {
        mTaskGroupsAdapter = new OuterListAdapter(this);

        ArrayList<Group<Task>> taskGroups = mIODirector.showUpcomingTasksAsGroup();
        for (Group<Task> group : taskGroups) {
            SimpleTaskGroupAdapter adapter = new SimpleTaskGroupAdapter(group.getMembers());
            adapter.setHeaderTitle(group.getName());
            mTaskGroupsAdapter.addAdapter(adapter);
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
