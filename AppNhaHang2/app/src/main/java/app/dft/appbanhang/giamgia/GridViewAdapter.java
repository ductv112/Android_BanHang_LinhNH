package app.dft.appbanhang.giamgia;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Gravity;
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
import app.dft.appbanhang.object.HangHoaObj;
import app.dft.appbanhang.util.DFTFormat;

/**
 * Created by ductv on 5/10/2017.
 */

public class GridViewAdapter extends BaseAdapter {
    Activity mActivity;
    List<HangHoaObj> list;
    RelativeLayout.LayoutParams layoutParams, layoutParams2;

    int mWidth;
    int mHeight;

    public GridViewAdapter(Activity mActivity, List<HangHoaObj> list) {
        this.mActivity = mActivity;
        this.list = list;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.mActivity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        mWidth = width * 4 / 8;
        mHeight = mWidth;
        layoutParams = new RelativeLayout.LayoutParams(
                mWidth, mHeight);
        layoutParams2 = new RelativeLayout.LayoutParams(
                mWidth, mHeight + mHeight / 2);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null)
            v = mActivity.getLayoutInflater().inflate(R.layout.layout_gridview_item_hang_giam_gia, null);

        ImageView imgHinhAnh = (ImageView) v.findViewById(R.id.imgHinhAnh);
        TextView txtTenHH = (TextView) v.findViewById(R.id.txtTenHH);
        TextView txtGia = (TextView) v.findViewById(R.id.txtGia);
        TextView txtTiGia = (TextView)v.findViewById(R.id.txtTiGia);
        imgHinhAnh.setLayoutParams(layoutParams);
        RelativeLayout layoutHangHoa = (RelativeLayout) v.findViewById(R.id.layoutHangHoa);
        layoutHangHoa.setLayoutParams(layoutParams2);

        HangHoaObj obj = list.get(position);
        if (obj != null) {
            if (obj.getName() != null) {
                txtTenHH.setText(obj.getName());
            }
            txtGia.setText(DFTFormat.NumberFormat(String.valueOf(obj.getPrice())) + " VNĐ");
            txtTiGia.setText("1 Điểm = " + DFTFormat.NumberFormat(String.valueOf(obj.getRateCoin2())) + " VNĐ");
            if (obj.getImage() != null) {
                Picasso.with(mActivity).load(DFTFormat.UrlImage(obj.getImage())).resize(mWidth, mHeight).into(imgHinhAnh);
            }

        }


        return v;
    }
}