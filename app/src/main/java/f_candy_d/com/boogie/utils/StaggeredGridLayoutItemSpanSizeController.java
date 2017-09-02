package f_candy_d.com.boogie.utils;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by daichi on 17/09/01.
 */

public class StaggeredGridLayoutItemSpanSizeController implements OnSetupItemSpanSizeListener {

    private StaggeredGridLayoutManager mLayoutManager;

    public StaggeredGridLayoutItemSpanSizeController(int maxSpanCount, int layoutOrientation) {
        mLayoutManager = new StaggeredGridLayoutManager(maxSpanCount, layoutOrientation);
    }

    public StaggeredGridLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public void registerAdapter(EditableItemSpanSizeAdapter adapter) {
        adapter.setOnSetupItemSpanSizeListener(this);
    }

    public void unregisterAdapter(EditableItemSpanSizeAdapter adapter) {
        adapter.setOnSetupItemSpanSizeListener(null);
    }

    /**
     * region; OnSetupItemSpanSizeListener implementation
     */

    @Override
    public void onSetupItemSpanSize(View itemView, boolean requestFullSpan) {
        StaggeredGridLayoutManager.LayoutParams layoutParams =
                (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
        if (layoutParams.isFullSpan() != requestFullSpan) {
            layoutParams.setFullSpan(requestFullSpan);
            itemView.setLayoutParams(layoutParams);
            mLayoutManager.invalidateSpanAssignments();
        }
    }
}
