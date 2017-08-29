package rafael.com.br.lanchonete.presenter;

import rafael.com.br.lanchonete.view.MainView;

/**
 * Created by rafael-iteris on 28/08/17.
 */

public interface MainPresenter extends BasePresenter<MainView> {

    public void showListOfLunchs();
    public void showListOfOrders();
    public void showListOfPromos();

}
