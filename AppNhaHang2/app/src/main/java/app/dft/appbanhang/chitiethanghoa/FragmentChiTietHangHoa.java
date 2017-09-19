package app.dft.appbanhang.chitiethanghoa;


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
import android.widget.EditText;
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
import app.dft.appbanhang.giamgia.AdapterLocThuongHieu;
import app.dft.appbanhang.object.ChiTietHangHoaObj;
import app.dft.appbanhang.object.GioHangObj;
import app.dft.appbanhang.object.HangHoaObj;
import app.dft.appbanhang.object.ThuongHieuObj;
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
public class FragmentChiTietHangHoa extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgBack, imgSearch, imgCart;
    TextView txtCartNum, txtSize;
    DFTPopup dftPopup;

    ViewPager viewPager;
    CirclePageIndicator indicator;
    List<String> listIMG;
    SlideImageChiTietHHAdapter adapter;
    ChiTietHangHoaObj chiTietHangHoaObj;

    ListView lvSize;
    List<String> listSize;

    AdapterSize adapterSize;
    LinearLayout layoutSize, layoutSoLuong;
    boolean flagSize = false;

    TextView txtTenSanPham, txtGia, txtFreeShip, txtTenThuongHieu, txtAddCart, txtByNow, txtChiTiet, txtSoLuong, txtTiGia;
    String id;

    DFTPopupInput dialog;
    DFTPopupInput.DFTPopupInputListener listener;

    public static ImageView imgDiemThuong;

    public FragmentChiTietHangHoa() {
        // Required empty public constructor
    }


    public FragmentChiTietHangHoa(String id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chi_tiet_hang_hoa, container, false);
        myActivity = getActivity();

        txtTenSanPham = (TextView) v.findViewById(R.id.txtTenSanPham);
        txtGia = (TextView) v.findViewById(R.id.txtGia);
        txtFreeShip = (TextView) v.findViewById(R.id.txtFreeShip);
        txtTenThuongHieu = (TextView) v.findViewById(R.id.txtTenThuongHieu);
        txtAddCart = (TextView) v.findViewById(R.id.txtAddCart);
        txtByNow = (TextView) v.findViewById(R.id.txtByNow);
        txtChiTiet = (TextView) v.findViewById(R.id.txtChiTiet);
        lvSize = (ListView) v.findViewById(R.id.lvSize);
        layoutSize = (LinearLayout) v.findViewById(R.id.layoutSize);
        txtSize = (TextView) v.findViewById(R.id.txtSize);
        layoutSoLuong = (LinearLayout) v.findViewById(R.id.layoutSoLuong);
        txtSoLuong = (TextView) v.findViewById(R.id.txtSoLuong);
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
        int imgHeight = imgWidth;

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
        txtAddCart.setOnClickListener(this);
        txtByNow.setOnClickListener(this);
        layoutSize.setOnClickListener(this);
        layoutSoLuong.setOnClickListener(this);

        lvSize.setVisibility(View.GONE);

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

            case R.id.layoutSize:
                if (!flagSize) {
                    ListSize();
                    lvSize.setVisibility(View.VISIBLE);
                } else {
                    lvSize.setVisibility(View.GONE);
                }
                flagSize = !flagSize;
                break;

            case R.id.layoutSoLuong:
                dialog = new DFTPopupInput(myActivity, "Số lượng", listener);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                break;

            case R.id.txtAddCart:

                if (chiTietHangHoaObj.getTotal() == 0) {
                    ShowMSG("Hàng hóa đã hết");
                } else {
                    AddToCart(false);
                }

                break;

            case R.id.txtByNow:
                AddToCart(true);
                break;

        }
    }

    private void ListSize() {
//        lvSize.setVisibility(View.VISIBLE);
        listSize = new ArrayList<>();
        if (chiTietHangHoaObj.getSize() != null && chiTietHangHoaObj.getSize().size() > 0) {
            for (int i = 0; i < chiTietHangHoaObj.getSize().size(); i++) {
                listSize.add(chiTietHangHoaObj.getSize().get(i));
            }
        }

        adapterSize = new AdapterSize(myActivity, listSize);
        lvSize.setAdapter(adapterSize);

        lvSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String size = listSize.get(i).toString();
                txtSize.setText(size);
                lvSize.setVisibility(View.GONE);
                flagSize = false;
            }
        });
    }


    private void ShowData() {


        if (chiTietHangHoaObj != null) {
            ShowSlideIMG();

            if (chiTietHangHoaObj.getName() != null) {
                txtTenSanPham.setText(chiTietHangHoaObj.getName());
            }
            txtGia.setText(DFTFormat.NumberFormat(String.valueOf(chiTietHangHoaObj.getPrice())) + " VNĐ");
            txtTiGia.setText("1 Điểm = " + DFTFormat.NumberFormat(String.valueOf(chiTietHangHoaObj.getRateCoin())) + " VNĐ");
//            if (chiTietHangHoaObj.getFreeShip() == 1) {
//                txtFreeShip.setText("Có");
//            } else {
//                txtFreeShip.setText("Không");
//            }
            txtFreeShip.setText("Có");

            if (chiTietHangHoaObj.getTrademarkId().getName() != null) {
                txtTenThuongHieu.setText(chiTietHangHoaObj.getTrademarkId().getName());
            }

            if (chiTietHangHoaObj.getDescription() != null) {
                txtChiTiet.setText(chiTietHangHoaObj.getDescription());
            }
            if (chiTietHangHoaObj.getSize() != null && chiTietHangHoaObj.getSize().size() > 0) {
                ListSize();
            } else {
                layoutSize.setVisibility(View.GONE);
            }

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


    private void AddToCart(final boolean isByNow) {
        RequestQueue queue = MyVolley.getRequestQueue();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("productId", chiTietHangHoaObj.get_id());
            jsonObject.put("total", txtSoLuong.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.POST, StaticVariable.apiAddToCart, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String msg = response.getString("msg");
                            if (code == 200) {
                                if (!isByNow) {
                                    ShowMSGSuccess(R.mipmap.ic_cart_white, myActivity.getString(R.string.msg_themvaogiohangthanhcong));
                                }
                                StaticVariable.soLuong++;
                                txtCartNum.setText(String.valueOf(StaticVariable.soLuong));
                                if (isByNow) {
                                    List<ChiTietHangHoaObj> list = new ArrayList<>();
                                    list.add(chiTietHangHoaObj);
                                    ((MainActivity) myActivity).loadFragment("dathang", null, list);
                                }
                            } else if (code == 3) {
                                ((MainActivity) myActivity).loadFragment("cart", id);
                                if (isByNow) {
                                    List<ChiTietHangHoaObj> list = new ArrayList<>();
                                    list.add(chiTietHangHoaObj);
                                    ((MainActivity) myActivity).loadFragment("dathang", null, list);
                                }
                            } else if (code == 2) {
                                ShowMSG(msg);
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
