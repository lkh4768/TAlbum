package kr.wes.talbum.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.ArrayList;

import kr.wes.talbum.Injection;
import kr.wes.talbum.db.ImageStoreAccessor;
import kr.wes.talbum.model.Image;

/**
 * Created by wes on 16. 3. 26.
 */
public class ImageController {
    private ImageStoreAccessor imageStoreAccessor = null;
    private ImageFetcher imageFetcher = null;
    private Activity activity = null;

    private static String TAG = "ImageController_CUSTOM_TAG";

    public ImageController(Activity activity) {
        this.activity = activity;
        this.imageStoreAccessor = Injection.provideImageStoreAccessor(activity);
    }

    public ArrayList<Image> getAllImages() {
        return imageStoreAccessor.getAllImages();
    }

    public void fetchImageAndSetImageView(ImageView imageView, Image image, int imageSize) {
        imageFetcher = new ImageFetcher(activity.getResources());
        imageFetcher.fetchImageAndSetImageView(imageView, image, imageSize);
    }
}
