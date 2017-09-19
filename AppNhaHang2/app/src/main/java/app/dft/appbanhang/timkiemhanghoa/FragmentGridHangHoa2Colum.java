package app.dft.appbanhang.timkiemhanghoa;


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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.giamgia.GridViewAdapter;
import app.dft.appbanhang.object.ADVFilterObj;
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
public class FragmentGridHangHoa2Colum extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgBack, imgSearch, imgCart;
    TextView txtCartNum;

    GridViewWithHeaderAndFooter gridView;
    GridViewAdapter adapter;
    ImageView imgAD2;
    List<HangHoaObj> list;
    Boolean loadingMore = false;
    int page = 1;
    int limit = 10;
    ADVFilterObj filterObj;
    HomeObj advObj;
    int imgAD2Width;
    int imgAD2Height;

    public static ImageView imgDiemThuong;

    public FragmentGridHangHoa2Colum(ADVFilterObj filterObj) {
        // Required empty public constructor
        this.filterObj = filterObj;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_grid_hanghoa_2colum, container, false);
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

        imgBack = (ImageView) v.findViewById(R.id.imgBack);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        imgDiemThuong.setOnClickListener(this);
        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));
        list = new ArrayList<>();
        GetData();

        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgCart.setOnClickListener(this);

        imgAD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DFTClickADV.ClickADV(myActivity, advObj);
            }
        });
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                int lastInScreen = i + i1;
                if ((lastInScreen == i2) && !(loadingMore)) {
                    GetData();
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


    private void GetData() {
        loadingMore = true;

        JSONArray productTypeId = new JSONArray();
        for (int i = 0; i < filterObj.getProductTypeId().size(); i++) {
            productTypeId.put(filterObj.getProductTypeId().get(i));
        }

        JSONArray trademarkId = new JSONArray();
        for (int i = 0; i < filterObj.getTrademarkId().size(); i++) {
            trademarkId.put(filterObj.getTrademarkId().get(i));
        }

        JSONObject jsonObject = new JSONObject();
        try {
            if (filterObj.getUserId() != null) {

                jsonObject.put("userId", filterObj.getUserId());

            }
            if (filterObj.getRateCoin() != null) {

                jsonObject.put("rateCoin", filterObj.getRateCoin());

            }
            if (filterObj.getKeyword() != null) {
                jsonObject.put("keyword", filterObj.getKeyword());
            }
            if (filterObj.getProductHot() != null) {
                jsonObject.put("productHot", filterObj.getProductHot());
            }
            if (filterObj.getPromotion() != null) {
                jsonObject.put("promotion", filterObj.getPromotion());
            }
            if (filterObj.getProductTypeId() != null && filterObj.getProductTypeId().size() > 0) {
                jsonObject.put("productTypeId", productTypeId);
            }

            if (filterObj.getTrademarkId() != null && filterObj.getTrademarkId().size() > 0) {
                jsonObject.put("trademarkId", trademarkId);
            }

            jsonObject.put("page", page);
            jsonObject.put("limit", limit);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.POST, StaticVariable.apiDanhSachHangHoaQuangCao, jsonObject, new Response.Listener<JSONObject>() {

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
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
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
