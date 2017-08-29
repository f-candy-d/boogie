package f_candy_d.com.boogie.infra;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Set;

/**
 * Created by daichi on 17/08/30.
 */

final public class SqlEntity {

    @Nullable private String mTableName;
    @NonNull private ContentValues mValues;

    public SqlEntity() {
        this(null, null);
    }

    public SqlEntity(String tableName) {
        this(tableName, null);
    }

    public SqlEntity(ContentValues values) {
        this(null, values);
    }

    public SqlEntity(String tableName, ContentValues values) {
        mTableName = tableName;
        if (values != null) {
            mValues = new ContentValues(values);
        } else {
            mValues = new ContentValues();
        }
    }

    public Set<String> getColumns() {
        return mValues.keySet();
    }

    public void setTableName(String tableName) {
        mTableName = tableName;
    }

    @Nullable
    public String getTableName() {
        return mTableName;
    }

    @NonNull
    public ContentValues getValueMap() {
        return mValues;
    }

    /**
     * region; Setter
     */

    public void put(@NonNull String key, int value) {
        mValues.put(key, value);
    }

    public void put(@NonNull String key, long value) {
        mValues.put(key, value);
    }

    public void put(@NonNull String key, short value) {
        mValues.put(key, value);
    }

    public void put(@NonNull String key, float value) {
        mValues.put(key, value);
    }

    public void put(@NonNull String key, double value) {
        mValues.put(key, value);
    }

    public void put(@NonNull String key, boolean value) {
        mValues.put(key, value);
    }

    public void put(@NonNull String key, String value) {
        mValues.put(key, value);
    }

    /**
     * region; Getter
     */

    public int getIntOrDefault(@NonNull String key, int defult) {
        if (mValues.containsKey(key)) {
            return mValues.getAsInteger(key);
        }
        return defult;
    }

    public long getLongOrDefault(@NonNull String key, long defult) {
        if (mValues.containsKey(key)) {
            return mValues.getAsLong(key);
        }
        return defult;
    }

    public short getShortOrDefault(@NonNull String key, short defult) {
        if (mValues.containsKey(key)) {
            return mValues.getAsShort(key);
        }
        return defult;
    }

    public float getFloatOrDefault(@NonNull String key, float defult) {
        if (mValues.containsKey(key)) {
            return mValues.getAsFloat(key);
        }
        return defult;
    }

    public double getDoubleOrDefault(@NonNull String key, double defult) {
        if (mValues.containsKey(key)) {
            return mValues.getAsDouble(key);
        }
        return defult;
    }

    public boolean getBooleanOrDefault(@NonNull String key, boolean defult) {
        if (mValues.containsKey(key)) {
            return mValues.getAsBoolean(key);
        }
        return defult;
    }

    public String getStringOrDefault(@NonNull String key, String defult) {
        if (mValues.containsKey(key)) {
            return mValues.getAsString(key);
        }
        return defult;
    }
}