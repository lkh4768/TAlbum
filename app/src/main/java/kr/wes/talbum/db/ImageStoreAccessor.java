package kr.wes.talbum.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

import kr.wes.talbum.model.Bucket;
import kr.wes.talbum.model.Image;

/**
 * Created by wes on 16. 3. 9.
 */
public class ImageStoreAccessor {
    private static ImageStoreAccessor imageStoreAccessor = null;
    private ContentResolver cr;
    private String TAG = "ImageStoreAccessor_CUSTOM_TAG";

    private ImageStoreAccessor(Activity activity) {
        cr = activity.getContentResolver();
    }

    public static ImageStoreAccessor getInstance(Activity activity) {
        if (imageStoreAccessor == null) {
            imageStoreAccessor = new ImageStoreAccessor(activity);
        }

        return imageStoreAccessor;
    }

    public ArrayList<Image> getAllImages() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.BUCKET_ID, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns._ID};

        Cursor c = cr.query(uri, projection, null, null, null);

        ArrayList<Image> images = new ArrayList<>();
        Image image = null;
        Bucket bucket = null;

        Log.d(TAG, "cursor count : " + c.getCount());
        if (c.moveToFirst()) {
            do {
                bucket = new Bucket(c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_ID)), c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)));
                image = new Image(bucket, c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN)), c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns._ID)));

                images.add(image);
            } while (c.moveToNext());
        }

        return images;
    }
}
