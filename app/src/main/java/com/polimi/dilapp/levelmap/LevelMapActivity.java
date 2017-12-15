package com.polimi.dilapp.levelmap;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.polimi.dilapp.R;


public class LevelMapActivity extends AppCompatActivity implements ILevelMap.View {

   ILevelMap.Presenter presenter;
   ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_levelmap_view);
        listView = findViewById(R.id.expandableListView);

        // Set up the presenter
        presenter = new LevelMapPresenter(this);
        presenter.initData();

        startAnimation();
    }

    private void startAnimation() {

        ImageView kiteAnimation = findViewById(R.id.kite_animation);
        kiteAnimation.setVisibility(View.VISIBLE);
        Animation extraAnimation = AnimationUtils.loadAnimation(LevelMapActivity.this, R.anim.move);
        kiteAnimation.setImageDrawable(getResources().getDrawable(R.drawable.kite));
        kiteAnimation.setAnimation(extraAnimation);
        kiteAnimation.startAnimation(extraAnimation);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showAdapter(CustomExpandableListAdapter adapter) {
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int mainCategoryPosition,
                                        int subcategoryPosition, long id) {
                presenter.onItemSelected(parent, mainCategoryPosition,subcategoryPosition);
                return false;
            }
        });
    }

    public void goBack(View view) {
        AlphaAnimation buttonClick = new AlphaAnimation(1F,0.8F);
        view.startAnimation(buttonClick);
        presenter.onClickBack();
    }
}
