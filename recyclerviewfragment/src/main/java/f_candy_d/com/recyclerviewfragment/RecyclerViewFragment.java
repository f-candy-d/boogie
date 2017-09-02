package f_candy_d.com.recyclerviewfragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by daichi on 17/08/29.
 */

abstract public class RecyclerViewFragment <VH extends RecyclerView.ViewHolder> extends Fragment
        implements RecyclerViewFragmentAdapter.DispatchMethodCallListener<VH> {

    private RecyclerViewFragmentAdapter<VH> mAdapter;

    /**
     * TODO; Call this method in onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState).
     */
    final protected View onCreateView(LayoutInflater inflater, ViewGroup container, int layoutId, int recyclerViewId) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(layoutId, container, false);
        RecyclerView recyclerView = view.findViewById(recyclerViewId);
        init(recyclerView);
        return view;
    }

    /**
     * Use this method instead of {@link RecyclerViewFragment#onCreateView(LayoutInflater, ViewGroup, int, int)}
     * if you want to use the default layout (includes only a RecyclerView).
     */
    final protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return onCreateView(
                inflater,
                container,
                R.layout.recycler_view_fragment_simple_layout,
                R.id.recycler_view_fragment_simple_layout_recycler_view);
    }

    private void init(RecyclerView recyclerView) {
        mAdapter = new RecyclerViewFragmentAdapter<>(this);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(mAdapter);
    }

    final public RecyclerViewFragmentAdapter<VH> getAdapter() {
        return mAdapter;
    }

    @NonNull
    abstract protected RecyclerView.LayoutManager getLayoutManager();

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
