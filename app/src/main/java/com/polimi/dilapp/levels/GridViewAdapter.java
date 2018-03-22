package com.polimi.dilapp.levels;


import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.view.ActivityTwoOne;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private List<Integer> images_id;
    private Context context;

    public GridViewAdapter(Context context, int firstId){
        this.context = context;
        images_id = new ArrayList<>();
        images_id.add(firstId);
    }

    @Override
    public int getCount() {
        return images_id.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    // Convert DP to PX
    // Source: http://stackoverflow.com/a/8490361
    public int dpToPx(int dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            ImageView gridView;
            ImageView lastImage; //last object to ask to the user

            // Want the width/height of the items
            // to be 120dp
            int wPixel = dpToPx(120);
            int hPixel = dpToPx(160);

            if (convertView == null) {
                // If convertView is null then inflate the appropriate layout file
                convertView = LayoutInflater.from(context).inflate(R.layout.multiple_item_view, null);
            }

            gridView = (ImageView) convertView.findViewById(R.id.imageGridView);

            // Set height and width constraints for the image view
            //gridView.setLayoutParams(new LinearLayout.LayoutParams(wPixel, hPixel));

            // Set the content of the image based on the provided URI
            int imageId = this.images_id.get(position);

            gridView.setVisibility(View.VISIBLE);
            gridView.setImageDrawable(context.getResources().getDrawable(imageId));

            //get last image in the gridView and set the animation effect

            Animation animationWait = AnimationUtils.loadAnimation(context, R.anim.blink);
            gridView.setAnimation(animationWait);
            gridView.startAnimation(animationWait);

            // Image should be cropped towards the center
            gridView.setAdjustViewBounds(true);
            gridView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            // Set Padding for images
            gridView.setPadding(0, 0, 1, 0);

            return convertView;
    }

    public void addImageResource(int imageResource){
        this.images_id.add(imageResource);
    }

    public void clearImageResources() {
        this.images_id.clear();
    }

}
