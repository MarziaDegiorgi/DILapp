package com.polimi.dilapp.startgame;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.polimi.dilapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * To make this test work disable manually the animation
 * commenting "start animation()" on StartGameActivity onCreate()
 */
@RunWith(AndroidJUnit4.class)
public class StartGameActivityTest {
    @Rule
    public ActivityTestRule<StartGameActivity> startGameRule = new ActivityTestRule<>(StartGameActivity.class);

    private StartGameActivity startGameActivity;

    @Before
    public void setUp() {
        //not working
     startGameActivity = startGameRule.getActivity();
     startGameActivity.runOnUiThread(new Runnable() {
         @Override
         public void run() {
            startGameActivity.clearAnimations();
         }
     });
    }

    @Test
    public void isDisplayed() throws Exception{

        onView(withId(R.id.playButton)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.menuButton)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.carrot)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.apple)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.pear)).check(matches(ViewMatchers.isDisplayed()));

        if(startGameRule.getActivity().mNfcAdapter == null || !startGameRule.getActivity().mNfcAdapter.isEnabled()){

            onView(withId(R.id.playButton)).perform(click());
            onView(withId(R.id.close)).inRoot(isDialog()).check(matches(ViewMatchers.isDisplayed()));
            onView(withText(R.string.enable_nfc)).inRoot(isDialog()).check(matches(ViewMatchers.isDisplayed()));
            onView(withId(R.id.close)).inRoot(isDialog()).check(matches(withText(R.string.enable)));
        }else{
            selectMenuItem();
        }
    }

    private void selectMenuItem(){
        onView(withId(R.id.menuButton)).perform(click());
        onView(withText(R.string.report)).check(matches(ViewMatchers.isDisplayed()));
        onView(withText(R.string.change_player)).check(matches(ViewMatchers.isDisplayed()));
        onView(withText(R.string.change_level)).check(matches(ViewMatchers.isDisplayed()));
    }
}