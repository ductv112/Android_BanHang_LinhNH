package app.dft.appbanhang.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import app.dft.appbanhang.R;

/**
 * Created by DucTV on 12/6/2017.
 */
public class DFTPopupInput extends Dialog {
    Context mContext;
    String hint = "";
    DFTPopupInputListener listener;
    static EditText editInput;

    public DFTPopupInput(Context context, String hint, DFTPopupInputListener listener) {
        super(context);
        this.mContext = context;
        this.hint = hint;
        this.listener = listener;
    }

    public static interface DFTPopupInputListener {
        public void userOK(String st);

        public void userCancel();
    }

    public static void SetValue(String st){
        editInput.setText(st);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_dft_popup_input);
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView imgClose = (ImageView) findViewById(R.id.imgClose);
        TextView txtOK = (TextView) findViewById(R.id.txtOK);
        editInput = (EditText) findViewById(R.id.editInput);
        editInput.setHint(hint);


        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st = null;
                if (!editInput.getText().toString().trim().equals("")) {
                    st = editInput.getText().toString().trim();
                }
                listener.userOK(st);
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.userCancel();
            }
        });

    }

}
