package kr.wes.talbum.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import kr.wes.talbum.controller.PermissionUtil;
import kr.wes.talbum.model.Bucket;
import kr.wes.talbum.model.Image;

public class ImageContainersActivity extends AppCompatActivity {
    private View mainLayout;
    private DynamicColumnGridView gridView;
    private BucketController bucketController;
    private ArrayList<Image> images;

    private static String TAG = "ImageContainersActivity_CUSTOM_TAG";

    private static String[] PERMISSIONS_EXTERNAL_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_containers);

        mainLayout = findViewById(R.id.imamgeContainerMainLayout);
        gridView = (DynamicColumnGridView) findViewById(R.id.ImageContainersGridView);

        bucketController = new BucketController(this);

        if (isGrantedExternalStoragePermissions()) {
            getAllImagesAndSetupGridView();
        } else
            requestExternalStoragePermissions();
    }

    public boolean isGrantedExternalStoragePermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            Log.i(TAG, "External storage permissions has NOT been granted. Requesting permissions.");
            return false;
        } else {
            Log.i(TAG,
                    "External storage permissions have already been granted. Get all images and setup GridView");
            return true;
        }
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

    private void requestExternalStoragePermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            Log.i(TAG,
                    "Displaying external storage permission rationale to provide additional context.");

            Snackbar.make(mainLayout, R.string.permission_external_storage_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(ImageContainersActivity.this, PERMISSIONS_EXTERNAL_STORAGE,
                                            REQUEST_EXTERNAL_STORAGE);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS_EXTERNAL_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            Log.i(TAG, "Received response for external storage permissions request.");

            if (PermissionUtil.verifyPermissions(grantResults)) {
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
}
