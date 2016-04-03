package kr.wes.talbum.controller;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.security.AccessControlContext;

import kr.wes.talbum.model.Image;

/**
 * Created by wes on 16. 3. 31.
 */
public class SampledBitmapDecoderWithPath extends SampledBitmapDecoder {
    @Override
    public Bitmap decodeSampledBitmap(String address, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(address, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(address, options);
    }
}
