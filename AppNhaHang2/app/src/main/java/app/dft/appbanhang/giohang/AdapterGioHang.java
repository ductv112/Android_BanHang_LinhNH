package app.dft.appbanhang.giohang;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.dft.appbanhang.R;
import app.dft.appbanhang.object.GioHangObj;
import app.dft.appbanhang.object.HangHoaObj;
import app.dft.appbanhang.util.DFTFormat;

/**
 * Created by Inuyasha on 19/05/2017.
 */

public class AdapterGioHang extends BaseAdapter {

    Activity activity;
    List<GioHangObj> list;
    ChangeSoLuong listenerChangeSoLuong;
    ChangeDiem listenerChangeDiem;
    XoaHH listenerXoaHH;

    LinearLayout.LayoutParams layoutParams;

    int mWidth;
    int mHeight;

    public AdapterGioHang(Activity activity, List<GioHangObj> list, ChangeSoLuong listenerChangeSoLuong, ChangeDiem listenerChangeDiem, XoaHH listenerXoaHH) {
        this.activity = activity;
        this.list = list;
        this.listenerChangeSoLuong = listenerChangeSoLuong;
        this.listenerChangeDiem = listenerChangeDiem;
        this.listenerXoaHH = listenerXoaHH;

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

    public interface ChangeSoLuong {
        void onChangeSoLuong(int position);
    }

    public interface ChangeDiem {
        void onChangeDiem(int position);
    }

    public interface XoaHH {
        void onXoaHH(int position);
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
            v = activity.getLayoutInflater().inflate(R.layout.layout_gio_hang_item, null);

        ImageView imgAnhHangHoa = (ImageView) v.findViewById(R.id.imgAnhHangHoa);
        TextView txtTenHH = (TextView) v.findViewById(R.id.txtTenHH);
        TextView txtGia = (TextView) v.findViewById(R.id.txtGia);
        TextView txtTiGia= (TextView)v.findViewById(R.id.txtTiGia);
        TextView txtThanhTien = (TextView) v.findViewById(R.id.txtThanhTien);

        LinearLayout layoutDiem = (LinearLayout) v.findViewById(R.id.layoutDiem);
        TextView txtDiem = (TextView) v.findViewById(R.id.txtDiem);

        RelativeLayout layoutSoLuong = (RelativeLayout) v.findViewById(R.id.layoutSoLuong);
        TextView txtSoLuong = (TextView) v.findViewById(R.id.txtSoLuong);

        TextView txtXoa = (TextView) v.findViewById(R.id.txtXoa);
        imgAnhHangHoa.setLayoutParams(layoutParams);

        GioHangObj obj = list.get(i);

        if (obj != null) {

            if (DFTFormat.UrlImage(obj.getImage()) != null) {
                Picasso.with(activity).load(DFTFormat.UrlImage(obj.getImage())).resize(mWidth, mHeight).into(imgAnhHangHoa);
            } else {
                Picasso.with(activity).load(R.mipmap.ic_wos).into(imgAnhHangHoa);
            }

            if (obj.getName() != null) {
                txtTenHH.setText(obj.getName());
            }

            if (obj.getThanhTien() != null) {
                txtThanhTien.setText("Thành tiền: " + DFTFormat.NumberFormat(obj.getThanhTien()) + " VNĐ");
            }

            if (obj.getPriceNew() != null) {
                txtGia.setText(DFTFormat.NumberFormat(obj.getPriceNew()) + " VNĐ");
            } else {
                txtGia.setText(DFTFormat.NumberFormat(String.valueOf(obj.getPrice())) + " VNĐ");
            }

            txtTiGia.setText("1 Điểm = " + DFTFormat.NumberFormat(obj.getRateCoin()) + " VNĐ");

            txtSoLuong.setText(obj.getTotalProductCart());
            txtDiem.setText(obj.getTotalPoinCart());
        }

        layoutSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerChangeSoLuong.onChangeSoLuong(i);
            }
        });

        layoutDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerChangeDiem.onChangeDiem(i);
            }
        });

        txtXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerXoaHH.onXoaHH(i);
            }
        });

        return v;
    }
}
