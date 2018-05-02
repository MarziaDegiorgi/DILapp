package com.polimi.dilapp.main;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.polimi.dilapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * The list of accounts should be empty to run correctly the test
 */
@RunWith(AndroidJUnit4.class)
public class CreateAccountActivityTest {

    @Rule
    public ActivityTestRule<CreateAccountActivity> createAccountRule = new ActivityTestRule<>(CreateAccountActivity.class);

    @Test
    public void isDisplayedTest(){
        onView(withId(R.id.welcomeImage)).check(matches(isDisplayed()));
        onView(withId(R.id.createAccount)).check(matches(isDisplayed()));
    }

    @Test
    public void createAndDeleteAccountTest(){
        if(createAccountRule.getActivity().presenter.getListOfChildren().isEmpty()) {
            ViewInteraction accountButton = onView(
                    allOf(withId(R.id.avatar),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.listOfAccounts),
                                            2),
                                    0)));
            accountButton.perform(scrollTo(), click());

            onView(withId(R.id.edit_birth)).check(matches(isDisplayed()));
            onView(withId(R.id.edit_name)).check(matches(isDisplayed()));
            onView(withId(R.id.edit_birth)).check(matches(withText("")));
            onView(withId(R.id.edit_name)).check(matches(withText("")));

            onView(withId(R.id.edit_name)).perform(typeText("Justin"));
            onView(withId(R.id.edit_birth)).perform(typeText("10/01/2000"));
            onView(withId(R.id.form_button)).perform(click());

            onView(
                    allOf(withId(R.id.name), withText("Justin"),
                            childAtPosition(
                                    childAtPosition(
                                            withId(R.id.listOfAccounts),
                                            0),
                                    1),
                            isDisplayed()));

            deleteAccount();
        }
    }

    private void deleteAccount(){
            onView(
                    allOf(childAtPosition(
                            childAtPosition(
                                    withId(R.id.listOfAccounts),
                                    2),
                            0))).perform(scrollTo(), longClick());

            onView(withText("Elimina")).check(matches(isDisplayed()));
            onView(withText("Elimina")).perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}