package com.example.make201512.makeblock_adele;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class ProjectActivity extends Activity implements View.OnClickListener {

    int projectIndex;

    ImageView iconBack;

    FloatingActionsMenu actionsMenu;
    FloatingActionButton actionPause;
    FloatingActionButton actionDesign;
    AddFloatingActionButton menuFragmentButton;

    Animation fabInAnimation;

    Animation fabOutAnimation;

    Animation menuButtonFirstIn;

    Animation menuButtonLastOut;

    Animation menuButtonCollapse;

    Animation menuButtonExpand;

    WidgetMenuFragment widgetMenuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        actionsMenu = (FloatingActionsMenu) findViewById(R.id.actions_menu);
        actionPause = (FloatingActionButton) findViewById(R.id.action_pause);
        actionDesign = (FloatingActionButton) findViewById(R.id.action_design);

        menuFragmentButton = (AddFloatingActionButton) findViewById(R.id.menu_fragment_button);

        menuFragmentButton.setOnClickListener(this);

        actionDesign.setOnClickListener(this);

        iconBack = (ImageView) findViewById(R.id.project_activity_back);

        iconBack.setOnClickListener(this);

        projectIndex = getIntent().getIntExtra("projectIndex",0);

        widgetMenuFragment = new WidgetMenuFragment();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.project_activity_back:{
                finish();
                break;
            }
            case R.id.action_design:{

                expandWidgetFragment();
                break;
            }
            case R.id.menu_fragment_button:{
                if (menuFragmentButton.getVisibility() == View.VISIBLE){
                    hideWidgetFragment();
                }

                break;
            }
        }
    }

    public void hideWidgetFragment(){

        menuButtonCollapse = AnimationUtils.loadAnimation(this,R.anim.widget_menu_fragment_button_collapse);
        menuFragmentButton.setAnimation(menuButtonCollapse);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.widget_fragment_in, R.anim.widget_fragment_out);

        fragmentTransaction.hide(widgetMenuFragment).commit();
    }

    public void showWidgetFragment(){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.widget_fragment_in, R.anim.widget_fragment_out);

        fragmentTransaction.show(widgetMenuFragment).commit();
    }

    public void expandWidgetFragment(){
        actionsMenu.collapse();

        fabOutAnimation = AnimationUtils.loadAnimation(this,R.anim.menu_fab_out);
        actionsMenu.startAnimation(fabOutAnimation);
        actionsMenu.setVisibility(View.INVISIBLE);

        menuButtonFirstIn = AnimationUtils.loadAnimation(this,R.anim.widget_menu_fragment_button_first_in);
        menuFragmentButton.setVisibility(View.VISIBLE);
        menuFragmentButton.startAnimation(menuButtonFirstIn);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.widget_fragment_in, R.anim.widget_fragment_out);

        if (widgetMenuFragment.isAdded()){
            fragmentTransaction.show(widgetMenuFragment).commit();
        }else {
            fragmentTransaction.add(R.id.widget_menu_container,widgetMenuFragment).commit();
        }
    }

    public void collapseWidgetFragment(){
        fabInAnimation = AnimationUtils.loadAnimation(this,R.anim.menu_fab_in);
        actionsMenu.startAnimation(fabInAnimation);
        actionsMenu.setVisibility(View.VISIBLE);

        menuButtonLastOut = AnimationUtils.loadAnimation(this,R.anim.widget_menu_fragment_button_last_out);
        menuFragmentButton.startAnimation(menuButtonLastOut);
        menuFragmentButton.setVisibility(View.GONE);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.widget_fragment_in, R.anim.widget_fragment_out);
        fragmentTransaction.hide(widgetMenuFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (widgetMenuFragment.isVisible()){
            collapseWidgetFragment();
        }else {
            finish();
        }
    }
}
