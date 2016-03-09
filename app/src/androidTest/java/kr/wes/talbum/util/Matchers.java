package kr.wes.talbum.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import kr.wes.talbum.ui.DynamicColumnGridView;

/**
 * Created by wes on 16. 3. 9.
 */
public class Matchers {
    public static String TAG = "Matchers_CUSTOM_TAG";

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

    public static Matcher<View> isScreenOrientationLandscape() {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("is screen orientation landscape");
            }

            @Override
            public boolean matchesSafely(View view) {
                Activity activity = (Activity) view.getContext();
                int orientation
                        = activity.getRequestedOrientation();
                return (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        };
    }

    public static Matcher<View> isScreenOrientationPortrait() {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("is screen orientation landscape");
            }

            @Override
            public boolean matchesSafely(View view) {
                Activity activity = (Activity) view.getContext();
                int orientation
                        = activity.getRequestedOrientation();
                return orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            }
        };
    }
}
