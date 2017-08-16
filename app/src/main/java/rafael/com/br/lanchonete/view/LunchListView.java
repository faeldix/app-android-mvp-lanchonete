package rafael.com.br.lanchonete.view;

import android.content.Context;

import rafael.com.br.lanchonete.adapter.LunchListAdapter;
import rafael.com.br.lanchonete.model.Lunch;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public interface LunchListView {

    void onShowLoading();
    void onDismissLoading();
    void onShowErrorMessage(String message);

    void showListOfLunch(LunchListAdapter adapter);
    void showOptionsOfLunch(Lunch lunch);

    void goToCustomize(Lunch lunch);
    void goToOrderFinish(Lunch lunch);

    Context getContext();

}
