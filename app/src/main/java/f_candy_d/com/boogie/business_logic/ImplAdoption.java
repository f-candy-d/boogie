package f_candy_d.com.boogie.business_logic;

import android.content.Context;

import f_candy_d.com.boogie.data_store.SqliteDatabaseOpenHelperImpl;
import f_candy_d.com.boogie.infra.SqlRepository;
import f_candy_d.com.boogie.infra.sql.SqliteDatabaseOpenHelper;
import f_candy_d.com.boogie.infra.sql.SqliteRepository;

/**
 * Created by daichi on 9/8/17.
 */

public class ImplAdoption {

    public static SqliteDatabaseOpenHelper createSqliteDatabaseOpenHelperImpl(Context context) {
        return new SqliteDatabaseOpenHelperImpl(context);
    }

    public static SqlRepository createSqlRepositoryImpl(Context context) {
        return new SqliteRepository(createSqliteDatabaseOpenHelperImpl(context));
    }
}
