package kr.wes.tablum;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.GridView;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kr.wes.talbum.R;
import kr.wes.talbum.db.FakeImageStoreAccessorImpl;
import kr.wes.talbum.model.Bucket;
import kr.wes.talbum.model.Image;
import kr.wes.talbum.ui.ImageContainersActivity;
import kr.wes.talbum.util.Matchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

/**
 * Created by wes on 16. 3. 15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ImageContainersScreenTest {
    private static String TAG = "ImageContainersScreenTest_CUSTOM_TAG";
    private static String BUCKET_NAME = "fake bucket";

    private static Bucket bucket = new Bucket("bucket_id_1", BUCKET_NAME);
    private static Image image = new Image(bucket, "1561548100", "image_id_1", "file:///android_asset/sample_image.png");
    @Rule
    public ActivityTestRule<ImageContainersActivity> imageContainersActivityActivityTestRule =
            new ActivityTestRule<>(ImageContainersActivity.class, true, false);

    @Before
    public void setUpFakeImageAndIntent() {
        if (FakeImageStoreAccessorImpl.getNumberOfImages() == 0)
            FakeImageStoreAccessorImpl.addImages(image);

        Intent startIntent = new Intent();
        imageContainersActivityActivityTestRule.launchActivity(startIntent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Test
    public void testImageSize() {
        Activity activity = imageContainersActivityActivityTestRule.getActivity();
        ImageView imageView = (ImageView) activity.findViewById(R.id.imageContainerRepresentativeImage);
        GridView gridView = (GridView) activity.findViewById(R.id.imageContainersGridView);
        Drawable drawable = imageView.getDrawable();
        assertEquals(drawable.getBounds().width(), gridView.getColumnWidth());
    }

    @Test
    public void testImageContainersDisplayInUi() {
        onView(withId(R.id.bucketName)).check(ViewAssertions.matches(withText(BUCKET_NAME)));
        onView(withId(R.id.numberOfImageInBucket)).check(ViewAssertions.matches(withText("1")));
        onView(withId(R.id.imageContainerRepresentativeImage)).check(matches(allOf(
                Matchers.hasDrawable(),
                isDisplayed())));
    }
}
