package app.dft.appbanhang.giamgia;

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

public class AdapterLoc extends BaseAdapter {

    Activity activity;
    List<String> list;

    public AdapterLoc(Activity act, List<String> list) {
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
            v = activity.getLayoutInflater().inflate(R.layout.adapter_loc_item, null);


        TextView itemLoc = (TextView) v.findViewById(R.id.itemLoc);

        itemLoc.setText(list.get(i).toString());

        return v;
    }
}
