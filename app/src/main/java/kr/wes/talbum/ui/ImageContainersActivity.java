package kr.wes.talbum.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.wes.talbum.R;

public class ImageContainersActivity extends AppCompatActivity {
    private DynamicColumnGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_containers);

        gridView = (DynamicColumnGridView) findViewById(R.id.containingMutipleParentsOfImageGridView);

        setUpGridViewAdapter();
    }

    private void setUpGridViewAdapter() {
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this, R.layout.item_image_containers, makeItems());

        gridView.setAdapter(gridViewAdapter);
    }

    private ArrayList<Map> makeItems() {
        ArrayList<Map> items = new ArrayList<>();
        Map item = null;

        for (int i = 0; i < 10; i++) {
            item = new HashMap();

            if (i % 3 == 0) {
                item.put("image", R.drawable.image1);
                item.put("numberOfImageInBucket", "1");
            } else if (i % 3 == 1) {
                item.put("image", R.drawable.image2);
                item.put("numberOfImageInBucket", "2");
            } else {
                item.put("image", R.drawable.image3);
                item.put("numberOfImageInBucket", "3");
            }

            item.put("bucketDisplayName", "dd");

            items.add(item);
        }

        return items;
    }

    private class GridViewAdapter extends ArrayAdapter<Map> {

        public GridViewAdapter(Context context, int resource, List<Map> item) {
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
            TextView bucketDisplayNameTextView = (TextView) view.findViewById(R.id.bucketName);
            TextView numberOfImageInBucketTextView = (TextView) view.findViewById(R.id.numberOfImageInBucket);

            imageContainerImageView.setImageResource((int) getItem(position).get("image"));
            bucketDisplayNameTextView.setText((String) getItem(position).get("bucketDisplayName"));
            numberOfImageInBucketTextView.setText((String) getItem(position).get("numberOfImageInBucket"));

            return view;
        }
    }
}
