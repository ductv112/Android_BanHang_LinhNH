package app.dft.appbanhang.giamgia;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.object.HangHoaObj;
import app.dft.appbanhang.object.HomeObj;
import app.dft.appbanhang.util.DFTClickADV;
import app.dft.appbanhang.util.DFTFormat;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;
import in.srain.cube.views.GridViewWithHeaderAndFooter;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentDangGiamGia extends Fragment {

    static Activity myActivity = new Activity();

    static GridViewWithHeaderAndFooter gridView;
    static GridViewAdapter adapter;
    static List<HangHoaObj> list;
    static ImageView imgAD2;
    ProgressDialog progressDialog;
    static Boolean loadingMore = false;
    static int page = 1;
    static int limit = 10;


    int PAGE_NUM = 10;
    String productTypeId, trademarkId;

    static HomeObj advObj;
    static int imgAD2Width;
    static int imgAD2Height;

    public FragmentDangGiamGia() {
        // Required empty public constructor
    }


    public FragmentDangGiamGia(String productTypeId, String trademarkId) {
        this.productTypeId = productTypeId;
        this.trademarkId = trademarkId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dang_giam_gia, container, false);
        myActivity = getActivity();

        page = 1;

        gridView = (GridViewWithHeaderAndFooter) v.findViewById(R.id.gridView);
        LayoutInflater layoutInflater = LayoutInflater.from(myActivity);
        View headerView = layoutInflater.inflate(R.layout.layout_header_gridview, null);
        imgAD2 = (ImageView) headerView.findViewById(R.id.imgAD2);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        imgAD2Width = width;
        imgAD2Height = imgAD2Width / 5;
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                imgAD2Width, imgAD2Height);
        imgAD2.setLayoutParams(layoutParams2);

        gridView.addHeaderView(headerView);

        list = new ArrayList<>();
        GetData();
        imgAD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DFTClickADV.ClickADV(myActivity, advObj);
            }
        });

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (list != null && list.size() >= PAGE_NUM) {
                    if (scrollState == SCROLL_STATE_IDLE) {
                        if ((gridView.getFirstVisiblePosition() == 0)) {
                            FragmentGiamGiaTotal.txtTitle.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (list != null && list.size() >= PAGE_NUM) {
                    int lastInScreen = firstVisibleItem + visibleItemCount;
                    if (lastInScreen == totalItemCount) {
                        FragmentGiamGiaTotal.txtTitle.setVisibility(View.GONE);
                    }
                    if ((lastInScreen == totalItemCount) && !(loadingMore)) {
                        GetData();
                    }
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity) myActivity).loadFragment("chitiet_hanghoa", list.get(position).get_id());
            }
        });

        return v;
    }

    public static void GetData() {
        loadingMore = true;

        String url = StaticVariable.apiDangGiamGia + "&page=" + page + "&limit=" + limit;

        if (FragmentGiamGiaTotal.sort != -1) {
            url = url + "&sort=" + FragmentGiamGiaTotal.sort;
        }
        if (FragmentGiamGiaTotal.filterLoaiHH != null) {
            url = url + "&productType=" + FragmentGiamGiaTotal.filterLoaiHH;
        }
        if (FragmentGiamGiaTotal.filterThuongHieu != null) {
            url = url + "&trademark=" + FragmentGiamGiaTotal.filterThuongHieu;
        }

        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();


                                List<HangHoaObj> list2 = new ArrayList<HangHoaObj>();
                                list2 = mapper.readValue(
                                        response.getJSONArray("data").toString(),
                                        new TypeReference<ArrayList<HangHoaObj>>() {
                                        });
                                if (list2 != null && list2.size() > 0) {
                                    list.addAll(list2);
                                    page = page + 1;
                                    adapter = new GridViewAdapter(myActivity, list);
                                    gridView.setAdapter(adapter);
                                    gridView.setNumColumns(2);
                                    gridView.setSelection(list.size() - list2.size());
                                }

                                advObj = mapper.readValue(
                                        response.getJSONObject("adv").toString(),
                                        new TypeReference<HomeObj>() {
                                        });
                                if (advObj != null) {
                                    if (advObj.getBanner() != null) {
                                        Picasso.with(myActivity).load(DFTFormat.UrlImage(advObj.getBanner())).resize(imgAD2Width, imgAD2Height).into(imgAD2);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        loadingMore = false;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                headers.put("x-access-token", StaticVariable.userToken);

                return headers;
            }
        };

        queue.add(myReq);
    }

}
