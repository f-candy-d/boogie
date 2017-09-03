package f_candy_d.com.boogie.data_store;

import f_candy_d.com.boogie.infra.sql.SqliteTableUtils;

/**
 * Created by daichi on 17/09/03.
 */

public class ExactTermTaskTable extends TaskTableBase {

    public static final String TABLE_NAMEE = "exact_term_task";

    public static SqliteTableUtils.TableSource getTableSource() {
        return getBaseTableSource();
    }
}
