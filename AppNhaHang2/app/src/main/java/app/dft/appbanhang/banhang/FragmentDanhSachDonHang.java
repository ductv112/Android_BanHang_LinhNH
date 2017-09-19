package app.dft.appbanhang.banhang;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.SplashActivity;
import app.dft.appbanhang.giohang.AdapterDatHang;
import app.dft.appbanhang.object.DatHangObj;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDanhSachDonHang extends Fragment implements View.OnClickListener {

    Activity myActivity = new Activity();
    ImageView imgHamburger, imgSearch, imgCart;
    TextView txtCartNum, txtTongSoDonHang;

    ListView lvDonHang;
    List<DatHangObj> listDonHang;
    AdapterDatHang adapterDonHang;

    public static ImageView imgDiemThuong;

    public FragmentDanhSachDonHang() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_danhsach_donhang_layout, container, false);
        myActivity = getActivity();

        imgHamburger = (ImageView) v.findViewById(R.id.imgHamburger);
        imgSearch = (ImageView) v.findViewById(R.id.imgSearch);
        imgCart = (ImageView) v.findViewById(R.id.imgCart);
        txtCartNum = (TextView) v.findViewById(R.id.txtCartNum);
        lvDonHang = (ListView) v.findViewById(R.id.lvDonHang);
        txtTongSoDonHang = (TextView) v.findViewById(R.id.txtTongSoDonHang);

        imgDiemThuong = (ImageView) v.findViewById(R.id.imgDiemThuong);
        imgDiemThuong.setOnClickListener(this);
        txtCartNum.setText(String.valueOf(StaticVariable.soLuong));
        imgHamburger.setOnClickListener(this);
        imgCart.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

        listDonHang = new ArrayList<>();
        GetData();

        lvDonHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ((MainActivity) myActivity).loadFragment("chitiet_donhang", listDonHang.get(i).get_id());
            }
        });

        return v;
    }


    private void GetData() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiDonHang, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                listDonHang = new ArrayList<DatHangObj>();
                                listDonHang = mapper.readValue(
                                        response.getJSONArray("data").toString(),
                                        new TypeReference<ArrayList<DatHangObj>>() {
                                        });
                                if (listDonHang != null && listDonHang.size() > 0) {
                                    adapterDonHang = new AdapterDatHang(myActivity, listDonHang);
                                    lvDonHang.setAdapter(adapterDonHang);
                                    txtTongSoDonHang.setText("Tổng số " + listDonHang.size() + " đơn hàng");
                                } else {
                                    txtTongSoDonHang.setText(myActivity.getString(R.string.text_khongcodonhangnao));
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
        }
    }
}
