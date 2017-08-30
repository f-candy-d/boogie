package f_candy_d.com.boogie.domain.service;

import android.support.annotation.NonNull;

import f_candy_d.com.boogie.domain.SqlRepositoryUser;
import f_candy_d.com.boogie.infra.SqlEntity;
import f_candy_d.com.boogie.infra.SqlRepository;
import f_candy_d.com.boogie.infra.sql.SqlCondExpr;
import f_candy_d.com.boogie.infra.sql.SqlWhere;

/**
 * Created by daichi on 17/08/30.
 */

public class SqlEntityRwService extends Service implements SqlRepositoryUser {

    private SqlRepository mSqlRepository = null;

    SqlEntityRwService() {}

    protected SqlRepository getSqlRepository() {
        return mSqlRepository;
    }

    @Override
    boolean isReady() {
        return (mSqlRepository != null);
    }

    @Override
    final public void setSqlRepository(@NonNull SqlRepository repository) {
        mSqlRepository = repository;
    }
}
