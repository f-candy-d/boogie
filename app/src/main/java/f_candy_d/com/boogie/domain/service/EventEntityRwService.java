package f_candy_d.com.boogie.domain.service;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import f_candy_d.com.boogie.data_store.EventTableContract;
import f_candy_d.com.boogie.data_store.SqlDbContract;
import f_candy_d.com.boogie.domain.structure.Event;
import f_candy_d.com.boogie.infra.SqlEntity;
import f_candy_d.com.boogie.utils.InstantTime;
import f_candy_d.com.boogie.utils.Month;
import f_candy_d.com.boogie.utils.TermInterface;

/**
 * Created by daichi on 17/08/30.
 */

public class EventEntityRwService extends SqlEntityRwService {

    public EventEntityRwService() {}

    public long insertEvent(Event event) {
        if (!checkValidation(event)) {
            return SqlDbContract.NULL_ID;
        }

        SqlEntity entity = createSqlEntityFromEvent(event);
        final long id = getSqlRepository().insert(entity);

        return (id != -1) ? id : SqlDbContract.NULL_ID;
    }

    @Nullable
    public Event getEventForId(long id) {
        SqlEntity result = getSqlRepository().selectColumnForId(EventTableContract.TABLE_NAME, id);
        if (result != null) {
            return createEventFromSqlEntity(result);
        } else {
            return null;
        }
    }

    @NonNull
    private SqlEntity createSqlEntityFromEvent(@NonNull Event event) {
        SqlEntity entity = new SqlEntity(EventTableContract.TABLE_NAME);

        entity.put(EventTableContract._ID, event.getId());
        entity.put(EventTableContract._NAME, event.getName());
        entity.put(EventTableContract._NOTE, event.getNote());
        entity.put(EventTableContract._REPETITION, event.getRepetition());

        entity.put(EventTableContract._START_YEAR, event.getStartYear());
        entity.put(EventTableContract._START_MONTH, event.getStartMonth());
        entity.put(EventTableContract._START_DAY_OF_MONTH, event.getStartDayOfMonth());
        entity.put(EventTableContract._START_TIME_SINCE_MIDNIGHT_IN_MINUTES,
                event.getStartTime().getTimeSinceMidnightInMinutes());
        entity.put(EventTableContract._START_DAY_OF_WEEK, event.getStartDayOfWeek());

        entity.put(EventTableContract._END_YEAR, event.getEndYear());
        entity.put(EventTableContract._END_MONTH, event.getEndMonth());
        entity.put(EventTableContract._END_DAY_OF_MONTH, event.getEndDayOfMonth());
        entity.put(EventTableContract._END_TIME_SINCE_MIDNIGHT_IN_MINUTES,
                event.getEndTime().getTimeSinceMidnightInMinutes());
        entity.put(EventTableContract._END_DAY_OF_WEEK, event.getEndDayOfWeek());

        return entity;
    }

    @NonNull
    private Event createEventFromSqlEntity(@NonNull SqlEntity entity) {
        Event event = new Event();

        event.setId(entity.getLongOrDefault(
                EventTableContract._ID, event.getId()));
        event.setName(entity.getStringOrDefault(
                EventTableContract._NAME, event.getName()));
        event.setNote(entity.getStringOrDefault(
                EventTableContract._NOTE, event.getNote()));

        event.setStartYear(entity.getIntOrDefault(
                EventTableContract._START_YEAR, event.getStartYear()));
        event.setStartMonth(entity.getQuantizableEnumOrDefault(
                EventTableContract._START_MONTH, Month.class, event.getStartMonth()));
        event.setStartDayOfMonth(entity.getIntOrDefault(
                EventTableContract._START_DAY_OF_MONTH, event.getStartDayOfMonth()));

        int timeSinceMidnightInMinutes = entity.getIntOrDefault(
                EventTableContract._START_TIME_SINCE_MIDNIGHT_IN_MINUTES, -1);
        InstantTime time = (timeSinceMidnightInMinutes != -1)
                ? new InstantTime(timeSinceMidnightInMinutes)
                : null;
        event.setStartTime(time);

        event.setEndYear(entity.getIntOrDefault(
                EventTableContract._END_YEAR, event.getEndYear()));
        event.setEndMonth(entity.getQuantizableEnumOrDefault(
                EventTableContract._END_MONTH, Month.class, event.getEndMonth()));
        event.setEndDayOfMonth(entity.getIntOrDefault(
                EventTableContract._END_DAY_OF_MONTH, event.getEndDayOfMonth()));

        timeSinceMidnightInMinutes = entity.getIntOrDefault(
                EventTableContract._END_TIME_SINCE_MIDNIGHT_IN_MINUTES, -1);
        time = (timeSinceMidnightInMinutes != -1)
                ? new InstantTime(timeSinceMidnightInMinutes)
                : null;
        event.setEndTime(time);

        event.setRepetition(entity.getQuantizableEnumOrDefault(
                EventTableContract._REPETITION, TermInterface.Repetition.class, event.getRepetition()));

        return event;
    }

    private boolean checkValidation(Event event) {
        if (event == null) {
            return false;
        }

        return true;
    }
}
