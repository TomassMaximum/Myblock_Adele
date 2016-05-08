package com.example.make201512.makeblock_adele.project_activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.make201512.makeblock_adele.R;

/**
 * Created by make201512 on 2016/4/2.
 */
public class WidgetMenuFragment extends Fragment implements View.OnClickListener {

    LinearLayout moveLinear;
    ImageView moveIcon;
    TextView moveText;

    LinearLayout displayLinear;
    ImageView displayIcon;
    TextView displayText;

    LinearLayout senseLinear;
    ImageView senseIcon;
    TextView senseText;

    LinearLayout controlLinear;
    ImageView controlIcon;
    TextView controlText;

    LinearLayout customLinear;
    ImageView customIcon;
    TextView customText;

    LinearLayout currentLinear;
    ImageView currentIcon;
    TextView currentText;

    WidgetFragment widgetFragment;
    Fragment currentFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_widget_menu, container, false);

        findViews(rootView);

        setMoveMenuSelected();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.move_linear:{
                setSelected(moveLinear,moveIcon,moveText);
                showCorrespondingFragment(0);
                break;
            }
            case R.id.display_linear:{
                setSelected(displayLinear,displayIcon,displayText);
                showCorrespondingFragment(1);
                break;
            }
            case R.id.sense_linear:{
                setSelected(senseLinear,senseIcon,senseText);
                showCorrespondingFragment(2);
                break;
            }
            case R.id.control_linear:{
                setSelected(controlLinear,controlIcon,controlText);
                showCorrespondingFragment(3);
                break;
            }
            case R.id.custom_linear:{
                setSelected(customLinear,customIcon,customText);
                showCorrespondingFragment(4);
                break;
            }
            default:
                break;
        }
    }

    public void showCorrespondingFragment(int position){

        if (currentFragment != null){
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(currentFragment).commit();
        }

        widgetFragment = new WidgetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        widgetFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.add(R.id.widget_fragment_container, widgetFragment);
        transaction.commit();

        currentFragment = widgetFragment;
    }

    public void findViews(View rootView){
        moveLinear = (LinearLayout) rootView.findViewById(R.id.move_linear);
        moveIcon = (ImageView) rootView.findViewById(R.id.move_icon);
        moveText = (TextView) rootView.findViewById(R.id.move_text);
        moveLinear.setOnClickListener(this);

        displayLinear = (LinearLayout) rootView.findViewById(R.id.display_linear);
        displayIcon = (ImageView) rootView.findViewById(R.id.display_icon);
        displayText = (TextView) rootView.findViewById(R.id.display_text);
        displayLinear.setOnClickListener(this);

        senseLinear = (LinearLayout) rootView.findViewById(R.id.sense_linear);
        senseIcon = (ImageView) rootView.findViewById(R.id.sense_icon);
        senseText = (TextView) rootView.findViewById(R.id.sense_text);
        senseLinear.setOnClickListener(this);

        controlLinear = (LinearLayout) rootView.findViewById(R.id.control_linear);
        controlIcon = (ImageView) rootView.findViewById(R.id.control_icon);
        controlText = (TextView) rootView.findViewById(R.id.control_text);
        controlLinear.setOnClickListener(this);

        customLinear = (LinearLayout) rootView.findViewById(R.id.custom_linear);
        customIcon = (ImageView) rootView.findViewById(R.id.custom_icon);
        customText = (TextView) rootView.findViewById(R.id.custom_text);
        customLinear.setOnClickListener(this);
    }

    public void setSelected(LinearLayout linearLayout,ImageView icon,TextView text){
        if (currentLinear != null){
            currentLinear.setSelected(false);
            currentIcon.setSelected(false);
            currentText.setSelected(false);
        }

        currentLinear = linearLayout;
        currentIcon = icon;
        currentText = text;

        currentLinear.setSelected(true);
        currentIcon.setSelected(true);
        currentText.setSelected(true);
    }

    public void setMoveMenuSelected(){
        setSelected(moveLinear,moveIcon,moveText);
        showCorrespondingFragment(0);
    }
}
