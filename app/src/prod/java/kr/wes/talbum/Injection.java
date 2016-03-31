package kr.wes.talbum;

import android.app.Activity;

import kr.wes.talbum.controller.SampledBitmapDecoder;
import kr.wes.talbum.controller.SampledBitmapDecoderWithPath;
import kr.wes.talbum.db.ImageStoreAccessor;
import kr.wes.talbum.db.ImageStoreAccessorImpl;

/**
 * Created by wes on 16. 3. 31.
 */
public class Injection {
    public static ImageStoreAccessor provideImageStoreAccessor(Activity activity) {
        return ImageStoreAccessorImpl.getInstance(activity);
    }

    public static SampledBitmapDecoder provideSampleBitmapDecoder() {
        return new SampledBitmapDecoderWithPath();
    }
}