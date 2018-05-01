package com.polimi.dilapp.levels.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.polimi.dilapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class ActivityTwoOneTest {

    @Rule
    public ActivityTestRule<ActivityTwoOne> rule = new ActivityTestRule<>(ActivityTwoOne.class);

    @Test
    public void startVideoIntro(){
      onView(withId(R.id.video_box)).check(matches(isDisplayed()));

    }

}