package app.dft.appbanhang.diemthuong;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.object.ChiTietHangHoaObj;
import app.dft.appbanhang.object.DoiDiemThuongObj;
import app.dft.appbanhang.util.DFTMessage;
import app.dft.appbanhang.util.DFTPopup;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNhanDiemThuong extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgHamburger, imgSearch, imgCart;
    TextView txtCartNum;

    TextView txtTime;
    //ImageView imgBonus;
    Boolean bonus = false;

    DFTPopup dftPopup;

    DoiDiemThuongObj obj = new DoiDiemThuongObj();

    public static ImageView imgDiemThuong;

    public FragmentNhanDiemThuong() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nhan_diem_thuong, container, false);
        myActivity = getActivity();

        imgHamburger = (ImageView) v.findViewById(R.id.imgHamburger);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);
        txtTime = (TextView) v.findViewById(R.id.txtTime);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        imgDiemThuong.setOnClickListener(this);
        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));
//        imgBonus = (ImageView) v.findViewById(R.id.imgBonus);
//
//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        myActivity.getWindowManager().getDefaultDisplay()
//                .getMetrics(displaymetrics);
//        int height = displaymetrics.heightPixels;
//        int width = displaymetrics.widthPixels;
//        int imgWidth = width / 2;
//        int imgHeight = imgWidth;
//
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imgWidth, imgHeight);
//        imgBonus.setLayoutParams(layoutParams);

        txtTime.setTextColor(myActivity.getResources().getColor(R.color.colorCam));
        txtTime.setBackgroundResource(0);

        bonus = false;
        GetData();
        ShowData();

        imgHamburger.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

        txtTime.setOnClickListener(this);

        return v;
    }

    private void ShowData() {
        if (obj != null) {
            StaticVariable.timeBonus = obj;
            if (obj.getTime() == -1) {
                txtTime.setText("Không có điểm thưởng để nhận");
                txtTime.setTextColor(myActivity.getResources().getColor(R.color.colorCam));
                txtTime.setBackgroundResource(0);
                txtTime.setTextSize(15);
            } else {
                txtTime.setTextSize(24);
                Bonus();
            }
        }
    }

    private void GetData() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiGetTimeBonus, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                obj = new DoiDiemThuongObj();
                                obj = mapper.readValue(
                                        response.getJSONObject("data").toString(),
                                        new TypeReference<DoiDiemThuongObj>() {
                                        });
                                if (obj != null) {
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

    private void NhanDiemThuong() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.POST, StaticVariable.apiGetTimeBonus, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String msg = response.getString("msg");
                            if (code == 200) {
                                bonus = false;
                                ShowMSGSuccess(R.mipmap.icon_done, "Chúc mừng bạn đã nhận thành công " + obj.getCoin() + " điểm thưởng!");
                                StaticVariable.user.setCoin(StaticVariable.user.getCoin() + obj.getCoin());
                                ((MainActivity) myActivity).setDrawerMenu();
                                ObjectMapper mapper = new ObjectMapper();
                                obj = new DoiDiemThuongObj();
                                obj = mapper.readValue(
                                        response.getJSONObject("data").toString(),
                                        new TypeReference<DoiDiemThuongObj>() {
                                        });
                                if (obj != null) {
                                    StaticVariable.timeBonus = obj;
                                    ((MainActivity) myActivity).Bonus();
                                    ShowData();
                                }
                            } else {
                                ShowMSG(msg);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            ShowMSG(myActivity.getString(R.string.msg_loihethong));
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        ShowMSG(myActivity.getString(R.string.msg_loihethong));
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

    private void Bonus() {
        txtTime.setText(FomateTimeBonus(obj.getTime()));
        txtTime.setTextColor(myActivity.getResources().getColor(R.color.colorCam));
        txtTime.setBackgroundResource(0);
        Long maxTime = obj.getTime() * 1000;
        new CountDownTimer(maxTime, 1000) {

            public void onTick(long millisUntilFinished) {
                //obj.setTime(obj.getTime() - 1);
                obj = StaticVariable.timeBonus;
                if (obj.getTime() % 2 == 0) {
                    txtTime.setTextColor(myActivity.getResources().getColor(R.color.colorCam));
                } else {
                    txtTime.setTextColor(myActivity.getResources().getColor(R.color.colorXanhLuc));
                }
                txtTime.setText(FomateTimeBonus(obj.getTime()));
            }

            public void onFinish() {
                bonus = true;
                txtTime.setBackgroundResource(R.drawable.bg_button_xanhluc);
                txtTime.setTextColor(myActivity.getResources().getColor(R.color.colorTrang));
                txtTime.setText("Nhận " + obj.getCoin() + " điểm");
            }
        }.start();
    }

    private String FomateTimeBonus(Long time) {
        String st = "";
        if (time >= 86400L) {
            Long ngay = (time / 86400L);
            st += ngay.intValue() + " ngày ";
            time = time - ngay * 86400L;
        }
        if (time >= 3600L) {
            Long gio = time / 3600L;
            st += gio.intValue() + " giờ ";
            time = time - gio * 3600L;
        }
        if (time >= 60L) {
            Long phut = time / 60L;
            st += phut.intValue() + " phút ";
            time = time - phut * 60L;
        }
        st += time + " giây";
        return st;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtTime:
                if (bonus) {
                    NhanDiemThuong();
                }
                break;
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
        }
    }
}
