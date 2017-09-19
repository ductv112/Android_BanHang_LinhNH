package app.dft.appbanhang.thongtinungdung;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.giohang.AdapterChiTietDatHang;
import app.dft.appbanhang.object.DatHangObj;
import app.dft.appbanhang.object.DonHangObj;
import app.dft.appbanhang.object.ThongTinUngDungObj;
import app.dft.appbanhang.util.CircleTransform;
import app.dft.appbanhang.util.DFTFormat;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThongTinLienHe extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgHamburger, imgSearch, imgCart, imgLogo;
    TextView txtCartNum;

    TextView txtInfoMail, txtInfoWeb, txtInfoTell, txtInfoSkype, txtInfoFacebook, txtPhienBan;
    RelativeLayout layoutEmail, layoutWeb, layoutPhone, layoutSkype, layoutFacebook;

    ThongTinUngDungObj thongTinUngDungObj;
    public static String FACEBOOK_URL = "";
    public static String FACEBOOK_PAGE_ID = "";

    public static ImageView imgDiemThuong;

    public FragmentThongTinLienHe() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thongtin_lienhe, container, false);
        myActivity = getActivity();

        imgHamburger = (ImageView) v.findViewById(R.id.imgHamburger);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);

        layoutEmail = (RelativeLayout) v.findViewById(R.id.layoutEmail);
        layoutWeb = (RelativeLayout) v.findViewById(R.id.layoutWeb);
        layoutPhone = (RelativeLayout) v.findViewById(R.id.layoutPhone);
        layoutSkype = (RelativeLayout) v.findViewById(R.id.layoutSkype);
        layoutFacebook = (RelativeLayout) v.findViewById(R.id.layoutFacebook);

        imgLogo = (ImageView) v.findViewById(R.id.imgLogo);

        txtInfoMail = (TextView) v.findViewById(R.id.txtInfoMail);
        txtInfoWeb = (TextView) v.findViewById(R.id.txtInfoWeb);
        txtInfoTell = (TextView) v.findViewById(R.id.txtInfoTell);
        txtInfoSkype = (TextView) v.findViewById(R.id.txtInfoSkype);
        txtInfoFacebook = (TextView) v.findViewById(R.id.txtInfoFacebook);
        txtPhienBan = (TextView) v.findViewById(R.id.txtPhienBan);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        imgDiemThuong.setOnClickListener(this);
        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));


        imgHamburger.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

        layoutEmail.setOnClickListener(this);
        layoutWeb.setOnClickListener(this);
        layoutPhone.setOnClickListener(this);
        layoutSkype.setOnClickListener(this);
        layoutFacebook.setOnClickListener(this);
        GetData();

        return v;
    }

    private void ShowThongTinUngDung() {

        if (thongTinUngDungObj != null) {

            if (thongTinUngDungObj.getLogo() != null) {
                Picasso.with(myActivity).load(DFTFormat.UrlImage(thongTinUngDungObj.getLogo())).transform(new CircleTransform()).into(imgLogo);
            } else {
                Picasso.with(myActivity).load(R.mipmap.ic_wos).into(imgLogo);
            }

            if (thongTinUngDungObj.getVersionAndroid() != null) {
                txtPhienBan.setText("Phiên bản " + thongTinUngDungObj.getVersionAndroid());
            }
            if (thongTinUngDungObj.getEmail() != null) {
                txtInfoMail.setText(thongTinUngDungObj.getEmail());
            }
            if (thongTinUngDungObj.getPage() != null) {
                txtInfoWeb.setText(thongTinUngDungObj.getPage());
            }
            if (thongTinUngDungObj.getPhone() != null) {
                txtInfoTell.setText(thongTinUngDungObj.getPhone());
            }
            if (thongTinUngDungObj.getSkype() != null) {
                txtInfoSkype.setText(thongTinUngDungObj.getSkype());
            }
            if (thongTinUngDungObj.getFacebook() != null) {
                txtInfoFacebook.setText(thongTinUngDungObj.getFacebook());
            }
//            if (thongTinUngDungObj.getDevelop() != null) {
//                txtNhaPhatTrien.setText("Điều hành và phát triển bởi " + thongTinUngDungObj.getDevelop());
//            }
        }
    }

    private void GetData() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiThongTinUngDung, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                JSONObject jsonObject = response.getJSONObject("data");
                                thongTinUngDungObj = mapper.readValue(
                                        jsonObject.toString(),
                                        new TypeReference<ThongTinUngDungObj>() {
                                        });
                                if (thongTinUngDungObj != null) {
                                    ShowThongTinUngDung();

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
            case R.id.layoutEmail:
                if (thongTinUngDungObj.getEmail() != null) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    emailIntent.setType("vnd.android.cursor.item/email");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{thongTinUngDungObj.getEmail()});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Email Subject");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "My email content");
                    startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                }

                break;

            case R.id.layoutWeb:
                if (thongTinUngDungObj.getPage() != null) {
                    String url = "http://";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url + thongTinUngDungObj.getPage()));
                    startActivity(i);
                }


                break;

            case R.id.layoutPhone:
                if (thongTinUngDungObj.getPhone() != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", thongTinUngDungObj.getPhone(), null));
                    startActivity(intent);
                }

                break;

            case R.id.layoutSkype:

                if (thongTinUngDungObj.getSkype() != null) {
                    Intent sky = new Intent("android.intent.action.VIEW");
                    sky.setData(Uri.parse("skype:" + thongTinUngDungObj.getSkype()));
                    startActivity(sky);
                }

                break;

            case R.id.layoutFacebook:
                if (thongTinUngDungObj.getFacebook() != null) {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    String facebookUrl = getFacebookURL(myActivity);
                    facebookIntent.setData(Uri.parse(facebookUrl));
                    startActivity(facebookIntent);
                }
                break;
        }
    }


    public String getFacebookURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (thongTinUngDungObj.getFacebook() != null) {
            FACEBOOK_PAGE_ID = "https://www.facebook.com/" + thongTinUngDungObj.getFacebook();
            FACEBOOK_URL = "https://www.facebook.com/" + thongTinUngDungObj.getFacebook();
        }
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return FACEBOOK_URL;
        }
    }
}


