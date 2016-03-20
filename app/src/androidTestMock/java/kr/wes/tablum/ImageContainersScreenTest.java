package kr.wes.tablum;

import android.content.Intent;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kr.wes.talbum.controller.ImageStoreAccessorFactory;
import kr.wes.talbum.db.FakeImageStoreAcessorImpl;
import kr.wes.talbum.db.ImageStoreAccessor;
import kr.wes.talbum.model.Bucket;
import kr.wes.talbum.model.Image;
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
    private static String BUCKET_NAME = "fake bucket";

    private static Bucket bucket = new Bucket("bucket_id_1", BUCKET_NAME);
    private static Image image = new Image(bucket, "2016-03-19", "image_id_1");
    @Rule
    public ActivityTestRule<ImageContainersActivity> imageContainersActivityActivityTestRule =
            new ActivityTestRule<>(ImageContainersActivity.class);

    @Before
    public void stubbedImage() {
        FakeImageStoreAcessorImpl.addImages(image);
    }

    @Test
    public void testImageContainersDisplayInUi() {
        //TODO remove dependence data and insert fake data
        onView(withText(BUCKET_NAME)).check(ViewAssertions.matches(isDisplayed()));
    }
}
