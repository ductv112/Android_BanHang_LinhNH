package app.dft.appbanhang.noibat;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.dft.appbanhang.R;
import app.dft.appbanhang.object.HangHoaObj;
import app.dft.appbanhang.util.DFTFormat;

/**
 * Created by Inuyasha on 19/05/2017.
 */

public class AdapterNoiBat extends BaseAdapter {

    Activity activity;
    List<HangHoaObj> list;
    LinearLayout.LayoutParams layoutParams;

    int mWidth;
    int mHeight;

    public AdapterNoiBat(Activity activity, List<HangHoaObj> list) {
        this.activity = activity;
        this.list = list;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        mWidth = width / 3;
        mHeight = mWidth;
        layoutParams = new LinearLayout.LayoutParams(
                mWidth, mHeight);
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
            v = activity.getLayoutInflater().inflate(R.layout.adapter_noibat, null);

        ImageView imgAnhHangHoa = (ImageView) v.findViewById(R.id.imgAnhHangHoa);
        TextView txtTenSamPham = (TextView) v.findViewById(R.id.txtTenSamPham);
        TextView txtChiTietSP = (TextView) v.findViewById(R.id.txtChiTietSP);
        TextView txtGiaSanPham = (TextView) v.findViewById(R.id.txtGiaSanPham);
        TextView txtTiGia = (TextView) v.findViewById(R.id.txtTiGia);
        imgAnhHangHoa.setLayoutParams(layoutParams);

        HangHoaObj obj = list.get(i);

        if (obj != null) {

            if (DFTFormat.UrlImage(obj.getImage()) != null) {
                Picasso.with(activity).load(DFTFormat.UrlImage(obj.getImage())).resize(mWidth, mWidth).into(imgAnhHangHoa);
            } else {
                Picasso.with(activity).load(R.mipmap.ic_wos).into(imgAnhHangHoa);
            }

            if (obj.getName() != null) {
                txtTenSamPham.setText(obj.getName());
            }

            if (obj.getDescriptionShort() != null) {
                txtChiTietSP.setText(obj.getDescriptionShort());
            } else {
                txtChiTietSP.setText("");
            }

            txtGiaSanPham.setText(DFTFormat.NumberFormat(String.valueOf(obj.getPrice())) + " VNĐ");
            txtTiGia.setText("1 Điểm = " + DFTFormat.NumberFormat(String.valueOf(obj.getRateCoin2())) + " VNĐ");
        }

        return v;
    }
}
