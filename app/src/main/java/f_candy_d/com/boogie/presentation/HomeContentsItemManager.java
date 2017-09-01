package f_candy_d.com.boogie.presentation;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by daichi on 17/09/01.
 */

public class HomeContentsItemManager implements HomeContentsAdapter.RequestListener {

    private HomeContentsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public HomeContentsItemManager(HomeContentsAdapter adapter, RecyclerView.LayoutManager layoutManager) {
        setAdapter(adapter);
        setLayoutManager(layoutManager);
    }

    public HomeContentsAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(HomeContentsAdapter adapter) {
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.setRequestListener(this);
        }
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    /**
     * region; HomeContentsAdapter.RequestListener implementation
     */

    @Override
    public void onManageItemSpanSize(View itemView, boolean isFullSpan) {
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams layoutParams =
                    (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
            if (layoutParams.isFullSpan() != isFullSpan) {
                layoutParams.setFullSpan(isFullSpan);
                itemView.setLayoutParams(layoutParams);
                ((StaggeredGridLayoutManager) mLayoutManager).invalidateSpanAssignments();
            }

        }
    }
}
