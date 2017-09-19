package app.dft.appbanhang.giamgia;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.dft.appbanhang.R;
import app.dft.appbanhang.object.LoaiHangHoaObj;

/**
 * Created by Inuyasha on 23/05/2017.
 */

public class AdapterLocLoai extends BaseAdapter {

    Activity activity;
    List<LoaiHangHoaObj> list;

    public AdapterLocLoai(Activity act, List<LoaiHangHoaObj> list) {
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
            v = activity.getLayoutInflater().inflate(R.layout.adapter_loc_loai_item, null);

        TextView itemLocLoai = (TextView) v.findViewById(R.id.itemLocLoai);

        LoaiHangHoaObj obj = list.get(i);

        itemLocLoai.setText(obj.getName());

        return v;
    }
}
