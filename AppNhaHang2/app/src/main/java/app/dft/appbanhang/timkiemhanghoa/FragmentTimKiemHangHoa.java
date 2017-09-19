package app.dft.appbanhang.timkiemhanghoa;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.giamgia.AdapterLoc;
import app.dft.appbanhang.giamgia.AdapterLocLoai;
import app.dft.appbanhang.giamgia.AdapterLocThuongHieu;
import app.dft.appbanhang.noibat.AdapterNoiBat;
import app.dft.appbanhang.object.HangHoaObj;
import app.dft.appbanhang.object.HomeObj;
import app.dft.appbanhang.object.LoaiHangHoaObj;
import app.dft.appbanhang.object.ThuongHieuObj;
import app.dft.appbanhang.util.DFTClickADV;
import app.dft.appbanhang.util.DFTFormat;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentTimKiemHangHoa extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgBackSearch, imgDeleteKeyWord, imgCart;
    TextView txtCartNum, txtKoTimThay;
    ListView lvSearch;
    AutoCompleteTextView edtSearch;
    List<HangHoaObj> listHangHoa;
    AdapterNoiBat adapterNoiBat;
    ImageView imgAD2;
    LinearLayout lineTong;

    ListView lvLoc, lvSapXep, lvLocLoai, lvLocThuongHieu;
    LinearLayout layoutLoc, layoutSapXep;

    List<String> listSapXep;
    List<String> listLoc;
    AdapterLoc adapterLoc;
    List<LoaiHangHoaObj> listLoaiHangHoa = new ArrayList<>();
    AdapterLocLoai adapterLocLoai;
    RelativeLayout layoutBack, layoutBackTH, relayFilter;

    List<ThuongHieuObj> listThuongHieu = new ArrayList<>();
    AdapterLocThuongHieu adapterLocThuongHieu;

    public static int sort = -1;
    public static String filterLoaiHH = null;
    public static String filterThuongHieu = null;

    HomeObj advObj;
    int imgAD2Width;
    int imgAD2Height;

    int page = 1;
    int limit = 10;
    Boolean loadingMore = false;

    String keyWord;

    public static ImageView imgDiemThuong;
    Dialog dialog;

    public FragmentTimKiemHangHoa() {
        // Required empty public constructor
    }


    public FragmentTimKiemHangHoa(String keyWord) {
        this.keyWord = keyWord;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tim_kiem_hang_hoa, container, false);
        myActivity = getActivity();

        page = 1;
        sort = -1;
        filterLoaiHH = null;
        filterThuongHieu = null;

        lvLoc = (ListView) v.findViewById(R.id.lvLoc);
        lvSapXep = (ListView) v.findViewById(R.id.lvSapXep);
        lvLocLoai = (ListView) v.findViewById(R.id.lvLocLoai);
        lvLocThuongHieu = (ListView) v.findViewById(R.id.lvLocThuongHieu);
        layoutSapXep = (LinearLayout) v.findViewById(R.id.layoutSapXep);
        layoutLoc = (LinearLayout) v.findViewById(R.id.layoutLoc);
        layoutBack = (RelativeLayout) v.findViewById(R.id.layoutBack);
        layoutBackTH = (RelativeLayout) v.findViewById(R.id.layoutBackTH);
        relayFilter = (RelativeLayout) v.findViewById(R.id.relayFilter);

        imgBackSearch = (ImageView) v.findViewById(R.id.imgBackSearch);
        imgDeleteKeyWord = (ImageView) v.findViewById(R.id.imgDeleteKeyWord);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);
        lvSearch = (ListView) v.findViewById(R.id.lvSearch);
        edtSearch = (AutoCompleteTextView) v.findViewById(R.id.edtSearch);
        lineTong = (LinearLayout) v.findViewById(R.id.lineTong);
        txtKoTimThay = (TextView) v.findViewById(R.id.txtKoTimThay);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        imgDiemThuong.setOnClickListener(this);
        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));

//        edtSearch.requestFocus();

