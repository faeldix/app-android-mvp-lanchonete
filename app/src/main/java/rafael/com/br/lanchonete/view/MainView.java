package rafael.com.br.lanchonete.view;

import android.content.Context;

/**
 * Created by rafaelfreitas on 8/20/17.
 */

public interface MainView extends BaseView {

    void onClickListOfLunchsOption();
    void onClickListOfOrdersOption();
    void onClickListOfPromosOption();

    void showListOfLunchsFragment();
    void showListOfOrdersFragment();
    void showListOfPromosFragment();

}
