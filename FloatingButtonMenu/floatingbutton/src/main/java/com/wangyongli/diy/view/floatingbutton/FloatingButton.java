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
    private Activity activity;
    private int itemCount = 0;
    private FrameLayout.LayoutParams layoutParams;
    private List<View> itemButtons = new ArrayList<>();
    private FloatingButton(Activity activity){
        if (activity == null) throw new IllegalArgumentException("activity don't must be null");
        this.activity = activity;
        openAnimator = new AnimatorSet();
        closeAnimator = new AnimatorSet();
        openAnimators = new ArrayList<>();
        closeAnimators = new ArrayList<>();

        buttonContainer = (FrameLayout) activity.getWindow().getDecorView();
        layoutParams = new FrameLayout.LayoutParams(100, 100);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        layoutParams.rightMargin = 10;
        layoutParams.bottomMargin = 10;

        openAnimator.setDuration(300);
        openAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        closeAnimator.setDuration(300);
        closeAnimator.setInterpolator(new AnticipateOvershootInterpolator());
    }

    public FloatingButton addItemVeiw(int viewId, int drawableId, View.OnClickListener onClickListener){
        ImageView imageView = new ImageView(activity);
        itemButtons.add(imageView);
        imageView.setId(viewId);
        imageView.setImageResource(drawableId);
        imageView.setOnClickListener(onClickListener);
        buttonContainer.addView(imageView, 0, layoutParams);
        PropertyValuesHolder translateX = PropertyValuesHolder.ofFloat("translationX", (float)(-300 * (Math.sin((Math.PI / 6) * (itemCount)))));
        PropertyValuesHolder translateY = PropertyValuesHolder.ofFloat("translationY", (float)(-300 * (Math.cos((Math.PI / 6) * (itemCount ++)))));
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1.3f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1.3f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        openAnimators.add(ObjectAnimator.ofPropertyValuesHolder(imageView, translateX, translateY, scaleX, scaleY, alpha));
        PropertyValuesHolder translateCloseX = PropertyValuesHolder.ofFloat("translationX", 0f);
        PropertyValuesHolder translateCloseY = PropertyValuesHolder.ofFloat("translationY", 0f);
        PropertyValuesHolder scaleCloseX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
        PropertyValuesHolder scaleCloseY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
        PropertyValuesHolder alphaClose = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        closeAnimators.add(ObjectAnimator.ofPropertyValuesHolder(imageView, translateCloseX, translateCloseY, scaleCloseX, scaleCloseY, alphaClose));
        return this;
    }

    public FloatingButton addFloatingButtonView(int drawableId){
        floatingButton = new ImageView(activity);
        buttonContainer.addView(floatingButton, layoutParams);
        openAnimators.add(ObjectAnimator.ofFloat(floatingButton, "rotation", 360));
        closeAnimators.add(ObjectAnimator.ofFloat(floatingButton, "rotation", -360));
        floatingButton.setImageResource(drawableId);
        floatingButton.setOnClickListener(this);
        return this;
    }

    public FloatingButton setFloatingButtonLayout(FrameLayout.LayoutParams floatingButtonLayout){
        layoutParams = floatingButtonLayout;
        return this;
    }

    public void build(){
        closeAnimator.playTogether(closeAnimators);
        openAnimator.playTogether(openAnimators);
    }

    public void open(){
        isOpen = true;
        openAnimator.start();
    }

    public void close(){
        isOpen = false;
        closeAnimator.start();
    }

    public boolean isOpened() {
        return isOpen;
    }

    @Override
    public void onClick(View v) {
        if (isOpened()) close();
        else open();
    }

    public static FloatingButton newInstance(Activity activity){
        return new FloatingButton(activity);
    }

    private void setItemButtonsVisibility(int isView){
        for (View view : itemButtons){
            view.setVisibility(isView);
        }
    }

    public void setVisibility(int visibility){
        if (floatingButton.getVisibility() == visibility) return;
        if (isOpen) close();
        setItemButtonsVisibility(visibility);
        floatingButton.setVisibility(visibility);
    }

}
