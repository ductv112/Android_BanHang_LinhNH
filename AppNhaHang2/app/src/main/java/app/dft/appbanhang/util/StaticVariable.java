package app.dft.appbanhang.util;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import app.dft.appbanhang.object.CheckConnectObj;
import app.dft.appbanhang.object.DoiDiemThuongObj;
import app.dft.appbanhang.object.HomeObj;
import app.dft.appbanhang.object.LoaiHangHoaObj;
import app.dft.appbanhang.object.RestaurantObj;
import app.dft.appbanhang.object.ThuongHieuObj;
import app.dft.appbanhang.object.UserObj;

/**
 * Created by ductv on 4/4/2017.
 */
public class StaticVariable {
    public static final String TAG = "MyTag";

    //API
    public static String urlServer = "http://123.30.214.196:3333";
    public static String apiCheckConnect = urlServer + "/connect";

    public static String apiDangNhap = urlServer + "/login";
    public static String apiQuenMatKhau = urlServer + "/forgot-password";
    public static String apiDangKy = urlServer + "/register";
    public static String apiThamQuan = urlServer + "/visit";
    public static String apiDoiMatKhau = urlServer + "/user/change-password";
    public static String apiThayDoiAvatar = urlServer + "/user/change-avatar";
    public static String apiUpdateUser = urlServer + "/user/update";
    public static String apiGetThongBaoTinKhuyenMai = urlServer + "/campaign";
    public static String apiThongBaoHeThong = urlServer + "/notification";

    public static String apiDanhSachNhaHang = urlServer + "/restaurant";
    public static String apiDangKyHoiVien = urlServer + "/member";
    public static String apiGetDieuKhoanSuDung = urlServer + "/webview?type=1";
    public static String apiHuongDanSuDung = urlServer + "/guide";
    public static String apiLogOut = urlServer + "/logout";
    public static String apiGetNhomHangHoa = urlServer + "/sublist?type=11";
    public static String apiGetLoaiHangHoa = urlServer + "/sublist?type=1";
    public static String apiGetLoaiHangHoaTheoNhom = urlServer + "/sublist?type=12";
    public static String apiGetThuongHieu = urlServer + "/sublist?type=2";
    public static String apiGetAllThuongHieu = urlServer + "/sublist?type=9";
    public static String apiGetGiamGiaVaNoiBat = urlServer + "/product?type=2";
    public static String apiGetHangHoaTheoLoai = urlServer + "/product?type=3";
    public static String apiDangGiamGia = urlServer + "/product?type=5";
    public static String apiSapGiamGia = urlServer + "/product?type=6";
    public static String apiDanhSachNoiBat = urlServer + "/product?type=7";
    public static String apiGetChiTietHangHoa = urlServer + "/product/";
    public static String apiAddToCart = urlServer + "/cart";
    public static String apiTimKiem = urlServer + "/sublist";
    public static String apiAutoComplete = urlServer + "/sublist?type=3";
    public static String apiDatHang = urlServer + "/order";
    public static String apiDangHangHoa = urlServer + "/product";
    public static String apiUploadAnh = urlServer + "/sublist";
    public static String apiDanhSachHangHoa = urlServer + "/product?type=1";
    public static String apiDonHang = urlServer + "/order?type=1";
    public static String apiGetSoGioHang = urlServer + "/sublist?type=6";
    public static String apiGetTimeBonus = urlServer + "/reward";
    public static String apiThongTinUngDung = urlServer + "/sublist?type=5";
    public static String apiChamSocKhachHang = urlServer + "/sublist?type=7";
    public static String apiDanhSachHangHoaQuangCao = urlServer + "/product-filter";


    public static UserObj user = new UserObj();
    public static String userToken = "";
    public static List<RestaurantObj> listRestaurant = new ArrayList<>();
    public static CheckConnectObj appInfo = new CheckConnectObj();
    public static int soLuong = 0;

    //SharedPreferences
    public static String showHDSD = "showHDSD";
    public static String androidToken = "androidToken";
    public static String typeLogin = "typeLogin";
    public static String userName = "userName";
    public static String passWord = "passWord";

    public static String deviceID = "deviceID";

    //Bonus
    public static DoiDiemThuongObj timeBonus = new DoiDiemThuongObj();

    public static SharedPreferences getPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Setting", Context.MODE_PRIVATE);
        return sharedPreferences;
    }
}
