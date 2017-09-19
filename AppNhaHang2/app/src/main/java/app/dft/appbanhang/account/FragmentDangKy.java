package app.dft.appbanhang.account;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.howtouse.HowToUseActivity;
import app.dft.appbanhang.object.UserObj;
import app.dft.appbanhang.util.DFTPopup;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDangKy extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();


    EditText edtTenDangNhap, edtEmail, edtSDT, edtMatKhau, edtNhapLaiMatKhau;
    TextView txtDieuKhoanSD, txtDangKy, txtQuayLaiDangNhap;
    LinearLayout lineTong;

    String userName, passWord, email, confirmPass, sdt;
    DFTPopup dftPopup;
    ProgressDialog progressDialog;

    public FragmentDangKy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dangky_layout, container, false);
        myActivity = getActivity();

        edtTenDangNhap = (EditText) v.findViewById(R.id.edtTenDangNhap);
        edtEmail = (EditText) v.findViewById(R.id.edtEmail);
        edtSDT = (EditText) v.findViewById(R.id.edtSDT);
        edtMatKhau = (EditText) v.findViewById(R.id.edtMatKhau);
        edtNhapLaiMatKhau = (EditText) v.findViewById(R.id.edtNhapLaiMatKhau);
        txtDieuKhoanSD = (TextView) v.findViewById(R.id.txtDieuKhoanSD);
        txtDangKy = (TextView) v.findViewById(R.id.txtDangKy);
        txtQuayLaiDangNhap = (TextView) v.findViewById(R.id.txtQuayLaiDangNhap);
        lineTong = (LinearLayout) v.findViewById(R.id.lineTong);


        txtDieuKhoanSD.setOnClickListener(this);
        txtDangKy.setOnClickListener(this);
        txtQuayLaiDangNhap.setOnClickListener(this);

        lineTong.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });

        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.txtQuayLaiDangNhap:
                myActivity.onBackPressed();
                break;

            case R.id.txtDieuKhoanSD:
                ((SplashActivity) myActivity).loadFragment("dieukhoan_sudung");
                break;
            case R.id.txtDangKy:
                userName = edtTenDangNhap.getText().toString();
                passWord = edtMatKhau.getText().toString();
                confirmPass = edtNhapLaiMatKhau.getText().toString();
                email = edtEmail.getText().toString();
                sdt = edtSDT.getText().toString();

                if (Validate() == true) {
                    DangKy();
                }

                break;
        }

    }

    private void DangKy() {

        progressDialog = new ProgressDialog(myActivity);
        progressDialog.setMessage(myActivity.getString(R.string.w02_dangxuly));
        progressDialog.setProgress(3000);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        RequestQueue queue = MyVolley.getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", userName);
            jsonObject.put("password", passWord);
            jsonObject.put("email", email);
            jsonObject.put("phone", sdt);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.POST, StaticVariable.apiDangKy, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String msg = response.getString("msg");
                            if (code == 200) {

                                JSONObject jsonObject = response.getJSONObject("data");
                                String token = jsonObject.getString("token");
                                StaticVariable.userToken = token;

                                ObjectMapper mapper = new ObjectMapper();
                                UserObj user = new UserObj();
                                user = mapper.readValue(
                                        jsonObject.getJSONObject("user").toString(),
                                        new TypeReference<UserObj>() {
                                        });

                                if (user != null) {
                                    StaticVariable.user = user;
                                    SharedPreferences preferences = StaticVariable.getPref(myActivity);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(StaticVariable.typeLogin, "1");
                                    editor.putString(StaticVariable.userName, userName);
                                    editor.putString(StaticVariable.passWord, passWord);
                                    editor.commit();

                                    Intent intent = new Intent(myActivity, MainActivity.class);
                                    startActivity(intent);
                                    progressDialog.dismiss();
                                    myActivity.finish();
                                }

                            } else {
                                progressDialog.dismiss();
                                ShowMSG(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                });
        queue.add(myReq);
    }

    private boolean isValidUserName2(String user) {
        if (user != "" && user != null) {
            return true;
        }

        return false;
    }

    private boolean isValidUserName(String user) {
        if (!checkSpace(user)) {
            return false;
        }
        String USER_NAME = "[ a-zA-Z0-9.?_ ]*";
        Pattern pattern = Pattern.compile(USER_NAME);
        Matcher matcher = pattern.matcher(user);
        return matcher.matches();
    }

    private boolean checkSpace(String st) {

        if (st.contains(" ")) {
            return false;
        }
        return true;
    }


    private boolean isValidUserName3(String user) {
        if (user.length() >= 4 && user.length() <= 20) {
            return true;
        }

        return false;
    }

    private boolean isValidatePassword(String password) {
        if (password.length() >= 4 && password.length() <= 20) {
            return true;
        }

        return false;
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

    private boolean Validate() {

        if (userName.equalsIgnoreCase("")) {
            String st = getString(R.string.w04_error_username);
            ShowMSG(st);
            return false;

        } else if (!isValidUserName(userName)) {
            String st = getString(R.string.w04_error_username2);
            ShowMSG(st);
            return false;

        } else if (!isValidUserName3(userName)) {
            String st = getString(R.string.w04_error_username3);
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
        } else if (passWord.equalsIgnoreCase("")) {
            String st = getString(R.string.w04_error_pass);
            ShowMSG(st);
            return false;
        } else if (!isValidatePassword(passWord)) {
            String st = getString(R.string.w04_error_pass2);
            ShowMSG(st);
            return false;

        } else if (confirmPass.equalsIgnoreCase("")) {
            String st = getString(R.string.w04_error_confirmPass);
            ShowMSG(st);
            return false;
        } else if (!confirmPass.equals(passWord)) {
            String st = getString(R.string.w04_error_confirmPass2);
            ShowMSG(st);
            return false;
        }

        return true;
    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
