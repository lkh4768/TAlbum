package kr.wes.talbum.controller;

import android.app.Activity;

import kr.wes.talbum.db.ImageStoreAccessor;
import kr.wes.talbum.db.ImageStoreAccessorImpl;

/**
 * Created by wes on 16. 3. 20.
 */
public class ImageStoreAccessorFactory {
    private static ImageStoreAccessor imageStoreAccessorImpl = null;

    public static ImageStoreAccessor getImageStoreAccessor(String imageStoreAccessorName, Activity activity){
        switch (imageStoreAccessorName){
            case "imageStoreAccessorImpl" :
                if(imageStoreAccessorImpl == null){
                    imageStoreAccessorImpl = new ImageStoreAccessorImpl(activity);
                }

                return imageStoreAccessorImpl;
            default:
                return null;
        }
    }
}
