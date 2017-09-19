package app.dft.appbanhang.banhang;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.giohang.AdapterChiTietDatHang;
import app.dft.appbanhang.object.DatHangObj;
import app.dft.appbanhang.object.DonHangObj;
import app.dft.appbanhang.util.DFTFormat;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentChiTietDonHang extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgBack, imgSearch, imgCart;
    TextView txtCartNum;

    ListView lvChiTietDatHang;
    List<DonHangObj> listDonHang;
    DatHangObj datHangObj;
    AdapterChiTietDatHang adapterChiTietDatHang;
    TextView txtCount, txtTongTien, txtDatHang, txtHoTen, txtEmail, txtSDT, txtDiaChi, txtMaVanChuyen, txtPhiVanChuyen, txtNgayGiaoHang;
    LinearLayout layoutMaVanChuyen, layoutPhiVanChuyen;

    String idOder;


    public FragmentChiTietDonHang() {
        // Required empty public constructor
    }

    public FragmentChiTietDonHang(String idOder) {
        this.idOder = idOder;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chitiet_dathang_layout, container, false);
        myActivity = getActivity();

        imgBack = (ImageView) v.findViewById(R.id.imgBack);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);
        lvChiTietDatHang = (ListView) v.findViewById(R.id.lvChiTietDatHang);

        txtCount = (TextView) v.findViewById(R.id.txtCount);
        txtTongTien = (TextView) v.findViewById(R.id.txtTongTien);
        txtDatHang = (TextView) v.findViewById(R.id.txtDatHang);
        txtHoTen = (TextView) v.findViewById(R.id.txtHoTen);
        txtEmail = (TextView) v.findViewById(R.id.txtEmail);
        txtSDT = (TextView) v.findViewById(R.id.txtSDT);
        txtDiaChi = (TextView) v.findViewById(R.id.txtDiaChi);

        txtMaVanChuyen = (TextView) v.findViewById(R.id.txtMaVanChuyen);
        txtPhiVanChuyen = (TextView) v.findViewById(R.id.txtPhiVanChuyen);
        txtNgayGiaoHang = (TextView) v.findViewById(R.id.txtNgayGiaoHang);

        layoutMaVanChuyen = (LinearLayout) v.findViewById(R.id.layoutMaVanChuyen);
        layoutPhiVanChuyen = (LinearLayout) v.findViewById(R.id.layoutPhiVanChuyen);
        layoutMaVanChuyen.setVisibility(View.VISIBLE);
        layoutPhiVanChuyen.setVisibility(View.VISIBLE);

        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));

        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        GetData();

        lvChiTietDatHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MainActivity) myActivity).loadFragment("chitiet_hanghoa_banhang", listDonHang.get(i).getProductId().get_id());
            }
        });


        return v;
    }


    private void SetText() {
        if (datHangObj != null) {
            if (datHangObj.getStatus() == 0) {
                txtDatHang.setText("Đã hủy");
            } else if (datHangObj.getStatus() == 1) {
                txtDatHang.setText("Chưa xác nhận");
            } else if (datHangObj.getStatus() == 2) {
                txtDatHang.setText("Đã xác nhận");
            } else if (datHangObj.getStatus() == 3) {
                txtDatHang.setText("Đang giao hàng");
            } else if (datHangObj.getStatus() == 4) {
                txtDatHang.setText("Đã nhận hàng");
            }
        }
    }

    private void ThongTinVanChuyen() {
        if (datHangObj != null) {
            if (datHangObj.getName() != null) {
                txtHoTen.setText(datHangObj.getName());
            }
            if (datHangObj.getEmail() != null) {
                txtEmail.setText(datHangObj.getEmail());
            }

            if (datHangObj.getPhone() != null) {
                txtSDT.setText(datHangObj.getPhone());
            }

            if (datHangObj.getAddress() != null) {
                txtDiaChi.setText(datHangObj.getAddress());
            }

            if (datHangObj.getTransportCode() != null) {
                txtMaVanChuyen.setText(datHangObj.getTransportCode());
            }
            if (datHangObj.getTransportCost() != null) {
                txtPhiVanChuyen.setText(DFTFormat.NumberFormat(datHangObj.getTransportCost()) + " VNĐ");
            }
            if (datHangObj.getDelivery() != null) {
                txtNgayGiaoHang.setText(DFTFormat.DateTime2(datHangObj.getDelivery()));
            }

        }
    }

    private void GetData() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiDatHang + "?type=2" + "&orderId=" + idOder, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                JSONObject jsonObject = response.getJSONObject("data");
                                datHangObj = mapper.readValue(
                                        jsonObject.toString(),
                                        new TypeReference<DatHangObj>() {
                                        });
                                if (datHangObj != null) {
                                    txtTongTien.setText(DFTFormat.NumberFormat(String.valueOf(datHangObj.getAmount())) + " VNĐ");
                                    ThongTinVanChuyen();
                                    SetText();

                                }
                                listDonHang = new ArrayList<DonHangObj>();
                                listDonHang.add(datHangObj.getTotalProduct());
                                if (listDonHang != null && listDonHang.size() > 0) {
                                    adapterChiTietDatHang = new AdapterChiTietDatHang(myActivity, listDonHang);
                                    lvChiTietDatHang.setAdapter(adapterChiTietDatHang);
                                    int soLuong = listDonHang.size();
                                    txtCount.setText("Tổng " + soLuong + " mặt hàng");

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
}
