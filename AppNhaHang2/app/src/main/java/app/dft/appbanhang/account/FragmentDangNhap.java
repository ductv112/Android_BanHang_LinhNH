package app.dft.appbanhang.account;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Random;
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

@SuppressLint("ValidFragment")
public class FragmentDangNhap extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    Activity myActivity = new Activity();

    EditText edtTenDangNhap, edtMatKhau;
    TextView txtDangNhap, txtThamQuan, txtQuenMatKhau, txtDangKyMoi;
    ImageView imgLoginFB, imgLoginGG;
    LinearLayout lineTong;
    RelativeLayout layoutLogo;

    String userName, passWord;
    DFTPopup dftPopup;
    ProgressDialog progressDialog;

    //Facebook
    CallbackManager callbackManager;
    String fbID, fbEmail, fbName, fbAvatar;
    //Google +
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 10;
    String mId, mEmail, mName;
    Uri mAvatar;

    boolean isMainCall = false;


    public FragmentDangNhap(boolean isMainCall) {
        // Required empty public constructor
        this.isMainCall = isMainCall;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myActivity = getActivity();

        FacebookSdk.sdkInitialize(getContext());
        AppEventsLogger.activateApp(myActivity);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accessToken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Bundle bFacebookData = getFacebookData(object);

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                LoginManager.getInstance().logOut();
//                LoginManager.getInstance().logInWithReadPermissions(error);
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_dangnhap_layout, container, false);

        myActivity = getActivity();

        edtTenDangNhap = (EditText) v.findViewById(R.id.edtTenDangNhap);
        edtMatKhau = (EditText) v.findViewById(R.id.edtMatKhau);
        txtDangNhap = (TextView) v.findViewById(R.id.txtDangNhap);
        txtThamQuan = (TextView) v.findViewById(R.id.txtThamQuan);
        txtQuenMatKhau = (TextView) v.findViewById(R.id.txtQuenMatKhau);
        txtDangKyMoi = (TextView) v.findViewById(R.id.txtDangKyMoi);
        imgLoginFB = (ImageView) v.findViewById(R.id.imgLoginFB);
        imgLoginGG = (ImageView) v.findViewById(R.id.imgLoginGG);
        lineTong = (LinearLayout) v.findViewById(R.id.lineTong);


        txtDangNhap.setOnClickListener(this);
        imgLoginFB.setOnClickListener(this);
        imgLoginGG.setOnClickListener(this);
        txtQuenMatKhau.setOnClickListener(this);
        txtDangKyMoi.setOnClickListener(this);
        txtThamQuan.setOnClickListener(this);

        lineTong.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });


