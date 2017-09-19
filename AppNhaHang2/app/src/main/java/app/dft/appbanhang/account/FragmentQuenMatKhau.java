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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.dft.appbanhang.R;
import app.dft.appbanhang.util.DFTPopup;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentQuenMatKhau extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();

    LinearLayout lineTong;
    ImageView imgBack;
    EditText edtNhapEmail;
    TextView txtXacNhan, txtHuyBo;
    String email;
    DFTPopup dftPopup;

    public FragmentQuenMatKhau() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quenmatkhau_layout, container, false);

        myActivity = getActivity();

        imgBack = (ImageView) v.findViewById(R.id.imgBack);
        edtNhapEmail = (EditText) v.findViewById(R.id.edtNhapEmail);
        txtXacNhan = (TextView) v.findViewById(R.id.txtXacNhan);
        txtHuyBo = (TextView) v.findViewById(R.id.txtHuyBo);
        lineTong = (LinearLayout) v.findViewById(R.id.lineTong);


        txtXacNhan.setOnClickListener(this);
        txtHuyBo.setOnClickListener(this);

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

            case R.id.txtHuyBo:
                myActivity.onBackPressed();
                break;
            case R.id.txtXacNhan:
                email = edtNhapEmail.getText().toString();
                if (Validate2() == true) {
                    DoiMatKhau();
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

    private boolean Validate2() {

        if (email.equalsIgnoreCase("")) {
            ShowMSG(myActivity.getString(R.string.w03_error));
            return false;
        } else if (!isValidEmail(email)) {
            ShowMSG(myActivity.getString(R.string.w03_error2));
            return false;
        }

        return true;
    }

    private void DoiMatKhau() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.POST, StaticVariable.apiQuenMatKhau, jsonObject, new Response.Listener<JSONObject>() {

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
