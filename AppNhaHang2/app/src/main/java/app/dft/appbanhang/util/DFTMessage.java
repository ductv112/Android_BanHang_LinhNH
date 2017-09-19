package app.dft.appbanhang.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import app.dft.appbanhang.R;

/**
 * Created by DucTV on 4/7/2016.
 */
public class DFTMessage extends Dialog {
    Context mContext;
    int icon;
    String msg = "";

    public DFTMessage(Context context, int icon, String msg) {
        super(context);
        this.mContext = context;
        this.icon = icon;
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_dft_message);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView imgIcon = (ImageView) findViewById(R.id.imgIcon);
        TextView txtMsg = (TextView) findViewById(R.id.txtMsg);

        txtMsg.setText(msg);

        if (icon != -1) {
            imgIcon.setBackgroundResource(icon);
        }

    }

}
