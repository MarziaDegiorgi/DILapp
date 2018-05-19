package com.polimi.dilapp.report;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.polimi.dilapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * To run this test just one account should be present
 */
@RunWith(AndroidJUnit4.class)
public class ReportMainActivityTest {

    @Rule
    public ActivityTestRule<ReportMainActivity> reportMainRule = new ActivityTestRule<>(ReportMainActivity.class);


    @Test
    public void mainActivityIsDisplayedTest(){

            onView(withId(R.id.spec_button)).check(matches(isDisplayed()));
            onView(withId(R.id.spec_button)).check(matches(isClickable()));
            onView(withId(R.id.menuButton)).check(matches(isDisplayed()));
            onView(withId(R.id.menuButton)).check(matches(isClickable()));

            onView(withId(R.id.name)).check(matches(isDisplayed()));
            onView(withId(R.id.profile_image)).check(matches(isDisplayed()));
            onView(withId(R.id.spec_button)).check(matches(isDisplayed()));
            onView(withId(R.id.graph)).check(matches(isDisplayed()));

            onView(withId(R.id.spec_button)).check(matches(withText("Specifiche per livello")));
            onView(withId(R.id.title_graph)).check(matches(withText("Progressi:")));

    }

    @Test
    public void onSpecButtonClickedTest(){
        onView(withId(R.id.spec_button)).perform(click()).check(doesNotExist());
        onView(withId(R.id.expandableListView)).check(matches(isDisplayed()));
        onView(withText("OGGETTI E COLORI")).check(matches(isDisplayed()));
        onView(withText("OGGETTI E COLORI")).perform(click());
        onView(withText("NOMI")).perform(click());
        onView(withText("Errori:")).check(matches(isDisplayed()));
    }

    @Test
    public void onMenuItemClicked(){
        onView(withId(R.id.menuButton)).perform(click());
        onView(withText("Impostazioni")).check(matches(isDisplayed()));
        onView(withText("Impostazioni")).perform(click());
        onView(withText("Abilita reportistica automatica")).check(matches(isDisplayed()));
    }
}