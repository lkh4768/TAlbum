package kr.wes.talbum.controller;

import android.app.Activity;

import java.sql.Date;
import java.util.ArrayList;

import kr.wes.talbum.db.ImageStoreAccessor;
import kr.wes.talbum.db.ImageStoreAccessorImpl;
import kr.wes.talbum.model.Bucket;
import kr.wes.talbum.model.Image;

/**
 * Created by wes on 16. 3. 15.
 */

//TODO refactoring
public class BucketController {
    private ImageStoreAccessor imageStoreAccessor = null;

    public BucketController(Activity activity) {
        this.imageStoreAccessor = ImageStoreAccessorFactory.getImageStoreAccessor("imageStoreAccessorImpl", activity);
    }

    public ArrayList<Image> getAllImages() {
        return imageStoreAccessor.getAllImages();
    }

    public ArrayList<Bucket> deduplicatedBucketInImage(ArrayList<Image> images) {
        ArrayList<Bucket> buckets = new ArrayList<>();
        boolean isDeduplicaed = false;

        for (Image image : images) {
            for (Bucket bucket : buckets) {
                if (image.getBucket().getId().equals(bucket.getId())) isDeduplicaed = true;
            }

            if (!isDeduplicaed) buckets.add(image.getBucket());
            isDeduplicaed = false;
        }
        return buckets;
    }

    public int getNumberOfImagesInBucket(ArrayList<Image> images, Bucket bucket) {
        int count = 0;

        for (Image image : images) {
            if (image.getBucket().getId().equals(bucket.getId())) count++;
        }
        return count;
    }

    public Image getLatestImageInBucket(ArrayList<Image> images, Bucket bucket) {
        Image latestImage = images.get(0);
        for (Image image : images) {
            if (image.getBucket().getId().equals(bucket.getId()) && Date.valueOf(image.getDate()).after(Date.valueOf(latestImage.getDate())))
                latestImage = image;
        }
        return latestImage;
    }
}
