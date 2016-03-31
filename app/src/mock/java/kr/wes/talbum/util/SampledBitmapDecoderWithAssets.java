package kr.wes.talbum.util;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.VisibleForTesting;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.AccessibleObject;

import kr.wes.talbum.controller.ImageCache;
import kr.wes.talbum.controller.SampledBitmapDecoder;

/**
 * Created by wes on 16. 3. 31.
 */
public class SampledBitmapDecoderWithAssets extends SampledBitmapDecoder {
    public static Activity activity = null;

    @Override
    public Bitmap decodeSampledBitmap(String address, int reqWidth, int reqHeight) {
        ImageCache imageCache = ImageCache.getInstance();
        AssetManager assetManager = activity.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(istr);
    }
}