//        InputMethodManager imm = (InputMethodManager) myActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT);
//        myActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        imgDeleteKeyWord.setVisibility(View.GONE);

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
        lvSearch.addHeaderView(headerView);

        imgBackSearch.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        imgDeleteKeyWord.setOnClickListener(this);

        lvLoc.setVisibility(View.GONE);
        lvSapXep.setVisibility(View.GONE);
        layoutBack.setVisibility(View.GONE);
        layoutBackTH.setVisibility(View.GONE);
        relayFilter.setVisibility(View.GONE);

        layoutLoc.setOnClickListener(this);
        layoutSapXep.setOnClickListener(this);
        layoutBack.setOnClickListener(this);
        layoutBackTH.setOnClickListener(this);
        listHangHoa = new ArrayList<>();

        if (keyWord != null) {
            edtSearch.setText(keyWord);
            Search();
        }

        GetAutoComplete();

        imgAD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DFTClickADV.ClickADV(myActivity, advObj);
            }
        });

        lineTong.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });

        imgDeleteKeyWord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MainActivity) myActivity).loadFragment("chitiet_hanghoa", listHangHoa.get(i - 1).get_id());
            }
        });


        lvSearch.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                int lastItem = i + i1;
                if ((lastItem == i2) && !(loadingMore)) {
                    Search();
                }
            }
        });


        edtSearch.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    page = 1;
                    Search();
                    hideKeyboard(v);
                    return true;
                }
                return false;
            }
        });

        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                keyWord = (String) adapterView.getItemAtPosition(i);
                page = 1;
                Search();
                hideKeyboard(edtSearch);
            }
        });

        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                imgDeleteKeyWord.setVisibility(View.VISIBLE);

            }
        });

        lvSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });

        relayFilter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });

        listHangHoa = new ArrayList<>();
        adapterNoiBat = new AdapterNoiBat(myActivity, listHangHoa);
        lvSearch.setAdapter(adapterNoiBat);

        edtSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) myActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBackSearch:
                myActivity.onBackPressed();
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
            case R.id.imgDeleteKeyWord:
                edtSearch.setText("");

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


        page = 1;
        listHangHoa = new ArrayList<>();
        adapterNoiBat = new AdapterNoiBat(myActivity, listHangHoa);
        lvSearch.setAdapter(adapterNoiBat);
        Search();

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


    private void Search() {
        String filter = edtSearch.getText().toString();
        loadingMore = true;


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("filter", filter);
            jsonObject.put("page", page);
            jsonObject.put("limit", limit);
            if (sort != -1) {
                jsonObject.put("sort", sort);
            }
            if (filterLoaiHH != null) {
                jsonObject.put("productType", filterLoaiHH);
            }
            if (filterThuongHieu != null) {
                jsonObject.put("trademark", filterThuongHieu);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.POST, StaticVariable.apiTimKiem, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                txtKoTimThay.setVisibility(View.GONE);
                                ObjectMapper mapper = new ObjectMapper();
                                List<HangHoaObj> list = new ArrayList<HangHoaObj>();
                                if (page == 1) {
                                    listHangHoa.clear();
                                    listHangHoa = new ArrayList<>();
                                    adapterNoiBat = new AdapterNoiBat(myActivity, listHangHoa);
                                    lvSearch.setAdapter(adapterNoiBat);
                                }
                                list = mapper.readValue(
                                        response.getJSONArray("data").toString(),
                                        new TypeReference<ArrayList<HangHoaObj>>() {
                                        });

                                if (list != null && list.size() > 0) {
                                    listHangHoa.addAll(list);

                                    page = page + 1;
//                                    adapterNoiBat = new AdapterNoiBat(myActivity, listHangHoa);
//                                    lvSearch.setAdapter(adapterNoiBat);
                                    adapterNoiBat.notifyDataSetChanged();
                                    lvSearch.setSelection(listHangHoa.size() - list.size());
                                    relayFilter.setVisibility(View.VISIBLE);

                                }
                                if (listHangHoa.size() <= 0) {
                                    txtKoTimThay.setVisibility(View.VISIBLE);
                                } else {
                                    txtKoTimThay.setVisibility(View.GONE);
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

    private void GetAutoComplete() {

        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiAutoComplete, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                List<String> listKey = new ArrayList<String>();
                                listKey = mapper.readValue(
                                        response.getJSONArray("data").toString(),
                                        new TypeReference<ArrayList<String>>() {
                                        });
                                if (listKey != null && listKey.size() > 0) {
                                    ArrayAdapter arrayAdapter = new ArrayAdapter(myActivity, android.R.layout.simple_list_item_1, listKey);
                                    edtSearch.setAdapter(arrayAdapter);
                                    edtSearch.setThreshold(1);
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

    protected void hideKeyboard(View view) {
//        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) myActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
