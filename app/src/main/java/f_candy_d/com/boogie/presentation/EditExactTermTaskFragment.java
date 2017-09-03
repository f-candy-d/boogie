package f_candy_d.com.boogie.presentation;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import f_candy_d.com.boogie.R;
import f_candy_d.com.boogie.data_store.DbContract;
import f_candy_d.com.boogie.domain.DomainDirector;
import f_candy_d.com.boogie.domain.service.ExactTermTaskEntityRWService;
import f_candy_d.com.boogie.domain.structure.ExactTermTask;
import f_candy_d.com.boogie.domain.structure.Task;
import f_candy_d.com.boogie.domain.structure.TaskType;

/**
 * Use the {@link EditExactTermTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditExactTermTaskFragment extends EditTaskFragment {

    private ExactTermTask mTask;
    private DomainDirector<Request> mDomainDirector;

    private enum Request {
        TASK_DATA_RW
    }

    public EditExactTermTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static EditExactTermTaskFragment newInstance(long id) {
        EditExactTermTaskFragment fragment = new EditExactTermTaskFragment();
        fragment.setArguments(makeArgs(id));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDomainDirector = new DomainDirector<>(getActivity(), Request.class);
        mDomainDirector.addService(Request.TASK_DATA_RW, null);
        prepareForEditing(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_exact_term_task, container, false);
    }

    private void prepareForEditing(Bundle args) {
        long id;
        if (args != null && (id = args.getLong(ARG_TASK_ID, DbContract.NULL_ID)) != DbContract.NULL_ID) {
            // findTaskById() is nullable
            mTask = mDomainDirector.getAndCastService(Request.TASK_DATA_RW, ExactTermTaskEntityRWService.class)
                    .findTaskById(id);

            Log.d("mylog", "existing task ##############");
        }

        if (mTask == null) {
            mTask = new ExactTermTask();
            Log.d("mylog", "empty ##############");
        }
    }
}
