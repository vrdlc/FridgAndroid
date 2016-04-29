package com.example.ramon.fridgandroid;

import android.support.test.rule.ActivityTestRule;

import com.example.ramon.fridgandroid.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.regex.Pattern.matches;

/**
 * Created by Ramon on 4/22/16.
 */
public class MainActivityInstrumentationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateEditText() {
        onView(withId(R.id.nameEditText)).perform(typeText("fast")).check(matches(withText("fast")));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.quantityEditText)).perform(typeText("slow")).check(matches(withText("slow")));
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.notesEditText)).perform(typeText("cat"), closeSoftKeyboard()).check(matches(withText("cat")));


}
