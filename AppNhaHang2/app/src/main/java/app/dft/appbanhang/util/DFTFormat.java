package app.dft.appbanhang.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import app.dft.appbanhang.R;

/**
 * Created by ductv on 8/26/2016.
 */
public class DFTFormat {
    public static String NumberFormat(String st) {

        if (st != null && !st.trim().equals("")) {
            st = st.trim();
            Double monkey = Double.parseDouble(st);
            DecimalFormat precision = new DecimalFormat("#,#00");
            String kq = precision.format(monkey);
            return kq.trim();
        }
        return "0";

    }

    public static String DateTime(String Date) {
        String time = null;
        TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat f = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        f.setTimeZone(utc);
        GregorianCalendar cal = new GregorianCalendar(utc);
        try {
            cal.setTime(f.parse(Date));
            SimpleDateFormat dateFormatExport = new SimpleDateFormat(
                    "dd/MM/yyyy HH:mm");
            time = dateFormatExport.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return time;
    }

    public static String DateTime1(String Date) {
        String time = null;
        TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat f = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        f.setTimeZone(utc);
        GregorianCalendar cal = new GregorianCalendar(utc);
        try {
            cal.setTime(f.parse(Date));
            SimpleDateFormat dateFormatExport = new SimpleDateFormat(
                    "HH:mm dd/MM/yyyy");
            time = dateFormatExport.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return time;
    }

    public static String DateTime2(String Date) {
        String time = null;
        TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat f = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        f.setTimeZone(utc);
        GregorianCalendar cal = new GregorianCalendar(utc);
        try {
            cal.setTime(f.parse(Date));
            SimpleDateFormat dateFormatExport = new SimpleDateFormat(
                    "dd/MM/yyyy");
            time = dateFormatExport.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return time;
    }

    public static String UrlImage(String st) {
        if (st != null && st.trim().length() > 4) {
            if (st.startsWith("http")) {
                return st.replaceAll("\\\\", "/");
            } else if (st.startsWith("/")) {
                return StaticVariable.urlServer + st.replaceAll("\\\\", "/");
            } else {
                return StaticVariable.urlServer + "/" + st.replaceAll("\\\\", "/");
            }
        }
        return null;
    }

    public static String MonneyFormat2(String st) {

        if (st != null && !st.equals("")) {
            st = st.trim();
            st = st.replace(".", "");
            Double monkey = Double.parseDouble(st);
            DecimalFormat precision = new DecimalFormat("#,#00");
            String kq = precision.format(monkey);
            return kq.trim();
        }
        return "0";

    }
}
