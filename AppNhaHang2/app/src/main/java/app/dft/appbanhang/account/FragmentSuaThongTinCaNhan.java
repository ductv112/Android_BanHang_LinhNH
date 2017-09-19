package app.dft.appbanhang.account;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.object.UserObj;
import app.dft.appbanhang.util.CircleTransform;
import app.dft.appbanhang.util.DFTFormat;
import app.dft.appbanhang.util.DFTPopup;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSuaThongTinCaNhan extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();

    EditText edtDiaChi, edtEmail, edtSoDienThoai;
    ImageView imgBack, imgSearch, imgCart;
    TextView txtXacNhan;
    ProgressDialog progressDialog;
    String fullName, diaChi, email, sdt;
    DFTPopup dftPopup;

    public static ImageView imgDiemThuong;

    public FragmentSuaThongTinCaNhan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chinhsua_thongtincanhan_layout, container, false);
        myActivity = getActivity();
        edtDiaChi = (EditText) v.findViewById(R.id.edtDiaChi);
        edtEmail = (EditText) v.findViewById(R.id.edtEmail);
        edtSoDienThoai = (EditText) v.findViewById(R.id.edtSoDienThoai);
        imgBack = (ImageView) v.findViewById(R.id.imgBack);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtXacNhan = (TextView) v.findViewById(R.id.txtXacNhan);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        imgDiemThuong.setOnClickListener(this);


        txtXacNhan.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgCart.setOnClickListener(this);

        ShowData();

        return v;
    }

    private void ShowData() {
        if (StaticVariable.user != null) {


            if (StaticVariable.user.getEmail() != null) {
                edtEmail.setText(StaticVariable.user.getEmail());
            }
            if (StaticVariable.user.getPhone() != null) {
                edtSoDienThoai.setText(StaticVariable.user.getPhone());
            }
            if (StaticVariable.user.getAddress() != null) {
                edtDiaChi.setText(StaticVariable.user.getAddress());
            }
        }
    }

    private void ChangeUserInfo() {
        progressDialog = new ProgressDialog(myActivity);
        progressDialog.setMessage(myActivity.getString(R.string.w02_dangxuly));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        RequestQueue queue = MyVolley.getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("phone", sdt);
            jsonObject.put("address", diaChi);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.PUT, StaticVariable.apiUpdateUser, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String msg = response.getString("msg");
                            if (code == 200) {

                                ObjectMapper mapper = new ObjectMapper();
                                UserObj user = new UserObj();
                                user = mapper.readValue(
                                        response.getJSONObject("data").toString(),
                                        new TypeReference<UserObj>() {
                                        });
                                if (user != null) {
                                    StaticVariable.user = user;

                                }
                                ((MainActivity) myActivity).loadFragment("thongtincanhan");
                                progressDialog.dismiss();
                            } else {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtXacNhan:
                diaChi = edtDiaChi.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                sdt = edtSoDienThoai.getText().toString().trim();

                if (Validate() == true) {
                    ChangeUserInfo();
                }

                break;
            case R.id.imgBack:
                myActivity.onBackPressed();
                break;
            case R.id.imgSearch:
                ((MainActivity) myActivity).loadFragment("search");
                break;

            case R.id.imgCart:
                ((MainActivity)myActivity).loadFragment("cart");
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

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidatePhone(String phone) {
        if (phone.length() >= 10 && phone.length() <= 11) {
            return true;
        }

        return false;
    }

    private boolean Validate() {

        if (diaChi.equalsIgnoreCase("")) {
            String st = getString(R.string.w08_diachi);
            ShowMSG(st);
            return false;
        } else if (email.equalsIgnoreCase("")) {
            String st = getString(R.string.w08_email);
            ShowMSG(st);
            return false;
        } else if (!isValidEmail(email)) {
            String st = getString(R.string.w08_email2);
            ShowMSG(st);
            return false;
        } else if (sdt.equalsIgnoreCase("")) {
            String st = getString(R.string.w08_sdt);
            ShowMSG(st);
            return false;
        } else if (!isValidatePhone(sdt)) {
            String st = getString(R.string.w08_sdt2);
            ShowMSG(st);
            return false;
        }


        return true;
    }
}
