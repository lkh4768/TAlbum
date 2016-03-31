package kr.wes.talbum.controller;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import kr.wes.talbum.BuildConfig;
import kr.wes.talbum.Injection;
import kr.wes.talbum.model.Image;

/**
 * Created by wes on 16. 3. 22.
 */
public class ImageFetcher {
    private static String TAG = "ImageController_CUSTOM_TAG";
    private Resources resources;
    private Bitmap loadingBitmap;
    private ImageCache imageCache;

    public ImageFetcher(Resources resources) {
        this.resources = resources;
        imageCache = ImageCache.getInstance();
    }

    public void fetchImage(ImageView imageView, Image image, int imageSize) {
        if (imageCache.hasBitmapFromMemCache(image.getId())) {
            fetchBitmapFromMemCache(image.getId(), imageView);
        } else {
            fetchBitmapFromExternalStorage(image, imageView, imageSize);
        }
    }

    private void fetchBitmapFromMemCache(String id, ImageView imageView) {
        Bitmap bitmap = imageCache.getBitmapFromMemCache(id);
        imageView.setImageBitmap(bitmap);
    }

    private void fetchBitmapFromExternalStorage(Image image, ImageView imageView, int imageSize) {
        if (cancelPotentialWork(image, imageView)) {
            BitmapWorkerTask task = new BitmapWorkerTask(image, imageView, imageSize);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(resources, loadingBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    /**
     * Returns true if the current work has been canceled or if there was no work in
     * progress on this image view.
     * Returns false if the work in progress deals with the same data. The work is not
     * stopped in that case.
     */
    private boolean cancelPotentialWork(Image image, ImageView imageView) {
        //BEGIN_INCLUDE(cancel_potential_work)
        BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            Image imageData = bitmapWorkerTask.image;
            if (imageData == null || !imageData.equals(image)) {
                bitmapWorkerTask.cancel(true);
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "cancelPotentialWork - cancelled work for " + image);
                }
            } else {
                // The same work is already in progress.
                return false;
            }
        }
        return true;
        //END_INCLUDE(cancel_potential_work)
    }

    private BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    /**
     * A custom Drawable that will be attached to the imageView while the work is in progress.
     * Contains a reference to the actual worker task, so that it can be stopped if a new binding is
     * required, and makes sure that only the last started worker process can bind its result,
     * independently of the finish order.
     */
    private class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    class BitmapWorkerTask extends AsyncTask<Image, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private Image image = null;
        private int imageSize;

        public BitmapWorkerTask(Image image, ImageView imageView, int imageSize) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
            this.imageSize = imageSize;
            this.image = image;
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Image... params) {
            SampledBitmapDecoder sampledBitmapDecoder = Injection.provideSampleBitmapDecoder();
            Bitmap bitmap = sampledBitmapDecoder.decodeSampledBitmap(image.getPath(), imageSize, imageSize);

            if (imageCache != null) {
                imageCache.addBitmapToMemoryCache(image.getId(), bitmap);
            }
            return bitmap;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    /**
     * Set placeholder bitmap that shows when the the background thread is running.
     *
     * @param resId
     */
    public void setLoadingImage(int resId) {
        loadingBitmap = BitmapFactory.decodeResource(resources, resId);
    }
}
