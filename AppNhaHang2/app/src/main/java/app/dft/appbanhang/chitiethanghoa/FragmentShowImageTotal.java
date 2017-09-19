package app.dft.appbanhang.chitiethanghoa;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.giamgia.PagerAdapter;
import app.dft.appbanhang.util.StaticVariable;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentShowImageTotal extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgBack, imgSearch, imgCart;
    TextView txtCartNum;

    public static ImageView imgDiemThuong;

    CirclePageIndicator indicator;
    ViewPager viewPager;
    PagerAdapterImage adapter;
    List<String> list;

    public FragmentShowImageTotal() {
        // Required empty public constructor
    }


    public FragmentShowImageTotal(List<String> list) {
        this.list = list;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_image_total, container, false);
        myActivity = getActivity();

        imgBack = (ImageView) v.findViewById(R.id.imgBack);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        indicator = (CirclePageIndicator) v.findViewById(R.id.indicator);

        viewPager = (ViewPager) v.findViewById(R.id.pager);

        imgDiemThuong.setOnClickListener(this);
        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));

        imgBack.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

        adapter = new PagerAdapterImage
                (getChildFragmentManager(), list);

        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

//        for (int i = 0; i <tabLayout.getTabCount() ; i++) {
//            tabLayout.getTabAt(i).setIcon(list.get(i));
//        }

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                myActivity.onBackPressed();
                break;
            case R.id.imgSearch:
                ((MainActivity) myActivity).loadFragment("search");
                break;
            case R.id.imgCart:
                ((MainActivity) myActivity).loadFragment("cart");
                break;
            case R.id.imgDiemThuong:
                if (StaticVariable.user != null && StaticVariable.user.get_id() != null) {
                    ((MainActivity) myActivity).loadFragment("nhandienthuong");
                } else {
                    Intent intent = new Intent(myActivity.getBaseContext(), SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("from", "main");
                    startActivity(intent);
                    myActivity.finish();
                }
                break;
        }
    }
}
