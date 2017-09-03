package f_candy_d.com.boogie.presentation;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.domain.DomainDirector;
import f_candy_d.com.boogie.domain.service.ExactTermTaskEntityRWService;
import f_candy_d.com.boogie.domain.structure.ExactTermTask;
import f_candy_d.com.boogie.utils.InstantDate;

/**
 * Use the {@link EditExactTermTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditExactTermTaskFragment extends EditTaskFragment {

    @Nullable ExactTermTask mTask;
    // Write input data in this variable
    ExactTermTask mBuffer;
    private DomainDirector<Request> mDomainDirector;

    // UI
    EditText mEditTextTitle;
    EditText mEditTextNote;
    Button mStartDateButton;
    Button mStartTimeButton;
    Button mEndDateButton;
    Button mEndTimeButton;

    private enum Request {
        TASK_DATA_RW
    }

    public EditExactTermTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static EditExactTermTaskFragment newInstance(long taskId) {
        EditExactTermTaskFragment fragment = new EditExactTermTaskFragment();
        fragment.setArguments(makeArgs(taskId));
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_exact_term_task, container, false);
        initUI(view);
        reflectBufferToUI();
        return view;
    }

    @Override
    protected void onPrepareToEdit(long taskId) {
        mTask = null;
        mBuffer = null;

        if (taskId != DbContract.NULL_ID) {
            // findTaskById() is nullable
            mTask = mDomainDirector
                    .getAndCastService(Request.TASK_DATA_RW, ExactTermTaskEntityRWService.class)
                    .findTaskById(taskId);
        }

        if (mTask != null) {
            mBuffer = new ExactTermTask(mTask);
        } else {
            mBuffer = new ExactTermTask();
            mBuffer.termStartDate = new InstantDate();
            mBuffer.termEndDate = new InstantDate();
        }
    }

    private void reflectBufferToUI() {
        mEditTextNote.setText(mBuffer.note);
        mEditTextTitle.setText(mBuffer.title);
        mStartDateButton.setText("Start " + DateFormat.format("yyyy-MM-dd", mBuffer.termStartDate.asCalendar()));
        mEndDateButton.setText("End " + DateFormat.format("yyyy-MM-dd", mBuffer.termEndDate.asCalendar()));
        mStartTimeButton.setText("Start " + DateFormat.format("hh:mm a", mBuffer.termStartDate.asCalendar()));
        mEndTimeButton.setText("End " + DateFormat.format("hh:mm a", mBuffer.termEndDate.asCalendar()));
    }

    @Override
    protected void init() {
        mDomainDirector = new DomainDirector<>(getActivity(), Request.class);
        mDomainDirector.addService(Request.TASK_DATA_RW, new ExactTermTaskEntityRWService());
    }

    private void initUI(View view) {
        mEditTextTitle = view.findViewById(R.id.edit_title);
        mEditTextNote = view.findViewById(R.id.edit_note);

        mStartDateButton = view.findViewById(R.id.select_start_date);
        mStartDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDatePicker(mBuffer.termStartDate.getYear(),
                                mBuffer.termStartDate.getMonth(),
                                mBuffer.termStartDate.getDayOfMonth(),
                                new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                mBuffer.termStartDate.setYear(i);
                                mBuffer.termStartDate.setMonth(i1);
                                mBuffer.termStartDate.setDayOfMonth(i2);

                                reflectBufferToUI();
                            }
                        });
                    }
                });

        mStartTimeButton = view.findViewById(R.id.select_start_time);
        mStartTimeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showTimePicker(mBuffer.termStartDate.getHourOfDay(),
                                mBuffer.termStartDate.getMinute(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                        mBuffer.termStartDate.setHourOfDay(i);
                                        mBuffer.termStartDate.setMinute(i1);

                                        reflectBufferToUI();
                                    }
                                });
                    }
                });

        mEndDateButton = view.findViewById(R.id.select_end_date);
        mEndDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDatePicker(mBuffer.termEndDate.getYear(),
                                mBuffer.termEndDate.getMonth(),
                                mBuffer.termEndDate.getDayOfMonth(),
                                new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                mBuffer.termEndDate.setYear(i);
                                mBuffer.termEndDate.setMonth(i1);
                                mBuffer.termEndDate.setDayOfMonth(i2);

                                reflectBufferToUI();
                            }
                        });
                    }
                });

        mEndTimeButton = view.findViewById(R.id.select_end_time);
        mEndTimeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showTimePicker(mBuffer.termEndDate.getHourOfDay(),
                                mBuffer.termEndDate.getMinute(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                        mBuffer.termEndDate.setHourOfDay(i);
                                        mBuffer.termEndDate.setMinute(i1);

                                        reflectBufferToUI();
                                    }
                                });
                    }
                });

        view.findViewById(R.id.ok_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

    }

    private void showDatePicker
            (int year, int month, int dayOfMonth, DatePickerDialog.OnDateSetListener listener) {

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), listener, year, month, dayOfMonth);
        dialog.show();
    }

    private void showTimePicker
            (int hourOfDay, int minute, TimePickerDialog.OnTimeSetListener listener) {

        TimePickerDialog dialog = new TimePickerDialog(getActivity(), listener, hourOfDay, minute, false);
        dialog.show();
    }
}
