package kr.wes.talbum.db;

import java.util.ArrayList;

import kr.wes.talbum.model.Image;

/**
 * Created by wes on 16. 3. 20.
 */
public interface ImageStoreAccessor {
    ArrayList<Image> getAllImages();
}
