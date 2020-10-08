package com.example.bouncingball;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class UpdateThread extends Thread {
    long time;
    boolean toRun = false;
    MovementView movementView;
    SurfaceHolder surfaceHolder;

    //To populate the surfaceHolder variable which will eventually be
    //used to provide a reference of the canvas.
    public UpdateThread(MovementView pMovementView){
        movementView = pMovementView;
        surfaceHolder = movementView.getHolder();
    }

    //To give thread permission to run or not to run
    public void setRunning(boolean run){
        toRun = run;
    }

    //Method which dictates what happens with every second of the thread
    //If it has permission to run, it sets the canvas to empty
    //Get canvas reference and lock it for drawing
    //Update physics of ball
    //Draw ball in new position
    //If safe lock and update canvas
    @SuppressLint("WrongCall")
    @Override
    public void run(){
        Canvas canvas;

        while (toRun){
            long canvasTime = System.currentTimeMillis();

            if((canvasTime - time) <= (1000)){
                canvas = null;
                try{
                    canvas = surfaceHolder.lockCanvas(null);

                    movementView.Bounce();
                    movementView.onDraw(canvas);
                } finally {
                    if(canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
            time = canvasTime;
        }
    }
}
