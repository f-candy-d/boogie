package f_candy_d.com.boogie.domain.structure;

/**
 * Created by daichi on 17/08/30.
 */

abstract public class Content {

    abstract ContentCategory getContentCategory();
    abstract String toSummary();
}
