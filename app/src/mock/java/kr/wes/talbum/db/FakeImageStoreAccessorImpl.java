package kr.wes.talbum.db;

import android.app.Activity;
import android.support.annotation.VisibleForTesting;

import java.util.ArrayList;

import kr.wes.talbum.db.ImageStoreAccessor;
import kr.wes.talbum.model.Image;

/**
 * Created by wes on 16. 3. 20.
 */
public class FakeImageStoreAccessorImpl implements ImageStoreAccessor {
    private static ArrayList<Image> FAKE_IMAGES = new ArrayList<>();

    @Override
    public ArrayList<Image> getAllImages() {
        return FAKE_IMAGES;
    }

    @VisibleForTesting
    public static int getNumberOfImages(){
        return FAKE_IMAGES.size();
    }

    @VisibleForTesting
    public static void addImages(Image... images) {
        for (Image image : images) {
            FAKE_IMAGES.add(image);
        }
    }
}
