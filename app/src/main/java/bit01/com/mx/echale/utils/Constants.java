package bit01.com.mx.echale.utils;

import android.text.TextUtils;

/**
 * Created by roeeyn on 22/05/17.
 */

public class Constants {


    public static final String TAG_USER = "user";
    public static final int RC_SIGN_IN = 9001; //Request code de la conexión con google.
    public static final String LOG_TAG = "myLog"; // Tag para logs de error (Android monitor)
    public static final String TAG_LOCAL = "LOCAL";
    public static final String TAG_AWAY = "AWAY";
    public static final String TAG_LOCAL_IMAGE = "LOCALIMAGE";
    public static final String TAG_AWAY_IMAGE = "AWAYIMAGE";
    public static final String TAG_PARTIDO_ID = "PArtidoid";


    public static final String GOOGLE_CHOOSER = "1";

    public static boolean validateEmail(CharSequence target){
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
