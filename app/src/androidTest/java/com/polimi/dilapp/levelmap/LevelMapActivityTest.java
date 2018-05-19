package com.polimi.dilapp.levelmap;

import android.support.test.rule.ActivityTestRule;

import com.polimi.dilapp.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * To run this test the kite animation in the background should be commented
 */
public class LevelMapActivityTest {
    @Rule
    public ActivityTestRule<LevelMapActivity> levelMapRule = new ActivityTestRule<>(LevelMapActivity.class);

    @Test
    public void isDisplayedTest() {
    onView(withId(R.id.expandableListView)).check(matches(isDisplayed()));
        onView(withText("OGGETTI E COLORI")).check(matches(isDisplayed()));
        onView(withText("LOGICA")).check(matches(isDisplayed()));
        onView(withText("LETTERE E NUMERI")).check(matches(isDisplayed()));
        onView(withText("OGGETTI E COLORI")).perform(click());
        onView(withText("NOMI")).check(matches(isDisplayed()));
        onView(withText("COLORI")).check(matches(isDisplayed()));
        onView(withText("FORME")).check(matches(isDisplayed()));
        onView(withText("LETTERE E NUMERI")).perform(click());
        onView(withText("NUMERI")).check(matches(isDisplayed()));
        onView(withText("ALFABETO")).check(matches(isDisplayed()));
        onView(withText("PAROLE")).check(matches(isDisplayed()));
        onView(withText("LOGICA")).perform(scrollTo(),click());
        onView(withText("CONTIAMO INSIEME")).check(matches(isDisplayed()));
        onView(withText("CUCINA CON NOSCO")).check(matches(isDisplayed()));
    }
}