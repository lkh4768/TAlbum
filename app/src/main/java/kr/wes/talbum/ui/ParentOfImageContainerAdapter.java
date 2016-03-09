package kr.wes.talbum.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import kr.wes.talbum.R;

/**
 * Created by wes on 16. 3. 7.
 */

public class ParentOfImageContainerAdapter extends ArrayAdapter<Map> {

    public ParentOfImageContainerAdapter(Context context, int resource, List<Map> item) {
        super(context, resource, item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = layoutInflater.inflate(R.layout.parent_of_image_container_item, null);
        }

        ImageView imageContainerImageView = (ImageView) view.findViewById(R.id.parentOfImageContainerImageView);
        TextView bucketDisplayNameTextView = (TextView) view.findViewById(R.id.bucketDisplayName);
        TextView numberOfImageInBucketTextView = (TextView) view.findViewById(R.id.numberOfImageInBucket);

        imageContainerImageView.setImageResource((int) getItem(position).get("image"));
        bucketDisplayNameTextView.setText((String) getItem(position).get("bucketDisplayName"));
        numberOfImageInBucketTextView.setText((String) getItem(position).get("numberOfImageInBucket"));

        return view;
    }
}
