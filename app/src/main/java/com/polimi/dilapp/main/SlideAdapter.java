package com.polimi.dilapp.main;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.polimi.dilapp.R;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context){
        this.context = context;
    }


    //Arrays
    public int[] slide_images = {
            R.raw.intro_gif,
            R.raw.nfc_gif,
            R.raw.repo_gif,
            R.raw.share_gif
    };

    public String[] slide_texts= {
            "SLIDE 1",
            "SLIDE 2",
            "SLIDE 3",
            "SLIDE 4"
    };


    @Override
    public int getCount() {
        return slide_texts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout)object;
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
        ((ViewPager) container).removeView((View) object);
    }

}

