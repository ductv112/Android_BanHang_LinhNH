package app.dft.appbanhang.chitiethanghoa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import app.dft.appbanhang.giamgia.FragmentDangGiamGia;
import app.dft.appbanhang.giamgia.FragmentSapGiamGia;

/**
 * Created by ductv on 5/10/2017.
 */

public class PagerAdapterImage extends FragmentStatePagerAdapter {
    List<String> list;

    public PagerAdapterImage(FragmentManager fm, List<String> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {

//        switch (position) {
////            case 0:
////                FragmentDangGiamGia tab1 = new FragmentDangGiamGia(productTypeId, trademarkId);
////                return tab1;
////            case 1:
////                FragmentSapGiamGia tab2 = new FragmentSapGiamGia(productTypeId, trademarkId);
////                return tab2;
////            case 2:
////                FragmentGiamGiaDangXem tab3 = new FragmentGiamGiaDangXem();
////                return tab3;
//            default:
//                return null;
//        }
        FragmentShowImage tab = new FragmentShowImage(list.get(position));
        return tab;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}