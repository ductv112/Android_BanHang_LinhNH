package app.dft.appbanhang.giamgia;


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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import app.dft.appbanhang.navmenu.CustomDrawerAdapter;
import app.dft.appbanhang.object.HangHoaObj;
import app.dft.appbanhang.object.LoaiHangHoaObj;
import app.dft.appbanhang.object.ThuongHieuObj;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentGiamGiaTotal extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();

    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter adapter;
    public static TextView txtTitle;
    ListView lvLoc, lvSapXep, lvLocLoai, lvLocThuongHieu;
    LinearLayout layoutLoc, layoutSapXep;
    ImageView imgHamburger, imgSearch, imgCart;
    TextView txtCartNum;

    String productTypeId, trademarkId;

    List<String> listSapXep;
    List<String> listLoc;
    AdapterLoc adapterLoc;
    List<LoaiHangHoaObj> listLoaiHangHoa = new ArrayList<>();
    AdapterLocLoai adapterLocLoai;
    RelativeLayout layoutBack, layoutBackTH;

    List<ThuongHieuObj> listThuongHieu = new ArrayList<>();
    AdapterLocThuongHieu adapterLocThuongHieu;

    public static ImageView imgDiemThuong;

    public static int sort = -1;
    public static String filterLoaiHH = null;
    public static String filterThuongHieu = null;


    public FragmentGiamGiaTotal() {
        // Required empty public constructor
    }


    public FragmentGiamGiaTotal(String productTypeId, String trademarkId) {
        this.productTypeId = productTypeId;
        this.trademarkId = trademarkId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_giam_gia_total, container, false);
        myActivity = getActivity();
        sort = -1;
        filterLoaiHH = null;
        filterThuongHieu = null;

        txtTitle = (TextView) v.findViewById(R.id.txtTitle);
        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) v.findViewById(R.id.pager);
        lvLoc = (ListView) v.findViewById(R.id.lvLoc);
        lvSapXep = (ListView) v.findViewById(R.id.lvSapXep);
        lvLocLoai = (ListView) v.findViewById(R.id.lvLocLoai);
        lvLocThuongHieu = (ListView) v.findViewById(R.id.lvLocThuongHieu);
        layoutSapXep = (LinearLayout) v.findViewById(R.id.layoutSapXep);
        layoutLoc = (LinearLayout) v.findViewById(R.id.layoutLoc);
        imgHamburger = (ImageView) v.findViewById(R.id.imgHamburger);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        layoutBack = (RelativeLayout) v.findViewById(R.id.layoutBack);
        layoutBackTH = (RelativeLayout) v.findViewById(R.id.layoutBackTH);


        txtCartNum = (TextView)v.findViewById(R.id.txtCartNum);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        imgDiemThuong.setOnClickListener(this);
        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));

        txtTitle.setVisibility(View.VISIBLE);
        lvLoc.setVisibility(View.GONE);
        lvSapXep.setVisibility(View.GONE);
        layoutBack.setVisibility(View.GONE);
        layoutBackTH.setVisibility(View.GONE);


        layoutLoc.setOnClickListener(this);
        layoutSapXep.setOnClickListener(this);
        imgHamburger.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        layoutBack.setOnClickListener(this);
        layoutBackTH.setOnClickListener(this);


        tabLayout.addTab(tabLayout.newTab().setText(myActivity.getString(R.string.tab_danggiamgia)));
        tabLayout.addTab(tabLayout.newTab().setText(myActivity.getString(R.string.tab_sapgiamgia)));
