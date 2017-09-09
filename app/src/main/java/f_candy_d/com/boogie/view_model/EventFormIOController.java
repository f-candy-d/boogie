package f_candy_d.com.boogie.view_model;

import android.content.Context;

import f_candy_d.com.boogie.business_logic.TaskIOLogic;
import f_candy_d.com.boogie.data_store.TaskTableRule;
import f_candy_d.com.boogie.structure.EventTaskWrapper;

/**
 * Created by daichi on 9/9/17.
 */

public class EventFormIOController {

    private TaskIOLogic mTaskIOLogic;

    public EventFormIOController(Context context) {
        mTaskIOLogic = new TaskIOLogic(context);
    }

    public TaskTableRule.ValidationErrorCode[] saveTask(EventTaskWrapper eventTaskWrapper) {
        TaskTableRule.ValidationErrorCode[] errorCodes = eventTaskWrapper.getTask().checkValidation();
        if (errorCodes.length != 0) return errorCodes;

        eventTaskWrapper.getTask().id = mTaskIOLogic.insert(eventTaskWrapper.getTask());
        return null;
    }
}
