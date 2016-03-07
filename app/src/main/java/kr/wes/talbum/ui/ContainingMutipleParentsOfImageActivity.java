package kr.wes.talbum.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

import kr.wes.talbum.R;

public class ContainingMutipleParentsOfImageActivity extends AppCompatActivity {
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_containing_mutiple_parents_of_image);

        gridView = (GridView) findViewById(R.id.containingMutipleParentsOfImageGridView);

        setUpGridViewAdapter();
    }

    private void setUpGridViewAdapter() {
        ParentOfImageConainerAdapter parentOfImageConainerAdapter = new ParentOfImageConainerAdapter(this, R.layout.parent_of_image_container_item, makeItems());

        gridView.setAdapter(parentOfImageConainerAdapter);
    }

    private ArrayList<Integer> makeItems() {
        ArrayList<Integer> image = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            if (i % 3 == 0)
                image.add(R.drawable.image1);
            else if (i % 3 == 1)
                image.add(R.drawable.image2);
            else
                image.add(R.drawable.image3);
        }


        return image;
    }
}
