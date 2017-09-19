package app.dft.appbanhang.chitiethanghoa;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.dft.appbanhang.R;

/**
 * Created by Inuyasha on 23/05/2017.
 */

public class AdapterSize extends BaseAdapter {

    Activity activity;
    List<String> list;

    public AdapterSize(Activity act, List<String> list) {
        this.activity = act;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (view == null)
            v = activity.getLayoutInflater().inflate(R.layout.adapter_size_item, null);


        TextView itemSize = (TextView) v.findViewById(R.id.itemSize);

        itemSize.setText(list.get(i).toString());

        return v;
    }
}
