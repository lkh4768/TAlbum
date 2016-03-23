package kr.wes.talbum.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.wes.talbum.R;
import kr.wes.talbum.controller.BucketController;
import kr.wes.talbum.controller.ImageController;
import kr.wes.talbum.controller.PermissionController;
import kr.wes.talbum.model.Bucket;
import kr.wes.talbum.model.Image;

public class ImageContainersActivity extends AppCompatActivity {
    private View mainLayout;
    private DynamicColumnGridView gridView;
    private ArrayList<Image> images;

    private BucketController bucketController;
    private PermissionController permissionController;
    private ImageController imageController;

    private static String TAG = "ImageContainersActivity_CUSTOM_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_containers);

        mainLayout = findViewById(R.id.imageContainersMainLayout);
        gridView = (DynamicColumnGridView) findViewById(R.id.imageContainersGridView);

        bucketController = new BucketController(this);
        permissionController = new PermissionController(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE});
        imageController = new ImageController(this.getResources());
        imageController.setLoadingImage(R.drawable.empty_photo);

        if (permissionController.isGrantedExternalStoragePermissions()) {
            getAllImagesAndSetupGridView();
        } else
            permissionController.requestExternalStoragePermissions();
    }

    private void getAllImagesAndSetupGridView() {
        getAllImages();
        setUpGridView();
    }

    private void getAllImages() {
        Log.i(TAG, "permission success!");
        images = bucketController.getAllImages();
    }

    private void setUpGridView() {
        ArrayList<Bucket> buckets = bucketController.deduplicatedBucketInImage(images);
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this, R.layout.item_image_containers, buckets);

        gridView.setAdapter(gridViewAdapter);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == PermissionController.REQUEST_EXTERNAL_STORAGE) {
            Log.i(TAG, "Received response for external storage permissions request.");

            if (permissionController.verifyPermissions(grantResults)) {
                Snackbar.make(mainLayout, R.string.permision_available_external_storage,
                        Snackbar.LENGTH_SHORT)
                        .show();

                getAllImagesAndSetupGridView();
            } else {
                Log.i(TAG, "External storage permissions were NOT granted.");
                Snackbar.make(mainLayout, R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private class GridViewAdapter extends ArrayAdapter<Bucket> {

        public GridViewAdapter(Context context, int resource, List<Bucket> item) {
            super(context, resource, item);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ImageView imageContainerImageView;
            TextView bucketNameTextView;
            TextView numberOfImageInBucketTextView;

            if (view == null) {
                view = layoutInflater.inflate(R.layout.item_image_containers, null);

                imageContainerImageView = (ImageView) view.findViewById(R.id.imageContainerRepresentativeImage);
                bucketNameTextView = (TextView) view.findViewById(R.id.bucketName);
                numberOfImageInBucketTextView = (TextView) view.findViewById(R.id.numberOfImageInBucket);
                view.setTag(new ViewHolder(imageContainerImageView, bucketNameTextView, numberOfImageInBucketTextView));
            } else {
                ViewHolder viewHolder = (ViewHolder) view.getTag();
                imageContainerImageView = viewHolder.imageContainerImageView;
                bucketNameTextView = viewHolder.bucketNameTextView;
                numberOfImageInBucketTextView = viewHolder.numberOfImageInBucketTextView;
            }

            imageContainerImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            bucketNameTextView.setText(getItem(position).getName());
            numberOfImageInBucketTextView.setText(String.valueOf(bucketController.getNumberOfImagesInBucket(images, getItem(position))));

            Image image = bucketController.getLatestImageInBucket(images, getItem(position));

            imageController.fetchImage(imageContainerImageView, image, gridView.getColumnWidth());
            return view;
        }
    }

    private static class ViewHolder {
        public ImageView imageContainerImageView;
        public TextView bucketNameTextView;
        public TextView numberOfImageInBucketTextView;

        public ViewHolder(ImageView imageContainerImageView, TextView bucketNameTextView, TextView numberOfImageInBucketTextView) {
            this.imageContainerImageView = imageContainerImageView;
            this.bucketNameTextView = bucketNameTextView;
            this.numberOfImageInBucketTextView = numberOfImageInBucketTextView;
        }
    }
}
