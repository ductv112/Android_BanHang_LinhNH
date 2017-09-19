package app.dft.appbanhang.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import app.dft.appbanhang.R;

/**
 * Created by DucTV on 4/7/2016.
 */
public class DFTPopup extends Dialog {
    Context mContext;
    int type; //1: Xác nhận ---- 2: Xác nhận - Hủy bỏ --- 3: Thử lại - Hủy bỏ
    String msg = "";
    DFTDialogListener listener;

    public DFTPopup(Context context, int type, String msg, DFTDialogListener listener) {
        super(context);
        this.mContext = context;
        this.type = type;
        this.msg = msg;
        this.listener = listener;
    }

    public static interface DFTDialogListener {
        public void userOK();

        public void userCancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_dft_popup);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView txtMsg = (TextView) findViewById(R.id.txtMsg);
        TextView txtOK = (TextView) findViewById(R.id.txtOK);
        TextView txtCancel = (TextView) findViewById(R.id.txtCancel);

        txtMsg.setText(msg);

        if (type == 1) {
            txtOK.setVisibility(View.VISIBLE);
            txtCancel.setVisibility(View.GONE);
        } else {
            txtOK.setVisibility(View.VISIBLE);
            txtCancel.setVisibility(View.VISIBLE);
            if (type == 3) {
                txtOK.setText(mContext.getString(R.string.text_thulai));
                txtCancel.setText(mContext.getString(R.string.text_thoat));
            }
        }

        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.userOK();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.userCancel();
            }
        });

    }

}
