package app.dft.appbanhang.banhang;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import app.dft.appbanhang.R;
import app.dft.appbanhang.object.NhomHangHoaObj;
import app.dft.appbanhang.object.ThuongHieuObj;

/**
 * Created by Inuyasha on 23/05/2017.
 */

public class AdapterNhomHangHoa extends BaseAdapter {

    Activity activity;
    List<NhomHangHoaObj> list;

    public AdapterNhomHangHoa(Activity act, List<NhomHangHoaObj> list) {
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
            v = activity.getLayoutInflater().inflate(R.layout.adapter_nhom_hanghoa, null);


        TextView itemNhomHangHoa = (TextView) v.findViewById(R.id.itemNhomHangHoa);

        NhomHangHoaObj obj = list.get(i);

        itemNhomHangHoa.setText(obj.getName());

        return v;
    }
}
