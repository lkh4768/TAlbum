package kr.wes.talbum.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import kr.wes.talbum.ui.DynamicColumnGridView;

/**
 * Created by wes on 16. 3. 9.
 */
public class Matchers {
    public static String TAG = "Matchers_CUSTOM_TAG";

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

    public static BoundedMatcher<View, ImageView> hasDrawable() {
        return new BoundedMatcher<View, ImageView>(ImageView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has drawable");
            }

            @Override
            public boolean matchesSafely(ImageView imageView) {
                return imageView.getDrawable() != null;
            }
        };
    }
}
