package com.polimi.dilapp.levels;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.polimi.dilapp.R;
import com.polimi.dilapp.levels.view.ActivityTwoOne;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private List<Integer> images_id;
    private Context context;

    public GridViewAdapter(Context context){
        this.context = context;
        images_id = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return images_id.size();
    }

    @Override
    public Object getItem(int i) {
        return images_id.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView image;
        if(view == null){
            image = new ImageView(context);
            image.setLayoutParams(new GridView.LayoutParams(160,160));
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setPadding(1,1,1,1);
        }else {
            image = (ImageView) view;
        }
        image.setImageResource(images_id.get(i));
        Animation animationWait = AnimationUtils.loadAnimation(context, R.anim.blink);
        image.setAnimation(animationWait);
        image.startAnimation(animationWait);

        return image;
    }

    public void addImageResource(int imageResource){
        this.images_id.add(imageResource);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
