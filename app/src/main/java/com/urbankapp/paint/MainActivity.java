package com.urbankapp.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.drawView = new DrawView(this);
        setContentView(this.drawView);
    }

    public class DrawView extends View {

        private final int finalWidthCircle = 14;
        private final int finalWidthBrush = 14;
        private final int finalColor = Color.parseColor("#9FA9DF");

        private float lastPosX,lastPosY;

        private Paint circlePaint; //Estilos
        private Path circlePath;   //Figuras geometricas

        private Paint brushPaint;
        private Path brushPath;

        private Canvas canvas;
        private Bitmap myBitMap;
        private Paint bitMapPaint;


        public DrawView(Context context){
            super(context);
            this.createCircle();
            this.createBrush();
        }

        private void createCircle(){
            this.circlePaint = new Paint();
            this.circlePath = new Path();

            this.circlePaint.setDither(true);
            this.circlePaint.setAntiAlias(true);
            this.circlePaint.setColor(this.finalColor);
            this.circlePaint.setStyle(Paint.Style.STROKE);
            this.circlePaint.setStrokeWidth(this.finalWidthCircle);
        }

        private void createBrush(){
            this.brushPaint = new Paint();
            this.brushPath = new Path();

            this.brushPaint.setDither(true);
            this.brushPaint.setAntiAlias(true);
            this.brushPaint.setColor(this.finalColor);
            this.brushPaint.setStyle(Paint.Style.STROKE);
            this.brushPaint.setStrokeCap(Paint.Cap.ROUND);
            this.brushPaint.setStrokeWidth(this.finalWidthBrush);
        }

        private void actionDown(float posx, float posy){
            this.lastPosX = posx;
            this.lastPosY = posy;

            this.circlePath.reset();
            this.brushPath.reset();

            this.brushPath.moveTo(lastPosX,lastPosY);
        }

        private void actionMove(float posx, float posy){
            this.circlePath.reset();

            this.brushPath.quadTo(this.lastPosX,this.lastPosY,(posx + this.lastPosX)/2,(posy + this.lastPosY)/2);
            this.lastPosX = posx;
            this.lastPosY = posy;

            this.circlePath.addCircle(posx,posy,30,Path.Direction.CCW);
        }

        private void actionUp(){
            this.circlePath.reset();
            this.brushPath.reset();
        }

        private void showMessage(String msg){
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            canvas.drawPath(this.brushPath,this.brushPaint);
            canvas.drawPath(this.circlePath,this.circlePaint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
            this.showMessage("Touch Event");

            float posx = event.getX();
            float posy = event.getY();

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    this.actionDown(posx,posy);
                    break;
                case MotionEvent.ACTION_MOVE:
                    this.actionMove(posx,posy);
                    break;
                case MotionEvent.ACTION_UP:
                    this.actionUp();
                    break;
            }

            this.invalidate();

            return true;
        }
    }
}
