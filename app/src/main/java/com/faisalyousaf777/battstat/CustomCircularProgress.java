package com.faisalyousaf777.battstat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class CustomCircularProgress extends View {

    private Paint backgroundPaint;
    private Paint progressPaint;
    private RectF rectF;
    private int progress = 0; // 0 - 100

    public CustomCircularProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.LTGRAY);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(40f);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(getResources().getColor(R.color.amber));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(40f);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        rectF = new RectF();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float padding = 40f;

        rectF.set(padding, padding, width - padding, height - padding);

        // background circle
        canvas.drawArc(rectF, -230, 280, false, backgroundPaint);

        // progress arc
        float sweepAngle = (280f * progress) / 100f;
        canvas.drawArc(rectF, -230, sweepAngle, false, progressPaint);
    }

    public void setProgress(int progress) {
        this.progress = Math.min(100, Math.max(0, progress));
        invalidate(); // redraw
    }
}
