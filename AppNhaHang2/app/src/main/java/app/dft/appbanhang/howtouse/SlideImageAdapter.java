package app.dft.appbanhang.howtouse;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.dft.appbanhang.R;
import app.dft.appbanhang.util.DFTFormat;

/**
 * Created by ductv on 4/17/2017.
 */

public class SlideImageAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    List<String> list;

    public SlideImageAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.list = list;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        String str = list.get(position);
        if (DFTFormat.UrlImage(str) != null) {
            Picasso.with(mContext).load(DFTFormat.UrlImage(str)).into(imageView);
            container.addView(itemView);
        }

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}