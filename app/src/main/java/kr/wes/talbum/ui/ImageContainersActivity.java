package kr.wes.talbum.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.wes.talbum.R;

public class ImageContainersActivity extends AppCompatActivity {
    private DynamicColumnGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_containing_mutiple_parents_of_image);

        gridView = (DynamicColumnGridView) findViewById(R.id.containingMutipleParentsOfImageGridView);

        setUpGridViewAdapter();
    }

    private void setUpGridViewAdapter() {
        ParentOfImageContainerAdapter parentOfImageContainerAdapter = new ParentOfImageContainerAdapter(this, R.layout.parent_of_image_container_item, makeItems());

        gridView.setAdapter(parentOfImageContainerAdapter);
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
}
