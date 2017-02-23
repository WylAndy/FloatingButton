package com.example.administrator.floatingbuttonmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wangyongli.diy.view.floatingbutton.FloatingButton;

import java.util.ArrayList;
import java.util.List;

public class FloatingButtonExample extends AppCompatActivity {

    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_button_example);
        List<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.menu_button);
        icons.add(R.drawable.menu_button);
        icons.add(R.drawable.menu_button);
        icons.add(R.drawable.menu_button);
        FloatingButton floatingButton = new FloatingButton(this);
        floatingButton.setItemIcons(icons);
        floatingButton.setFloatingButtonIcon(R.drawable.floating_button);
    }
}
