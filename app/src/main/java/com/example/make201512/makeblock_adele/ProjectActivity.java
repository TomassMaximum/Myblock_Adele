package com.example.make201512.makeblock_adele;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProjectActivity extends Activity implements View.OnClickListener {

    int projectIndex;

    ImageView homeIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        homeIcon = (ImageView) findViewById(R.id.nav_home);

        projectIndex = getIntent().getIntExtra("projectIndex",0);

        homeIcon.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.nav_home:{
                finish();
                break;
            }
        }
    }
}
