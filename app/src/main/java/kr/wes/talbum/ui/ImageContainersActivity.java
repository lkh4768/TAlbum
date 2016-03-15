package kr.wes.talbum.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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
import kr.wes.talbum.model.Bucket;
import kr.wes.talbum.model.Image;

public class ImageContainersActivity extends AppCompatActivity {
    private DynamicColumnGridView gridView;
    private BucketController bucketController;
    private ArrayList<Image> images;
    private final int MY_PERMISSION_REQUEST_STORAGE = 100;
    private static String TAG = "ImageContainersActivity_CUSTOM_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_containers);

        gridView = (DynamicColumnGridView) findViewById(R.id.ImageContainersGridView);

        bucketController = new BucketController(this);
        checkPermission();
        setUpGridViewAdapter();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST_STORAGE);

            } else {
                Log.e(TAG, "permission deny");
                images = bucketController.getAllImages();
            }
        }
    }

    private void setUpGridViewAdapter() {
        ArrayList<Bucket> buckets = bucketController.deduplicatedBucketInImage(images);
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this, R.layout.item_image_containers, buckets);

        gridView.setAdapter(gridViewAdapter);
    }

    private class GridViewAdapter extends ArrayAdapter<Bucket> {

        public GridViewAdapter(Context context, int resource, List<Bucket> item) {
            super(context, resource, item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (view == null) {
                view = layoutInflater.inflate(R.layout.item_image_containers, null);
            }

            ImageView imageContainerImageView = (ImageView) view.findViewById(R.id.imageContainerRepresentativeImage);
            TextView bucketNameTextView = (TextView) view.findViewById(R.id.bucketName);
            TextView numberOfImageInBucketTextView = (TextView) view.findViewById(R.id.numberOfImageInBucket);

            bucketNameTextView.setText(getItem(position).getName());
            numberOfImageInBucketTextView.setText(String.valueOf(bucketController.getNumberOfImagesInBucket(images, getItem(position))));

            return view;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    images = bucketController.getAllImages();

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    Log.d(TAG, "Permission always deny");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }
}
