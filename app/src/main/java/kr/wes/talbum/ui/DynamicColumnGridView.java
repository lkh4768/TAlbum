package kr.wes.talbum.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by wes on 16. 3. 8.
 */

public class DynamicColumnGridView extends GridView {
    private String TAG = "DynamicColumenGridView_CUSTOM_LOG";
    public final static int MAX_COLUMN_SIZE = 360;

    public DynamicColumnGridView(Context context) {
        super(context);
    }

    public DynamicColumnGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicColumnGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DynamicColumnGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        setNumColumsWithScreenWidth(w);
    }

    private void setNumColumsWithScreenWidth(int width) {
        int numColumn;
        for (numColumn = 1; MAX_COLUMN_SIZE < (width / numColumn); numColumn++) ;
        setNumColumns(numColumn);
    }
}
