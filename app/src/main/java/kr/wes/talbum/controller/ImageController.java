package kr.wes.talbum.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import kr.wes.talbum.model.Image;

/**
 * Created by wes on 16. 3. 22.
 */
public class ImageController {
    private static String TAG = "ImageController_CUSTOM_TAG";

    public Bitmap inquiryImage(Image image, int imageSize) {
        Log.i(TAG, "inquiry Image start");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Log.i(TAG, image.getPath());
        BitmapFactory.decodeFile(image.getPath().trim(), options);

        options.inSampleSize = calculateInSampleSize(options, imageSize);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(image.getPath().trim(), options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqSize) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqSize || width > reqSize) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqSize
                    && (halfWidth / inSampleSize) > reqSize) {
                inSampleSize *= 2;
            }


            long totalPixels = width * height / inSampleSize;

            final long totalReqPixelsCap = reqSize * reqSize * 2;

            while (totalPixels > totalReqPixelsCap) {
                inSampleSize *= 2;
                totalPixels /= 2;
            }
        }
        return inSampleSize;
    }
}
