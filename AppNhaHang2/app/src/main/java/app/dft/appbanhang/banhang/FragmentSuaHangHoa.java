package app.dft.appbanhang.banhang;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.giamgia.AdapterLocLoai;
import app.dft.appbanhang.giamgia.AdapterLocThuongHieu;
import app.dft.appbanhang.object.ChiTietHangHoaObj;
import app.dft.appbanhang.object.LoaiHangHoaObj;
import app.dft.appbanhang.object.NhomHangHoaObj;
import app.dft.appbanhang.object.ResposeArrayObj;
import app.dft.appbanhang.object.ThuongHieuObj;
import app.dft.appbanhang.util.DFTFormat;
import app.dft.appbanhang.util.DFTMessage;
import app.dft.appbanhang.util.DFTPopup;
import app.dft.appbanhang.util.MultiSpinner;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;
import app.dft.appbanhang.volley.VolleyMultipartRequest;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class FragmentSuaHangHoa extends Fragment implements View.OnClickListener, MultiSpinner.MultiSpinnerListener {

    Activity myActivity = new Activity();
    ImageView imgHamburger, imgSearch, imgCart, imgAnhHangHoa;
    TextView txtCartNum, txtDangHangHoa, txtTaiAnh;
    EditText edtTag, edtMoTa, edtMoTaNgan, edtTyGiaCoin, edtSoLuong, edtDonGia, edtTenHangHoa, edtBaoHanh;
    LinearLayout lineTong;
    Spinner spinnerThuongHieu, spinnerNhomHangHoa;
    MultiSpinner spinnerLoaiHangHoa;

    List<NhomHangHoaObj> listNhomHangHoa = new ArrayList<>();
    AdapterNhomHangHoa adapterNhomHangHoa;
    List<LoaiHangHoaObj> listLoaiHangHoa = new ArrayList<>();
    AdapterLocLoai adapterLocLoai;
    List<ThuongHieuObj> listThuongHieu = new ArrayList<>();
    AdapterLocThuongHieu adapterLocThuongHieu;

    DFTPopup dftPopup;
    ProgressDialog progressDialog;
    //String thuongHieu, tenHangHoa, donGia, soLuong, baoHanh, nhomHangHoa;

    Uri imageUri;
    String anhHangHoa = null;
    String id;
    ChiTietHangHoaObj chiTietHangHoaObj;

    boolean[] selected;

    public FragmentSuaHangHoa() {
        // Required empty public constructor
    }


    public FragmentSuaHangHoa(String id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dang_hanghoa_moi, container, false);
        myActivity = getActivity();

        imgHamburger = (ImageView) v.findViewById(R.id.imgHamburger);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);
        txtDangHangHoa = (TextView) v.findViewById(R.id.txtDangHangHoa);
        imgAnhHangHoa = (ImageView) v.findViewById(R.id.imgAnhHangHoa);
        txtTaiAnh = (TextView) v.findViewById(R.id.txtTaiAnh);
        lineTong = (LinearLayout) v.findViewById(R.id.lineTong);
        edtBaoHanh = (EditText) v.findViewById(R.id.edtBaoHanh);

        edtTag = (EditText) v.findViewById(R.id.edtTag);
        edtMoTa = (EditText) v.findViewById(R.id.edtMoTa);
        edtMoTaNgan = (EditText) v.findViewById(R.id.edtMoTaNgan);
        edtTyGiaCoin = (EditText) v.findViewById(R.id.edtTyGiaCoin);
        edtSoLuong = (EditText) v.findViewById(R.id.edtSoLuong);
        edtDonGia = (EditText) v.findViewById(R.id.edtDonGia);
        edtTenHangHoa = (EditText) v.findViewById(R.id.edtTenHangHoa);
        spinnerThuongHieu = (Spinner) v.findViewById(R.id.spinnerThuongHieu);
        spinnerLoaiHangHoa = (MultiSpinner) v.findViewById(R.id.spinnerLoaiHangHoa);
        spinnerNhomHangHoa = (Spinner) v.findViewById(R.id.spinnerNhomHangHoa);

        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));

        imgHamburger.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        txtDangHangHoa.setOnClickListener(this);
        imgAnhHangHoa.setOnClickListener(this);

        txtTaiAnh.setVisibility(View.GONE);
        GetChiTietHangHoa();

        spinnerNhomHangHoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ResetComboCon();
                String nhomHangHoa = listNhomHangHoa.get(i).get_id();
                GetLoaiHangHoa(nhomHangHoa);
