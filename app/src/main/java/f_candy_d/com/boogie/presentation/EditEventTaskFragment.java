package f_candy_d.com.boogie.presentation;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.domain.DomainDirector;
import f_candy_d.com.boogie.domain.service.TaskRWService;
import f_candy_d.com.boogie.domain.structure.EventTaskWrapper;
import f_candy_d.com.boogie.utils.InstantDate;

/**
 * Use the {@link EditEventTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditEventTaskFragment extends EditTaskFragment {

    @Nullable
    EventTaskWrapper mPassedTask;
    // Write input data in this variable
    EventTaskWrapper mBuffer;
    private DomainDirector mDomainDirector;

    // UI
    EditText mEditTextTitle;
    EditText mEditTextNote;
    Button mStartDateButton;
    Button mStartTimeButton;
    Button mEndDateButton;
    Button mEndTimeButton;

    public EditEventTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static EditEventTaskFragment newInstance(long taskId) {
        EditEventTaskFragment fragment = new EditEventTaskFragment();
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
        mPassedTask = null;
        mBuffer = null;

        if (taskId != DbContract.NULL_ID) {
            // findTaskById() is nullable
            mPassedTask = new EventTaskWrapper(
                    mDomainDirector.getService(TaskRWService.class).findTaskById(taskId));
        }

        if (mPassedTask != null) {
            mBuffer = new EventTaskWrapper(mPassedTask.getTask());
        } else {
            mBuffer = new EventTaskWrapper();
            mBuffer.setStartDate(new InstantDate());
            mBuffer.setEndDate(new InstantDate());
        }
    }

    private void reflectBufferToUI() {
        mStartDateButton.setText("Start " + DateFormat.format("yyyy-MM-dd", mBuffer.getStartDate().asCalendar()));
        mEndDateButton.setText("End " + DateFormat.format("yyyy-MM-dd", mBuffer.getEndDate().asCalendar()));
        mStartTimeButton.setText("Start " + DateFormat.format("hh:mm a", mBuffer.getStartDate().asCalendar()));
        mEndTimeButton.setText("End " + DateFormat.format("hh:mm a", mBuffer.getEndDate().asCalendar()));
    }

    @Override
    protected void init() {
        mDomainDirector = new DomainDirector(getActivity());
        mDomainDirector.addService(new TaskRWService());
    }

    private void initUI(View view) {
        mEditTextTitle = view.findViewById(R.id.edit_title);
        mEditTextNote = view.findViewById(R.id.edit_note);

        mStartDateButton = view.findViewById(R.id.select_start_date);
        mStartDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDatePicker(mBuffer.getStartDate().getYear(),
                                mBuffer.getStartDate().getMonth(),
                                mBuffer.getStartDate().getDayOfMonth(),
                                new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                mBuffer.getStartDate().setYear(i);
                                mBuffer.getStartDate().setMonth(i1);
                                mBuffer.getStartDate().setDayOfMonth(i2);

                                reflectBufferToUI();
                            }
                        });
                    }
                });

        mStartTimeButton = view.findViewById(R.id.select_start_time);
        mStartTimeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showTimePicker(mBuffer.getStartDate().getHourOfDay(),
                                mBuffer.getStartDate().getMinute(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                        mBuffer.getStartDate().setHourOfDay(i);
                                        mBuffer.getStartDate().setMinute(i1);

                                        reflectBufferToUI();
                                    }
                                });
                    }
                });

        mEndDateButton = view.findViewById(R.id.select_end_date);
        mEndDateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDatePicker(mBuffer.getEndDate().getYear(),
                                mBuffer.getEndDate().getMonth(),
                                mBuffer.getEndDate().getDayOfMonth(),
                                new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                mBuffer.getEndDate().setYear(i);
                                mBuffer.getEndDate().setMonth(i1);
                                mBuffer.getEndDate().setDayOfMonth(i2);

                                reflectBufferToUI();
                            }
                        });
                    }
                });

        mEndTimeButton = view.findViewById(R.id.select_end_time);
        mEndTimeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showTimePicker(mBuffer.getEndDate().getHourOfDay(),
                                mBuffer.getEndDate().getMinute(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                        mBuffer.getEndDate().setHourOfDay(i);
                                        mBuffer.getEndDate().setMinute(i1);

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
