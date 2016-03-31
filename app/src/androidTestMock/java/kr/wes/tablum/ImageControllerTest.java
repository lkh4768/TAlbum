package kr.wes.tablum;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.test.rule.ActivityTestRule;
import android.widget.GridView;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import kr.wes.talbum.R;
import kr.wes.talbum.controller.ImageCache;
import kr.wes.talbum.controller.ImageController;
import kr.wes.talbum.controller.SampledBitmapDecoder;
import kr.wes.talbum.db.FakeImageStoreAccessorImpl;
import kr.wes.talbum.model.Bucket;
import kr.wes.talbum.model.Image;
import kr.wes.talbum.ui.ImageContainersActivity;
import kr.wes.talbum.util.SampledBitmapDecoderWithAssets;

import static org.junit.Assert.assertTrue;

/**
 * Created by wes on 16. 3. 31.
 */
public class ImageControllerTest {
    private static String TAG = "ImageControllerTest_CUSTOM_TAG";
    private static String BUCKET_NAME = "fake bucket";

    private static Bucket bucket = new Bucket("bucket_id_1", BUCKET_NAME);
    private static Image image = new Image(bucket, "1561548100", "image_id_1", "sample_image.png");
    @Rule
    public ActivityTestRule<ImageContainersActivity> imageContainersActivityActivityTestRule =
            new ActivityTestRule<>(ImageContainersActivity.class);

    @Before
    public void setUpFakeImageAndIntent() {
        SampledBitmapDecoderWithAssets.activity = imageContainersActivityActivityTestRule.getActivity();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Test
    public void testAddImageInMemoryCache() {
        Activity activity = imageContainersActivityActivityTestRule.getActivity();
        ImageView imageView = (ImageView) activity.findViewById(R.id.imageContainerRepresentativeImage);
        GridView gridView = (GridView) activity.findViewById(R.id.imageContainersGridView);

        ImageController imageController = new ImageController(imageContainersActivityActivityTestRule.getActivity());
        imageController.fetchImage(imageView, image, gridView.getColumnWidth());
        assertTrue(ImageCache.getInstance().hasBitmapFromMemCache(image.getId()));
    }
}
