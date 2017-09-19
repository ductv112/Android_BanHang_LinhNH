package app.dft.appbanhang.home;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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
import app.dft.appbanhang.object.HomeObj;
import app.dft.appbanhang.object.LoaiHangHoaObj;
import app.dft.appbanhang.object.ThongBaoHeThongObj;
import app.dft.appbanhang.util.CircleTransform;
import app.dft.appbanhang.util.DFTClickADV;
import app.dft.appbanhang.util.DFTFormat;
import app.dft.appbanhang.util.DFTPopup;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();

    LinearLayout lineHome;
    ImageView imgHamburger, imgCart, imgDeleteKeyWord;
    public static ImageView imgDiemThuong;
    AutoCompleteTextView edtSearch;
    ProgressDialog progressDialog;
    DFTPopup dftPopup;
    List<HomeObj> listHomeObj;
    TextView txtCartNum;
    ImageView imgAD1, imgAD2;

    ListView listView;
    AdapterHome adapterHome;
    HomeObj adv1, adv2;

    int imgAD1Width;
    int imgAD1Height;

    int imgAD2Width;
    int imgAD2Height;

    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        myActivity = getActivity();

        lineHome = (LinearLayout) v.findViewById(R.id.lineHome);
        imgHamburger = (ImageView) v.findViewById(R.id.imgHamburger);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        imgDeleteKeyWord = (ImageView) v.findViewById(R.id.imgDeleteKeyWord);
        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);

        edtSearch = (AutoCompleteTextView) v.findViewById(R.id.edtSearch);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);
        listView = (ListView) v.findViewById(R.id.listView);

        imgDeleteKeyWord.setVisibility(View.GONE);

        LayoutInflater layoutInflater = LayoutInflater.from(myActivity);
        View headerView1 = layoutInflater.inflate(R.layout.layout_header_ad1, null);
        imgAD1 = (ImageView) headerView1.findViewById(R.id.imgAD1);

        View headerView2 = layoutInflater.inflate(R.layout.layout_header_gridview, null);
        imgAD2 = (ImageView) headerView2.findViewById(R.id.imgAD2);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        myActivity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        imgAD1Width = width;
        imgAD1Height = imgAD1Width / 2;
        ListView.LayoutParams layoutParams1, layoutParams2;
        layoutParams1 = new ListView.LayoutParams(
                imgAD1Width, imgAD1Height);

        imgAD2Width = width;
        imgAD2Height = imgAD2Width / 5;
        layoutParams2 = new ListView.LayoutParams(
                imgAD2Width, imgAD2Height);

        imgAD1.setLayoutParams(layoutParams1);
        imgAD2.setLayoutParams(layoutParams2);
        listView.addHeaderView(imgAD1);
        listView.addHeaderView(imgAD2);

        //DucTV fake giỏ hàng


        imgHamburger.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        imgDeleteKeyWord.setOnClickListener(this);
        imgDiemThuong.setOnClickListener(this);


        GetData();
        GetSoGioHang();
        GetAutoComplete();

        imgAD1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DFTClickADV.ClickADV(myActivity, adv1);
            }
        });

        imgAD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DFTClickADV.ClickADV(myActivity, adv2);
            }
        });

        edtSearch.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((MainActivity) myActivity).loadFragment("search", edtSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String keyWord = (String) adapterView.getItemAtPosition(i);
                ((MainActivity) myActivity).loadFragment("search", keyWord);
                hideKeyboard(edtSearch);
            }
        });

        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                imgDeleteKeyWord.setVisibility(View.VISIBLE);
            }
        });

        listView.setOnTouchListener(new View.OnTouchListener() {
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

        lineHome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });

        return v;
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

            case R.id.imgCart:
                ((MainActivity) myActivity).loadFragment("cart");
                break;

            case R.id.imgDeleteKeyWord:
                edtSearch.setText("");
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

        progressDialog = new ProgressDialog(myActivity);
        progressDialog.setMessage(myActivity.getString(R.string.w02_dangxuly));
        progressDialog.setProgress(3000);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, StaticVariable.apiGetGiamGiaVaNoiBat, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int code = response.getInt("code");
                    String msg = response.getString("msg");
                    if (code == 200) {
                        ObjectMapper mapper = new ObjectMapper();
                        listHomeObj = new ArrayList<>();
                        listHomeObj = mapper.readValue(
                                response.getJSONArray("data").toString(),
                                new TypeReference<ArrayList<HomeObj>>() {
                                });
                        if (listHomeObj != null && listHomeObj.size() > 0) {
                            adapterHome = new AdapterHome(myActivity, listHomeObj);
                            listView.setAdapter(adapterHome);
                        }
                        adv1 = mapper.readValue(
                                response.getJSONObject("adv").toString(),
                                new TypeReference<HomeObj>() {
                                });
                        if (adv1 != null && adv1.getBanner() != null) {
                            Picasso.with(myActivity).load(DFTFormat.UrlImage(adv1.getBanner())).resize(imgAD1Width, imgAD1Height).into(imgAD1);
                        }

                        adv2 = mapper.readValue(
                                response.getJSONObject("advOther").toString(),
                                new TypeReference<HomeObj>() {
                                });
                        if (adv2 != null && adv2.getBanner() != null) {
                            Picasso.with(myActivity).load(DFTFormat.UrlImage(adv2.getBanner())).resize(imgAD2Width, imgAD2Height).into(imgAD2);
                        }
                        progressDialog.dismiss();

                    } else {
                        progressDialog.dismiss();
                        ShowMSG(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
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

    private void ShowMSG(String msg) {
        DFTPopup.DFTDialogListener listener = new DFTPopup.DFTDialogListener() {
            @Override
            public void userOK() {
                if (dftPopup != null) {
                    dftPopup.dismiss();
                }
            }

            @Override
            public void userCancel() {

            }
        };
        dftPopup = new DFTPopup(myActivity, 1, msg, listener);
        dftPopup.show();
        dftPopup.setCanceledOnTouchOutside(false);
    }

    private void GetSoGioHang() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiGetSoGioHang, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            int data = response.getInt("data");
                            String msg = response.getString("msg");

                            StaticVariable.soLuong = data;
                            txtCartNum.setText(String.valueOf(StaticVariable.soLuong));

                        } catch (Exception e) {
                            e.printStackTrace();
//                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
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
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
