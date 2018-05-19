package com.polimi.dilapp.report;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.polimi.dilapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * To run this test just one account should be present
 */
@RunWith(AndroidJUnit4.class)
public class ReportSpecActivityTest {

    @Rule
    public ActivityTestRule<ReportSpecActivity> reportSpecRule = new ActivityTestRule<>(ReportSpecActivity.class);

    @Test
    public void mainActivityIsDisplayedTest(){

        onView(withId(R.id.spec_button)).check(matches(is(not(isDisplayed()))));
        onView(withId(R.id.menuButton)).check(matches(isDisplayed()));
        onView(withId(R.id.menuButton)).check(matches(isClickable()));

        onView(withId(R.id.name)).check(matches(isDisplayed()));
        onView(withId(R.id.profile_image)).check(matches(isDisplayed()));
        onView(withId(R.id.title_graph)).check(matches(isDisplayed()));
        onView(withId(R.id.graph)).check(matches(isDisplayed()));

        onView(withId(R.id.title_graph)).check(matches(withText("Errori:")));
    }

    @Test
    public void onMenuClickedTest(){

        onView(withId(R.id.menuButton)).perform(click());
        onView(withText("Impostazioni")).check(matches(isDisplayed()));

        //TODO: Add click on Impostazioni che attualmente crasha
    }

}