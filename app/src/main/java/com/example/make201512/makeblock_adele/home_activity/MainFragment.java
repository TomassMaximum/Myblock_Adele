package com.example.make201512.makeblock_adele.home_activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.make201512.makeblock_adele.project_activity.ProjectActivity;
import com.example.make201512.makeblock_adele.R;
import com.example.make201512.makeblock_adele.Constants;

import java.io.InputStream;

/**
 * Created by make201512 on 2016/3/29.
 */
public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    private GridView projectsGridView;

    private RecyclerView recyclerView;

    private int[] projectsImagesId = new int[]{
            R.drawable.robotic_arm_tank,R.drawable.racing_car,R.drawable.color_bar,R.drawable.mars_explorer,
            R.drawable.camera_dolly,R.drawable.robotic_bartender,R.drawable.self_balancing_robot,R.drawable.detecting_robot,
            R.drawable.capture_a,R.drawable.capture_b,R.drawable.rolling_tank,R.drawable.robotic_ant,
            R.drawable.catapult_ram,R.drawable.mbot,R.drawable.starter,R.drawable.scanner};

    private Bitmap[] projectsImages = new Bitmap[Constants.getProjectsNum()];

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 0;i < projectsImagesId.length;i++){
            InputStream inputStream = getActivity().getResources().openRawResource(+ projectsImagesId[i]);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 2;
            Bitmap currentBitmap = BitmapFactory.decodeStream(inputStream);
            projectsImages[i] = currentBitmap;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.projects_recycler_view);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(),projectsImages);

        recyclerView.setAdapter(adapter);



        return rootView;
    }

    private class GridViewListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(),ProjectActivity.class);
            intent.putExtra("enter_mode",position + "");
            startActivity(intent);

        }
    }

    private static class RecyclerViewAdapter extends RecyclerView.Adapter{

        Context context;
        Bitmap[] projectsImages;
        int position = -1;

        int startOffSet = 200;

        int showTimes = 0;

        private String[] projectsNames = new String[]{
                "Robotic-Arm-Tank","Racing-Car","Color-Bar","Mars-Explorer",
                "Camera-Dolly","Robotic-Bartender","Self-Balancing-Robot","Detecting-Robot",
                "3D-Capture-A","3D-Capture-B","Rolling-Tank","Robotic-Ant",
                "Catapult-Ram","mBot","Starter","3D-Scanner"
        };

        public RecyclerViewAdapter(Context context,Bitmap[] projectsImages){
            this.context = context;
            this.projectsImages = projectsImages;
        }

        private class Holder extends RecyclerView.ViewHolder{

            LinearLayout projectLinearLayout;
            ImageView projectImage;
            TextView projectName;

            public Holder(View itemView) {
                super(itemView);
                projectLinearLayout = (LinearLayout) itemView.findViewById(R.id.project_item_linear_layout);
                projectImage = (ImageView) itemView.findViewById(R.id.project_image);
                projectName = (TextView) itemView.findViewById(R.id.project_name);
            }

            public void clearAnimation(){
                projectLinearLayout.clearAnimation();
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item,parent,false);
            return new Holder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.e(TAG,"onBindViewHolder被执行");
            startOffSet += 80;
            showTimes++;
            Holder mHolder = (Holder) holder;
            mHolder.projectImage.setImageBitmap(projectsImages[position]);
            mHolder.projectName.setText(projectsNames[position]);
            if (showTimes < 7){
                setAnimation(mHolder.projectLinearLayout,position);
            }
        }

        @Override
        public int getItemCount() {
            return projectsImages.length;
        }

        @Override
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
            ((Holder)holder).clearAnimation();
        }

        public void setAnimation(View animatedView, int position){
            Animation appearingAnimation = AnimationUtils.loadAnimation(context,R.anim.project_item_appearing);
            if (position > this.position){
                appearingAnimation.setStartOffset(startOffSet);
                appearingAnimation.setInterpolator(new DecelerateInterpolator());
                animatedView.setAnimation(appearingAnimation);
                animatedView.animate();
            }
        }
    }

}
