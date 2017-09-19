package app.dft.appbanhang.account;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.object.ResposeObj;
import app.dft.appbanhang.object.UserObj;
import app.dft.appbanhang.util.CircleTransform;
import app.dft.appbanhang.util.DFTFormat;
import app.dft.appbanhang.util.DFTPopup;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;
import app.dft.appbanhang.volley.VolleyMultipartRequest;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThongTinNguoiDung extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgAvatar, imgEditUserName, imgHamburger, imgSearch, imgCart;
    TextView txtDoiMatKhau, txtDiaChi, txtEmail, txtSDT, txtChinhSua, txtDangXuat, txtCartNum, txtLuu, txtHuy;
    EditText edtUserName;
    LinearLayout lineTong, layoutDoiMatKhau;
    Uri imageUri;
    DFTPopup dftPopup;
    ProgressDialog progressDialog;
    String fullName, diaChi, email, sdt;

    public static ImageView imgDiemThuong;

    boolean flagEditUser = false;

    public FragmentThongTinNguoiDung() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thongtin_nguoidung_layout, container, false);
        myActivity = getActivity();


        imgHamburger = (ImageView) v.findViewById(R.id.imgHamburger);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        imgAvatar = (ImageView) v.findViewById(R.id.imgAvatar);
        imgEditUserName = (ImageView) v.findViewById(R.id.imgEditUserName);
        edtUserName = (EditText) v.findViewById(R.id.edtUserName);
        txtDoiMatKhau = (TextView) v.findViewById(R.id.txtDoiMatKhau);
        txtDiaChi = (TextView) v.findViewById(R.id.txtDiaChi);
        txtEmail = (TextView) v.findViewById(R.id.txtEmail);
        txtSDT = (TextView) v.findViewById(R.id.txtSDT);
        txtChinhSua = (TextView) v.findViewById(R.id.txtChinhSua);
        txtDangXuat = (TextView) v.findViewById(R.id.txtDangXuat);
        lineTong = (LinearLayout) v.findViewById(R.id.lineTong);
        layoutDoiMatKhau = (LinearLayout) v.findViewById(R.id.layoutDoiMatKhau);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);
        txtHuy = (TextView) v.findViewById(R.id.txtHuy);
        txtLuu = (TextView) v.findViewById(R.id.txtLuu);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        imgDiemThuong.setOnClickListener(this);
        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));

        imgAvatar.setOnClickListener(this);
        txtDoiMatKhau.setOnClickListener(this);
        txtChinhSua.setOnClickListener(this);
        txtDangXuat.setOnClickListener(this);
        imgEditUserName.setOnClickListener(this);
        imgHamburger.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        txtHuy.setOnClickListener(this);
        txtLuu.setOnClickListener(this);

        if (StaticVariable.user != null && StaticVariable.user.getSocial() != null) {
            if (StaticVariable.user.getSocial().equalsIgnoreCase("1")) {
                layoutDoiMatKhau.setVisibility(View.GONE);
            } else {
                layoutDoiMatKhau.setVisibility(View.VISIBLE);
            }
        }

        lineTong.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });

        ShowData();
        Disnable();

        return v;
    }

    private void ShowData() {
        if (StaticVariable.user != null) {
            if (DFTFormat.UrlImage(StaticVariable.user.getAvatar()) != null) {
                Picasso.with(myActivity).load(DFTFormat.UrlImage(StaticVariable.user.getAvatar())).transform(new CircleTransform()).into(imgAvatar);
            } else {
                Picasso.with(myActivity).load(R.mipmap.ic_wos).into(imgAvatar);
            }
            if (StaticVariable.user.getFullName() != null) {
                edtUserName.setText(StaticVariable.user.getFullName());
            } else {
                edtUserName.setText(StaticVariable.user.getUsername());
            }
            if (StaticVariable.user.getEmail() != null) {
                txtEmail.setText(StaticVariable.user.getEmail());
            } else {
                txtEmail.setText(R.string.w08_chuacapnhat);
            }
            if (StaticVariable.user.getPhone() != null) {
                txtSDT.setText(StaticVariable.user.getPhone());
            } else {
                txtSDT.setText(R.string.w08_chuacapnhat);
            }
            if (StaticVariable.user.getAddress() != null) {
                txtDiaChi.setText(StaticVariable.user.getAddress());
            } else {
                txtDiaChi.setText(R.string.w08_chuacapnhat);
            }

        }
    }

    private void Disnable() {
        edtUserName.setEnabled(false);
        edtUserName.setBackgroundResource(R.drawable.bg_edittext_info2);
        txtLuu.setVisibility(View.GONE);
        txtHuy.setVisibility(View.GONE);
        imgEditUserName.setVisibility(View.VISIBLE);

    }

    private void Enable() {
        edtUserName.setEnabled(true);
        edtUserName.setBackgroundResource(R.drawable.bg_while_edittext);
        txtLuu.setVisibility(View.VISIBLE);
        txtHuy.setVisibility(View.VISIBLE);
        imgEditUserName.setVisibility(View.GONE);
    }


    private void ChangeUserInfo() {
        progressDialog = new ProgressDialog(myActivity);
        progressDialog.setMessage(myActivity.getString(R.string.w02_dangxuly));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        RequestQueue queue = MyVolley.getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fullName", fullName);

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
                                    ShowData();
                                }
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


    private void ChangeAvatar() {
        progressDialog = new ProgressDialog(myActivity);
        progressDialog.setMessage(myActivity.getString(R.string.w02_dangxuly));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        final File file = new File(imageUri.getPath());
        RequestQueue queue = MyVolley.getRequestQueue();

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.PUT, StaticVariable.apiThayDoiAvatar, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    String resultResponse = new String(response.data);


                    //UpdateAvatar

                    ObjectMapper mapper = new ObjectMapper();
                    ResposeObj resposeObj = mapper.readValue(
                            resultResponse,
                            new TypeReference<ResposeObj>() {
                            });
                    if (resposeObj != null) {
                        StaticVariable.user.setAvatar(resposeObj.getData());
                        if (DFTFormat.UrlImage(StaticVariable.user.getAvatar()) != null) {
                            Picasso.with(myActivity).load(DFTFormat.UrlImage(StaticVariable.user.getAvatar())).transform(new CircleTransform()).into(imgAvatar);
                            ((MainActivity) getActivity()).setDrawerMenu();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                ShowMSG(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                //params.put("fullName", edtUserName.getText().toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                byte[] data = null;
                try {
                    data = FileUtils.readFileToByteArray(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                params.put("avatar", new DataPart("avatar.jpg", data, "image/jpeg"));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Requested-With", "XMLHttpRequest");
                headers.put("x-access-token", StaticVariable.userToken);

                return headers;
            }
        };

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(multipartRequest);
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


            case R.id.imgAvatar:
                // start picker to get image for cropping and then use the image in cropping activity
//                CropImage.activity(null)
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .start(myActivity);

                // for fragment (DO NOT use `getActivity()`)
                CropImage.activity(null)
                        .start(getContext(), this);
                break;

            case R.id.txtDangXuat:
                Logout();
                break;

            case R.id.txtChinhSua:
                ((MainActivity) myActivity).loadFragment("chinhsua_profile");

                break;


            case R.id.txtDoiMatKhau:
                ((MainActivity) myActivity).loadFragment("doimatkhau");
                break;

            case R.id.imgEditUserName:

                Enable();

                break;

            case R.id.txtHuy:
                Disnable();
                break;

            case R.id.txtLuu:
                Disnable();
                fullName = edtUserName.getText().toString().trim();

                ChangeUserInfo();
                break;
        }
    }

    private boolean isValidateCharacter(String password) {
        if (password.length() >= 4 && password.length() <= 20) {
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

    private boolean isValidatePhone(String phone) {
        if (phone.length() >= 10 && phone.length() <= 11) {
            return true;
        }

        return false;
    }

    private boolean Validate() {

        if (fullName.equalsIgnoreCase("")) {
            String st = getString(R.string.w08_fullname);
            ShowMSG(st);
            return false;
        } else if (!isValidateCharacter(fullName)) {
            String st = getString(R.string.w08_fullname2);
            ShowMSG(st);
            return false;
        } else if (diaChi.equalsIgnoreCase("")) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                //Picasso.with(myActivity).load(imageUri).transform(new CircleTransform()).into(imgAvatar);
                ChangeAvatar();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(myActivity, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void Logout() {
        progressDialog = new ProgressDialog(myActivity);
        progressDialog.setMessage(getString(R.string.w02_dangxuly));
        progressDialog.setProgress(3000);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.POST, StaticVariable.apiLogOut, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                StaticVariable.user = null;
                                SharedPreferences preferences = StaticVariable.getPref(myActivity);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(StaticVariable.typeLogin, "0");
                                editor.putString(StaticVariable.userName, "");
                                editor.putString(StaticVariable.passWord, "");
                                editor.commit();
                                //listFragment.clear();
                                progressDialog.dismiss();
                                Intent intent = new Intent(getContext(), SplashActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("from", "main");
                                startActivity(intent);

                                ((MainActivity)myActivity).countDownTimer.cancel();
                                myActivity.finish();
                            }
                        } catch (JSONException e) {
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
}
