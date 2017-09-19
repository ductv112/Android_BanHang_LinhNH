package app.dft.appbanhang.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.object.HomeObj;

/**
 * Created by ductv on 6/7/2017.
 */
public class DFTClickADV {
    public static void ClickADV(Activity myActivity, HomeObj obj) {
        try {
            if (obj != null && obj.getPosition() != 4) {
                // Position
                //1: Banner to home
                //2: Banner nho home
                //3: Banner nho dau danh sach
                //4: Danh sach home
                //5: Popup nhan thuong

                // Type
                //1: Link
                //2: Mot hang hoa
                //3: Danh sach
                if (obj.getType() == 1&& obj.getLink() != null) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(obj.getLink()));
                    myActivity.startActivity(i);
                } else if (obj.getType() == 2 && obj.getProductId() != null) {
                    ((MainActivity) myActivity).loadFragment("chitiet_hanghoa", obj.getProductId());
                } else if (obj.getType() == 3 && obj.getFilter() != null) {
                    ((MainActivity) myActivity).loadFragment("grid_hanghoa_2colum", obj.getFilter());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
