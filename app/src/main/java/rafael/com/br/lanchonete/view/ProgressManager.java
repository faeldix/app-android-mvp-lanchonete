package rafael.com.br.lanchonete.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public class ProgressManager {

    private Activity activity;
    private ProgressDialog dialog;

    public ProgressManager(Activity context) {
        this.activity = (Activity) context;
    }

    public synchronized void show() {
        dialog = new ProgressDialog(activity);
        dialog.setTitle("Atenção");
        dialog.setMessage("Aguarde um instance...");

        if(!activity.isFinishing())
            dialog.show();
    }

    public synchronized void dismiss() {
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

}
