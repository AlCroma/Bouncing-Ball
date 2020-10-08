package com.example.bouncingball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class MovementView extends SurfaceView implements SurfaceHolder.Callback {
    int xPos,yPos, xVel, yVel, width, height, circleRadius;
    Paint circlePaint;

    UpdateThread updateThread;

    public MovementView(Context context){
        super(context);
        getHolder().addCallback(this);

        //Setting Paint properties
        circlePaint = new Paint();
        circlePaint.setColor(Color.CYAN);
        circlePaint.setStyle(Paint.Style.FILL);

        circleRadius = 150;

        //To set the speed of the ball in each direction
        xVel = 8;
        yVel = 8;
    }

    //Method which draws on the canvas
    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.BLACK);

        canvas.drawCircle(xPos, yPos, circleRadius, circlePaint);
    }

    //Method which makes the ball bounce on the edges of the screen
    public void Bounce(){
        //Change direction in x-axis
        xPos += xVel;

        //Uncomment for all direction bouncing
//        //Change direction in y-axis
//        yPos += yVel;
//
//        //To check if the ball has hit the top or bottom of the canvas
//        if(yPos - circleRadius < 0 || yPos + circleRadius > height){
//            if(yPos - circleRadius < 0){
//                //the ball has hit the top of the canvas
//                yPos = circleRadius;
//            }
//            else{
//                //the ball has hit the bottom of the canvas
//                yPos = height - circleRadius;
//            }
//
//            //reverse the y direction of the ball
//            yVel *= -1;
//        }

        //To check if the ball has hit either sides of the canvas
        if (xPos-circleRadius < 0 || xPos + circleRadius > width){
            if(xPos - circleRadius < 0){
                //the ball has hit the left of the canvas
                xPos = circleRadius;
            }
            else{
                //the ball has hit the right of the canvas
                xPos = width - circleRadius;
            }

            //reverse the x direction of the ball
            xVel *= -1;
        }
    }

    public void surfaceCreated(SurfaceHolder holder){
        //To get the width and height of the screen
        Rect surfaceFrame = holder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();

        //To set the x-position and y-position to centre of screen
        xPos = width/2;
        yPos = height/2;

        //To draw circle at centre before the animation begins
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawCircle(xPos, yPos, 150, circlePaint);
        getHolder().unlockCanvasAndPost(canvas);

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        //To suspend the ball's movement until start button is again pressed
        boolean retry = true;
        while (retry){
            try{
                updateThread.join();
                retry = false;
            } catch (InterruptedException e){

            }
        }
    }
}
