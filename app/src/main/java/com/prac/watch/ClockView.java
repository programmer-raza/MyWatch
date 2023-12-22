package com.prac.watch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ClockView extends View {

    private Paint circlePaint;
    private Paint handPaint;
    private Paint textPaint;
    private Paint digitalTimePaint;

    private int centerX, centerY;
    private float clockRadius;

    private Calendar calendar;

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5);

        handPaint = new Paint();
        handPaint.setStrokeWidth(5);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);

        digitalTimePaint = new Paint();
        digitalTimePaint.setColor(Color.BLACK);
        digitalTimePaint.setTextSize(40);
        digitalTimePaint.setTypeface(Typeface.DEFAULT_BOLD);

        calendar = Calendar.getInstance();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        clockRadius = Math.min(centerX, centerY) - 10;

        // Draw clock circle
        canvas.drawCircle(centerX, centerY, clockRadius, circlePaint);

        // Draw clock hands
        drawClockHands(canvas);

        // Draw clock numbers
        drawClockNumbers(canvas);

        // Draw digital time
        drawDigitalTime(canvas);
    }

    private void drawClockHands(Canvas canvas) {
        float secondX, secondY, minuteX, minuteY, hourX, hourY;

        int hour = calendar.get(Calendar.HOUR_OF_DAY) % 12;
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        // Draw second hand
        drawHand(canvas, second, 60, clockRadius * 0.9, handPaint, Color.RED);

        // Draw minute hand
        drawHand(canvas, minute, 60, clockRadius * 0.8, handPaint, Color.BLACK);

        // Draw hour hand
        drawHand(canvas, hour * 5 + minute / 12, 60, clockRadius * 0.6, handPaint, Color.BLACK);
    }

    private void drawHand(Canvas canvas, int value, int range, double length, Paint paint, int color) {
        paint.setColor(color);
        double angle = Math.toRadians(-90 + (360.0 / range) * value);
        float startX = centerX;
        float startY = centerY;
        float endX = centerX + (float) (length * Math.cos(angle));
        float endY = centerY + (float) (length * Math.sin(angle));
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    private void drawClockNumbers(Canvas canvas) {
        float textOffset = 50;
        float lineLength = 30; // Length of the lines

        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(-90 + (360.0 / 12) * i);
            float x = centerX + (float) ((clockRadius - textOffset) * Math.cos(angle));
            float y = centerY + (float) ((clockRadius - textOffset) * Math.sin(angle));

            // Draw short line
            float startX = centerX + (float) ((clockRadius) * Math.cos(angle));
            float startY = centerY + (float) ((clockRadius) * Math.sin(angle));
            float endX = centerX + (float) ((clockRadius - lineLength) * Math.cos(angle));
            float endY = centerY + (float) ((clockRadius - lineLength) * Math.sin(angle));
            canvas.drawLine(startX, startY, endX, endY, textPaint);

            // Draw number
            String number = String.valueOf(i);
            float textWidth = textPaint.measureText(number);
            float textHeight = textPaint.descent() - textPaint.ascent(); // Measure text height
            canvas.drawText(number, x - textWidth / 2, y + textHeight / 2, textPaint);


        }
    }


    private void drawDigitalTime(Canvas canvas) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        String digitalTime = sdf.format(calendar.getTime());
        float textWidth = digitalTimePaint.measureText(digitalTime);
        canvas.drawText(digitalTime, centerX - textWidth / 2, centerY + clockRadius + 60, digitalTimePaint);
    }

    public void setCurrentTime() {
        calendar = Calendar.getInstance();
        invalidate(); // Trigger redraw with the updated time
    }
}
