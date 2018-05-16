package ruemouffetard.stewardboard.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 14/05/2018.
 */

public class NoteStackView extends View {

    private Path noteStackPath;
    private Paint noteStackPaint;

    private Paint detailsPaint;

    private PointF A, B, C, D, G;
    private double theta;

    private float h0, h, epsilon, b0, b1;

    private int width = 300, height = 300;

    RectF cornerNoteRect = new RectF();

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

        A = new PointF(2 * width / 5, 4 * height / 5);

        h0 = 100;
        theta = Math.PI / 4;
        b0 = 500;
        b1 = (float) (b0 - 2 * h0 / Math.tan(theta));

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

        epsilon = 5;
        h = 50;

        if(h > epsilon) {
            for (int i = 0; i < h / epsilon; i++) {

                noteStackPath.moveTo(A.x, A.y + (i + 1) * epsilon);
                noteStackPath.lineTo(D.x, A.y + (i + 1) * epsilon);

            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(noteStackPath, noteStackPaint);

        float thetaInDegrees = (float)Math.toDegrees(theta);

        drawCorner(canvas, A, 360 - thetaInDegrees, thetaInDegrees, 50);
        drawCorner(canvas, B,0, 180 - thetaInDegrees, 10);
        drawCorner(canvas, C, thetaInDegrees, 180 - thetaInDegrees, 10);
        drawCorner(canvas, D, 180, thetaInDegrees, 50);

        drawTextInTheMiddle(canvas, G,"€",0);

        detailsPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(noteStackPath, detailsPaint);
    }

    private void drawCorner(Canvas canvas, PointF M, float startAngle, float spanAngle, float cornerRadius) {

        cornerNoteRect.left = M.x - cornerRadius;
        cornerNoteRect.top = M.y - cornerRadius;
        cornerNoteRect.right = M.x + cornerRadius;
        cornerNoteRect.bottom = M.y + cornerRadius;

        canvas.drawArc(cornerNoteRect, startAngle, spanAngle, true, detailsPaint);
    }

    private void drawTextInTheMiddle(Canvas canvas, PointF M, String text, float cornerRadius) {

        detailsPaint.setTextSize(40);
        detailsPaint.setStyle(Paint.Style.FILL);

        canvas.drawText(text, M.x - cornerRadius, M.y - cornerRadius, detailsPaint);

    }

    @Override
    public void invalidate() {
        init();
        super.invalidate();
    }
}
