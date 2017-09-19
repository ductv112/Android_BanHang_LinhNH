package app.dft.appbanhang.chitiethanghoa;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.util.DFTFormat;
import app.dft.appbanhang.util.StaticVariable;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentShowImage extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    PhotoView photoView;
    String link;
    LinearLayout.LayoutParams layoutParams;
    int imgWidth, imgHeight;


    public FragmentShowImage() {
        // Required empty public constructor
    }


    public FragmentShowImage(String link) {
        this.link = link;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_image, container, false);
        myActivity = getActivity();
        photoView = (PhotoView) v.findViewById(R.id.photoView);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        myActivity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        imgWidth = width * 9 / 10;
        imgHeight = imgWidth;

        layoutParams = new LinearLayout.LayoutParams(
                imgWidth, imgHeight);

        if (DFTFormat.UrlImage(link) != null) {
            Picasso.with(myActivity).load(DFTFormat.UrlImage(link)).into(photoView);
        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
