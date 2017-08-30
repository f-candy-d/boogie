package f_candy_d.com.boogie.domain.service;

/**
 * Created by daichi on 17/08/30.
 */

abstract public class Service {

    abstract boolean isReady();

    protected void onServiceStart() {
        if (!isReady()) {
            throw new IllegalStateException("This service is not ready");
        }
    }
}