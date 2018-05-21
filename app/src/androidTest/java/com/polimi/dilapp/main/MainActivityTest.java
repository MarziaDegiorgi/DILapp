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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void slideshowTest(){
        if(mainRule.getActivity().slideshow){
            //slideshow is displayed
            ViewInteraction viewPager = onView(
                    allOf(withId(R.id.slide_view_pager),
                            childAtPosition(
                                    childAtPosition(
                                            withId(android.R.id.content),
                                            0),
                                    0),
                            isDisplayed()));

            ViewInteraction firstSlideImage = onView(
                    allOf(withId(R.id.icon_slide),
                            childAtPosition(
                                    withParent(withId(R.id.slide_view_pager)),
                                    0),
                            isDisplayed()));
            firstSlideImage.check(matches(isDisplayed()));

            ViewInteraction firstDot = onView(
                    allOf(withText("•"),
                            childAtPosition(
                                    allOf(withId(R.id.dots),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                    1)),
                                    0),
                            isDisplayed()));
            ViewInteraction secondDot = onView(
                    allOf(withText("•"),
                            childAtPosition(
                                    allOf(withId(R.id.dots),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                    1)),
                                    1),
                            isDisplayed()));
            ViewInteraction thirdDot = onView(
                    allOf(withText("•"),
                            childAtPosition(
                                    allOf(withId(R.id.dots),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                    1)),
                                    2),
                            isDisplayed()));
            ViewInteraction fourthDot = onView(
                    allOf(withText("•"),
                            childAtPosition(
                                    allOf(withId(R.id.dots),
                                            childAtPosition(
                                                    IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                    1)),
                                    3),
                            isDisplayed()));

            //first page
            firstDot.check(matches(hasTextColor(R.color.colorAccent)));
            secondDot.check(matches(not(hasTextColor(R.color.colorAccent))));
            thirdDot.check(matches(not(hasTextColor(R.color.colorAccent))));
            fourthDot.check(matches(not(hasTextColor(R.color.colorAccent))));

           onView(
                    allOf(withId(R.id.text_slide), withText(R.string.slide1),
                            childAtPosition(
                                    withParent(withId(R.id.slide_view_pager)),
                                    1),
                            isDisplayed())).check(matches(withText(R.string.slide1)));


            viewPager.perform(swipeLeft());
            //second page
            firstDot.check(matches(not(hasTextColor(R.color.colorAccent))));
            secondDot.check(matches(hasTextColor(R.color.colorAccent)));
            thirdDot.check(matches(not(hasTextColor(R.color.colorAccent))));
            fourthDot.check(matches(not(hasTextColor(R.color.colorAccent))));

            onView(
                    allOf(withId(R.id.text_slide), withText(R.string.slide2),
                            childAtPosition(
                                    withParent(withId(R.id.slide_view_pager)),
                                    1),
                            isDisplayed())).check(matches(withText(R.string.slide2)));

            viewPager.perform(swipeLeft());
            //third page
            firstDot.check(matches(not(hasTextColor(R.color.colorAccent))));
            secondDot.check(matches(not(hasTextColor(R.color.colorAccent))));
            thirdDot.check(matches(hasTextColor(R.color.colorAccent)));
            fourthDot.check(matches(not(hasTextColor(R.color.colorAccent))));

            onView(
                    allOf(withId(R.id.text_slide), withText(R.string.slide3),
                            childAtPosition(
                                    withParent(withId(R.id.slide_view_pager)),
                                    1),
                            isDisplayed())).check(matches(withText(R.string.slide3)));

            viewPager.perform(swipeLeft());
            //fourth
            firstDot.check(matches(not(hasTextColor(R.color.colorAccent))));
            secondDot.check(matches(not(hasTextColor(R.color.colorAccent))));
            thirdDot.check(matches(not(hasTextColor(R.color.colorAccent))));
            fourthDot.check(matches(hasTextColor(R.color.colorAccent)));

            onView(
                    allOf(withId(R.id.text_slide), withText(R.string.slide4),
                            childAtPosition(
                                    withParent(withId(R.id.slide_view_pager)),
                                    1),
                            isDisplayed())).check(matches(withText(R.string.slide4)));

            onView(withText("FINE")).check(matches(isDisplayed()));
            onView(withText("FINE")).perform(click());

            //create account activity is displayed
            onView(withId(R.id.icon_slide)).check(doesNotExist());
            onView(withId(R.id.dots)).check(doesNotExist());
            onView(withId(R.id.welcomeImage)).check(matches(isDisplayed()));
            onView(withId(R.id.listOfAccounts)).check(matches(isDisplayed()));

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