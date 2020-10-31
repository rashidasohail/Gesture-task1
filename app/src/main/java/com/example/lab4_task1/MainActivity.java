package com.example.lab4_task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener{
    ImageView detailImage;
    GestureDetector detector;
    View.OnTouchListener listener;
    ScaleGestureDetector detector1;
    private float scale = 1f;
    float onScaleBegin = 0;
    float onScaleEnd = 0;

    int[] images = {R.drawable.img1, R.drawable.img2, R.drawable.img3,R.drawable.img3,R.drawable.img4,R.drawable.img5,R.drawable.img6};
    int MIN_DISTANCE = 150;
    int OFF_PATH = 100;
    int VELOCITY_THRESHOLD = 75;
    int imageIndex = 0;

    String TAG = "DBG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detector = new GestureDetector(this,this);
        detector1= new ScaleGestureDetector(this,this);
        listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                 detector.onTouchEvent(event);
                 detector1.onTouchEvent(event);
                 return detector.onTouchEvent(event);
            }
        };
        
        detailImage = (ImageView) findViewById(R.id.iv);
        detailImage.setImageResource(R.drawable.img2);
        detailImage.setOnTouchListener(listener);

    }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {}

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {}

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling");

            if (Math.abs(event1.getY() - event2.getY()) > OFF_PATH)
                return false;

            if (images.length != 0) {
                if (event1.getX() - event2.getX() > MIN_DISTANCE && Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                    // Swipe left
                    imageIndex++;
                    if (imageIndex == images.length)
                        imageIndex = 0;
                    detailImage.setImageResource(images[imageIndex]);
                } else {
                    // Swipe right
                    if (event2.getX() - event1.getX() > MIN_DISTANCE && Math.abs(velocityX) > VELOCITY_THRESHOLD) {
                        imageIndex--;
                        if (imageIndex < 0) imageIndex =
                                images.length - 1;
                        detailImage.setImageResource(images[imageIndex]);
                    }
                }
            }
            return true;
        }
    @Override
    public boolean onScale(ScaleGestureDetector detector1) {
        scale *= detector1.getScaleFactor();
        scale = Math.max(0.1f, Math.min(scale, 5.0f));
        detailImage.setScaleX(scale);
        detailImage.setScaleY(scale);
        return true;
    }
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Log.d(TAG, "onScaleBegin");
        onScaleBegin = scale;
        return true;
    }
    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.d(TAG, "onScaleEnd");
        onScaleEnd = scale;


        if (onScaleEnd > onScaleBegin) {
            Toast.makeText(getApplicationContext(), "Scaled Up by a factor of " + String.valueOf(onScaleEnd / onScaleBegin), Toast.LENGTH_SHORT).show();
        }

        if (onScaleEnd < onScaleBegin) {
            Toast.makeText(getApplicationContext(), "Scaled Down by a factor of " + String.valueOf(onScaleBegin / onScaleEnd), Toast.LENGTH_SHORT).show();
        }

    }



}