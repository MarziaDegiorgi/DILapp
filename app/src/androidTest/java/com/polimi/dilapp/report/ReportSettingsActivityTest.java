package com.polimi.dilapp.report;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Checkable;


import com.polimi.dilapp.R;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.core.IsNot.not;

/**
 * To run this test just one account should be present
 */
@RunWith(AndroidJUnit4.class)
public class ReportSettingsActivityTest {

    @Rule
    public ActivityTestRule<ReportSettingsActivity> settingsRule = new ActivityTestRule<>(ReportSettingsActivity.class);

    @Test
    public void notEnabledSwitchDisplayTest(){

        onView(withText("Impostazioni")).check(matches(isDisplayed()));
        onView(withId(R.id.switch1)).check(matches(isDisplayed()));
        onView(withId(R.id.info)).check(matches(isDisplayed()));

        onView(withId(R.id.edit_box)).check(matches(is(not(isDisplayed()))));
    }

    @Test
    public void insertWrongEmailTest(){
        onView(withId(R.id.switch1)).perform(setChecked(true));

        onView(withText("Inserisci qui la tua email:")).check(matches(isDisplayed()));
        onView(withId(R.id.confirm_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_box)).check(matches(isDisplayed()));

        onView(withId(R.id.edit_box)).perform(typeText("abcdef"));
        onView(withId(R.id.confirm_btn)).perform(click());
        //Verify Toast Visualization
        onView(withText("Indirizzo email non valido"))
                .inRoot(withDecorView(not(is(settingsRule.getActivity().getWindow().getDecorView()))))
                        .check(matches(isDisplayed()));

        onView(withId(R.id.edit_box)).perform(typeText(""));
        onView(withId(R.id.switch1)).perform(setChecked(false));
    }

    @Test
    public void insertCorrectEmailTest(){
        onView(withId(R.id.switch1)).perform(setChecked(true));

        onView(withText("Inserisci qui la tua email:")).check(matches(isDisplayed()));
        onView(withId(R.id.confirm_btn)).check(matches(withText(R.string.confirm_email)));
        onView(withId(R.id.edit_box)).check(matches(isDisplayed()));

        onView(withId(R.id.edit_box)).perform(typeText("marzia@email.it"));
        onView(withId(R.id.confirm_btn)).perform(click());

        onView(withId(R.id.edit_box)).check(matches(withText("marzia@email.it")));
        onView(withId(R.id.confirm_btn)).check(matches(withText(R.string.modify_email)));

        onView(withId(R.id.confirm_btn)).perform(click());
        onView(withId(R.id.edit_box)).perform(typeText(""));
        onView(withId(R.id.switch1)).perform(setChecked(false));
    }

    public static ViewAction setChecked(final boolean checked){
        return new ViewAction() {
            @Override
            public BaseMatcher<View> getConstraints() {
                return new BaseMatcher<View>() {
                    @Override
                    public boolean matches(Object item) {
                        return isA(Checkable.class).matches(item);
                    }

                    @Override
                    public void describeTo(Description description) {}
                };
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                Checkable checkableView = (Checkable) view;
                checkableView.setChecked(checked);
            }
        };
    }
}