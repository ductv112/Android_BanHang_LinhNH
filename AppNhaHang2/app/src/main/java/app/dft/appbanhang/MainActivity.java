package app.dft.appbanhang;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import app.dft.appbanhang.account.FragmentDangNhap;
import app.dft.appbanhang.account.FragmentDoiMatKhau;
import app.dft.appbanhang.account.FragmentSuaThongTinCaNhan;
import app.dft.appbanhang.account.FragmentThongTinNguoiDung;
import app.dft.appbanhang.banhang.FragmentChiTietDonHang;
import app.dft.appbanhang.banhang.FragmentChiTietHangHoaBanHang;
import app.dft.appbanhang.banhang.FragmentDangHangHoaMoi;
import app.dft.appbanhang.banhang.FragmentDanhSachDonHang;
import app.dft.appbanhang.banhang.FragmentDanhSachHangHoaBanHang;
import app.dft.appbanhang.banhang.FragmentSuaHangHoa;
import app.dft.appbanhang.chamsockhachhang.FragmentChamSocKhachHang;
import app.dft.appbanhang.chitiethanghoa.FragmentChiTietHangHoa;
import app.dft.appbanhang.chitiethanghoa.FragmentShowImageTotal;
import app.dft.appbanhang.diemthuong.FragmentNhanDiemThuong;
import app.dft.appbanhang.giamgia.FragmentGiamGiaTotal;
import app.dft.appbanhang.giohang.FragmentChiTietDatHang;
import app.dft.appbanhang.giohang.FragmentDanhSachDatHang;
import app.dft.appbanhang.giohang.FragmentDatHang;
import app.dft.appbanhang.giohang.FragmentGioHang;
import app.dft.appbanhang.home.FragmentHome;
import app.dft.appbanhang.navmenu.CustomDrawerAdapter;
import app.dft.appbanhang.navmenu.DrawerItem;
import app.dft.appbanhang.noibat.FragmentNoiBat;
import app.dft.appbanhang.object.ADVFilterObj;
import app.dft.appbanhang.object.ChiTietHangHoaObj;
import app.dft.appbanhang.object.DoiDiemThuongObj;
import app.dft.appbanhang.object.GioHangObj;
import app.dft.appbanhang.object.LoaiHangHoaObj;
import app.dft.appbanhang.theoloai.FragmentTheoLoai;
import app.dft.appbanhang.thongtinungdung.FragmentThongTinLienHe;
import app.dft.appbanhang.timkiemhanghoa.FragmentGridHangHoa2Colum;
import app.dft.appbanhang.timkiemhanghoa.FragmentTimKiemHangHoa;
import app.dft.appbanhang.util.FragmentDefault;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;

public class MainActivity extends AppCompatActivity {

    static Stack<Fragment> listFragment = new Stack<Fragment>();
    NavigationView navigationView;
    View headerLayout;
    TextView txtHello, txtDiem;
    ListView lst_menu_items, lst_menu_items_banhang, lst_menu_items_loaihang;
    LinearLayout layout_menu_items_banhang, layout_menu_items_loaihang, backBanHang, backLoaiHang;

    public static DrawerLayout drawerLayout;
    private List<DrawerItem> dataList, dataListBanHang, dataListLoaiHang;
    private CustomDrawerAdapter mDrawerAdapter, adapterBanHang, adapterLoaiHang;
    CustomDrawerAdapter.OnSelectMenu listener, listenerBanHang, listenerLoaiHang;
    int backCount = 0;
    ProgressDialog progressDialog;

    List<LoaiHangHoaObj> listLoaiHangHoa = new ArrayList<>();

    DoiDiemThuongObj objDiemThuong = new DoiDiemThuongObj();

    public static CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        txtHello = (TextView) headerLayout.findViewById(R.id.txtHello);
        txtDiem = (TextView) headerLayout.findViewById(R.id.txtDiem);
        lst_menu_items = (ListView) findViewById(R.id.lst_menu_items);
        lst_menu_items_banhang = (ListView) findViewById(R.id.lst_menu_items_banhang);
        lst_menu_items_loaihang = (ListView) findViewById(R.id.lst_menu_items_loaihang);
        layout_menu_items_banhang = (LinearLayout) findViewById(R.id.layout_menu_items_banhang);
        layout_menu_items_loaihang = (LinearLayout) findViewById(R.id.layout_menu_items_loaihang);
        backBanHang = (LinearLayout) findViewById(R.id.backBanHang);
        backLoaiHang = (LinearLayout) findViewById(R.id.backLoaiHang);

