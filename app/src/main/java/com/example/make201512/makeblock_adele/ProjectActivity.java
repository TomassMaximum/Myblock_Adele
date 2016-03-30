package com.example.make201512.makeblock_adele;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;

import java.io.InputStream;

public class ProjectActivity extends Activity {

    CardView projectView;

    int projectIndex;

    ImageView projectImage;

    TextView projectName;

    Bitmap currentBitmap;

    private int[] projectsImagesId = new int[]{
            R.drawable.capture_a,R.drawable.capture_b,R.drawable.scanner,R.drawable.camera_dolly,
            R.drawable.catapult_ram,R.drawable.color_bar,R.drawable.detecting_robot,R.drawable.mars_explorer,
            R.drawable.mbot,R.drawable.racing_car,R.drawable.robotic_ant,R.drawable.robotic_arm_tank,
            R.drawable.robotic_bartender,R.drawable.rolling_tank,R.drawable.self_balancing_robot,R.drawable.starter};

    private String[] projectsNames = new String[]{
            "3D-Capture-A","3D-Capture-B","3D-Scanner","Camera-Dolly",
            "Catapult-Ram","Color-Bar","Detecting-Robot","Mars-Explorer",
            "mBot","Racing-Car","Robotic-Ant","Robotic-Arm-Tank",
            "Robotic-Bartender","Rolling-Tank","Self-Balancing-Robot","Starter"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        projectImage = (ImageView) findViewById(R.id.project_image);
        projectName = (TextView) findViewById(R.id.project_name);

        projectIndex = getIntent().getIntExtra("projectIndex",0);

        projectView = (CardView) findViewById(R.id.project_card_view);

        ActivityTransition.with(getIntent()).to(projectView).start(savedInstanceState);

        InputStream inputStream = getResources().openRawResource(+ projectsImagesId[projectIndex]);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 2;
        currentBitmap = BitmapFactory.decodeStream(inputStream);

        projectImage.setImageBitmap(currentBitmap);
        projectName.setText(projectsNames[projectIndex]);

    }
}
