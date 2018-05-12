package ruemouffetard.stewardboard.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

import ruemouffetard.stewardboard.UsefulMehtod;

/**
 * Created by admin on 30/03/2018.
 */

public class WeighBalance extends View {

    protected final static int GROUND = 0, THRESHOLD = 1, BEAM = 2, TRAY = 3;
    protected final static float EARTH_GRAVITY = 9.81f;
    protected final static float SLOW_DOWN_PENDULUM_MOVE = 5000f;


    protected Path groundPath, thresholdPath, beamPath, trayPath;
    protected Paint groundPaint, thresholdPaint, beamPaint, trayPaint;

    protected float h0, groundWingWidth, beamWingWidth, b1, trayWingWidth;
    protected double alpha0;
    protected float newAlpha;
    protected PointF M0, M1, M2, M3, primeM3;
    protected PointF M11;

    private int time = 0;
    private double oscillationPeriod;
    protected double pulsation;
    protected float standardMass, perfectableMass;

    protected Choreographer choreographer = Choreographer.getInstance();

    public WeighBalance(Context context) {
        this(context, null);
    }

    public WeighBalance(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeighBalance(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.newAlpha = 0;
        this.time = 0;
        init();
    }


    private void init() {

        initPathPaint(GROUND, Color.BLACK, 10, Paint.Style.STROKE);
        initPathPaint(THRESHOLD, Color.BLUE, 7, Paint.Style.STROKE);
        initPathPaint(BEAM, Color.GRAY, 7, Paint.Style.STROKE);
        initPathPaint(TRAY, Color.GRAY, 7, Paint.Style.STROKE);


        this.h0 = 150;
        this.b1 = 50;
        this.alpha0 = Math.PI / 6;

        this.M0 = new PointF(500, 400);
        this.trayWingWidth = 75;
        this.beamWingWidth = (float) (h0 / Math.sin(alpha0));
        this.groundWingWidth = (float) (h0 / Math.tan(alpha0));

        double beta = (Math.PI / 2 - alpha0) / 2;

        this.M1 = new PointF(M0.x + (float) (h0 / Math.tan(alpha0)), M0.y + h0);
        this.M2 = new PointF(M0.x - b1, M0.y + (float) (b1 / Math.tan(beta)));


        try {
            this.pulsation = Math.sqrt(Math.abs((standardMass - perfectableMass) / (standardMass + perfectableMass))
                    * EARTH_GRAVITY / (beamWingWidth * SLOW_DOWN_PENDULUM_MOVE));

            this.oscillationPeriod = SLOW_DOWN_PENDULUM_MOVE * Math
                    .sqrt(Math.abs((standardMass - perfectableMass) / (standardMass + perfectableMass)) * beamWingWidth / EARTH_GRAVITY) * Math.PI / 2;


        } catch (ArithmeticException e) {
            this.pulsation = 0;
            this.oscillationPeriod = 0;
            Log.i(getClass().getSimpleName(), "masses null");
        }


        schemeGround();
        schemeThreshold();
        schemeDynamicBeam();
        //schemeBeam();
        schemeDynamicTrays(50);
        // schemeTrays(50);

    }


    private void initPathPaint(int pathIndex, int pathColor, int pathStrokeWidth, Paint.Style pathStrokeStyle) {

        switch (pathIndex) {
            case GROUND:

                groundPaint = new Paint();
                groundPaint.setColor(pathColor);
                groundPaint.setStrokeWidth(pathStrokeWidth);
                groundPaint.setStyle(pathStrokeStyle);

                break;
            case THRESHOLD:

                thresholdPaint = new Paint();
                thresholdPaint.setColor(pathColor);
                thresholdPaint.setStrokeWidth(pathStrokeWidth);
                thresholdPaint.setStyle(pathStrokeStyle);

                break;
            case BEAM:

                beamPaint = new Paint();
                beamPaint.setColor(pathColor);
                beamPaint.setStrokeWidth(pathStrokeWidth);
                beamPaint.setStyle(pathStrokeStyle);

                break;
            case TRAY:

                trayPaint = new Paint();
                trayPaint.setColor(pathColor);
                trayPaint.setStrokeWidth(pathStrokeWidth);
                trayPaint.setStyle(pathStrokeStyle);

                break;

        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(groundPath, groundPaint);
        canvas.drawPath(thresholdPath, thresholdPaint);
        canvas.drawPath(beamPath, beamPaint);
        canvas.drawPath(trayPath, trayPaint);

    }


    private void schemeThreshold() {

        thresholdPath = new Path();

        thresholdPath.moveTo(M0.x, M0.y);
        thresholdPath.lineTo(M2.x, M2.y);
        thresholdPath.lineTo(M2.x, M0.y + h0);
        thresholdPath.lineTo(M0.x + b1, M0.y + h0);
        thresholdPath.lineTo(M0.x + b1, M2.y);
        thresholdPath.lineTo(M0.x, M0.y);

    }

    private void schemeGround() {

        float superfluous = 7;
        float groundWidth = 2 * groundWingWidth;

        this.groundPath = new Path();

        groundPath.moveTo(M1.x, M1.y + superfluous);
        groundPath.lineTo(M1.x - groundWidth, M1.y + superfluous);

        groundPath.moveTo(M1.x - 50, M1.y + 20 + superfluous);
        groundPath.lineTo(M1.x - groundWidth + 50, M1.y + 20 + superfluous);


    }

    private void schemeBeam() {

        this.beamPath = new Path();

        beamPath.moveTo(M1.x, M1.y);
        beamPath.lineTo(2 * M0.x - M1.x, 2 * M0.y - M1.y);

    }

    private void schemeDynamicBeam() {

        this.beamPath = new Path();

        M11 = new PointF((float) (M0.x + beamWingWidth * Math.cos(newAlpha)),
                (float) (M0.y + beamWingWidth * Math.sin(newAlpha)));

        beamPath.moveTo(M11.x, M11.y);
        beamPath.lineTo(2 * M0.x - M11.x, 2 * M0.y - M11.y);

        choreographer.postFrameCallback(frameCallback);

    }

    private void schemeTrays(float traySupport) {

        trayPath = new Path();

        double radius = traySupport / Math.sin(alpha0);

        M3 = new PointF((float) (M1.x + radius), M1.y);

        primeM3 = new PointF((float) (M1.x - radius * Math.cos(2 * alpha0)),
                (float) (M1.y - 2 * traySupport * Math.cos(alpha0)));

        PointF rightVector = new PointF((M3.x + primeM3.x) / 2 - M1.x, (M3.y + primeM3.y) / 2 - M1.y);

        schemeATray(M1, M3, primeM3, 20);
        schemeATray(new PointF(2 * M0.x - M1.x, 2 * M0.y - M1.y),
                UsefulMehtod.getSymmetryWithRespectToARight(rightVector, M0, M3),
                UsefulMehtod.getSymmetryWithRespectToARight(rightVector, M0, primeM3), 20);

    }

    protected void schemeDynamicTrays(float traySupport) {

        trayPath = new Path();

        double radius = traySupport / Math.sin(alpha0);

        double lambda = alpha0;

        M3 = new PointF((float) (M11.x + radius * Math.cos(lambda - newAlpha)),
                (float) (M11.y - radius * Math.sin(lambda - newAlpha)));

        primeM3 = new PointF((float) (M11.x - radius * Math.cos(lambda + newAlpha)),
                (float) (M11.y - radius * Math.sin(lambda + newAlpha)));

        PointF rightVector = new PointF((M3.x + primeM3.x) / 2 - M11.x, (M3.y + primeM3.y) / 2 - M11.y);

        schemeDynamicATray(M11, M3, primeM3, 20);
        schemeDynamicATray(new PointF(2 * M0.x - M11.x, 2 * M0.y - M11.y),
                UsefulMehtod.getSymmetryWithRespectToARight(rightVector, M0, M3),
                UsefulMehtod.getSymmetryWithRespectToARight(rightVector, M0, primeM3), 20);

    }

    protected void schemeATray(PointF startDrawingPoint, PointF trayRightEndPoint, PointF trayLeftEndPoint, float edging) {

        trayPath.moveTo(startDrawingPoint.x, startDrawingPoint.y);
        trayPath.lineTo((trayRightEndPoint.x + trayLeftEndPoint.x) / 2, (trayRightEndPoint.y + trayLeftEndPoint.y) / 2);

        trayPath.moveTo(trayRightEndPoint.x, trayRightEndPoint.y);
        trayPath.lineTo(trayLeftEndPoint.x, trayLeftEndPoint.y);

        trayPath.moveTo(trayRightEndPoint.x, trayRightEndPoint.y);
        trayPath.lineTo((float) (trayRightEndPoint.x + edging * Math.sin(alpha0)), (float) (trayRightEndPoint.y - edging * Math.cos(alpha0)));

        trayPath.moveTo(trayLeftEndPoint.x, trayLeftEndPoint.y);
        trayPath.lineTo((float) (trayLeftEndPoint.x + edging * Math.sin(alpha0)), (float) (trayLeftEndPoint.y - edging * Math.cos(alpha0)));
    }

    protected void schemeDynamicATray(PointF startDrawingPoint, PointF trayRightEndPoint, PointF trayLeftEndPoint, float edging) {

        trayPath.moveTo(startDrawingPoint.x, startDrawingPoint.y);
        trayPath.lineTo((trayRightEndPoint.x + trayLeftEndPoint.x) / 2, (trayRightEndPoint.y + trayLeftEndPoint.y) / 2);

        trayPath.moveTo(trayRightEndPoint.x, trayRightEndPoint.y);
        trayPath.lineTo(trayLeftEndPoint.x, trayLeftEndPoint.y);

        trayPath.moveTo(trayRightEndPoint.x, trayRightEndPoint.y);
        trayPath.lineTo((float) (trayRightEndPoint.x + edging * Math.sin(newAlpha)), (float) (trayRightEndPoint.y - edging * Math.cos(newAlpha)));

        trayPath.moveTo(trayLeftEndPoint.x, trayLeftEndPoint.y);
        trayPath.lineTo((float) (trayLeftEndPoint.x + edging * Math.sin(newAlpha)), (float) (trayLeftEndPoint.y - edging * Math.cos(newAlpha)));

    }

    protected void setUpCornerEffet(int pathIndex, float radius) {

        CornerPathEffect cornerPathEffect =
                new CornerPathEffect(radius);

        switch (pathIndex) {
            case TRAY:

                trayPaint.setPathEffect(cornerPathEffect);

                break;

        }


    }


    private Choreographer.FrameCallback frameCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long l) {


            if (time <= oscillationPeriod) {
                    newAlpha = (float) (alpha0 * Math.sin(time * pulsation + perfectableMass < standardMass ? 0:Math.PI));
            } else {
                if(perfectableMass <= standardMass) {
                    newAlpha = (float) (alpha0 - Math.abs(Math.sin(time * Math.PI / (2 * oscillationPeriod)))
                            * Math.exp(-time + oscillationPeriod) * Math.PI / 180);
                }else{
                    newAlpha = (float) (-alpha0 + Math.abs(Math.sin(time * Math.PI / (2 * oscillationPeriod)))
                            * Math.exp(-time + oscillationPeriod) * Math.PI / 180);
                }
            }

            invalidate();

            if (time <= 2 * oscillationPeriod) {

                time += 100;

                choreographer.postFrameCallback(frameCallback);

            } else {

                time = (int) (2 * oscillationPeriod);
                choreographer.removeFrameCallback(frameCallback);
                
                if(balanceListener != null){
                    balanceListener.sendDifference(standardMass - perfectableMass);
                }

            }

        }
    };


    public void playWeighBalance() {

        perfectableMass = standardMass * standardMass;
        standardMass += 0.1;
        this.time = 0;

        choreographer.postFrameCallback(frameCallback);
    }

    @Override
    public void invalidate() {
        init();
        super.invalidate();
    }

    public void pause() {
        time = 1000;
        invalidate();
        choreographer.removeFrameCallback(frameCallback);
    }
    
    protected WeighBalanceListener balanceListener = null;
    
    public void setBalanceListener(WeighBalanceListener balanceListener) {
        this.balanceListener = balanceListener;
    }

    public interface WeighBalanceListener{
        
        void sendDifference(float difference);
    }
}
