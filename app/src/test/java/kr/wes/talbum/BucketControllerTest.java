package kr.wes.talbum;

import org.junit.Test;

import java.util.ArrayList;

import kr.wes.talbum.controller.BucketController;
import kr.wes.talbum.model.Bucket;
import kr.wes.talbum.model.Image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class BucketControllerTest {
    private BucketController bucketController = new BucketController();

    private Bucket bucket0 = new Bucket("bucket_id_0", "bucket_name_0");
    private Bucket bucket1 = new Bucket("bucket_id_1", "bucket_name_1");
    private ArrayList<Image> images = new ArrayList<>();

    @Test
    public void testDeduplicatedBucketInImages() {
        setupImages();
        ArrayList<Bucket> buckets = bucketController.deduplicatedBucketInImages(images);

        assertEquals(buckets.size(), 2);

        for (int i = 0; i < buckets.size(); i++) {
            Bucket bucket = buckets.get(i);
            assertTrue(bucket.getId().equals("bucket_id_" + i));
        }
    }

    @Test
    public void testGetNumberOfImagesInBucket() {
        setupImages();
        int bucket0InImagesCount = bucketController.getNumberOfImagesInBucket(images, bucket0);
        assertEquals(bucket0InImagesCount, 2);
    }

    @Test
    public void testGetLatestImageInBucket() {
        setupImages();
        Image image = bucketController.getLatestImageInBucket(images, bucket0);

        assertEquals(image.getDate(), images.get(0).getDate());
    }

    public void setupImages() {
        images.add(new Image(bucket0, "150240000", "image_id_0", "file:///android_asset/sample_image.png"));
        images.add(new Image(bucket0, "150210000", "image_id_1", "file:///android_asset/sample_image.png"));
        images.add(new Image(bucket1, "151652000", "image_id_2", "file:///android_asset/sample_image.png"));
    }
}