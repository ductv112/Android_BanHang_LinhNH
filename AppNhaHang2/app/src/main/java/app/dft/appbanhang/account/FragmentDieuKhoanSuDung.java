package app.dft.appbanhang.account;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import app.dft.appbanhang.R;
import app.dft.appbanhang.util.StaticVariable;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDieuKhoanSuDung extends Fragment {

    Activity myActivity = new Activity();

    ImageView imgBack;
    WebView webView;
    String myUrl;

    public FragmentDieuKhoanSuDung() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webview, container, false);

        myActivity = getActivity();

        imgBack = (ImageView) v.findViewById(R.id.imgBack);
        webView = (WebView) v.findViewById(R.id.webView);

        myUrl = StaticVariable.apiGetDieuKhoanSuDung;

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivity.onBackPressed();
            }
        });

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);


        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {

            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });
        webView.loadUrl(myUrl);

        return v;
    }

}
