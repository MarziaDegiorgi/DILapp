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
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void slideshowTest(){
        if(mainRule.getActivity().slideshow){
            //slideshow is displayed
            ViewInteraction firstSlideImage = onView(
                    allOf(withId(R.id.icon_slide),
                            childAtPosition(
                                    withParent(withId(R.id.slide_view_pager)),
                                    0),
                            isDisplayed()));
            firstSlideImage.check(matches(isDisplayed()));

            ViewInteraction textView = onView(
                    allOf(withText("•"),
                            childAtPosition(
                                    allOf(withId(R.id.dots),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                    1)),
                                    0),
                            isDisplayed()));
            textView.check(matches(isDisplayed()));

            ViewInteraction viewPager = onView(
                    allOf(withId(R.id.slide_view_pager),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    0),
                            isDisplayed()));
            viewPager.perform(swipeLeft());
            viewPager.perform(swipeLeft());
            viewPager.perform(swipeLeft());

            onView(withText("FINE")).check(matches(isDisplayed()));

        }else{
            //create account activity is displayed
            onView(withId(R.id.welcomeImage)).check(matches(isDisplayed()));
            onView(withId(R.id.listOfAccounts)).check(matches(isDisplayed()));
        }
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