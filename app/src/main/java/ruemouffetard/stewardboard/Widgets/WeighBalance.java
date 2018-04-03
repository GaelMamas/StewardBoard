package ruemouffetard.stewardboard.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 30/03/2018.
 */

public class WeighBalance extends View {

    private Paint paint = new Paint();

    public WeighBalance(Context context) {
        this(context, null);
    }

    public WeighBalance(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeighBalance(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }



    private void init() {
        paint.setColor(Color.BLACK);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawGround(canvas, 3);

        //Ellipse

        //2
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        RectF oval2 = new RectF(50, 50, 150, 150);

        Paint p2 = new Paint();
        p2.setColor(Color.GREEN);

        canvas.drawText("Child", 75, 75, p2);
        canvas.drawOval(oval2, paint);

    }

    private void drawGround(@Nullable Canvas canvas, int underlinesNumber){

        for (int i = 0; i < underlinesNumber; i++) {

            canvas.drawLine(10*i, 10*i, 200 - 10*i, 10*i, paint);

        }
    }


}
