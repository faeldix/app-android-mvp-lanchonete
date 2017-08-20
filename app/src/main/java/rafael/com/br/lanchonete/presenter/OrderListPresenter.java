package rafael.com.br.lanchonete.presenter;

import rafael.com.br.lanchonete.view.OrderListView;

/**
 * Created by rafaelfreitas on 8/20/17.
 */

public interface OrderListPresenter extends BasePresenter<OrderListView> {

    public void getListOfOrders();

}
