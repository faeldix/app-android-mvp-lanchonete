package rafael.com.br.lanchonete.view;

import android.content.Context;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public interface BaseView {

    void onShowLoading();
    void onDismissLoading();

    Context getContext();

}
