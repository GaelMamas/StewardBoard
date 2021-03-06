package ruemouffetard.stewardboard.Widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.View;

import ruemouffetard.stewardboard.R;
import ruemouffetard.stewardboard.UsefulMehtod;

/**
 * Created by admin on 14/05/2018.
 */

public class NoteStackView extends View {

    private final static int HEAVY_STACK_QUOTIENT = 100;
    private final static int STACK_LAYERS = 10;
    private final static int ANIMATION_TIME_MAX = 2000;

    private Path noteStackPath;
    private Paint noteStackPaint;

    private Paint detailsPaint;

    private int width, height;

    private PointF A, B, C, D, G;

    private double theta;

    private float h0, epsilon, b0, b1, stackHeight;

    private float stackDensity;

    private String currency = "€££";
    private int ellipseHoriRadius = 50;
    private int ellipseVertiRadius = 20;

    private float stripeWidth1, stripeWidth2, stripeWitdh2;

    private RectF usefulRect = new RectF();

    private Choreographer choreographer = Choreographer.getInstance();

    public NoteStackView(Context context) {
        this(context, null);
    }

    public NoteStackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoteStackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

        noteStackPaint = new Paint();
        noteStackPaint.setStyle(Paint.Style.FILL);
        noteStackPaint.setColor(Color.GREEN);
        noteStackPaint.setAntiAlias(true);
        noteStackPaint.setStrokeWidth(4);

        detailsPaint = new Paint();
        detailsPaint.setStyle(Paint.Style.STROKE);
        detailsPaint.setColor(Color.BLACK);
        detailsPaint.setAntiAlias(true);
        detailsPaint.setStrokeWidth(2);


        h0 = 100;
        theta = Math.atan(2);
        b0 = 3 * h0;
        b1 = 2 * h0;

        epsilon = 5;
        stackHeight = 10000;

    }

    private void initPoints() {

        A = new PointF((width - b0) / 2, (height + h0) / 2);
        B = new PointF(A.x + (float) (h0 / Math.tan(theta)), A.y - h0);
        C = new PointF(B.x + b1, B.y);
        D = new PointF(A.x + b0, A.y);
        G = new PointF(A.x + b0 / 2, A.y - h0 / 2);


        noteStackPath = new Path();

        noteStackPath.moveTo(A.x, A.y);
        noteStackPath.lineTo(B.x, B.y);
        noteStackPath.lineTo(C.x, C.y);
        noteStackPath.lineTo(D.x, D.y);
        noteStackPath.lineTo(A.x, A.y);

    }

    private void initStack() {

        for (int i = 0; i < STACK_LAYERS; i++) {

            noteStackPath.moveTo(A.x, A.y + (i + 1) * epsilon);
            noteStackPath.lineTo(D.x, A.y + (i + 1) * epsilon);

        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        int minh = MeasureSpec.getSize(w) + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(minh), heightMeasureSpec, 0);

        width = w;
        height = h;

        stackDensity = stackHeight / HEAVY_STACK_QUOTIENT;

        initPoints();
        initStack();

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        //choreographer.postFrameCallback(frameCallback);

        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (stackDensity < .2) {
            usefulRect.set(G.x - h0 / 2,
                    G.y - h0 / 2, G.x + h0 / 2, G.y + h0 / 2);

            Bitmap bitmap = UsefulMehtod.getBitmapFromVectorDrawable(getContext(),
                    stackDensity >= .01? R.drawable.ic_coins_silver: R.drawable.ic_coins_bronze);

            canvas.drawBitmap(bitmap, null, usefulRect, null);

            return;
        }

        noteStackPaint.setColor(getNoteColor(stackDensity));
        canvas.drawPath(noteStackPath, noteStackPaint);

        float thetaInDegrees = (float) Math.toDegrees(theta);

        drawCorner(canvas, A, 360 - thetaInDegrees, thetaInDegrees, 35);
        drawCorner(canvas, B, 0, 180 - thetaInDegrees, 10);
        drawCorner(canvas, C, thetaInDegrees, 180 - thetaInDegrees, 10);
        drawCorner(canvas, D, 180, thetaInDegrees, 35);

        usefulRect.set(G.x - ellipseHoriRadius,
                G.y - ellipseVertiRadius, G.x + ellipseHoriRadius, G.y + ellipseVertiRadius);

        canvas.drawOval(usefulRect, detailsPaint);

        drawTextInTheMiddle(canvas, G, TextUtils.isEmpty(currency) ? "" : currency.trim().substring(0, 1), 13);

        //Draw Border and Layers
        detailsPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(noteStackPath, detailsPaint);
    }

    private void drawCorner(Canvas canvas, PointF M, float startAngle, float spanAngle, float cornerRadius) {

        usefulRect.left = M.x - cornerRadius;
        usefulRect.top = M.y - cornerRadius;
        usefulRect.right = M.x + cornerRadius;
        usefulRect.bottom = M.y + cornerRadius;

        canvas.drawArc(usefulRect, startAngle, spanAngle, true, detailsPaint);
    }

    private void drawTextInTheMiddle(Canvas canvas, PointF M, String text, float cornerRadius) {

        if (TextUtils.isEmpty(text)) return;

        detailsPaint.setTextSize(40);
        detailsPaint.setStyle(Paint.Style.FILL);

        canvas.drawText(text, M.x - cornerRadius, M.y + cornerRadius, detailsPaint);

    }

    private int getNoteColor(float stackDensity) {

        if (stackDensity == 0) return Color.BLACK;

        if (stackDensity >= 1000) {
            return ActivityCompat.getColor(getContext(), R.color.purple_euro_note_color);
        } else if (stackDensity >= 100) {
            return ActivityCompat.getColor(getContext(), R.color.green_euro_note_color);
        } else if (stackDensity >= 10) {
            return ActivityCompat.getColor(getContext(), R.color.blue_euro_note_color);
        } else if (stackDensity >= 0.2) {
            return ActivityCompat.getColor(getContext(), R.color.copper_euro_note_color);
        } else {
            return ActivityCompat.getColor(getContext(), R.color.silver_euro_note_color);
        }


    }

    public void runStewardShip(float stackHeight) {

        this.stackHeight = stackHeight;
        choreographer.postFrameCallback(frameCallback);

    }

    int time = 0;

    Choreographer.FrameCallback frameCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long l) {


            invalidate();

            /*noteStackPath.moveTo(A.x, A.y + (time / 25 + 1) * epsilon);
            noteStackPath.lineTo(D.x, A.y + (time / 25 + 1) * epsilon);*/

            stackDensity = (-stackHeight * time / ANIMATION_TIME_MAX + stackHeight) / HEAVY_STACK_QUOTIENT;


            if (time >= ANIMATION_TIME_MAX) {

                time += 5;
                choreographer.postFrameCallback(frameCallback);

            } else {
                choreographer.removeFrameCallback(frameCallback);
                time = ANIMATION_TIME_MAX;
            }
        }
    };


    private void pause() {
        choreographer.removeFrameCallback(frameCallback);
        time = ANIMATION_TIME_MAX;
    }

    @Override
    protected void onDetachedFromWindow() {
        pause();
        super.onDetachedFromWindow();
    }
}