//                if (chiTietHangHoaObj != null && chiTietHangHoaObj.getProductTypeId() != null && chiTietHangHoaObj.getProductTypeId().size() > 0) {
//                    GetLoaiHangHoa(nhomHangHoa);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        spinnerThuongHieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                thuongHieu = listThuongHieu.get(i).get_id();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        edtDonGia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!edtDonGia.getText().toString().equalsIgnoreCase("")) {
                    if (!b) {
                        edtDonGia.setText(DFTFormat.MonneyFormat2(edtDonGia.getText().toString()));
                    }
                }
            }
        });


        lineTong.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });

        return v;
    }

    private void ResetComboCon() {
        List<String> list = new ArrayList<>();
        spinnerLoaiHangHoa.setItems(list, "", this);

        listThuongHieu.clear();
        adapterLocThuongHieu = new AdapterLocThuongHieu(myActivity, listThuongHieu);
        spinnerThuongHieu.setAdapter(adapterLocThuongHieu);
    }

    private void ShowData() {

        if (chiTietHangHoaObj != null) {
            if (chiTietHangHoaObj.getImage() != null) {
                Picasso.with(myActivity).load(DFTFormat.UrlImage(chiTietHangHoaObj.getImage())).into(imgAnhHangHoa);
                anhHangHoa = chiTietHangHoaObj.getImage();
            }
            if (chiTietHangHoaObj.getName() != null) {
                edtTenHangHoa.setText(chiTietHangHoaObj.getName());
            }
            edtDonGia.setText(DFTFormat.NumberFormat(String.valueOf(chiTietHangHoaObj.getPrice())));
            edtSoLuong.setText(String.valueOf(chiTietHangHoaObj.getTotal()));
            edtTyGiaCoin.setText(String.valueOf(chiTietHangHoaObj.getRateCoin()));
            if (chiTietHangHoaObj.getDescription() != null) {
                edtMoTa.setText(chiTietHangHoaObj.getDescription());
            }
            if (chiTietHangHoaObj.getDescriptionShort() != null) {
                edtMoTaNgan.setText(chiTietHangHoaObj.getDescriptionShort());
            }
            edtBaoHanh.setText(String.valueOf(chiTietHangHoaObj.getWarranty()));
            if (chiTietHangHoaObj.getTag() != null && chiTietHangHoaObj.getTag().size() > 0) {
                edtTag.setText(chiTietHangHoaObj.getTag().get(0));
            }

            if (chiTietHangHoaObj.getProductGroupId() != null) {
                GetNhomHangHoa();
            }
        }
        if (StaticVariable.user != null) {
            edtTyGiaCoin.setText(String.valueOf(StaticVariable.user.getRateCoin()));
        }

    }

    private void SetLoaiHangHoa() {
        for (int i = 0; i < chiTietHangHoaObj.getProductTypeId().size(); i++) {
            int vitri = TimViTriLoaiHH(chiTietHangHoaObj.getProductTypeId().get(i).get_id());
            if (vitri != -1) {
                spinnerLoaiHangHoa.UpdateItems(vitri);
            }
        }
    }

    private void ShowSpinnerLoaiHangHoa() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listLoaiHangHoa.size(); i++) {
            list.add(listLoaiHangHoa.get(i).getName());
        }
        spinnerLoaiHangHoa.setItems(list, "", this);
        SetLoaiHangHoa();

        if (spinnerLoaiHangHoa.getSelectedItem() == null || spinnerLoaiHangHoa.getSelectedItem().toString().trim().equalsIgnoreCase("")) {
            for (int i = 0; i < selected.length; i++) {
                selected[i] = false;
            }
        }

        GetThuongHieu();
    }

    private int TimViTriLoaiHH(String id) {
        for (int i = 0; i < listLoaiHangHoa.size(); i++) {
            if (listLoaiHangHoa.get(i).get_id().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    private int TimViTriNhomHH(String id) {
        for (int i = 0; i < listNhomHangHoa.size(); i++) {
            if (listNhomHangHoa.get(i).get_id().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    private void SetNhomHH() {
        if (chiTietHangHoaObj.getProductGroupId() != null && TimViTriNhomHH(chiTietHangHoaObj.getProductGroupId().getName()) != -1) {
            spinnerNhomHangHoa.setSelection(TimViTriNhomHH(chiTietHangHoaObj.getProductGroupId().getName()));
        }
        String nhomHangHoa = listNhomHangHoa.get(spinnerNhomHangHoa.getSelectedItemPosition()).get_id();
        GetLoaiHangHoa(nhomHangHoa);
    }

    private void GetNhomHangHoa() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiGetNhomHangHoa, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                listNhomHangHoa = mapper.readValue(
                                        response.getJSONArray("data").toString(),
                                        new TypeReference<ArrayList<NhomHangHoaObj>>() {
                                        });
                                if (listNhomHangHoa != null) {
                                    adapterNhomHangHoa = new AdapterNhomHangHoa(myActivity, listNhomHangHoa);
                                    spinnerNhomHangHoa.setAdapter(adapterNhomHangHoa);
                                    SetNhomHH();
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

    private void GetLoaiHangHoa(String nhomId) {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiGetLoaiHangHoaTheoNhom + "&productGroupId=" + nhomId, null, new Response.Listener<JSONObject>() {

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
                                    ShowSpinnerLoaiHangHoa();

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
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < listLoaiHangHoa.size(); i++) {
            if (selected[i] == true) {
                jsonArray.put(listLoaiHangHoa.get(i).get_id());
            }
        }
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiGetThuongHieu + "&productTypeId=" + jsonArray, null, new Response.Listener<JSONObject>() {

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
                                    spinnerThuongHieu.setAdapter(adapterLocThuongHieu);
                                    SetThuongHieu();
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

    private int TimViTri(String st) {
        if (listThuongHieu != null && listThuongHieu.size() > 0) {
            for (int i = 0; i < listThuongHieu.size(); i++) {
                if (listThuongHieu.get(i).getName().equalsIgnoreCase(st)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void SetThuongHieu() {
        if (chiTietHangHoaObj.getTrademarkId() != null && TimViTri(chiTietHangHoaObj.getTrademarkId().getName()) != -1) {
            spinnerThuongHieu.setSelection(TimViTri(chiTietHangHoaObj.getTrademarkId().getName()));
        }

    }

    private boolean CheckChonLoaiHH() {
        if (selected != null && selected.length > 0) {
            for (int i = 0; i < selected.length; i++) {
                if (selected[i] == true) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean Validate() {
        if (anhHangHoa == null) {
            String st = getString(R.string.msg_anhhanghoa);
            ShowMSG(st);
            return false;

        } else if (spinnerLoaiHangHoa.getSelectedItem() == null || spinnerLoaiHangHoa.getSelectedItem().toString().trim().equalsIgnoreCase("")) {
            String st = getString(R.string.msg_loaihanghoa);
            ShowMSG(st);
            return false;

        } else if (spinnerThuongHieu.getSelectedItem() == null) {
            String st = getString(R.string.msg_thuonghieu);
            ShowMSG(st);
            return false;

        } else if (edtTenHangHoa.getText().toString().trim().equalsIgnoreCase("")) {
            String st = getString(R.string.msg_tenhanghoa);
            ShowMSG(st);
            return false;

        } else if (edtDonGia.getText().toString().trim().equalsIgnoreCase("")) {
            String st = getString(R.string.msg_dongia);
            ShowMSG(st);
            return false;

        } else if (edtSoLuong.getText().toString().trim().equalsIgnoreCase("")) {
            String st = getString(R.string.msg_soluong);
            ShowMSG(st);
            return false;

        }
        return true;
    }

    private void SuaHangHoa() {

        progressDialog = new ProgressDialog(myActivity);
        progressDialog.setMessage(myActivity.getString(R.string.w02_dangxuly));
        progressDialog.setProgress(3000);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        RequestQueue queue = MyVolley.getRequestQueue();
        JSONArray jsonArray = new JSONArray();
        String newTag = "";
        for (int i = 0; i < listLoaiHangHoa.size(); i++) {
            if (selected[i] == true) {
                jsonArray.put(listLoaiHangHoa.get(i).get_id());
                newTag = newTag + listLoaiHangHoa.get(i).getName() + ",";
            }
        }
        String thuongHieu = listThuongHieu.get(spinnerThuongHieu.getSelectedItemPosition()).get_id();
        String nhomHangHoa = listNhomHangHoa.get(spinnerNhomHangHoa.getSelectedItemPosition()).get_id();
        newTag = newTag + thuongHieu + "," + nhomHangHoa;

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("productTypeId", jsonArray);
            jsonObject.put("trademarkId", thuongHieu);
            jsonObject.put("name", edtTenHangHoa.getText().toString().trim());
            jsonObject.put("price", edtDonGia.getText().toString().trim().replace(".", ""));
            jsonObject.put("total", edtSoLuong.getText().toString().trim());
            jsonObject.put("newTag", newTag);
            if (!edtTyGiaCoin.getText().toString().equalsIgnoreCase("")) {
                jsonObject.put("rateCoin", edtTyGiaCoin.getText().toString());
            }
            if (!edtMoTa.getText().toString().equalsIgnoreCase("")) {
                jsonObject.put("description", edtMoTa.getText().toString());
            }
            if (!edtMoTaNgan.getText().toString().equalsIgnoreCase("")) {
                jsonObject.put("descriptionShort", edtMoTaNgan.getText().toString());
            }
            if (!edtBaoHanh.getText().toString().trim().equalsIgnoreCase("")) {
                jsonObject.put("warranty", edtBaoHanh.getText().toString().trim());
            }
            if (!edtTag.getText().toString().equalsIgnoreCase("")) {
                jsonObject.put("tag", edtTag.getText().toString());
            }
            if (anhHangHoa != null) {
                jsonObject.put("product", anhHangHoa);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.PUT, StaticVariable.apiGetChiTietHangHoa + id, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String msg = response.getString("msg");
                            if (code == 200) {
                                progressDialog.dismiss();
                                ShowMSG(msg);
                                // DS Đặt hàng
                                ((MainActivity) myActivity).loadFragment("danhsach_hanghoa");
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

    private void UploadAnh() {
//        progressDialog = new ProgressDialog(myActivity);
//        progressDialog.setMessage(myActivity.getString(R.string.w02_dangxuly));
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        final File file = new File(imageUri.getPath());
        RequestQueue queue = MyVolley.getRequestQueue();

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, StaticVariable.apiUploadAnh, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    String resultResponse = new String(response.data);


                    //UpdateAvatar

                    ObjectMapper mapper = new ObjectMapper();
                    ResposeArrayObj resposeObj = mapper.readValue(
                            resultResponse,
                            new TypeReference<ResposeArrayObj>() {
                            });
                    if (resposeObj != null) {
                        anhHangHoa = resposeObj.getData().get(0);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                ShowMSG(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fileSave", "product");
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
                params.put("product", new DataPart("avatar.jpg", data, "image/jpeg"));

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
            case R.id.txtDangHangHoa:
                if (Validate() == true) {
                    SuaHangHoa();
                }
                break;

            case R.id.imgAnhHangHoa:
                CropImage.activity(null)
                        .start(getContext(), this);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                Picasso.with(myActivity).load(imageUri).into(imgAnhHangHoa);
                txtTaiAnh.setVisibility(View.GONE);
                UploadAnh();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(myActivity, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onItemsSelected(boolean[] selected) {
        this.selected = selected;
        GetThuongHieu();
    }
}
