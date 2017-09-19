package app.dft.appbanhang.home;

import android.app.Activity;
import android.graphics.Paint;
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

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.object.HangHoaObj;
import app.dft.appbanhang.object.HomeObj;
import app.dft.appbanhang.util.DFTFormat;

/**
 * Created by Inuyasha on 19/05/2017.
 */

public class AdapterHome extends BaseAdapter {

    Activity activity;
    List<HomeObj> list;
    int imgGiamGiaWidth;
    int imgGiamGiaHeight;

    LinearLayout.LayoutParams layoutParams;

    public AdapterHome(Activity activity, List<HomeObj> list) {
        this.activity = activity;
        this.list = list;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        int imgAD1Width = width;
        int imgAD1Height = imgAD1Width / 2;

        imgGiamGiaWidth = width * 9 / 10;
        imgGiamGiaHeight = imgGiamGiaWidth;
        layoutParams = new LinearLayout.LayoutParams(
                imgGiamGiaWidth, imgGiamGiaHeight);

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
            v = activity.getLayoutInflater().inflate(R.layout.adapter_home, null);

        ImageView imgGiamGia;
        RelativeLayout layoutXemGiamGia;
        TextView txtTenGiamGia, txtGiaMoi, txtGiaCu, txtTitle, txtXemToanBo;


        imgGiamGia = (ImageView) v.findViewById(R.id.imgGiamGia);
        txtTenGiamGia = (TextView) v.findViewById(R.id.txtTenGiamGia);
        txtGiaMoi = (TextView) v.findViewById(R.id.txtGiaMoi);
        txtGiaCu = (TextView) v.findViewById(R.id.txtGiaCu);
        layoutXemGiamGia = (RelativeLayout) v.findViewById(R.id.layoutXemGiamGia);
        txtTitle = (TextView) v.findViewById(R.id.txtTitle);
        txtXemToanBo = (TextView) v.findViewById(R.id.txtXemToanBo);

        imgGiamGia.setLayoutParams(layoutParams);


        final HomeObj obj = list.get(i);

        if (obj != null) {

            if (obj.getTitle() != null) {
                txtTitle.setText(obj.getTitle());
            }

            if (obj.getProductFirst() != null && DFTFormat.UrlImage(obj.getProductFirst().getImage()) != null) {
                Picasso.with(activity).load(DFTFormat.UrlImage(obj.getProductFirst().getImage())).resize(imgGiamGiaWidth, imgGiamGiaHeight).into(imgGiamGia);
            } else {
                Picasso.with(activity).load(R.mipmap.ic_wos).resize(imgGiamGiaWidth, imgGiamGiaHeight).into(imgGiamGia);
            }

            if (obj.getProductFirst() != null && obj.getProductFirst().getName() != null) {
                txtTenGiamGia.setText(obj.getProductFirst().getName());
            }

            if (obj.getProductFirst() != null) {
                txtGiaCu.setText(DFTFormat.NumberFormat(String.valueOf(obj.getProductFirst().getPrice())) + " VNĐ");
            }

            if (obj.getProductFirst() != null && obj.getProductFirst().getPriceNew() != null) {
                txtGiaMoi.setText(DFTFormat.NumberFormat(String.valueOf(obj.getProductFirst().getPriceNew())) + " VNĐ   ");
                txtGiaCu.setPaintFlags(txtGiaCu.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

            if (obj.getDescribe() != null) {
                txtXemToanBo.setText(obj.getDescribe());
            }
        }

        layoutXemGiamGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (obj.getTypeAdvertise() == 1 && obj.getType() == 3) {
                    if (obj.getFilter().getPromotion().equalsIgnoreCase("1") && obj.getFilter().getProductHot().equalsIgnoreCase("0") && ((obj.getFilter().getProductTypeId() != null && obj.getFilter().getProductTypeId().size() == 0) || obj.getFilter().getProductTypeId() == null) && ((obj.getFilter().getTrademarkId() != null && obj.getFilter().getTrademarkId().size() == 0) || obj.getFilter().getTrademarkId() == null)) {
                        ((MainActivity) activity).loadFragment("giamgia");
                    } else if (obj.getFilter().getPromotion().equalsIgnoreCase("0") && obj.getFilter().getProductHot().equalsIgnoreCase("1") && ((obj.getFilter().getProductTypeId() != null && obj.getFilter().getProductTypeId().size() == 0) || obj.getFilter().getProductTypeId() == null) && ((obj.getFilter().getTrademarkId() != null && obj.getFilter().getTrademarkId().size() == 0) || obj.getFilter().getTrademarkId() == null)) {
                        ((MainActivity) activity).loadFragment("noibat");
                    } else {
                        ((MainActivity) activity).loadFragment("grid_hanghoa_2colum", obj.getFilter());
                    }
                } else {
                    ((MainActivity) activity).loadFragment("grid_hanghoa_2colum", obj.getFilter());
                }
            }
        });
        imgGiamGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).loadFragment("chitiet_hanghoa", obj.getProductFirst().get_id());
            }
        });
        txtTenGiamGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).loadFragment("chitiet_hanghoa", obj.getProductFirst().get_id());
            }
        });

        return v;
    }
}
