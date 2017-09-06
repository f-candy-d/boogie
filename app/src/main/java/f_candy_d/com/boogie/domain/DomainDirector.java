package f_candy_d.com.boogie.domain;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import f_candy_d.com.boogie.data_store.SqliteDatabaseOpenHelperImpl;
import f_candy_d.com.boogie.domain.service.Service;
import f_candy_d.com.boogie.infra.SqlRepository;
import f_candy_d.com.boogie.infra.sql.SqliteRepository;

/**
 * Created by daichi on 17/08/30.
 */

public class DomainDirector {

    @NonNull final private Map<String, Service> mServiceMap;
    @NonNull final private SqlRepository mSqlRepository;

    public DomainDirector(@NonNull Context context) {
        mServiceMap = new HashMap<>();
        mSqlRepository = new SqliteRepository(new SqliteDatabaseOpenHelperImpl(context));
    }

    public void addService(Service service) {
        mServiceMap.put(service.getClass().getSimpleName(), service);
        if (service instanceof SqlRepositoryUser) {
            ((SqlRepositoryUser) service).setSqlRepository(mSqlRepository);
        }
    }

    public <U extends Service> U getService(@NonNull Class<U> serviceClass) {
        if (mServiceMap.containsKey(serviceClass.getSimpleName())) {
            return serviceClass.cast(mServiceMap.get(serviceClass.getSimpleName()));
        }
        throw new IllegalStateException("The required service does not exist");
    }
}