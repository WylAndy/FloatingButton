package com.wangyongli.diy.view.floatingbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */

public class FloatingButton implements View.OnClickListener{
    private boolean isOpen = false;
    private ImageView floatingButton;
    private FrameLayout buttonContainer;
    private AnimatorSet openAnimator, closeAnimator;
    private List<Animator> openAnimators;
    private List<Animator> closeAnimators;
    private List<Integer> itemIcons;
    public FloatingButton(Activity activity){
        if (activity == null) throw new IllegalArgumentException("activity don't must be null");
        itemIcons = new ArrayList<>();
        openAnimator = new AnimatorSet();
        closeAnimator = new AnimatorSet();
        openAnimators = new ArrayList<>();
        closeAnimators = new ArrayList<>();
        floatingButton = new ImageView(activity);
        floatingButton.setOnClickListener(this);
        buttonContainer = (FrameLayout) activity.getWindow().getDecorView();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(100, 100);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        layoutParams.rightMargin = 10;
        layoutParams.bottomMargin = 10;
        buttonContainer.addView(floatingButton, layoutParams);
        openAnimators.add(ObjectAnimator.ofFloat(floatingButton, "rotation", 360));
        closeAnimators.add(ObjectAnimator.ofFloat(floatingButton, "rotation", -360));
        for (int i = 0; i < 4; i ++){
            ImageView itemView = new ImageView(activity);
            buttonContainer.addView(itemView, 0, layoutParams);
            PropertyValuesHolder translateX = PropertyValuesHolder.ofFloat("translationX", (float)(-300 * (Math.sin((Math.PI / 6) * i))));
            PropertyValuesHolder translateY = PropertyValuesHolder.ofFloat("translationY", (float)(-300 * (Math.cos((Math.PI / 6) * i))));
            PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1.3f);
            PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1.3f);
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            openAnimators.add(ObjectAnimator.ofPropertyValuesHolder(itemView, translateX, translateY, scaleX, scaleY, alpha));

            PropertyValuesHolder translateCloseX = PropertyValuesHolder.ofFloat("translationX", 0f);
            PropertyValuesHolder translateCloseY = PropertyValuesHolder.ofFloat("translationY", 0f);
            PropertyValuesHolder scaleCloseX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
            PropertyValuesHolder scaleCloseY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
            PropertyValuesHolder alphaClose = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
            closeAnimators.add(ObjectAnimator.ofPropertyValuesHolder(itemView, translateCloseX, translateCloseY, scaleCloseX, scaleCloseY, alphaClose));
        }
        openAnimator.setDuration(300);
        openAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        openAnimator.playTogether(openAnimators);
        closeAnimator.setDuration(300);
        closeAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        closeAnimator.playTogether(closeAnimators);
    }

    public void setItemIcons(List<Integer> itemIcons) {
        if (itemIcons == null) throw new IllegalArgumentException("arg don't must be null");
        this.itemIcons = itemIcons;
        for (int i = 0; i < itemIcons.size(); i ++){
            ImageView imageView = (ImageView) buttonContainer.getChildAt(i);
            imageView.setImageResource(itemIcons.get(i));
        }
    }

    public void setFloatingButtonIcon(int drawableId){
        floatingButton.setImageResource(drawableId);
    }

    public void open(){
        isOpen = true;
        openAnimator.start();
    }

    public void close(){
        isOpen = false;
        closeAnimator.start();
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void onClick(View v) {
        if (isOpen) close();
        else open();
    }
}