        lst_menu_items.setVisibility(View.VISIBLE);
        layout_menu_items_banhang.setVisibility(View.GONE);
        layout_menu_items_loaihang.setVisibility(View.GONE);

        backBanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_menu_items_banhang.setVisibility(View.GONE);
                lst_menu_items.setVisibility(View.VISIBLE);
            }
        });

        backLoaiHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_menu_items_loaihang.setVisibility(View.GONE);
                lst_menu_items.setVisibility(View.VISIBLE);
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                View view = MainActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        dataList = new ArrayList<DrawerItem>();
        addItemsToDataList();

        listener = new CustomDrawerAdapter.OnSelectMenu() {
            @Override
            public void onSelect(int position) {
                DrawerItem dItem = (DrawerItem) dataList.get(position);


                if (dItem.getItemName().equals(getString(R.string.menu_banhang))) {
                    //Load menu bán hàng
                    lst_menu_items.setVisibility(View.GONE);
                    layout_menu_items_banhang.setVisibility(View.VISIBLE);
//                    dataList.get(1).setActive(true);

                } else if (dItem.getItemName().equals(getString(R.string.menu_loaihanghoa))) {
                    //Load menu loại hàng hóa
                    lst_menu_items.setVisibility(View.GONE);
                    layout_menu_items_loaihang.setVisibility(View.VISIBLE);
//                    dataList.get(2).setActive(true);

                } else {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    if (listFragment != null && listFragment.size() > 1) {
                        for (int i = 1; i < listFragment.size(); i++) {
                            transaction.remove(listFragment.peek());
                            listFragment.pop();
                        }
                        transaction.commit();
                    }
//                    for (int i = 0; i < dataList.size(); i++) {
//                        if (dataList.get(i).getActive()) {
//                            dataList.get(i).setActive(false);
//                        }
//                    }

                    if (dItem.getItemName().equals(getString(R.string.menu_trangchu))) {
                        loadFragment("home");
//                        dataList.get(0).setActive(true);
                        drawerLayout.closeDrawer(GravityCompat.START);

                    } else if (dItem.getItemName().equals(getString(R.string.menu_giamgia))) {
                        loadFragment("giamgia");
//                        dataList.get(3).setActive(true);
                        drawerLayout.closeDrawer(GravityCompat.START);

                    } else if (dItem.getItemName().equals(getString(R.string.menu_noibat))) {
                        loadFragment("noibat");
//                        dataList.get(4).setActive(true);
                        drawerLayout.closeDrawer(GravityCompat.START);

                    } else if (dItem.getItemName().equals(getString(R.string.menu_thongtincanhan))) {
                        if (StaticVariable.user != null && StaticVariable.user.get_id() != null) {
                            loadFragment("thongtincanhan");
//                        dataList.get(5).setActive(true);
                        } else {
                            listFragment.clear();
                            Intent intent = new Intent(getBaseContext(), SplashActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("from", "main");
                            startActivity(intent);
                            finish();
                        }

                        drawerLayout.closeDrawer(GravityCompat.START);

                    } else if (dItem.getItemName().equals(getString(R.string.menu_dadathang))) {
                        if (StaticVariable.user != null && StaticVariable.user.get_id() != null) {
                            loadFragment("danhsach_dathang");
//                        dataList.get(6).setActive(true);
                        } else {
                            listFragment.clear();
                            Intent intent = new Intent(getBaseContext(), SplashActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("from", "main");
                            startActivity(intent);
                            finish();
                        }

                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else if (dItem.getItemName().equals(getString(R.string.menu_nhandiemthuong))) {
                        if (StaticVariable.user != null && StaticVariable.user.get_id() != null) {
                            loadFragment("nhandienthuong");
//                        dataList.get(7).setActive(true);
                        } else {
                            listFragment.clear();
                            Intent intent = new Intent(getBaseContext(), SplashActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("from", "main");
                            startActivity(intent);
                            finish();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else if (dItem.getItemName().equals(getString(R.string.menu_chamsockhachang))) {
                        loadFragment("chamsoc_khachhang");
//                        dataList.get(8).setActive(true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else if (dItem.getItemName().equals(getString(R.string.menu_lienhe))) {
                        loadFragment("lienhe");
//                        dataList.get(9).setActive(true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                }


                mDrawerAdapter.notifyDataSetChanged();

            }
        }

        ;

        mDrawerAdapter = new

                CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataList, listener);

        lst_menu_items.setAdapter(mDrawerAdapter);

        dataListBanHang = new ArrayList<DrawerItem>();

        addItemsToDataListBanHang();

        listenerBanHang = new CustomDrawerAdapter.OnSelectMenu()

        {
            @Override
            public void onSelect(int position) {
                DrawerItem dItem = (DrawerItem) dataListBanHang.get(position);

                if (dItem.getItemName().equals(getString(R.string.menu_danghanghoamoi))) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    loadFragment("dang_hanghoa");
                } else if (dItem.getItemName().equals(getString(R.string.menu_danhsachhanghoa))) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    loadFragment("danhsach_hanghoa");
                } else if (dItem.getItemName().equals(getString(R.string.menu_donhang))) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    loadFragment("donhang");
                }
                mDrawerAdapter.notifyDataSetChanged();

            }
        }

        ;
        adapterBanHang = new

                CustomDrawerAdapter(this, R.layout.custom_drawer_item, dataListBanHang, listenerBanHang);

        lst_menu_items_banhang.setAdapter(adapterBanHang);


        listenerLoaiHang = new CustomDrawerAdapter.OnSelectMenu()

        {
            @Override
            public void onSelect(int position) {
//                Toast.makeText(MainActivity.this, dataListLoaiHang.get(position).getItemName(), Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                loadFragment("hanghoa_theoloai", listLoaiHangHoa.get(position).get_id());
            }
        }

        ;
        dataListLoaiHang = new ArrayList<DrawerItem>();

        GetLoaiHangHoa();


        setDrawerMenu();

        if (StaticVariable.user != null && StaticVariable.user.get_id() != null) {
            GetDataDiemThuong();
        }

        loadFragment("home");
    }

    private void GetDataDiemThuong() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiGetTimeBonus, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                objDiemThuong = new DoiDiemThuongObj();
                                objDiemThuong = mapper.readValue(
                                        response.getJSONObject("data").toString(),
                                        new TypeReference<DoiDiemThuongObj>() {
                                        });
                                if (objDiemThuong != null) {
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

    private void ShowData() {
        if (objDiemThuong != null) {
            StaticVariable.timeBonus = objDiemThuong;
//            if (objDiemThuong.getTime() == -1) {
//                FragmentHome.imgDiemThuong.setBackgroundResource(R.drawable.ic_alarm_white_48dp);
//            } else {
//                Bonus();
//            }
            if (objDiemThuong.getTime() != -1) {
                Bonus();
            }
        }
    }

    public static void Bonus() {
        Long maxTime = StaticVariable.timeBonus.getTime() * 1000;
        countDownTimer = new CountDownTimer(maxTime, 1000) {

            public void onTick(long millisUntilFinished) {
                StaticVariable.timeBonus.setTime(StaticVariable.timeBonus.getTime() - 1);
                SetIconDiemThuong(R.drawable.ic_alarm_white_48dp);
            }

            public void onFinish() {
                try {
                    SetIconDiemThuong(R.drawable.ic_alarm_on_white_48dp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static void SetIconDiemThuong(int idIcon) {
        try {
            if (listFragment != null && listFragment.size() > 0 && listFragment.peek().getTag() != null) {
                if (listFragment.peek().getTag().equalsIgnoreCase("home")) {
                    FragmentHome.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("chamsoc_khachhang")) {
                    FragmentChamSocKhachHang.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("chitiet_hanghoa")) {
                    FragmentChiTietHangHoa.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("chinhsua_profile")) {
                    FragmentSuaThongTinCaNhan.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("chitiet_dathang")) {
                    FragmentChiTietDatHang.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("chitiet_hanghoa_banhang")) {
                    FragmentChiTietHangHoaBanHang.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("dang_hanghoa")) {
                    FragmentDangHangHoaMoi.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("danhsach_dathang")) {
                    FragmentDanhSachDatHang.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("donhang")) {
                    FragmentDanhSachDonHang.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("danhsach_hanghoa")) {
                    FragmentDanhSachHangHoaBanHang.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("dathang")) {
                    FragmentDatHang.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("giamgia")) {
                    FragmentGiamGiaTotal.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("cart")) {
                    FragmentGioHang.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("grid_hanghoa_2colum")) {
                    FragmentGridHangHoa2Colum.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("nhandienthuong")) {
                    FragmentNhanDiemThuong.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("noibat")) {
                    FragmentNoiBat.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("hanghoa_theoloai")) {
                    FragmentTheoLoai.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("lienhe")) {
                    FragmentThongTinLienHe.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("thongtincanhan")) {
                    FragmentThongTinNguoiDung.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("search")) {
                    FragmentTimKiemHangHoa.imgDiemThuong.setBackgroundResource(idIcon);
                } else if (listFragment.peek().getTag().equalsIgnoreCase("default")) {
                    FragmentHome.imgDiemThuong.setBackgroundResource(idIcon);
                }
            }
        } catch (Exception myEx) {

        }
    }

    private void GetLoaiHangHoa() {
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiGetLoaiHangHoa, null, new Response.Listener<JSONObject>() {

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
                                    //StaticVariable.loaiHangHoa = listLoaiHangHoa;
                                }
                                addItemsToDataListLoaiHang(listLoaiHangHoa);
                                adapterLoaiHang = new CustomDrawerAdapter(MainActivity.this, R.layout.custom_drawer_item, dataListLoaiHang, listenerLoaiHang);
                                lst_menu_items_loaihang.setAdapter(adapterLoaiHang);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
//                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
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

    private void Logout() {
        progressDialog = new ProgressDialog(MainActivity.this);
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
                                SharedPreferences preferences = StaticVariable.getPref(MainActivity.this);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(StaticVariable.typeLogin, "0");
                                editor.putString(StaticVariable.userName, "");
                                editor.putString(StaticVariable.passWord, "");
                                editor.commit();
                                listFragment.clear();
                                progressDialog.dismiss();
                                Intent intent = new Intent(getBaseContext(), SplashActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("from", "main");
                                startActivity(intent);
                                finish();
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

    /**
     * Tao danh sach NAV menu
     */

    private void addItemsToDataList() {
        dataList.add(new DrawerItem(getString(R.string.menu_trangchu)));
        if (StaticVariable.user != null) {
            if (StaticVariable.user.getType() == 2) {
                dataList.add(new DrawerItem(getString(R.string.menu_banhang), true));
            }
        }
        dataList.add(new DrawerItem(getString(R.string.menu_loaihanghoa), true));
        dataList.add(new DrawerItem(getString(R.string.menu_giamgia)));
        dataList.add(new DrawerItem(getString(R.string.menu_noibat)));
        dataList.add(new DrawerItem(getString(R.string.menu_thongtincanhan)));
        dataList.add(new DrawerItem(getString(R.string.menu_dadathang)));
        dataList.add(new DrawerItem(getString(R.string.menu_nhandiemthuong)));
        dataList.add(new DrawerItem(getString(R.string.menu_chamsockhachang)));
        dataList.add(new DrawerItem(getString(R.string.menu_lienhe)));

        dataList.get(0).setActive(true);
    }

    private void addItemsToDataListBanHang() {
        dataListBanHang.add(new DrawerItem(getString(R.string.menu_danghanghoamoi)));
        dataListBanHang.add(new DrawerItem(getString(R.string.menu_danhsachhanghoa)));
        dataListBanHang.add(new DrawerItem(getString(R.string.menu_donhang)));
    }

    private void addItemsToDataListLoaiHang(List<LoaiHangHoaObj> list) {
        for (int i = 0; i < list.size(); i++) {
            dataListLoaiHang.add(new DrawerItem(list.get(i).getName()));
        }

    }

    /**
     * Hiển thị thông tin User đăng nhập tren Drawer Menu
     */
    public void setDrawerMenu() {
        if (StaticVariable.user != null) {
            if (StaticVariable.user.getFullName() != null) {
                txtHello.setText(getString(R.string.hello) + ", " + StaticVariable.user.getFullName());
            } else if (StaticVariable.user.getUsername() != null) {
                txtHello.setText(getString(R.string.hello) + ", " + StaticVariable.user.getUsername());
            }
            if (StaticVariable.user.getCoin() >= 0) {
                txtDiem.setText("Điểm của bạn: " + String.valueOf(StaticVariable.user.getCoin()));
            }
        }
    }

    public void loadFragment(String st) {
        backCount = 0;
        hideSoftKeyboard();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new Fragment();
        if (st.equals("home")) {
            fragment = new FragmentHome();
            transaction.add(R.id.container, fragment, "home");
        } else if (st.equals("thongtincanhan")) {
            fragment = new FragmentThongTinNguoiDung();
            transaction.add(R.id.container, fragment, "thongtincanhan");
        } else if (st.equals("doimatkhau")) {
            fragment = new FragmentDoiMatKhau();
            transaction.add(R.id.container, fragment, "doimatkhau");
        } else if (st.equals("giamgia")) {
            fragment = new FragmentGiamGiaTotal();
            transaction.add(R.id.container, fragment, "giamgia");
        } else if (st.equals("chinhsua_profile")) {
            fragment = new FragmentSuaThongTinCaNhan();
            transaction.add(R.id.container, fragment, "chinhsua_profile");
        } else if (st.equals("chitiethanghoa")) {
            fragment = new FragmentChiTietHangHoa();
            transaction.add(R.id.container, fragment, "chitiethanghoa");
        } else if (st.equals("noibat")) {
            fragment = new FragmentNoiBat();
            transaction.add(R.id.container, fragment, "noibat");
        } else if (st.equals("search")) {
            fragment = new FragmentTimKiemHangHoa();
            transaction.add(R.id.container, fragment, "search");
        } else if (st.equals("cart")) {
            fragment = new FragmentGioHang();
            transaction.add(R.id.container, fragment, "cart");
        } else if (st.equals("nhandienthuong")) {
            fragment = new FragmentNhanDiemThuong();
            transaction.add(R.id.container, fragment, "nhandienthuong");
        } else if (st.equals("danhsach_dathang")) {
            fragment = new FragmentDanhSachDatHang();
            transaction.add(R.id.container, fragment, "danhsach_dathang");
        } else if (st.equals("dang_hanghoa")) {
            fragment = new FragmentDangHangHoaMoi();
            transaction.add(R.id.container, fragment, "dang_hanghoa");
        } else if (st.equals("danhsach_hanghoa")) {
            fragment = new FragmentDanhSachHangHoaBanHang();
            transaction.add(R.id.container, fragment, "danhsach_hanghoa");
        } else if (st.equals("donhang")) {
            fragment = new FragmentDanhSachDonHang();
            transaction.add(R.id.container, fragment, "donhang");
        } else if (st.equals("lienhe")) {
            fragment = new FragmentThongTinLienHe();
            transaction.add(R.id.container, fragment, "lienhe");
        } else if (st.equals("chamsoc_khachhang")) {
            fragment = new FragmentChamSocKhachHang();
            transaction.add(R.id.container, fragment, "chamsoc_khachhang");
        } else {
            fragment = new FragmentDefault();
            transaction.add(R.id.container, fragment, "default");
        }
        if (listFragment != null && listFragment.size() > 0) {
            transaction.hide(listFragment.peek());
        }
        listFragment.push(fragment);
        transaction.commit();
    }

    public void loadFragment(String st, String productTypeId) {
        backCount = 0;
        hideSoftKeyboard();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new Fragment();
        if (st.equals("hanghoa_theoloai")) {
            fragment = new FragmentTheoLoai(productTypeId);
            transaction.add(R.id.container, fragment, "hanghoa_theoloai");
        } else if (st.equals("chitiet_hanghoa")) {
            fragment = new FragmentChiTietHangHoa(productTypeId);
            transaction.add(R.id.container, fragment, "chitiet_hanghoa");
        } else if (st.equals("search")) {
            fragment = new FragmentTimKiemHangHoa(productTypeId);
            transaction.add(R.id.container, fragment, "search");
        } else if (st.equals("chitiet_dathang")) {
            fragment = new FragmentChiTietDatHang(productTypeId);
            transaction.add(R.id.container, fragment, "chitiet_dathang");
        } else if (st.equals("chitiet_hanghoa_banhang")) {
            fragment = new FragmentChiTietHangHoaBanHang(productTypeId);
            transaction.add(R.id.container, fragment, "chitiet_hanghoa_banhang");
        } else if (st.equals("chitiet_donhang")) {
            fragment = new FragmentChiTietDonHang(productTypeId);
            transaction.add(R.id.container, fragment, "chitiet_donhang");
        } else if (st.equals("sua_hanghoa")) {
            fragment = new FragmentSuaHangHoa(productTypeId);
            transaction.add(R.id.container, fragment, "sua_hanghoa");
        } else if (st.equals("cart")) {
            fragment = new FragmentGioHang(productTypeId);
            transaction.add(R.id.container, fragment, "cart");
        } else {
            fragment = new FragmentDefault();
            transaction.add(R.id.container, fragment, "default");
        }
        if (listFragment != null && listFragment.size() > 0) {
            transaction.hide(listFragment.peek());
        }
        listFragment.push(fragment);
        transaction.commit();
    }

    public void loadFragment(String st, List<GioHangObj> list, List<ChiTietHangHoaObj> listMuaNgay) {
        backCount = 0;
        hideSoftKeyboard();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new Fragment();
        if (st.equals("dathang")) {
            fragment = new FragmentDatHang(list, listMuaNgay);
            transaction.add(R.id.container, fragment, "dathang");
        } else {
            fragment = new FragmentDefault();
            transaction.add(R.id.container, fragment, "default");
        }
        if (listFragment != null && listFragment.size() > 0) {
            transaction.hide(listFragment.peek());
        }
        listFragment.push(fragment);
        transaction.commit();
    }

    public void loadFragment(String st, ADVFilterObj filterObj) {
        backCount = 0;
        hideSoftKeyboard();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new Fragment();
        if (st.equals("grid_hanghoa_2colum")) {
            fragment = new FragmentGridHangHoa2Colum(filterObj);
            transaction.add(R.id.container, fragment, "grid_hanghoa_2colum");
        } else {
            fragment = new FragmentDefault();
            transaction.add(R.id.container, fragment, "default");
        }
        if (listFragment != null && listFragment.size() > 0) {
            transaction.hide(listFragment.peek());
        }
        listFragment.push(fragment);
        transaction.commit();
    }

    public void loadFragment(String st, List<String> list) {
        backCount = 0;
        hideSoftKeyboard();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new Fragment();
        if (st.equals("show_image_total")) {
            fragment = new FragmentShowImageTotal(list);
            transaction.add(R.id.container, fragment, "show_image_total");
        } else {
            fragment = new FragmentDefault();
            transaction.add(R.id.container, fragment, "default");
        }
        if (listFragment != null && listFragment.size() > 0) {
            transaction.hide(listFragment.peek());
        }
        listFragment.push(fragment);
        transaction.commit();
    }

    private void hideSoftKeyboard() {
        final InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (MainActivity.this.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    private boolean CheckExit() {
        if (listFragment.peek().getTag().equalsIgnoreCase("home")) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        hideSoftKeyboard();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (listFragment != null && listFragment.size() > 1) {
                if (listFragment.peek().getTag() != null && CheckExit()) {
                    if (backCount == 1) {
                        finish();
                    } else {
                        backCount = 1;
                        Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.message_exit), Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.message_exit), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestQueue queue = MyVolley.getRequestQueue();
        if (queue != null) {
            queue.cancelAll(StaticVariable.TAG);
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
