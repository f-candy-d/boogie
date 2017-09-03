package f_candy_d.com.boogie.domain.structure;

/**
 * Created by daichi on 17/09/03.
 */

public class ExactTermTask extends Task {

    public ExactTermTask() {}

    public ExactTermTask(ExactTermTask task) {
        super(task);
    }

    @Override
    public TaskType getType() {
        return TaskType.EXACT_TERM;
    }

    @Override
    public String toSummary() {
        return super.title;
    }
}
