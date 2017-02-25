package com.example.administrator.floatingbuttonmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wangyongli.diy.view.floatingbutton.FloatingButton;

public class FloatingButtonExample extends AppCompatActivity implements View.OnClickListener {

    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_button_example);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(100, 100);
        layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        layoutParams.rightMargin = 20;

        FloatingButton.newInstance(this)
                .setFloatingButtonLayout(layoutParams)
                .addItemVeiw(0, R.drawable.menu_button, this)
                .addItemVeiw(1, R.drawable.menu_button, this)
                .addItemVeiw(2, R.drawable.menu_button, this)
                .addItemVeiw(3, R.drawable.menu_button, this)
                .addItemVeiw(4, R.drawable.menu_button, this)
                .addItemVeiw(5, R.drawable.menu_button, this)
                .addItemVeiw(6, R.drawable.menu_button, this)
                .addFloatingButtonView(R.drawable.floating_button)
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case 0 :
                Toast.makeText(this, "item 0 is pressed!", Toast.LENGTH_SHORT).show();
                break;
            case 1 :
                Toast.makeText(this, "item 1 is pressed!", Toast.LENGTH_SHORT).show();
                break;
            case 2 :
                Toast.makeText(this, "item 2 is pressed!", Toast.LENGTH_SHORT).show();
                break;
            case 3 :
                Toast.makeText(this, "item 3 is pressed!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
