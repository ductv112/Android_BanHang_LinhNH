package app.dft.appbanhang.giohang;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.dft.appbanhang.R;
import app.dft.appbanhang.object.DonHangObj;
import app.dft.appbanhang.object.GioHangObj;
import app.dft.appbanhang.util.DFTFormat;

/**
 * Created by Inuyasha on 19/05/2017.
 */

public class AdapterChiTietDatHang extends BaseAdapter {

    Activity activity;
    List<DonHangObj> list;
    LinearLayout.LayoutParams layoutParams;

    int mWidth;
    int mHeight;

    public AdapterChiTietDatHang(Activity activity, List<DonHangObj> list) {
        this.activity = activity;
        this.list = list;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        mWidth = width / 4;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (view == null)
            v = activity.getLayoutInflater().inflate(R.layout.adapter_chitiet_dathang, null);

        ImageView imgAnhHangHoa = (ImageView) v.findViewById(R.id.imgAnhHangHoa);
        TextView txtTenHH = (TextView) v.findViewById(R.id.txtTenHH);
        TextView txtGia = (TextView) v.findViewById(R.id.txtGia);
        TextView txtThanhTien = (TextView) v.findViewById(R.id.txtThanhTien);
        TextView txtTiGia = (TextView) v.findViewById(R.id.txtTiGia);

        TextView txtDiem = (TextView) v.findViewById(R.id.txtDiem);

        TextView txtSoLuong = (TextView) v.findViewById(R.id.txtSoLuong);

        imgAnhHangHoa.setLayoutParams(layoutParams);
        DonHangObj obj = list.get(i);

        if (obj != null) {

            if (DFTFormat.UrlImage(obj.getProductId().getImage()) != null) {
                Picasso.with(activity).load(DFTFormat.UrlImage(obj.getProductId().getImage())).resize(mWidth, mHeight).into(imgAnhHangHoa);
            } else {
                Picasso.with(activity).load(R.mipmap.ic_wos).into(imgAnhHangHoa);
            }

            if (obj.getProductId().getName() != null) {
                txtTenHH.setText(obj.getProductId().getName());
            }
            txtTiGia.setText("1 Điểm = " + DFTFormat.NumberFormat(String.valueOf(obj.getRateCoin())) + " VNĐ");

            txtGia.setText(DFTFormat.NumberFormat(String.valueOf(obj.getPrice())) + " VNĐ");
            txtSoLuong.setText(String.valueOf(obj.getTotal()));
            txtDiem.setText(String.valueOf(obj.getCoin()));

            Long thanhTien = 0L;
            thanhTien = Long.valueOf(obj.getPrice() * obj.getTotal());
            thanhTien = thanhTien - obj.getCoin() * obj.getRateCoin();
            txtThanhTien.setText("Thành tiền: " + DFTFormat.NumberFormat(String.valueOf(thanhTien)) + " VNĐ");
        }

        return v;
    }
}
