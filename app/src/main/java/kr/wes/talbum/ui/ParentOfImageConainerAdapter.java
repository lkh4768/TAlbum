package kr.wes.talbum.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import kr.wes.talbum.R;

/**
 * Created by wes on 16. 3. 7.
 */
public class ParentOfImageConainerAdapter extends ArrayAdapter<Integer> {
    private ArrayList<Integer> items;
    private Context context;

    public ParentOfImageConainerAdapter(Context context, int resource, ArrayList<Integer> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.parent_of_image_container_item, null);
        }

        ImageView imageContainerImageView = (ImageView) view.findViewById(R.id.parentOfImageContainerImageView);

        imageContainerImageView.setImageResource(items.get(position));

        return view;
    }
}
