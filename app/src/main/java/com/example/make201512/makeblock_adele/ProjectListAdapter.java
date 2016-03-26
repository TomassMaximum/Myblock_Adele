package com.example.make201512.makeblock_adele;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by make201512 on 2016/3/25.
 */
public class ProjectListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "ProjectListAdapter";

    MainActivity mainActivity;

    private int[] projectImages = {R.drawable.project_mbot,R.drawable.project_mdrawbot,R.drawable.project_starter,R.drawable.project_ultimate,R.drawable.project_xy_plotter,R.drawable.project_melephant,R.drawable.project_mbot,R.drawable.project_mdrawbot,R.drawable.project_starter,R.drawable.project_ultimate,R.drawable.project_xy_plotter,R.drawable.project_melephant};
    private String[] projectNames = {"mBot Ranger - Racing Car","mDrawerBot","Starter - Robot lit","Ultimate - Robotic Arm Tank","XY - Plotter","mElephant - 3D Printer","mBot Ranger - Racing Car","mDrawerBot","Starter - Robot lit","Ultimate - Robotic Arm Tank","XY - Plotter","mElephant - 3D Printer"};

    ArrayList<Bitmap> bitmapList = new ArrayList<>();

    public ProjectListAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
//        initBitmap();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerHolder viewHolder = (RecyclerHolder) holder;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        Bitmap currentImage = BitmapFactory.decodeResource(mainActivity.getResources(),projectImages[position],options);

        viewHolder.projectPictureImageView.setImageBitmap(currentImage);
        viewHolder.projectName.setText(projectNames[position]);

    }

    @Override
    public int getItemCount() {
        return projectImages.length;
    }

    private class RecyclerHolder extends RecyclerView.ViewHolder{

        ImageView projectPictureImageView;
        TextView projectName;

        public RecyclerHolder(View itemView) {
            super(itemView);

            projectPictureImageView = (ImageView) itemView.findViewById(R.id.pic_project);
            projectName = (TextView) itemView.findViewById(R.id.text_project_name);
        }
    }

    public void initBitmap(){
        long startTime = System.currentTimeMillis();

        for (int i = 0;i < projectImages.length;i++){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap currentImage = BitmapFactory.decodeResource(mainActivity.getResources(),projectImages[i],options);
            bitmapList.add(currentImage);
        }

        long endTime = System.currentTimeMillis();

        long timeUsed = endTime - startTime;
        Log.e(TAG,timeUsed + "");
    }
}
