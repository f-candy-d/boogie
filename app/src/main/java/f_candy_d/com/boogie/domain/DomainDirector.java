package f_candy_d.com.boogie.domain;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.EnumMap;

import f_candy_d.com.boogie.data_store.SqliteDatabaseOpenHelperImpl;
import f_candy_d.com.boogie.infra.SqlRepository;
import f_candy_d.com.boogie.infra.sql.SqliteRepository;

/**
 * Created by daichi on 17/08/30.
 */

public class DomainDirector<E extends Enum<E>> {

    @NonNull final private EnumMap<E, Service> mServiceMap;
    @NonNull final private SqlRepository mSqlRepository;

    public DomainDirector(@NonNull Context context, @NonNull Class<E> keyClass) {
        mServiceMap = new EnumMap<>(keyClass);
        mSqlRepository = new SqliteRepository(new SqliteDatabaseOpenHelperImpl(context));
    }

    public void addService(@NonNull E key, Service service) {
        mServiceMap.put(key, service);
        if (service instanceof SqlRepositoryUser) {
            ((SqlRepositoryUser) service).setSqlRepository(mSqlRepository);
        }
    }

    public Service getService(@NonNull E key) {
        if (mServiceMap.containsKey(key)) {
            return mServiceMap.get(key);
        }
        throw new IllegalStateException("The required service does not exist");
    }

    public <U extends Service> U getAndCastService(@NonNull E key, @NonNull Class<U> serviceClass) {
        return serviceClass.cast(getService(key));
    }
}