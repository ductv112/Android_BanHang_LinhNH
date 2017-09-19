package app.dft.appbanhang.giohang;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.object.ChiTietHangHoaObj;
import app.dft.appbanhang.object.GioHangObj;
import app.dft.appbanhang.util.DFTMessage;
import app.dft.appbanhang.util.DFTPopup;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentDatHang extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgHamburger, imgSearch, imgCart;
    TextView txtCartNum, txtDatHang;
    EditText edtFullName, edtEmail, edtSDT, edtDiaChi, edtGhiChu;
    List<GioHangObj> list;
    DFTPopup dftPopup;
    ProgressDialog progressDialog;
    String fullName, email, sdt, diaChi, ghiChu;
    List<ChiTietHangHoaObj> listMuaNgay;

    public static ImageView imgDiemThuong;

    public FragmentDatHang() {
        // Required empty public constructor
    }


    public FragmentDatHang(List<GioHangObj> list, List<ChiTietHangHoaObj> listMuaNgay) {
        this.list = list;
        this.listMuaNgay = listMuaNgay;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dathang_layout, container, false);
        myActivity = getActivity();

        imgHamburger = (ImageView) v.findViewById(R.id.imgHamburger);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);
        txtDatHang = (TextView) v.findViewById(R.id.txtDatHang);
        edtFullName = (EditText) v.findViewById(R.id.edtFullName);
        edtEmail = (EditText) v.findViewById(R.id.edtEmail);
        edtSDT = (EditText) v.findViewById(R.id.edtSDT);
        edtDiaChi = (EditText) v.findViewById(R.id.edtDiaChi);
        edtGhiChu = (EditText) v.findViewById(R.id.edtGhiChu);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        imgDiemThuong.setOnClickListener(this);
        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));

        imgHamburger.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        txtDatHang.setOnClickListener(this);

        ShowThongTin();
        return v;
    }

    private void ShowThongTin() {
        if (StaticVariable.user != null) {
            if (StaticVariable.user.getFullName() != null) {
                edtFullName.setText(StaticVariable.user.getFullName());
            }
            if (StaticVariable.user.getEmail() != null) {
                edtEmail.setText(StaticVariable.user.getEmail());
            }
            if (StaticVariable.user.getPhone() != null) {
                edtSDT.setText(StaticVariable.user.getPhone());
            }
            if (StaticVariable.user.getAddress() != null) {
                edtDiaChi.setText(StaticVariable.user.getAddress());
            }
        }

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
                fullName = edtFullName.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                sdt = edtSDT.getText().toString().trim();
                diaChi = edtDiaChi.getText().toString().trim();
                ghiChu = edtGhiChu.getText().toString().trim();
                if (Validate() == true) {
                    DatHang();
                }
                break;
        }
    }

    private boolean isValidatePhone(String phone) {
        if (phone.length() >= 10 && phone.length() <= 11) {
            return true;
        }

        return false;
    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean Validate() {
        if (fullName.equalsIgnoreCase("")) {
            String st = getString(R.string.msg_fullname);
            ShowMSG(st);
            return false;

        } else if (email.equalsIgnoreCase("")) {
            String st = getString(R.string.w04_error_email);
            ShowMSG(st);
            return false;

        } else if (!isValidEmail(email)) {
            String st = getString(R.string.w04_error_email2);
            ShowMSG(st);
            return false;

        } else if (sdt.equalsIgnoreCase("")) {
            String st = getString(R.string.w04_error_sdt);
            ShowMSG(st);
            return false;

        } else if (!isValidatePhone(sdt)) {
            String st = getString(R.string.w04_error_sdt2);
            ShowMSG(st);
            return false;

        } else if (diaChi.equalsIgnoreCase("")) {
            String st = getString(R.string.msg_diachi);
            ShowMSG(st);
            return false;

        }
        return true;
    }

    private void DatHang() {

        progressDialog = new ProgressDialog(myActivity);
        progressDialog.setMessage(myActivity.getString(R.string.w02_dangxuly));
        progressDialog.setProgress(3000);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        RequestQueue queue = MyVolley.getRequestQueue();
        JSONArray totalProduct = new JSONArray();

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {

                if (list.get(i).get_id() != null) {
                    try {
                        JSONObject hangHoaObj = new JSONObject();
                        hangHoaObj.put("productId", list.get(i).get_id());
                        hangHoaObj.put("total", list.get(i).getTotalProductCart());
                        hangHoaObj.put("coin", list.get(i).getTotalPoinCart());

                        totalProduct.put(hangHoaObj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }
        else if (listMuaNgay != null && listMuaNgay.size() > 0) {
            for (int i = 0; i < listMuaNgay.size(); i++) {
                try {
                    JSONObject hangHoaObj = new JSONObject();
                    hangHoaObj.put("productId", listMuaNgay.get(i).get_id());
                    hangHoaObj.put("total", listMuaNgay.get(i).getTotal());
                    hangHoaObj.put("coin", 0);

                    totalProduct.put(hangHoaObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("totalProduct", totalProduct);
            jsonObject.put("name", fullName);
            jsonObject.put("address", diaChi);
            jsonObject.put("phone", sdt);
            jsonObject.put("email", email);
            if (!ghiChu.equalsIgnoreCase("")) {
                jsonObject.put("note", ghiChu);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.POST, StaticVariable.apiDatHang, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String msg = response.getString("msg");
                            if (code == 200) {
                                progressDialog.dismiss();
                                ShowMSGSuccess(R.mipmap.icon_done, myActivity.getString(R.string.text_dathang_thanhcong));
                                // DS Đặt hàng
                                ((MainActivity) myActivity).loadFragment("danhsach_dathang");
                                StaticVariable.soLuong = 0;
                            } else {
                                ShowMSG(msg);
                                progressDialog.dismiss();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressDialog.dismiss();
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
}
