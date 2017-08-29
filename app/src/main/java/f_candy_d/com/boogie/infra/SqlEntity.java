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

    public int getIntOrDefault(@NonNull String key, int deflt) {
        if (mValues.containsKey(key)) {
            return mValues.getAsInteger(key);
        }
        return deflt;
    }

    public long getLongOrDefault(@NonNull String key, long deflt) {
        if (mValues.containsKey(key)) {
            return mValues.getAsLong(key);
        }
        return deflt;
    }

    public short getShortOrDefault(@NonNull String key, short deflt) {
        if (mValues.containsKey(key)) {
            return mValues.getAsShort(key);
        }
        return deflt;
    }

    public float getFloatOrDefault(@NonNull String key, float deflt) {
        if (mValues.containsKey(key)) {
            return mValues.getAsFloat(key);
        }
        return deflt;
    }

    public double getDoubleOrDefault(@NonNull String key, double deflt) {
        if (mValues.containsKey(key)) {
            return mValues.getAsDouble(key);
        }
        return deflt;
    }

    public boolean getBooleanOrDefault(@NonNull String key, boolean deflt) {
        if (mValues.containsKey(key)) {
            return mValues.getAsBoolean(key);
        }
        return deflt;
    }

    public String getStringOrDefault(@NonNull String key, String deflt) {
        if (mValues.containsKey(key)) {
            return mValues.getAsString(key);
        }
        return deflt;
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
}