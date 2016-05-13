package com.example.ramon.fridgandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.TextView;

import com.example.ramon.fridgandroid.ui.GroceryActivity;
import com.example.ramon.fridgandroid.ui.MainActivity;
import com.example.ramon.fridgandroid.ui.PantryListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Ramon on 4/22/16.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest {
    MainActivity activity;
    private Context context;


    @Before
    public void setup() {
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void validateTextViewContent() {
        TextView mainActivityTextView = (TextView) activity.findViewById(R.id.mainActivityTextView);
        assertTrue("Fridg".equals(mainActivityTextView.getText().toString()));
    }

    @Test
    public void pantryActivityStarted() {
        activity.findViewById(R.id.pantryButton).performClick();
        Intent expectedIntent = new Intent(activity, PantryListActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void everythingActivityStarted() {
        activity.findViewById(R.id.everythingButton).performClick();
        Intent expectedIntent = new Intent(activity, EverythingActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
    @Test
    public void groceryActivityStarted() {
        activity.findViewById(R.id.groceryButton).performClick();
        Intent expectedIntent = new Intent(activity, GroceryActivity.class);
        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
}
