package kr.wes.talbum.controller;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by wes on 16. 3. 23.
 */
public class ImageCache {
    private LruCache<String, Bitmap> memoryCache;
    private static ImageCache imageCache = null;

    private ImageCache() {
        initLruCache();
    }

    public static ImageCache getInstance() {
        if (imageCache == null) {
            imageCache = new ImageCache();
        }
        return imageCache;
    }

    public void initLruCache() {
        final int maxMemoryOfApp = getMaxMemoryOfApp();
        int cacheSize = getCacheSize(maxMemoryOfApp);

        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    private int getMaxMemoryOfApp() {
        return (int) (Runtime.getRuntime().maxMemory() / 1024);
    }

    private int getCacheSize(int maxMemoryOfApp) {
        return maxMemoryOfApp / 4;
    }

    public boolean hasBitmapFromMemCache(String key) {
        return (memoryCache.get(key) != null);
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String imageURL) {
        Bitmap bitmap = memoryCache.get(imageURL);

        return bitmap;
    }
}
