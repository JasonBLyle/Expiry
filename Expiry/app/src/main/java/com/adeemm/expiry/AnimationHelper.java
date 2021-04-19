package com.adeemm.expiry;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;


/**
 * This class helps with the animations we have in our app
 */
public class AnimationHelper {

    /**
     * This function makes the view disappear immediately
     */
    public static void setInvisible(View view) {
        view.setVisibility(View.GONE);
        view.setAlpha(0f);
        view.setTranslationY(view.getHeight());
    }

    /**
     * This function animates the view's disappear over 200 ms
     */
    public static void animateDisappear(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(1f);
        view.setTranslationY(0);
        view.animate()
                .setDuration(200)
                .translationY(view.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(0f)
                .start();
    }

    /**
     * This function animates the view's appearance over 200 ms
     */
    public static void animateAppear(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        view.setTranslationY(view.getHeight());
        view.animate()
                .setDuration(200)
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1f)
                .start();
    }

    /**
     * This function rotates the FAB when pressed
     */
    public static boolean rotateFAB(View view, boolean shouldRotate) {
        view.animate()
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .rotation(shouldRotate ? 135f : 0f);

        return shouldRotate;
    }
}
