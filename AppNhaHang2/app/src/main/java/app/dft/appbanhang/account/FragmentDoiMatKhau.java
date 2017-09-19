package app.dft.appbanhang.account;


import android.app.Activity;
import android.content.Context;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.dft.appbanhang.R;
import app.dft.appbanhang.util.DFTPopup;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDoiMatKhau extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();

    ImageView imgBack;
    EditText edtMatKhauCu, edtMatKhauMoi, edtNhapLaiMatKhauMoi;
    TextView txtXacNhan;
    DFTPopup dftPopup;
    String passOld, passNew, confirmPassNew;
    LinearLayout lineTong;

    public FragmentDoiMatKhau() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doimatkhau_layout, container, false);
        myActivity = getActivity();


        imgBack = (ImageView) v.findViewById(R.id.imgBack);
        edtMatKhauCu = (EditText) v.findViewById(R.id.edtMatKhauCu);
        edtMatKhauMoi = (EditText) v.findViewById(R.id.edtMatKhauMoi);
        edtNhapLaiMatKhauMoi = (EditText) v.findViewById(R.id.edtNhapLaiMatKhauMoi);
        txtXacNhan = (TextView) v.findViewById(R.id.txtXacNhan);
        lineTong = (LinearLayout) v.findViewById(R.id.lineTong);


        imgBack.setOnClickListener(this);
        txtXacNhan.setOnClickListener(this);

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
            case R.id.imgBack:
                myActivity.onBackPressed();
                break;

            case R.id.txtXacNhan:
                passOld = edtMatKhauCu.getText().toString();
                passNew = edtMatKhauMoi.getText().toString();
                confirmPassNew = edtNhapLaiMatKhauMoi.getText().toString();

                if (Validate() == true) {
                    DoiMatKhau();
                }

                break;
        }

    }

    private boolean isValidatePassword(String password) {
        if (password.length() >= 4 && password.length() <= 20) {
            return true;
        }

        return false;
    }

    private boolean Validate() {
        if (passOld.equalsIgnoreCase("")) {
            ShowMSG(myActivity.getString(R.string.w09_message_001));
            return false;
        } else if (!isValidatePassword(passOld)) {
            ShowMSG(myActivity.getString(R.string.w09_message_002));
            return false;
        } else if (passNew.equalsIgnoreCase("")) {
            ShowMSG(myActivity.getString(R.string.w09_message_003));
            return false;
        } else if (!isValidatePassword(passNew)) {
            ShowMSG(myActivity.getString(R.string.w09_message_004));
            return false;
        } else if (confirmPassNew.equalsIgnoreCase("")) {
            ShowMSG(myActivity.getString(R.string.w09_message_005));
            return false;
        } else if (!passNew.equals(confirmPassNew)) {
            ShowMSG(myActivity.getString(R.string.w09_message_006));
            return false;
        }

        return true;
    }

    private void DoiMatKhau() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("oldPassword", passOld);
            jsonObject.put("password", passNew);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.PUT, StaticVariable.apiDoiMatKhau, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String msg = response.getString("msg");
                            if (code == 200) {
                                ShowMSG(msg);
                                myActivity.onBackPressed();
                            } else {
                                ShowMSG(msg);
                            }
                        } catch (JSONException e) {
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

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
