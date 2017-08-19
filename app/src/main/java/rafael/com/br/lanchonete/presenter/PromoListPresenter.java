package rafael.com.br.lanchonete.presenter;

import rafael.com.br.lanchonete.model.Lunch;
import rafael.com.br.lanchonete.view.BaseView;
import rafael.com.br.lanchonete.view.LunchListView;
import rafael.com.br.lanchonete.view.PromoListView;

/**
 * Created by rafaelfreitas on 8/19/17.
 */

public interface PromoListPresenter extends BasePresenter<PromoListView> {

    public void getListOfPromo();

}
