package kr.wes.tablum;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kr.wes.talbum.ui.ImageContainersActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by wes on 16. 3. 15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ImageContainersScreenTest {
    @Rule
    public ActivityTestRule<ImageContainersActivity> imageContainersActivityActivityTestRule =
            new ActivityTestRule<>(ImageContainersActivity.class);

    @Test
    public void testImageContainersDisplayInUi() {
        onView(withText("Image_9")).check(ViewAssertions.matches(isDisplayed()));
    }
}
