package com.polimi.dilapp.main;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.polimi.dilapp.R;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context){
        this.context = context;
    }


    //Arrays
    private int[] slide_images = {
            R.raw.intro_gif,
            R.raw.nfc_gif,
            R.raw.repo_gif,
            R.raw.share_gif
    };

    private int[] slide_texts= {
            R.string.slide1,
            R.string.slide2,
            R.string.slide3,
            R.string.slide4
    };


    @Override
    public int getCount() {
        return slide_texts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon_slide);
        TextView textView = view.findViewById(R.id.text_slide);


        imageView.setImageResource(slide_images[position]);
        textView.setText(slide_texts[position]);
        container.addView(view);

        return view;
    };

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}

