package app.dft.appbanhang.banhang;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.chitiethanghoa.AdapterSize;
import app.dft.appbanhang.chitiethanghoa.SlideImageChiTietHHAdapter;
import app.dft.appbanhang.object.ChiTietHangHoaObj;
import app.dft.appbanhang.util.DFTFormat;
import app.dft.appbanhang.util.DFTMessage;
import app.dft.appbanhang.util.DFTPopup;
import app.dft.appbanhang.util.DFTPopupInput;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentChiTietHangHoaBanHang extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgBack, imgSearch, imgCart;
    TextView txtCartNum, txtSize;
    DFTPopup dftPopup;

    ViewPager viewPager;
    CirclePageIndicator indicator;
    List<String> listIMG;
    SlideImageChiTietHHAdapter adapter;
    ChiTietHangHoaObj chiTietHangHoaObj;


    TextView txtTenSanPham, txtGia, txtTenThuongHieu, txtSoLuong, txtSua, txtXoa, txtTiGia;
    String id;

    DFTPopupInput dialog;
    DFTPopupInput.DFTPopupInputListener listener;

    public static ImageView imgDiemThuong;

    public FragmentChiTietHangHoaBanHang() {
        // Required empty public constructor
    }


    public FragmentChiTietHangHoaBanHang(String id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chitiet_hanghoa_banhang, container, false);
        myActivity = getActivity();

        txtTenSanPham = (TextView) v.findViewById(R.id.txtTenSanPham);
        txtGia = (TextView) v.findViewById(R.id.txtGia);
        txtTenThuongHieu = (TextView) v.findViewById(R.id.txtTenThuongHieu);
        txtSize = (TextView) v.findViewById(R.id.txtSize);
        txtSoLuong = (TextView) v.findViewById(R.id.txtSoLuong);
        txtSua = (TextView) v.findViewById(R.id.txtSua);
        txtXoa = (TextView) v.findViewById(R.id.txtXoa);
        txtTiGia = (TextView) v.findViewById(R.id.txtTiGia);

        imgBack = (ImageView) v.findViewById(R.id.imgBack);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        indicator = (CirclePageIndicator) v.findViewById(R.id.indicator);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        imgDiemThuong.setOnClickListener(this);

        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));


        DisplayMetrics displaymetrics = new DisplayMetrics();
        myActivity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        int imgWidth = width * 9 / 10;
        int imgHeight = imgWidth * 2 / 3;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                imgWidth, imgHeight);
        viewPager.setLayoutParams(layoutParams);

        listener = new DFTPopupInput.DFTPopupInputListener() {
            @Override
            public void userOK(String st) {
                txtSoLuong.setText(st);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void userCancel() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        };


        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        txtCartNum.setOnClickListener(this);
        txtSua.setOnClickListener(this);
        txtXoa.setOnClickListener(this);

        GetChiTietHangHoa();


        return v;
    }

    private void ShowSlideIMG() {
        listIMG = new ArrayList<>();
        if (chiTietHangHoaObj.getImage() != null) {
            listIMG.add(chiTietHangHoaObj.getImage());
        }
        if (chiTietHangHoaObj.getDescribe() != null && chiTietHangHoaObj.getDescribe().size() > 0) {
            listIMG.addAll(chiTietHangHoaObj.getDescribe());
        }
        adapter = new SlideImageChiTietHHAdapter(myActivity, listIMG);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
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

            case R.id.txtSua:
                ((MainActivity) myActivity).loadFragment("sua_hanghoa", id);
                break;

            case R.id.txtXoa:
                XoaHangHoa();
                break;

        }
    }


    private void ShowData() {


        if (chiTietHangHoaObj != null) {
            ShowSlideIMG();

            if (chiTietHangHoaObj.getName() != null) {
                txtTenSanPham.setText(chiTietHangHoaObj.getName());
            }
            txtGia.setText(DFTFormat.NumberFormat(String.valueOf(chiTietHangHoaObj.getPrice())) + " VNĐ");
            txtTiGia.setText("1 Điểm = " + DFTFormat.NumberFormat(String.valueOf(chiTietHangHoaObj.getRateCoin())) + " VNĐ");

            if (chiTietHangHoaObj.getTrademarkId().getName() != null) {
                txtTenThuongHieu.setText(chiTietHangHoaObj.getTrademarkId().getName());
            }
            txtSoLuong.setText(String.valueOf(chiTietHangHoaObj.getTotal()));


        }
    }

    private void GetChiTietHangHoa() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiGetChiTietHangHoa + id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                chiTietHangHoaObj = mapper.readValue(
                                        response.getJSONObject("data").toString(),
                                        new TypeReference<ChiTietHangHoaObj>() {
                                        });
                                if (chiTietHangHoaObj != null) {
                                    ShowData();
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

    private void XoaHangHoa() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.DELETE, StaticVariable.apiGetChiTietHangHoa + id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String msg = response.getString("msg");
                            if (code == 200) {
                                ShowMSG(msg);
                                ((MainActivity) myActivity).loadFragment("danhsach_hanghoa");

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


    private void ShowMSGSuccess(int icon, String msg) {
        final DFTMessage dftMessage = new DFTMessage(myActivity, icon, msg);
        dftMessage.show();
        dftMessage.setCanceledOnTouchOutside(false);

        // Hide after some seconds
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (dftMessage != null && dftMessage.isShowing()) {
                    dftMessage.dismiss();
                }
            }
        };

        dftMessage.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 3000);
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

}
