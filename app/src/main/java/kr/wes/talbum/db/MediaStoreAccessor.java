package kr.wes.talbum.db;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by wes on 16. 3. 9.
 */
public class MediaStoreAccessor {
    private static MediaStoreAccessor mediaStoreAccessor = null;
    private ContentResolver cr;
    private String TAG = "MediaStoreAccessor_CUSTOM_TAG";

    private MediaStoreAccessor(Activity activity) {
        cr = activity.getContentResolver();
    }

    public static MediaStoreAccessor getMediaStoreAccessorInstance(Activity activity) {
        if (mediaStoreAccessor == null) {

            mediaStoreAccessor = new MediaStoreAccessor(activity);

            return mediaStoreAccessor;
        }

        return null;
    }

    public ArrayList<String> getBucketDisplayNameInMediaStore() {
        ArrayList<String> bucketInfoList = new ArrayList<>();

        String[] projection = {MediaStore.Images.ImageColumns.BUCKET_ID, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns.DATA};
        String groupBy = "1) GROUP BY 1, (2";
        String sortOrder = "MAX(" + MediaStore.Images.ImageColumns.DATE_TAKEN + ") ASC";
        Cursor c = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, groupBy, null, sortOrder);
        Log.d(TAG, "cursor count" + c.getCount());
        if (c.moveToFirst()) {
            do {
                bucketInfoList.add(c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)));
            } while (c.moveToNext());
        }
        return bucketInfoList;
    }
}
