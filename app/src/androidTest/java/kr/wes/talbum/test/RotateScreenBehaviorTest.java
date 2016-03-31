package kr.wes.talbum.test;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.GridView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kr.wes.talbum.ui.ImageContainersActivity;
import kr.wes.talbum.ui.DynamicColumnGridView;
import kr.wes.talbum.util.Matchers;
import kr.wes.talbum.util.OrientationChangeAction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RotateScreenBehaviorTest {
    public static String TAG = "RotateScreenBehaviorTest_CUSTOM_TAG";

    @Rule
    public ActivityTestRule<ImageContainersActivity> activityRule = new ActivityTestRule<>(
            ImageContainersActivity.class);

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Test
    public void testScreenOrientationLandscapeGriColumnUpperWidth() {
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape()).check(matches(Matchers.isScreenOrientationLandscape()));

        GridView gridView = (GridView) activityRule.getActivity().findViewById(kr.wes.talbum.R.id.imageContainersGridView);
        assertTrue(gridView.getColumnWidth() <= DynamicColumnGridView.MAX_COLUMN_SIZE);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Test
    public void testScreenOrientationPortraitGriColumnUpperWidth() {
        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait()).check(matches(Matchers.isScreenOrientationPortrait()));

        GridView gridView = (GridView) activityRule.getActivity().findViewById(kr.wes.talbum.R.id.imageContainersGridView);
        assertTrue(gridView.getColumnWidth() <= DynamicColumnGridView.MAX_COLUMN_SIZE);
    }

}