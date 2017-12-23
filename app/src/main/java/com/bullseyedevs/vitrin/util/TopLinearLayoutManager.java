package com.bullseyedevs.vitrin.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;

/**
 * Created by brkckr on 18.11.2017.
 */

public class TopLinearLayoutManager extends LinearLayoutManager
{
    public TopLinearLayoutManager(Context context, int orientation)
    {
        super(context, orientation, false);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position)
    {
        RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private static class CenterSmoothScroller extends LinearSmoothScroller
    {
        CenterSmoothScroller(Context context)
        {
            super(context);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference)
        {
            return (boxStart + (boxEnd - boxStart) / 10) - (viewStart + (viewEnd - viewStart) / 10);
        }
    }
}