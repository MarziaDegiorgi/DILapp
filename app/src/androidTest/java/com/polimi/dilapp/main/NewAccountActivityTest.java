package com.polimi.dilapp.main;

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
public class NewAccountActivityTest {
    @Rule
    public ActivityTestRule<NewAccountActivity> newAccountRule = new ActivityTestRule<>(NewAccountActivity.class);

    @Test
    public void inputInsertionSuccessTest(){
        onView(withId(R.id.avatar)).check(matches(isDisplayed()));
        onView(withId(R.id.edit_birth)).check(matches(withText("")));
        onView(withId(R.id.edit_name)).check(matches(withText("")));

        onView(withId(R.id.edit_name)).perform(typeText("Marzia")).check(matches(withText("Marzia")));
        onView(withId(R.id.edit_birth)).perform(typeText("11/11/1111")).check(matches(withText("11/11/1900")));
        pressBack();

        onView(withId(R.id.form_button)).perform(click());
        onView(withId(R.id.createAccount)).check(matches(withText(R.string.select_account)));
    }

    @Test
    public void inputMissingFieldsFailureTest(){
        onView(withId(R.id.form_button)).perform(click());

        onView(withText(R.string.fields_missing)).check(matches(isDisplayed()));
        onView(withId(R.id.close)).check(matches(isDisplayed()));
    }

    @Test
    public void inputMissingBirthFailureTest(){
        onView(withId(R.id.edit_name)).perform(typeText("Marzia"));
        pressBack();
        onView(withId(R.id.form_button)).perform(click());

        onView(withText(R.string.birth_missing)).check(matches(isDisplayed()));
        onView(withId(R.id.close)).check(matches(isDisplayed()));
    }

    @Test
    public void inputMissingNameFailureTest(){
        onView(withId(R.id.edit_birth)).perform(typeText("13/05/1993"));
        pressBack();
        onView(withId(R.id.form_button)).perform(click());

        onView(withText(R.string.name_missing)).check(matches(isDisplayed()));
        onView(withId(R.id.close)).check(matches(isDisplayed()));
    }
}