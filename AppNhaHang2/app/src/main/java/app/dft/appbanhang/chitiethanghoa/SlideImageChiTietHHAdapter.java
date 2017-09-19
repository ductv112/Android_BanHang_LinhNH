package app.dft.appbanhang.chitiethanghoa;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.util.DFTFormat;

/**
 * Created by ductv on 4/17/2017.
 */

public class SlideImageChiTietHHAdapter extends PagerAdapter {

    Activity mActivity;
    LayoutInflater mLayoutInflater;
    List<String> list;
    LinearLayout.LayoutParams layoutParams;
    int imgWidth, imgHeight;

    public SlideImageChiTietHHAdapter(Activity mActivity, List<String> list) {
        this.mActivity = mActivity;
        this.list = list;
        mLayoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        imgWidth = width * 9 / 10;
        imgHeight = imgWidth;

        layoutParams = new LinearLayout.LayoutParams(
                imgWidth, imgHeight);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.adapter_slide_image_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setLayoutParams(layoutParams);

        String str = list.get(position);
        if (DFTFormat.UrlImage(str) != null) {
            Picasso.with(mActivity).load(DFTFormat.UrlImage(str)).into(imageView);
            container.addView(itemView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)mActivity).loadFragment("show_image_total", list);
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}