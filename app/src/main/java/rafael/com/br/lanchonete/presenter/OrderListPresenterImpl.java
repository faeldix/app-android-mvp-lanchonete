package rafael.com.br.lanchonete.presenter;

import java.util.List;

import rafael.com.br.lanchonete.model.Order;
import rafael.com.br.lanchonete.service.OrderService;
import rafael.com.br.lanchonete.view.OrderListView;

/**
 * Created by rafaelfreitas on 8/20/17.
 */

public class OrderListPresenterImpl implements OrderListPresenter {

    private OrderListView view;
    private OrderService service;

    public OrderListPresenterImpl(OrderService service) {
        this.service = service;
    }

    @Override
    public void getListOfOrders() {
        service.getOrders(getOrdersCallback());
    }

    public OrderService.GetOrdersCallback getOrdersCallback(){
        return new OrderService.GetOrdersCallback() {

            @Override
            public void onSuccess(List<Order> orders) {
                view.showListOfOrder(orders);
            }

            @Override
            public void onError(Throwable err) {
                view.onShowErrorMessage("Não foi possivel buscar a lista de pedidos");
            }

            @Override
            public void onStart() {
                view.onShowLoading();
            }

            @Override
            public void onEnd() {
                view.onDismissLoading();
            }
        };
    }

    @Override
    public OrderListView getView() {
        return view;
    }

    @Override
    public void setView(OrderListView view) {
        this.view = view;
    }

}
