package kr.wes.talbum;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.GridView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kr.wes.talbum.ui.ContainingMutipleParentsOfImageActivity;
import kr.wes.talbum.ui.DynamicColumnGridView;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RotateScreenBehaviorTest {
    @Rule
    public ActivityTestRule<ContainingMutipleParentsOfImageActivity> activityRule = new ActivityTestRule<>(
            ContainingMutipleParentsOfImageActivity.class);

    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation
                = context.getResources().getConfiguration().orientation;

        Activity activity = activityRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Test
    public void rotateScreenGriColumnUpperWidthTest() {
        onView(withId(R.id.containingMutipleParentsOfImageGridView)).check(matches(withUpperWidthOfGridColumn(DynamicColumnGridView.MAX_COLUMN_SIZE)));

        rotateScreen();

        onView(withId(R.id.containingMutipleParentsOfImageGridView)).check(matches(withUpperWidthOfGridColumn(DynamicColumnGridView.MAX_COLUMN_SIZE)));
    }

    public static Matcher<View> withUpperWidthOfGridColumn(final int width) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Maximem width of grid column should be " + width);
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean matchesSafely(View view) {
                return ((GridView) view).getColumnWidth() <= DynamicColumnGridView.MAX_COLUMN_SIZE;
            }
        };
    }
}