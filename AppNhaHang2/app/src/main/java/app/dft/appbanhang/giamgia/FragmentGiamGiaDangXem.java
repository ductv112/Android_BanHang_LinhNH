package app.dft.appbanhang.giamgia;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.dft.appbanhang.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGiamGiaDangXem extends Fragment {

    Activity myActivity = new Activity();

    public FragmentGiamGiaDangXem() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_giam_gia_dang_xem, container, false);
        myActivity = getActivity();

        return v;
    }

}
