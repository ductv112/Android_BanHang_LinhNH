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
import app.dft.appbanhang.object.DatHangObj;
import app.dft.appbanhang.object.GioHangObj;
import app.dft.appbanhang.util.DFTFormat;

/**
 * Created by Inuyasha on 19/05/2017.
 */

public class AdapterDatHang extends BaseAdapter {

    Activity activity;
    List<DatHangObj> list;
    LinearLayout.LayoutParams layoutParams;

    int mWidth;
    int mHeight;


    public AdapterDatHang(Activity activity, List<DatHangObj> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (view == null)
            v = activity.getLayoutInflater().inflate(R.layout.adapter_dathang_item, null);

        ImageView imgAnhHangHoa = (ImageView) v.findViewById(R.id.imgAnhHangHoa);
        TextView txtTenHH = (TextView) v.findViewById(R.id.txtTenHH);
        TextView txtGia = (TextView) v.findViewById(R.id.txtGia);
        TextView txtTiGia = (TextView) v.findViewById(R.id.txtTiGia);

        TextView txtTrangThai = (TextView) v.findViewById(R.id.txtTrangThai);
        imgAnhHangHoa.setLayoutParams(layoutParams);

        DatHangObj obj = list.get(i);

        if (obj != null) {

            if (DFTFormat.UrlImage(obj.getTotalProduct().getProductId().getImage()) != null) {
                Picasso.with(activity).load(DFTFormat.UrlImage(obj.getTotalProduct().getProductId().getImage())).resize(mWidth, mWidth).into(imgAnhHangHoa);
            } else {
                Picasso.with(activity).load(R.mipmap.ic_wos).into(imgAnhHangHoa);
            }

            txtTenHH.setText(obj.getTotalProduct().getProductId().getName());

            txtGia.setText(DFTFormat.NumberFormat(String.valueOf(obj.getAmount())) + " VNĐ");
            txtTiGia.setText("1 Điểm = " + DFTFormat.NumberFormat(String.valueOf(obj.getTotalProduct().getRateCoin())) + " VNĐ");


            if (obj.getStatus() == 0) {
                txtTrangThai.setText("Đã hủy");
            } else if (obj.getStatus() == 1) {
                txtTrangThai.setText("Chưa xác nhận");
            } else if (obj.getStatus() == 2) {
                txtTrangThai.setText("Đã xác nhận");
            } else if (obj.getStatus() == 3) {
                txtTrangThai.setText("Đang giao hàng");
            } else if (obj.getStatus() == 4) {
                txtTrangThai.setText("Đã giao hàng");
            }
        }


        return v;
    }
}
