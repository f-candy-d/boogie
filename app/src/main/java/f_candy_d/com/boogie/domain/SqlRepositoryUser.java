package f_candy_d.com.boogie.domain;

import android.support.annotation.NonNull;

import f_candy_d.com.boogie.infra.SqlRepository;

/**
 * Created by daichi on 17/08/30.
 */

public interface SqlRepositoryUser {
    void setSqlRepository(@NonNull SqlRepository repository);
}