//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay()
//                .getMetrics(displaymetrics);
//        int height = displaymetrics.heightPixels;
//        int width = displaymetrics.widthPixels;
//        int iWidth = width * 1 / 3;
//        int iHeight = iWidth;
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//                iWidth, iHeight);
//        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//
//        imgLogo.setLayoutParams(layoutParams);
//
//        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
//                width, height * 2 / 7
//        );
//        layoutLogo.setLayoutParams(layoutParams1);

        if (!isMainCall) {
            AutoLogin();
        }

        return v;

    }

    @Nullable
    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();
        try {

            fbID = object.getString("id");
            fbEmail = object.getString("email");
            fbName = object.getString("first_name") + " " + object.getString("last_name");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + fbID + "/picture?width=400&height=400");
                bundle.putString("profile_pic", profile_pic.toString());
                fbAvatar = profile_pic.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            bundle.putString("idFacebook", fbID);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginFacebook();

        return bundle;
    }

    private void Login() {

        SharedPreferences preferences = StaticVariable.getPref(myActivity);
        String tokenAndroid = preferences.getString(StaticVariable.androidToken, "");

//        progressDialog = new ProgressDialog(myActivity);
//        progressDialog.setMessage(myActivity.getString(R.string.w02_dangdangnhap));
//        progressDialog.setProgress(3000);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        RequestQueue queue = MyVolley.getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", userName);
            jsonObject.put("password", passWord);
            if (tokenAndroid != null && !tokenAndroid.equals("")) {
                jsonObject.put("androidToken", "" + tokenAndroid);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.POST, StaticVariable.apiDangNhap, jsonObject, new Response.Listener<JSONObject>() {

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
//                                    progressDialog.dismiss();
                                    myActivity.finish();
                                }

                            } else {
//                                progressDialog.dismiss();
                                ShowMSG(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
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

    private void LoginFacebook() {

        SharedPreferences preferences = StaticVariable.getPref(myActivity);
        String tokenAndroid = preferences.getString(StaticVariable.androidToken, "");

//        progressDialog = new ProgressDialog(myActivity);
//        progressDialog.setMessage(myActivity.getString(R.string.w02_dangdangnhap));
//        progressDialog.setProgress(3000);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();

        RequestQueue queue = MyVolley.getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("facebookId", fbID);
            jsonObject.put("email", fbEmail);
            jsonObject.put("avatar", fbAvatar);
            jsonObject.put("fullName", fbName);
            if (tokenAndroid != null && !tokenAndroid.equals("")) {
                jsonObject.put("androidToken", "" + tokenAndroid);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.POST, StaticVariable.apiDangNhap, jsonObject, new Response.Listener<JSONObject>() {

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
                                    editor.putString(StaticVariable.typeLogin, "2");
                                    editor.commit();
                                    Intent intent = new Intent(myActivity, MainActivity.class);
                                    startActivity(intent);
//                                    progressDialog.dismiss();
                                    myActivity.finish();
                                }

                            } else {
//                                progressDialog.dismiss();
                                ShowMSG(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
//                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(myReq);
    }

    private void LoginGooglePlus() {

        SharedPreferences preferences = StaticVariable.getPref(myActivity);
        String tokenAndroid = preferences.getString(StaticVariable.androidToken, "");

//        progressDialog = new ProgressDialog(myActivity);
//        progressDialog.setMessage(myActivity.getString(R.string.w02_dangdangnhap));
//        progressDialog.setProgress(3000);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();

        RequestQueue queue = MyVolley.getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("googleId", mId);
            jsonObject.put("email", mEmail);
            jsonObject.put("avatar", mAvatar);
            jsonObject.put("fullName", mName);
            if (tokenAndroid != null && !tokenAndroid.equals("")) {
                jsonObject.put("androidToken", "" + tokenAndroid);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.POST, StaticVariable.apiDangNhap, jsonObject, new Response.Listener<JSONObject>() {

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
                                    editor.putString(StaticVariable.typeLogin, "3");
                                    editor.commit();

                                    Intent intent = new Intent(myActivity, MainActivity.class);
                                    startActivity(intent);
//                                    progressDialog.dismiss();
                                    myActivity.finish();
                                }

                            } else {
//                                progressDialog.dismiss();
                                ShowMSG(msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
//                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(myReq);

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            mEmail = acct.getEmail();
            mId = acct.getId();
            mName = acct.getDisplayName();
            mAvatar = acct.getPhotoUrl();
            LoginGooglePlus();


        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    private void signInGoogle() {
        //Google
        if (mGoogleApiClient == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(myActivity)
                    .enableAutoManage(getActivity(), 0, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
//                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
//                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtDangNhap:
                userName = edtTenDangNhap.getText().toString();
                passWord = edtMatKhau.getText().toString();
                if (Validate() == true) {
                    Login();
                }

                break;

            case R.id.imgLoginFB:
                LoginManager.getInstance().logOut();
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                break;

            case R.id.imgLoginGG:
                signInGoogle();
                break;

            case R.id.txtThamQuan:
                ThamQuan();
                break;

            case R.id.txtQuenMatKhau:
                ((SplashActivity) myActivity).loadFragment("quenmatkhau");
                break;

            case R.id.txtDangKyMoi:
                ((SplashActivity) myActivity).loadFragment("dangky");
                break;
        }
    }

    private void ThamQuan() {
        String deviceId = "";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) myActivity.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (deviceId != null && !deviceId.trim().equals("")) {
            SharedPreferences preferences = StaticVariable.getPref(myActivity);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(StaticVariable.deviceID, deviceId);
            editor.commit();
        } else {
            SharedPreferences preferences = StaticVariable.getPref(myActivity);
            deviceId = preferences.getString(StaticVariable.deviceID, "");
            if (deviceId == null || deviceId.trim().equals("")) {
                long timeL = System.currentTimeMillis();
                SimpleDateFormat dateFormatExport = new SimpleDateFormat(
                        "yyyyMMddhhmmssSSS");
                Random rand = new Random();
                int n = rand.nextInt(9999);
                deviceId = dateFormatExport.format(timeL) + n;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(StaticVariable.deviceID, deviceId);
                editor.commit();
            }
        }

//        progressDialog = new ProgressDialog(myActivity);
//        progressDialog.setMessage(myActivity.getString(R.string.w02_dangdangnhap));
//        progressDialog.setProgress(3000);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        RequestQueue queue = MyVolley.getRequestQueue();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("deviceId", deviceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.POST, StaticVariable.apiThamQuan, jsonObject, new Response.Listener<JSONObject>() {

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

                                    Intent intent;
                                    SharedPreferences preferences = StaticVariable.getPref(myActivity);
                                    String showHDSD = preferences.getString(StaticVariable.showHDSD, "0");

                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString(StaticVariable.typeLogin, "0");
                                    editor.commit();

                                    if (showHDSD != null && !showHDSD.equalsIgnoreCase("1")) {
                                        intent = new Intent(myActivity, HowToUseActivity.class);
                                    } else {
                                        intent = new Intent(myActivity, MainActivity.class);
                                    }
                                    startActivity(intent);
//                                    progressDialog.dismiss();
                                    myActivity.finish();
                                }


                            } else {
//                                progressDialog.dismiss();
                                ShowMSG(msg);
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
                });

        queue.add(myReq);
    }

    private boolean isValidUserName3(String user) {
        if (user.length() >= 4 && user.length() <= 20) {
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

    private boolean Validate() {
        if (userName.equalsIgnoreCase("")) {

            String st = getString(R.string.w02_toast);
            ShowMSG(st);
            return false;
        } else if (!isValidUserName3(userName)) {

            String st = getString(R.string.w02_toast2);
            ShowMSG(st);
            return false;
        } else if (!isValidUserName(userName)) {

            String st = getString(R.string.w02_toast3);
            ShowMSG(st);
            return false;
        } else if (passWord.equalsIgnoreCase("")) {

            String st = getString(R.string.w02_toast4);
            ShowMSG(st);
            return false;
        } else if (!isValidUserName3(passWord)) {

            String st = getString(R.string.w02_toast5);
            ShowMSG(st);
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void AutoLogin() {
        SharedPreferences preferences = StaticVariable.getPref(myActivity);
        String typeLogin = preferences.getString(StaticVariable.typeLogin, "0");

        if (typeLogin.equalsIgnoreCase("1")) {
            userName = preferences.getString("userName", "");
            passWord = preferences.getString("passWord", "");

            edtTenDangNhap.setText(userName);
            edtMatKhau.setText(passWord);
            Login();
        } else if (typeLogin.equalsIgnoreCase("2")) {
            LoginManager.getInstance().logOut();
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        } else if (typeLogin.equalsIgnoreCase("3")) {
            signInGoogle();
        } else {
            ThamQuan();
        }
    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


}
