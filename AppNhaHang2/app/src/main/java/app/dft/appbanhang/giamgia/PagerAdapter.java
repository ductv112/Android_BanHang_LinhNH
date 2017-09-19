package app.dft.appbanhang.giamgia;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.dft.appbanhang.util.FragmentDefault;

/**
 * Created by ductv on 5/10/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    String productTypeId, trademarkId;

    public PagerAdapter(FragmentManager fm, int NumOfTabs, String productTypeId, String trademarkId) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.productTypeId = productTypeId;
        this.trademarkId = trademarkId;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentDangGiamGia tab1 = new FragmentDangGiamGia(productTypeId, trademarkId);
                return tab1;
            case 1:
                FragmentSapGiamGia tab2 = new FragmentSapGiamGia(productTypeId, trademarkId);
                return tab2;
//            case 2:
//                FragmentGiamGiaDangXem tab3 = new FragmentGiamGiaDangXem();
//                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}