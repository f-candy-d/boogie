package f_candy_d.com.boogie.presentation;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.domain.DomainDirector;
import f_candy_d.com.boogie.domain.service.TaskRWService;
import f_candy_d.com.boogie.domain.structure.Task;
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

        if (Build.VERSION.SDK_INT >= 21) {3
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

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
        mItemSideSpace = (int) (6 * density);
        mItemGroupTopSpace = (int) (6 * density);
        mItemGroupBottomSpace = (int) (16 * density);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        final long today = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        final long nextDay = calendar.getTimeInMillis();

        ArrayList<Task> tasksInTerm =
                mDomainDirector.getService(TaskRWService.class).getTasksInTerm(today, nextDay);

        mSimpleTaskGroupAdapter = new SimpleTaskGroupAdapter(tasksInTerm);
        mSimpleTaskGroupAdapter.setHeaderTitle("Today's Tasks");

        mDividerItemDecoration = new f_candy_d.com.boogie.utils.DividerItemDecoration(
                null, getResources().getDrawable(R.drawable.simple_divider, null));
        mDividerItemDecoration.setCallback(new f_candy_d.com.boogie.utils.DividerItemDecoration.Callback() {
            @Override
            public boolean drawDividerAboveItem(int adapterPosition) {
                return (mSimpleTaskGroupAdapter.getFirstItemPosition() < adapterPosition &&
                        adapterPosition < mSimpleTaskGroupAdapter.getItemCount());
            }
        });

        mSpacerItemDecoration = new SpacerItemDecoration(this, new SpacerItemDecoration.Callback() {
            @Override
            public void getInsertedSpaceAroundItem(int adapterPosition, Rect output) {
                if (0 < adapterPosition) {
                    output.top = mItemGroupTopSpace;
                    if (adapterPosition % 2 == 0) {
                        output.right = mItemSideSpace;
                        output.left = mItemSideSpace / 2;
                    } else {
                        output.left = mItemSideSpace;
                        output.right = mItemSideSpace / 2;
                    }
                }

                if (adapterPosition == mSimpleTaskGroupAdapter.getItemCount() - 1 ||
                        adapterPosition == mSimpleTaskGroupAdapter.getItemCount() - 2) {
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
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) return 2;
                return 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mSimpleTaskGroupAdapter);
        recyclerView.addItemDecoration(mSpacerItemDecoration);
//        recyclerView.addItemDecoration(mDividerItemDecoration);
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
