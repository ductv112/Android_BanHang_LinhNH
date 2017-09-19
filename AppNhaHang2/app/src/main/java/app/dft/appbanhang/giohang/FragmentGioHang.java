package app.dft.appbanhang.giohang;


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
import app.dft.appbanhang.object.GioHangObj;
import app.dft.appbanhang.util.DFTFormat;
import app.dft.appbanhang.util.DFTPopup;
import app.dft.appbanhang.util.DFTPopupInput;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentGioHang extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgHamburger, imgSearch, imgCart;
    TextView txtCartNum;

    TextView txtCount, txtTongTien, txtDatHang;
    ListView listView;
    List<GioHangObj> list;
    AdapterGioHang adapter;
    AdapterGioHang.ChangeSoLuong listenerChangeSoLuong;
    AdapterGioHang.ChangeDiem listenerChangeDiem;
    AdapterGioHang.XoaHH listenerXoaHH;
    DFTPopupInput dialog;
    DFTPopupInput.DFTPopupInputListener listener;
    DFTPopup dftPopup;

    public static ImageView imgDiemThuong;

    String id;

    public FragmentGioHang() {
        // Required empty public constructor
    }


    public FragmentGioHang(String id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_giohang_layout, container, false);
        myActivity = getActivity();

        imgHamburger = (ImageView) v.findViewById(R.id.imgHamburger);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);

        txtCount = (TextView) v.findViewById(R.id.txtCount);
        txtTongTien = (TextView) v.findViewById(R.id.txtTongTien);
        txtDatHang = (TextView) v.findViewById(R.id.txtDatHang);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        imgDiemThuong.setOnClickListener(this);
        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));

        txtDatHang.setVisibility(View.GONE);

        listView = (ListView) v.findViewById(R.id.listView);

        GetData();

        imgHamburger.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        txtDatHang.setOnClickListener(this);

        listenerChangeSoLuong = new AdapterGioHang.ChangeSoLuong() {
            @Override
            public void onChangeSoLuong(final int position) {
                listener = new DFTPopupInput.DFTPopupInputListener() {
                    @Override
                    public void userOK(String st) {
                        String soLuong = "1";
                        if (st != null) {
                            soLuong = st;
                        }
                        list.get(position).setTotalProductCart(soLuong);
                        list.get(position).setTotalPoinCart("0");
                        adapter.notifyDataSetChanged();
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        UpdateThanhTien(position);
                        TinhTong();
                    }

                    @Override
                    public void userCancel() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                };

                dialog = new DFTPopupInput(myActivity, "Số lượng", listener);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        };

        listenerChangeDiem = new AdapterGioHang.ChangeDiem() {
            @Override
            public void onChangeDiem(final int position) {
                listener = new DFTPopupInput.DFTPopupInputListener() {
                    @Override
                    public void userOK(String st) {
                        String soLuong = "0";
                        if (st != null) {
                            soLuong = st;
                        }
                        if (CheckCoin(Integer.parseInt(soLuong), position) >= 0) {
                            if (CheckThanhTien(Integer.parseInt(soLuong), position) == -1) {
                                list.get(position).setTotalPoinCart(st);
                            } else {
                                list.get(position).setTotalPoinCart(CheckThanhTien(Integer.parseInt(soLuong), position) + "");
                            }
                            adapter.notifyDataSetChanged();
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            UpdateThanhTien(position);
                            TinhTong();

                        } else {
                            ShowMSG("Bạn không đủ Điểm, bạn có thể sử dụng thêm " + (CheckCoin(Integer.parseInt(soLuong), position) + Integer.parseInt(soLuong)) + " điểm");
                            dialog.SetValue(CheckCoin(Integer.parseInt(soLuong), position) + Integer.parseInt(soLuong) + "");
                        }

                    }

                    @Override
                    public void userCancel() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                };

                dialog = new DFTPopupInput(myActivity, "Điểm", listener);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        };

        listenerXoaHH = new AdapterGioHang.XoaHH() {
            @Override
            public void onXoaHH(int position) {
                XoaHangHoa(list.get(position).get_id(), position);

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MainActivity) myActivity).loadFragment("chitiet_hanghoa", list.get(i).get_id());
            }
        });

        return v;
    }

    private int CheckCoin(int coin, int position) {
        int oldCoin = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTotalPoinCart() != null) {
                if (i != position) {
                    oldCoin += Integer.parseInt(list.get(i).getTotalPoinCart());
                }
            }
        }


        int kq = StaticVariable.user.getCoin() - (oldCoin + coin);

        return kq;
    }

    private int CheckThanhTien(int coin, int position) {
        GioHangObj obj = list.get(position);
        if (obj.getRateCoin() == null || (obj.getRateCoin() != null && Integer.parseInt(obj.getRateCoin()) == 0)) {
            return 0;
        }
        Long thanhTien = 0L;
        if (obj.getPriceNew() != null) {
            thanhTien = (Long.parseLong(obj.getPriceNew()) * Long.parseLong(obj.getTotalProductCart()));
        } else {
            thanhTien = obj.getPrice() * Long.parseLong(obj.getTotalProductCart());
        }
        Long coinMax = thanhTien / Long.valueOf(obj.getRateCoin());
        int intCoinMax = coinMax.intValue();
        if (coin > intCoinMax) {
            return intCoinMax;
        }
        return -1;
    }

    private void UpdateThanhTien(int position) {
        GioHangObj obj = list.get(position);
        Long thanhTien = 0L;
        if (obj.getPriceNew() != null) {
            thanhTien = (Long.parseLong(obj.getPriceNew()) * Long.parseLong(obj.getTotalProductCart()));
            if (obj.getRateCoin() != null) {
                thanhTien = thanhTien - Long.parseLong(obj.getTotalPoinCart()) * Long.parseLong(obj.getRateCoin());
                obj.setThanhTien(String.valueOf(thanhTien));
            }
        } else {
            thanhTien = obj.getPrice() * Long.parseLong(obj.getTotalProductCart());
            if (obj.getRateCoin() != null) {
                thanhTien = thanhTien - Long.parseLong(obj.getTotalPoinCart()) * Long.parseLong(obj.getRateCoin());
                obj.setThanhTien(String.valueOf(thanhTien));
            }
        }
        if (thanhTien < 0L) {
            thanhTien = 0L;
        }
        obj.setThanhTien(String.valueOf(thanhTien));
    }

    private void ThanhTien() {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                GioHangObj obj = list.get(i);
                Long thanhTien = 0L;
                if (obj.getPriceNew() != null) {
                    thanhTien = (Long.parseLong(obj.getPriceNew()) * Long.parseLong(obj.getTotalProductCart()));
                    if (obj.getRateCoin() != null) {
                        thanhTien = thanhTien - Long.parseLong(obj.getTotalPoinCart()) * Long.parseLong(obj.getRateCoin());
                        obj.setThanhTien(String.valueOf(thanhTien));
                    }
                } else {
                    thanhTien = obj.getPrice() * Long.parseLong(obj.getTotalProductCart());
                    if (obj.getRateCoin() != null) {
                        thanhTien = thanhTien - Long.parseLong(obj.getTotalPoinCart()) * Long.parseLong(obj.getRateCoin());
                        obj.setThanhTien(String.valueOf(thanhTien));
                    }
                }
                obj.setThanhTien(String.valueOf(thanhTien));
            }
        }
    }

    private void TinhTong() {
        if (list != null) {
            int soLuong = list.size();
            if (soLuong == 0) {
                txtCount.setText(myActivity.getString(R.string.text_giohang_chuachon));
                txtTongTien.setText("0 VNĐ");

                txtDatHang.setVisibility(View.GONE);
            } else {
                Long tongTien = 0L;
                for (int i = 0; i < list.size(); i++) {
                    GioHangObj obj = list.get(i);
                    tongTien = tongTien + Long.parseLong(obj.getThanhTien());
                }
                txtCount.setText("Tổng " + soLuong + " mặt hàng");
                txtTongTien.setText(DFTFormat.NumberFormat(tongTien.toString()) + " VNĐ");

                txtDatHang.setVisibility(View.VISIBLE);
            }
        }

    }

    private int TimViTri(String st) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).get_id().equalsIgnoreCase(st)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void SetGioHang() {
        if (TimViTri(id) != -1) {
            listView.setSelection(TimViTri(id));
        }
    }

    private void GetData() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiAddToCart, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                list = new ArrayList<GioHangObj>();
                                list = mapper.readValue(
                                        response.getJSONArray("data").toString(),
                                        new TypeReference<ArrayList<GioHangObj>>() {
                                        });
                                if (list != null && list.size() > 0) {
                                    adapter = new AdapterGioHang(myActivity, list, listenerChangeSoLuong, listenerChangeDiem, listenerXoaHH);
                                    listView.setAdapter(adapter);
                                    ThanhTien();
                                    TinhTong();
                                    SetGioHang();
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

    private void XoaHangHoa(String id, final int position) {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.PUT, StaticVariable.apiAddToCart + "/" + id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                                TinhTong();
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
            case R.id.txtDatHang:
                ((MainActivity) myActivity).loadFragment("dathang", list, null);
                break;
        }
    }
}