//        tabLayout.addTab(tabLayout.newTab().setText(myActivity.getString(R.string.tab_daxem)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new PagerAdapter
                (getChildFragmentManager(), tabLayout.getTabCount(), productTypeId, trademarkId);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return v;
    }

    private void LocHangHoa() {
        listLoc = new ArrayList<String>();
        listLoc.add("Loại");
        listLoc.add("Thương hiệu");

        adapterLoc = new AdapterLoc(myActivity, listLoc);
        lvLoc.setAdapter(adapterLoc);

        lvLoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listLoc.get(i).equalsIgnoreCase("Loại")) {
                    GetLoaiHangHoa();
                } else {
                    GetThuongHieu();

                }
            }
        });

        lvLocLoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listLoaiHangHoa != null && listLoaiHangHoa.size() > 0) {
                    filterLoaiHH = listLoaiHangHoa.get(i).get_id();
                    filterThuongHieu = null;
                    //Gọi load data
                    LoadData();
                }
            }
        });

        lvLocThuongHieu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listThuongHieu != null && listThuongHieu.size() > 0) {
                    filterThuongHieu = listThuongHieu.get(i).get_id();
                    filterLoaiHH = null;
                    //Gọi load data
                    LoadData();
                }
            }
        });


    }

    private void SapXep() {

        listSapXep = new ArrayList<String>();
        listSapXep.add("Mới nhất");
        listSapXep.add("Giá tăng dần");
        listSapXep.add("Giá giảm dần");
        listSapXep.add("Tỉ giá coin");

        adapterLoc = new AdapterLoc(myActivity, listSapXep);
        lvSapXep.setAdapter(adapterLoc);

        lvSapXep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listSapXep != null && listSapXep.size() > 0) {
                    lvSapXep.setVisibility(View.GONE);
                    sort = i + 1;
                    //Gọi load data
                    LoadData();
                }

            }
        });

    }

    private void LoadData() {
        lvLoc.setVisibility(View.GONE);
        layoutBack.setVisibility(View.GONE);
        layoutBackTH.setVisibility(View.GONE);
        lvLocLoai.setVisibility(View.GONE);
        lvLocThuongHieu.setVisibility(View.GONE);
        lvSapXep.setVisibility(View.GONE);


        FragmentDangGiamGia.page = 1;
        FragmentDangGiamGia.list = new ArrayList<>();
        FragmentDangGiamGia.adapter = new GridViewAdapter(myActivity, FragmentDangGiamGia.list);
        FragmentDangGiamGia.gridView.setAdapter(FragmentDangGiamGia.adapter);
        FragmentSapGiamGia.page = 1;
        FragmentSapGiamGia.list = new ArrayList<>();
        FragmentSapGiamGia.adapter = new GridViewAdapter(myActivity, FragmentSapGiamGia.list);
        FragmentSapGiamGia.gridView.setAdapter(FragmentSapGiamGia.adapter);
        FragmentDangGiamGia.GetData();
        FragmentSapGiamGia.GetData();
    }


    private void GetLoaiHangHoa() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiGetLoaiHangHoa, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                listLoaiHangHoa = mapper.readValue(
                                        response.getJSONArray("data").toString(),
                                        new TypeReference<ArrayList<LoaiHangHoaObj>>() {
                                        });
                                if (listLoaiHangHoa != null) {
                                    adapterLocLoai = new AdapterLocLoai(myActivity, listLoaiHangHoa);
                                    lvLocLoai.setAdapter(adapterLocLoai);
                                    layoutBack.setVisibility(View.VISIBLE);
                                    lvLocLoai.setVisibility(View.VISIBLE);
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

    private void GetThuongHieu() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiGetAllThuongHieu, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                listThuongHieu = mapper.readValue(
                                        response.getJSONArray("data").toString(),
                                        new TypeReference<ArrayList<ThuongHieuObj>>() {
                                        });
                                if (listThuongHieu != null) {
                                    adapterLocThuongHieu = new AdapterLocThuongHieu(myActivity, listThuongHieu);
                                    lvLocThuongHieu.setAdapter(adapterLocThuongHieu);
                                    layoutBackTH.setVisibility(View.VISIBLE);
                                    lvLocThuongHieu.setVisibility(View.VISIBLE);
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

                if (MainActivity.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    MainActivity.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    MainActivity.drawerLayout.openDrawer(GravityCompat.START);
                }

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

            case R.id.layoutLoc:
                if (lvLoc.getVisibility() == View.VISIBLE) {
                    lvLoc.setVisibility(View.GONE);
                    layoutBack.setVisibility(View.GONE);
                    layoutBackTH.setVisibility(View.GONE);
                    lvLocLoai.setVisibility(View.GONE);
                    lvLocThuongHieu.setVisibility(View.GONE);
                } else {
                    lvLoc.setVisibility(View.VISIBLE);
                    lvSapXep.setVisibility(View.GONE);
                }
                LocHangHoa();

                break;

            case R.id.layoutSapXep:
                if (lvSapXep.getVisibility() == View.VISIBLE) {
                    lvSapXep.setVisibility(View.GONE);
                } else {
                    lvSapXep.setVisibility(View.VISIBLE);
                    lvLoc.setVisibility(View.GONE);
                    layoutBack.setVisibility(View.GONE);
                    layoutBackTH.setVisibility(View.GONE);
                    lvLocLoai.setVisibility(View.GONE);
                    lvLocThuongHieu.setVisibility(View.GONE);
                }
                SapXep();
                break;

            case R.id.layoutBack:
                layoutBack.setVisibility(View.GONE);
                lvLocLoai.setVisibility(View.GONE);
                lvLoc.setVisibility(View.VISIBLE);
                break;

            case R.id.layoutBackTH:
                layoutBackTH.setVisibility(View.GONE);
                lvLocThuongHieu.setVisibility(View.GONE);
                lvLoc.setVisibility(View.VISIBLE);
                break;

        }

    }

}
