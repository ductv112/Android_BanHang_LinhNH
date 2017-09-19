package app.dft.appbanhang.util;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import app.dft.appbanhang.R;

/**
 * Created by Inuyasha on 29/03/2016.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public RegistrationIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String token = null;
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (token != null) {
            SharedPreferences preferences = StaticVariable.getPref(getBaseContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(StaticVariable.androidToken, token);
            editor.commit();
        }

    }
}
