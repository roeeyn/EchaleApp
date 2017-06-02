package bit01.com.mx.echale.utils;

import android.text.TextUtils;

/**
 * Created by roeeyn on 22/05/17.
 */

public class Constants {

    public static final String SKU_COINS = "coins";
    public static final String TAG_USER = "user";
    public static final int RC_SIGN_IN = 9001; //Request code de la conexión con google.
    public static final String LOG_TAG = "myLog"; // Tag para logs de error (Android monitor)
    public static final String TAG_LOCAL = "LOCAL";
    public static final String TAG_RESULTADOS = "Resultados";
    public static final String TAG_AWAY = "AWAY";
    public static final String TAG_LOCAL_IMAGE = "LOCALIMAGE";
    public static final String TAG_AWAY_IMAGE = "AWAYIMAGE";
    public static final String TAG_DATE = "TAG_DATE";

    public static final double TAG_PORCENTAJE_GANANCIA = 0.01;

    public static final int REQUEST_CODE_UPLOAD_PICTURE = 101;
    public static final int REQUEST_CODE_TAKE_PICTURE = 102;
    public static final int REQUEST_PERMISSION_CAMERA = 300;
    public static final int RC_REQUEST_STORE = 10001;

    public static final String USER_IMAGES_FOLDER = "gs://echale-ded0d.appspot.com/user_images/";

    public static final String TAG_PARTIDO_ID = "PArtidoid";
    public static final String TAG_SHARED_PREFERENCES = "Mis preferencias";
    public static final String TAG_GUIA_APUESTA = "guia_apuesta";
    public static final String TAG_GUIA_PARTIDOS = "guia_partidos";

    public static final String TAG_BASED64_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiVLSjIR0uKvihnZOXsS1zSBbRMZM0dbIgHv6IwoX1iM73pv9zVQse8aukJT9BSwEvNXmGXFgeEYCgPr8HWlH4LzMXXunQZBlUNhzlR/gI11/LutYmxYc4fyeD3V1mDCzGV5zSstoSpb3rspYSQ42UEF9fpoozjFGNWK/PB5jr4EdOPPv40LrpK01fJBH/O3tvpA8LPf8cn0lLuXjrjshQeN47iLHADy8r7k3uslDMpOJxUHsNwBsCvHmcYP+Euls2/4X0b2HnBFbN2Y0rdlr52OFGC3Sp4rsIzp4MdwTsX2NJxW8JXwmo/BWnBy4L/zQzVUoJ8n7UH6f5igmcJWC8QIDAQAB";



    public static final String GOOGLE_CHOOSER = "1";

    public static boolean validateEmail(CharSequence target){
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
