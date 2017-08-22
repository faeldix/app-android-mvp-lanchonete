package rafael.com.br.lanchonete.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by rafael-iteris on 14/08/2017.
 */

public class NetworkUtils {

    private static Context context;

    public static void init(Context ctx){
        context = ctx;
    }

    public static boolean isConnected(){
        if(context == null)
            throw new IllegalStateException("É necessário inicializar o o utilitário de rede: " + NetworkUtils.class.getCanonicalName());

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}
