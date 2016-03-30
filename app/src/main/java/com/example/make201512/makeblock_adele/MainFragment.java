package com.example.make201512.makeblock_adele;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kogitune.activity_transition.ActivityTransitionLauncher;

import java.io.InputStream;

/**
 * Created by make201512 on 2016/3/29.
 */
public class MainFragment extends Fragment {

    private GridView projectsGridView;

    private int[] projectsImagesId = new int[]{
            R.drawable.capture_a,R.drawable.capture_b,R.drawable.scanner,R.drawable.camera_dolly,
            R.drawable.catapult_ram,R.drawable.color_bar,R.drawable.detecting_robot,R.drawable.mars_explorer,
            R.drawable.mbot,R.drawable.racing_car,R.drawable.robotic_ant,R.drawable.robotic_arm_tank,
            R.drawable.robotic_bartender,R.drawable.rolling_tank,R.drawable.self_balancing_robot,R.drawable.starter};

    private Bitmap[] projectsImages = new Bitmap[Variable.getProjectsNum()];

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

        projectsGridView = (GridView) rootView.findViewById(R.id.projects_grid_view);

        projectsGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Variable.isScreenChanged = true;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        GridViewAdapter adapter = new GridViewAdapter(getActivity(),projectsImages);

        projectsGridView.setAdapter(adapter);

        projectsGridView.setOnItemClickListener(new GridViewListener());

        return rootView;
    }

    private class GridViewListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            startActivity(new Intent(getActivity(),ProjectActivity.class));

            final Intent intent = new Intent(getActivity(),ProjectActivity.class);

            intent.putExtra("projectIndex",position);

            ActivityTransitionLauncher.with(getActivity()).from(view).launch(intent);

        }
    }

    private static class GridViewAdapter extends BaseAdapter{

        Context context;
        Bitmap[] projectsImages;

        private String[] projectsNames = new String[]{
                "3D-Capture-A","3D-Capture-B","3D-Scanner","Camera-Dolly",
                "Catapult-Ram","Color-Bar","Detecting-Robot","Mars-Explorer",
                "mBot","Racing-Car","Robotic-Ant","Robotic-Arm-Tank",
                "Robotic-Bartender","Rolling-Tank","Self-Balancing-Robot","Starter"
        };

        public GridViewAdapter(Context context,Bitmap[] projectsImages){
            this.context = context;
            this.projectsImages = projectsImages;
        }

        @Override
        public int getCount() {
            return Variable.getProjectsNum();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            GridViewHolder viewHolder;

            if (null == convertView){
                viewHolder = new GridViewHolder();
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.project_item,parent,false);

                viewHolder.projectImage = (ImageView) convertView.findViewById(R.id.project_image);
                viewHolder.projectName = (TextView) convertView.findViewById(R.id.project_name);

                convertView.setTag(viewHolder);
            }else {
                viewHolder = (GridViewHolder) convertView.getTag();
            }

            viewHolder.projectImage.setImageBitmap(projectsImages[position]);
            viewHolder.projectName.setText(projectsNames[position]);

            return convertView;
        }

        static class GridViewHolder {
            ImageView projectImage;
            TextView projectName;
        }
    }
}
