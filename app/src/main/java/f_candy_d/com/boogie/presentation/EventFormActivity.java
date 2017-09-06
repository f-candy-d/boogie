package f_candy_d.com.boogie.presentation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.domain.DomainDirector;
import f_candy_d.com.boogie.domain.service.TaskEntityRWService;
import f_candy_d.com.boogie.domain.structure.EventTaskWrapper;
import f_candy_d.com.boogie.domain.structure.Task;
import f_candy_d.com.boogie.utils.InstantDate;

public class EventFormActivity extends AppCompatActivity {

    // Write user inputs into this variable
    EventTaskWrapper mBuffer;
    private DomainDirector mDomainDirector;

    // UI
    EditText mEditTextTitle;
    EditText mEditTextNote;
    Button mStartDateButton;
    Button mStartTimeButton;
    Button mEndDateButton;
    Button mEndTimeButton;

    public static final String EXTRA_TASK_ID = "extra_task_id";

    public static Intent makeIntent(Activity from) {
        return makeIntent(from, DbContract.NULL_ID);
    }

    public static Intent makeIntent(Activity from, long taskId) {
        Intent intent = new Intent(from, EventFormActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }

    public static long getTaskIdFromIntent(Intent intent) {
        if (intent != null) {
            return intent.getLongExtra(EXTRA_TASK_ID, DbContract.NULL_ID);
        } else {
            return DbContract.NULL_ID;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_form);

        init();
        final long taskId = getIntent().getLongExtra(EXTRA_TASK_ID, DbContract.NULL_ID);
        prepareToEdit(taskId);
        initUI();
    }

    private void init() {
        mDomainDirector = new DomainDirector(this);
        mDomainDirector.addService(new TaskEntityRWService());
    }

   private void initUI() {
       mEditTextTitle = (EditText) findViewById(R.id.edit_title);
       mEditTextNote = (EditText) findViewById(R.id.edit_note);

       mStartDateButton = (Button) findViewById(R.id.select_start_date);
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

       mStartTimeButton = (Button) findViewById(R.id.select_start_time);
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

       mEndDateButton = (Button) findViewById(R.id.select_end_date);
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

       mEndTimeButton = (Button) findViewById(R.id.select_end_time);
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

       findViewById(R.id.ok_button)
               .setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       finishEditing();
                   }
               });

       reflectBufferToUI();
   }

    protected void prepareToEdit(long taskId) {
        Task task = null;
        if (taskId != DbContract.NULL_ID) {
            // findTaskById() is nullable
            task = mDomainDirector.getService(TaskEntityRWService.class).findTaskById(taskId);
        }

        if (task != null) {
            mBuffer = new EventTaskWrapper(task);
        } else {
            mBuffer = new EventTaskWrapper();
            mBuffer.setStartDate(new InstantDate());
            mBuffer.setEndDate(new InstantDate());
            mBuffer.setIsDone(false);
        }
    }

    private void showDatePicker
            (int year, int month, int dayOfMonth, DatePickerDialog.OnDateSetListener listener) {

        DatePickerDialog dialog = new DatePickerDialog(this, listener, year, month, dayOfMonth);
        dialog.show();
    }

    private void showTimePicker
            (int hourOfDay, int minute, TimePickerDialog.OnTimeSetListener listener) {

        TimePickerDialog dialog = new TimePickerDialog(this, listener, hourOfDay, minute, false);
        dialog.show();
    }

    private void reflectBufferToUI() {
        mStartDateButton.setText("Start " + DateFormat.format("yyyy-MM-dd", mBuffer.getStartDate().asCalendar()));
        mEndDateButton.setText("End " + DateFormat.format("yyyy-MM-dd", mBuffer.getEndDate().asCalendar()));
        mStartTimeButton.setText("Start " + DateFormat.format("hh:mm a", mBuffer.getStartDate().asCalendar()));
        mEndTimeButton.setText("End " + DateFormat.format("hh:mm a", mBuffer.getEndDate().asCalendar()));
    }

    private void finishEditing() {
        mBuffer.setTitle(mEditTextTitle.getText().toString());

        final long id = mDomainDirector.getService(TaskEntityRWService.class)
                .insertTask(mBuffer.getTask());

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_ID, id);
        setResult(RESULT_OK, intent);
        finish();
    }
}
