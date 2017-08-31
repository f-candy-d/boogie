package f_candy_d.com.boogie.domain.structure;

/**
 * Created by daichi on 17/08/30.
 */

abstract public class Content {

    abstract public ContentCategory getContentCategory();

    abstract public String toSummary();

    @Override
    abstract public boolean equals(Object obj);

    @Override
    abstract public int hashCode();
}
