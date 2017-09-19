package app.dft.appbanhang.chamsockhachhang;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.dft.appbanhang.R;
import app.dft.appbanhang.object.ChamSocKhachHangObj;
import app.dft.appbanhang.object.DatHangObj;
import app.dft.appbanhang.util.DFTFormat;

/**
 * Created by Inuyasha on 19/05/2017.
 */

public class AdapterChamSocKhachHang extends BaseAdapter {

    Activity activity;
    List<ChamSocKhachHangObj> list;


    public AdapterChamSocKhachHang(Activity activity, List<ChamSocKhachHangObj> list) {
        this.activity = activity;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (view == null)
            v = activity.getLayoutInflater().inflate(R.layout.adapter_chamsoc_khachhang, null);

        TextView txtCauHoi = (TextView) v.findViewById(R.id.txtCauHoi);
        TextView txtTraLoi = (TextView) v.findViewById(R.id.txtTraLoi);


        ChamSocKhachHangObj obj = list.get(i);

        if (obj != null) {
            if (obj.getAsk() != null) {
                txtCauHoi.setText(obj.getAsk() + "?");
            }
            if (obj.getAnswer() != null) {
                txtTraLoi.setText(obj.getAnswer());
            }

        }


        return v;
    }
}
