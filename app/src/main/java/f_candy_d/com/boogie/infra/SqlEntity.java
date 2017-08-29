package f_candy_d.com.boogie.infra;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Set;

/**
 * Created by daichi on 17/08/30.
 */

final public class SqlEntity {

    @Nullable private String mTableName;
    @NonNull private ContentValues mValueMap;

    public SqlEntity() {
        this(null, null);
    }

    public SqlEntity(String tableName) {
        this(tableName, null);
    }

    public SqlEntity(ContentValues valueMap) {
        this(null, valueMap);
    }

    public SqlEntity(String tableName, ContentValues valueMap) {
        mTableName = tableName;
        if (valueMap != null) {
            mValueMap = new ContentValues(valueMap);
        } else {
            mValueMap = new ContentValues();
        }
    }

    public Set<String> getColumns() {
        return mValueMap.keySet();
    }

    public boolean hasColumn(String column) {
        return (column != null && mValueMap.containsKey(column));
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
        return mValueMap;
    }

    /**
     * region; Setter
     */

    public void put(@NonNull String column, int value) {
        mValueMap.put(column, value);
    }

    public void put(@NonNull String column, long value) {
        mValueMap.put(column, value);
    }

    public void put(@NonNull String column, short value) {
        mValueMap.put(column, value);
    }

    public void put(@NonNull String column, float value) {
        mValueMap.put(column, value);
    }

    public void put(@NonNull String column, double value) {
        mValueMap.put(column, value);
    }

    public void put(@NonNull String column, boolean value) {
        mValueMap.put(column, value);
    }

    public void put(@NonNull String column, String value) {
        mValueMap.put(column, value);
    }

    /**
     * region; Getter
     */

    public int getIntOrDefault(@NonNull String column, int defult) {
        if (mValueMap.containsKey(column)) {
            return mValueMap.getAsInteger(column);
        }
        return defult;
    }

    public long getLongOrDefault(@NonNull String column, long defult) {
        if (mValueMap.containsKey(column)) {
            return mValueMap.getAsLong(column);
        }
        return defult;
    }

    public short getShortOrDefault(@NonNull String column, short defult) {
        if (mValueMap.containsKey(column)) {
            return mValueMap.getAsShort(column);
        }
        return defult;
    }

    public float getFloatOrDefault(@NonNull String column, float defult) {
        if (mValueMap.containsKey(column)) {
            return mValueMap.getAsFloat(column);
        }
        return defult;
    }

    public double getDoubleOrDefault(@NonNull String column, double defult) {
        if (mValueMap.containsKey(column)) {
            return mValueMap.getAsDouble(column);
        }
        return defult;
    }

    public boolean getBooleanOrDefault(@NonNull String column, boolean defult) {
        if (mValueMap.containsKey(column)) {
            return mValueMap.getAsBoolean(column);
        }
        return defult;
    }

    public String getStringOrDefault(@NonNull String column, String defult) {
        if (mValueMap.containsKey(column)) {
            return mValueMap.getAsString(column);
        }
        return defult;
    }
}