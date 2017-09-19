package app.dft.appbanhang.banhang;


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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.giamgia.GridViewAdapter;
import app.dft.appbanhang.noibat.AdapterNoiBat;
import app.dft.appbanhang.object.HangHoaObj;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;
import in.srain.cube.views.GridViewWithHeaderAndFooter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDanhSachHangHoaBanHang extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgHamburger, imgSearch, imgCart, imgAD2;
    TextView txtCartNum;
    GridView gridView;
    List<HangHoaObj> listHangHoa;
    GridViewAdapter adapterHangHoa;

    Boolean loadingMore = false;
    int page = 1;
    int limit = 10;

    public static ImageView imgDiemThuong;

    public FragmentDanhSachHangHoaBanHang() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_danhsach_hanghoa, container, false);
        myActivity = getActivity();

        page = 1;

        imgHamburger = (ImageView) v.findViewById(R.id.imgHamburger);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);
        gridView = (GridView) v.findViewById(R.id.gridView);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        imgDiemThuong.setOnClickListener(this);
        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));
        imgHamburger.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        imgSearch.setOnClickListener(this);


        listHangHoa = new ArrayList<>();

        GetData();

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                int lastItem = i + i1;
                if ((lastItem == i2) && !(loadingMore)) {
                    GetData();
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MainActivity) myActivity).loadFragment("chitiet_hanghoa_banhang", listHangHoa.get(i).get_id());
            }
        });
        return v;
    }

    private void GetData() {
        loadingMore = true;
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiDanhSachHangHoa + "&page=" + page + "&limit=" + limit, null, new Response.Listener<JSONObject>() {

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
                                    listHangHoa.addAll(list2);
                                    page = page + 1;
                                    adapterHangHoa = new GridViewAdapter(myActivity, listHangHoa);
                                    gridView.setAdapter(adapterHangHoa);
                                    gridView.setNumColumns(2);
                                    adapterHangHoa.notifyDataSetChanged();
                                    gridView.setSelection(listHangHoa.size() - list2.size());

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgHamburger:
                MainActivity.drawerLayout.openDrawer(GravityCompat.START);
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
