package kr.wes.talbum.test;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import kr.wes.talbum.ui.ImageContainersActivity;
import kr.wes.talbum.util.Matchers;
import kr.wes.talbum.util.OrientationChangeAction;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

/**
 * Created by wes on 16. 3. 9.
 */
public class OrientationChangeActionTest {
    @Rule
    public ActivityTestRule<ImageContainersActivity> activityRule = new ActivityTestRule<>(
            ImageContainersActivity.class);

    @Test
    public void testScreenOrientationLandscape() {
        onView(isRoot()).perform(OrientationChangeAction.orientationLandscape()).check(matches(Matchers.isScreenOrientationLandscape()));
    }

    @Test
    public void testScreenOrientationPortrait() {
        onView(isRoot()).perform(OrientationChangeAction.orientationPortrait()).check(matches(Matchers.isScreenOrientationPortrait()));
    }
}
