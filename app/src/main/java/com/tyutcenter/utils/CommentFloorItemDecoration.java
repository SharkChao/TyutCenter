package com.tyutcenter.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CommentFloorItemDecoration extends RecyclerView.ItemDecoration {
    private static float BORDER_OFFSET;
    private static float BORDER_WIDTH;
    private Context mContext;
    private Paint mBorderPaint;

    public CommentFloorItemDecoration(Context context) {
        mContext = context;

        BORDER_OFFSET = context.getResources().getDisplayMetrics().density * 1;
        BORDER_WIDTH = context.getResources().getDisplayMetrics().density * 1;
        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(Color.LTGRAY);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(BORDER_WIDTH);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int itemCount = parent.getChildCount();

        int top = parent.getChildAt(0).getTop();
        for (int i = 0; i < itemCount; i++) {
            View child = parent.getChildAt(i);
            int left = child.getLeft();
            int right = child.getRight();
            int bottom = child.getBottom();
            c.drawRect(left, top, right, bottom, mBorderPaint);
            top -= (BORDER_WIDTH + BORDER_OFFSET);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.Adapter adapter = parent.getAdapter();
        int itemCount = adapter.getItemCount();
        int i = parent.getChildAdapterPosition(view);
        int top = (int) ((itemCount - i) * BORDER_WIDTH + (itemCount - i - 1) * BORDER_OFFSET);
        int left = top;
        int right = top;
        int bottom = (int) BORDER_WIDTH;
        if (i == 0) {
            outRect.set(left, top, right, bottom);
        } else {
            outRect.set(left, 0, right, bottom);
        }
    }
}
