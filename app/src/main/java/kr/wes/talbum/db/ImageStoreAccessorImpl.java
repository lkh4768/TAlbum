package kr.wes.talbum.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import kr.wes.talbum.model.Bucket;
import kr.wes.talbum.model.Image;

/**
 * Created by wes on 16. 3. 9.
 */
public class ImageStoreAccessorImpl implements ImageStoreAccessor {
    private static ImageStoreAccessor imageStoreAccessor = null;
    private static Uri URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private ContentResolver cr;
    private String TAG = "ImageStoreAccessor_CUSTOM_TAG";

    private ImageStoreAccessorImpl(Activity activity) {
        cr = activity.getContentResolver();
    }

    public static ImageStoreAccessor getInstance(Activity activity) {
        if (imageStoreAccessor == null) {
            imageStoreAccessor = new ImageStoreAccessorImpl(activity);
        }
        return imageStoreAccessor;
    }

    @Override
    public ArrayList<Image> getAllImages() {
        String[] projection = {MediaStore.Images.ImageColumns.BUCKET_ID, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATE_MODIFIED, MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA};

        Cursor c = cr.query(URI, projection, null, null, null);

        ArrayList<Image> images = new ArrayList<>();
        Image image = null;
        Bucket bucket = null;

        if (c.moveToFirst()) {
            do {
                bucket = new Bucket(c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_ID)), c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)));

                image = new Image(bucket, c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns.DATE_MODIFIED)), c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns._ID)), c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));

                images.add(image);
            } while (c.moveToNext());
        }

        return images;
    }
}
