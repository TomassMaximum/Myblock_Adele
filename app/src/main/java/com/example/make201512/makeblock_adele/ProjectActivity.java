package com.example.make201512.makeblock_adele;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

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

    WidgetMenuFragment widgetMenuFragment;

    FrameLayout fabContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        actionsMenu = (FloatingActionsMenu) findViewById(R.id.actions_menu);
        actionPause = (FloatingActionButton) findViewById(R.id.action_pause);
        actionDesign = (FloatingActionButton) findViewById(R.id.action_design);

        menuFragmentButton = (AddFloatingActionButton) findViewById(R.id.menu_fragment_button);

        fabContainer = (FrameLayout) findViewById(R.id.fab_container);

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
                if (widgetMenuFragment.isHidden()){
                    showWidgetFragment();
                }

                break;
            }
        }
    }

    public void hideWidgetFragment(){

        ObjectAnimator translateAnimation = ObjectAnimator.ofFloat(fabContainer,"x",0);
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(fabContainer,"rotation",-540);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateAnimation, rotateAnimation);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(300);
        animatorSet.start();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.widget_fragment_in, R.anim.widget_fragment_out);

        fragmentTransaction.hide(widgetMenuFragment).commit();
    }

    public void showWidgetFragment(){

        ObjectAnimator translateAnimation = ObjectAnimator.ofFloat(fabContainer,"x",650);
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(fabContainer,"rotation",585);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateAnimation, rotateAnimation);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(700);
        animatorSet.start();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.widget_fragment_in, R.anim.widget_fragment_out);

        fragmentTransaction.show(widgetMenuFragment).commit();
    }

    public void expandWidgetFragment(){
        actionsMenu.collapse();

        fabOutAnimation = AnimationUtils.loadAnimation(this, R.anim.menu_fab_out);
        actionsMenu.startAnimation(fabOutAnimation);
        actionsMenu.setVisibility(View.INVISIBLE);

        menuFragmentButton.setVisibility(View.VISIBLE);

        ObjectAnimator translateAnimation = ObjectAnimator.ofFloat(fabContainer,"x",-200,650);
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(fabContainer,"rotation",765);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateAnimation,rotateAnimation);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(700);
        animatorSet.start();

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

        ObjectAnimator translateAnimation = ObjectAnimator.ofFloat(fabContainer,"x",-200);
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(fabContainer,"rotation",-765);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateAnimation, rotateAnimation);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setDuration(300);
        animatorSet.start();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.widget_fragment_in, R.anim.widget_fragment_out);
        fragmentTransaction.hide(widgetMenuFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (!(actionsMenu.getVisibility() == View.VISIBLE)){
            collapseWidgetFragment();
        }else {
            finish();
        }
    }
}
