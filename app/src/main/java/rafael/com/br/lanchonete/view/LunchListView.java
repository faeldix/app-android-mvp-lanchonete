package rafael.com.br.lanchonete.view;

import android.content.Context;

import java.util.List;

import rafael.com.br.lanchonete.adapter.LunchListAdapter;
import rafael.com.br.lanchonete.model.Lunch;

/**
 * Created by rafael-iteris on 15/08/17.
 */

public interface LunchListView extends BaseView {

    void onShowErrorMessage(String message);

    void showListOfLunch(List<Lunch> list);
    void showOptionsOfLunch(Lunch lunch);

    void goToCustomize(Lunch lunch);
    void goToOrderFinish(Lunch lunch);

}
