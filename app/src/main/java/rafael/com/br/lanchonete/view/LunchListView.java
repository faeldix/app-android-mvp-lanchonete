package rafael.com.br.lanchonete.view;

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
    void onClickOnLunchItem(Lunch lunch);

    void onDefaultLunchSelected(Lunch lunch);
    void onCustomLunchSelected(Lunch lunch);

}
