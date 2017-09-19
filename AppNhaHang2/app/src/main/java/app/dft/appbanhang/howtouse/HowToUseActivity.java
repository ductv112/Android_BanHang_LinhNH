package app.dft.appbanhang.howtouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.dft.appbanhang.MainActivity;
import app.dft.appbanhang.R;
import app.dft.appbanhang.util.AutoScrollViewPager;
import app.dft.appbanhang.util.StaticVariable;
import app.dft.appbanhang.volley.MyVolley;

public class HowToUseActivity extends AppCompatActivity implements View.OnClickListener {

    AutoScrollViewPager viewPager;
    CirclePageIndicator indicator;
    TextView txtBoQua;
    List<String> list;
    SlideImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);

        viewPager = (AutoScrollViewPager) findViewById(R.id.viewPager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        txtBoQua = (TextView) findViewById(R.id.txtBoQua);

        CreatePager();

        txtBoQua.setOnClickListener(this);
    }

    private void CreatePager() {
        list = new ArrayList<>();
        RequestQueue queue = MyVolley.getRequestQueue();
        JsonObjectRequest myReq = new JsonObjectRequest
                (Request.Method.GET, StaticVariable.apiHuongDanSuDung, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String msg = response.getString("msg");
                            if (code == 200) {
                                ObjectMapper mapper = new ObjectMapper();
                                list = mapper.readValue(
                                        response.getJSONArray("data").toString(),
                                        new TypeReference<ArrayList<String>>() {
                                        });

                            }
                            adapter = new SlideImageAdapter(HowToUseActivity.this, list);
                            viewPager.setAdapter(adapter);
                            indicator.setViewPager(viewPager);
                            viewPager.setInterval(3000);
                            viewPager.startAutoScroll();
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

    @Override
    public void onBackPressed() {
        return;
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

    @Override
    public void onClick(View v) {
        if (v == txtBoQua) {
            SharedPreferences preferences = StaticVariable.getPref(HowToUseActivity.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(StaticVariable.showHDSD, "1");
            editor.commit();
            Intent intent = new Intent(HowToUseActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
