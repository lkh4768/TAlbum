package kr.wes.talbum.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import kr.wes.talbum.R;

/**
 * Created by wes on 16. 3. 7.
 */

public class ParentOfImageContainerAdapter extends ArrayAdapter<Integer> {

    public ParentOfImageContainerAdapter(Context context, int resource, List<Integer> item) {
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

        imageContainerImageView.setImageResource(getItem(position));

        return view;
    }
}
