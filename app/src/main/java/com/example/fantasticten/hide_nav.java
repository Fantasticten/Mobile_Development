package com.example.fantasticten;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class hide_nav implements View.OnTouchListener{
    private GestureDetector gestureDetector;

    public hide_nav (Context context,View viewAnimation){
        gestureDetector = new GestureDetector(context, new SimpleGesturDetector(viewAnimation));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public class SimpleGesturDetector extends GestureDetector.SimpleOnGestureListener{
        private View viewanimation;
        private boolean isFinisanimation = true;

        public SimpleGesturDetector(View viewanimation){
            this.viewanimation=viewanimation;

        }

        @Override
        public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            if (distanceY>0){
                hiddenView();
            }else {
                showView();
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
        private void hiddenView(){
            if ( viewanimation == null || viewanimation.getVisibility() == View.GONE){
                return;
            }
            Animation animationDown = AnimationUtils.loadAnimation(viewanimation.getContext(), R.anim.move_down);
            animationDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    viewanimation.setVisibility(View.VISIBLE);
                    isFinisanimation = false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    viewanimation.setVisibility(View.GONE);
                    isFinisanimation = true;

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            if (isFinisanimation){
                viewanimation.startAnimation(animationDown);
            }

        }
        private void showView(){

            if ( viewanimation == null || viewanimation.getVisibility() == View.VISIBLE){
                return;
            }
            Animation animationUp = AnimationUtils.loadAnimation(viewanimation.getContext(), R.anim.move_up);
            animationUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    viewanimation.setVisibility(View.VISIBLE);
                    isFinisanimation = false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isFinisanimation = true;

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            if (isFinisanimation){
                viewanimation.startAnimation(animationUp);
            }

        }
    }
}
