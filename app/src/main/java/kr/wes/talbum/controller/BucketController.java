package kr.wes.talbum.controller;

import java.sql.Date;
import java.util.ArrayList;

import kr.wes.talbum.model.Bucket;
import kr.wes.talbum.model.Image;

/**
 * Created by wes on 16. 3. 15.
 */
public class BucketController {
    public ArrayList<Bucket> deduplicatedBucketInImage(ArrayList<Image> images) {
        ArrayList<Bucket> buckets = new ArrayList<>();
        boolean isDeduplicaed = false;

        for (Image image : images) {
            for (Bucket bucket : buckets) {
                if (image.getBucket().equals(bucket)) isDeduplicaed = true;
            }

            if (!isDeduplicaed) buckets.add(image.getBucket());
            isDeduplicaed = false;
        }
        return buckets;
    }

    public int getNumberOfImagesInBucket(ArrayList<Image> images, Bucket bucket) {
        int count = 0;

        for (Image image : images) {
            if (image.getBucket().equals(bucket)) count++;
        }
        return count;
    }

    public Image getLatestImageInBucket(ArrayList<Image> images, Bucket bucket) {
        Image latestImage = images.get(0);
        for (Image image : images) {
            if (image.getBucket().equals(bucket) && Date.valueOf(image.getDate()).after(Date.valueOf(latestImage.getDate())))
                latestImage = image;
        }
        return latestImage;
    }

}
