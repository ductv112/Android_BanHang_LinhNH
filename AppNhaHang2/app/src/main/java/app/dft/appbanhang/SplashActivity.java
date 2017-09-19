package app.dft.appbanhang;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Stack;

import app.dft.appbanhang.account.FragmentDangKy;
import app.dft.appbanhang.account.FragmentDangNhap;
import app.dft.appbanhang.account.FragmentDieuKhoanSuDung;
import app.dft.appbanhang.account.FragmentQuenMatKhau;
import app.dft.appbanhang.howtouse.HowToUseActivity;
import app.dft.appbanhang.object.CheckConnectObj;
import app.dft.appbanhang.object.UserObj;
import app.dft.appbanhang.util.DFTPopup;
import app.dft.appbanhang.util.FragmentDefault;
import app.dft.appbanhang.util.NetworkUtils;
import app.dft.appbanhang.util.RegistrationIntentService;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;

/**
 * Created by DucTV on 4/7/2016.
 */
public class SplashActivity extends AppCompatActivity {

    Stack<Fragment> listFragment = new Stack<Fragment>();
    DFTPopup dftPopup;

    int backCount = 0;

    ImageView imgWOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        imgWOS = (ImageView) findViewById(R.id.imgWOS);

        Intent intent = getIntent();
        if (intent != null) {
            String from = intent.getStringExtra("from");
            if (from != null && from.equals("main")) {
                loadFragment("dangnhap2");
            } else {
                Intent intentService = new Intent(this, RegistrationIntentService.class);
                startService(intentService);

                imgWOS.setVisibility(View.VISIBLE);
                Runnable progressRunnable = new Runnable() {
                    @Override
                    public void run() {
                        imgWOS.setVisibility(View.GONE);
                        CheckMang();
                    }
                };

                Handler handler = new Handler();
                handler.postDelayed(progressRunnable, 5000);
            }
        }


    }

    private void CheckMang() {
        if (!NetworkUtils.isOnline(getBaseContext())) {
            DFTPopup.DFTDialogListener listener = new DFTPopup.DFTDialogListener() {
                @Override
                public void userOK() {
                    if (dftPopup != null) {
                        dftPopup.dismiss();
                    }
                    CheckMang();
                }

                @Override
                public void userCancel() {
                    if (dftPopup != null) {
                        dftPopup.dismiss();
                        finish();
                    }
                }
            };
            dftPopup = new DFTPopup(SplashActivity.this, 3, getString(R.string.msg_loimang), listener);
            dftPopup.show();
            dftPopup.setCanceledOnTouchOutside(false);

        } else {
//            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
            CheckServer();
        }
    }

    private void CheckServer() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiCheckConnect, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String msg = response.getString("msg");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                CheckConnectObj connectObj = new CheckConnectObj();
                                connectObj = mapper.readValue(
                                        response.getJSONObject("data").toString(),
                                        new TypeReference<CheckConnectObj>() {
                                        });

                                if (connectObj != null) {
                                    StaticVariable.appInfo = connectObj;
                                }
                                CheckSeverOK();
                            } else {
                                CheckSeverFail();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckSeverFail();
                    }
                });
        queue.add(myReq);
    }

    private void CheckSeverOK() {
        imgWOS.setVisibility(View.GONE);
        loadFragment("dangnhap");
    }

    private void CheckSeverFail() {
        DFTPopup.DFTDialogListener listener = new DFTPopup.DFTDialogListener() {
            @Override
            public void userOK() {
                if (dftPopup != null) {
                    dftPopup.dismiss();
                }
                CheckServer();
            }

            @Override
            public void userCancel() {
                if (dftPopup != null) {
                    dftPopup.dismiss();
                    finish();
                }
            }
        };
        dftPopup = new DFTPopup(SplashActivity.this, 3, getString(R.string.msg_loiketnoiserver), listener);
        dftPopup.show();
        dftPopup.setCanceledOnTouchOutside(false);
    }

    public void loadFragment(String st) {
        backCount = 0;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new Fragment();
        if (st.equals("dangnhap")) {
            fragment = new FragmentDangNhap(false);
            transaction.add(R.id.containerSplash, fragment, "dangnhap");
        } else if (st.equals("dangnhap2")) {
            fragment = new FragmentDangNhap(true);
            transaction.add(R.id.containerSplash, fragment, "dangnhap2");
        }else if (st.equals("dangky")) {
            fragment = new FragmentDangKy();
            transaction.add(R.id.containerSplash, fragment, "dangky");
        } else if (st.equals("dieukhoan_sudung")) {
            fragment = new FragmentDieuKhoanSuDung();
            transaction.add(R.id.containerSplash, fragment, "dieukhoan_sudung");
        } else if (st.equals("quenmatkhau")) {
            fragment = new FragmentQuenMatKhau();
            transaction.add(R.id.containerSplash, fragment, "quenmatkhau");
        } else {
            fragment = new FragmentDefault();
            transaction.add(R.id.containerSplash, fragment, "default");
        }
        if (listFragment != null && listFragment.size() > 0) {
            transaction.hide(listFragment.peek());
        }
        listFragment.push(fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        hideSoftKeyboard();

        if (listFragment != null && listFragment.size() > 1) {
            if (listFragment.peek().getTag() != null && CheckExit()) {
                if (backCount == 1) {
                    finish();
                } else {
                    backCount = 1;
                    Toast.makeText(SplashActivity.this, SplashActivity.this.getString(R.string.message_exit), Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.remove(listFragment.peek());
                listFragment.pop();
                transaction.show(listFragment.peek());
                transaction.commit();
            }
        } else {
            if (backCount == 1) {
                finish();
            } else {
                backCount = 1;
                Toast.makeText(SplashActivity.this, SplashActivity.this.getString(R.string.message_exit), Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    private boolean CheckExit() {
        if (listFragment.peek().getTag().equalsIgnoreCase("dangnhap")) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestQueue queue = MyVolley.getRequestQueue();
        if (queue != null) {
            queue.cancelAll(StaticVariable.TAG);
        }
    }

    private void hideSoftKeyboard() {
        final InputMethodManager inputMethodManager = (InputMethodManager) SplashActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (SplashActivity.this.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(SplashActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        RequestQueue queue = MyVolley.getRequestQueue();
        if (queue != null) {
            queue.cancelAll(StaticVariable.TAG);
        }
    }
}

