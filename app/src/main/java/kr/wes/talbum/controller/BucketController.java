package kr.wes.talbum.controller;

import android.app.Activity;
import android.support.annotation.VisibleForTesting;
import android.text.format.DateFormat;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import kr.wes.talbum.db.ImageStoreAccessor;
import kr.wes.talbum.db.ImageStoreAccessorImpl;
import kr.wes.talbum.model.Bucket;
import kr.wes.talbum.model.Image;

/**
 * Created by wes on 16. 3. 15.
 */

public class BucketController {
    private static String TAG = "BucketController_CUSTOM_TAG";

    public ArrayList<Bucket> deduplicatedBucketInImages(ArrayList<Image> images) {
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
            if (image.getBucket().getId().equals(bucket.getId()) && (Long.valueOf(image.getDate()) > Long.valueOf(latestImage.getDate())))
                latestImage = image;
        }
        return latestImage;
    }
}
