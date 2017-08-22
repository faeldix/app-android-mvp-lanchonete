package rafael.com.br.lanchonete.presenter;

import java.util.List;

import rafael.com.br.lanchonete.model.Order;
import rafael.com.br.lanchonete.service.BaseRequestCallback;
import rafael.com.br.lanchonete.service.OrderService;
import rafael.com.br.lanchonete.view.OrderListView;

/**
 * Created by rafaelfreitas on 8/20/17.
 */

public class OrderListPresenterImpl implements OrderListPresenter {

    private OrderListView view;
    private OrderService service;

    public OrderListPresenterImpl() {}

    public OrderListPresenterImpl(OrderService service) {
        this.service = service;
    }

    @Override
    public void getListOfOrders() {
        if(view == null)
            throw new IllegalStateException("A view deve ser setada.");

        service.getOrders(getOrdersCallback());
    }

    public BaseRequestCallback<List<Order>,RuntimeException> getOrdersCallback() {
        return new BaseRequestCallback<List<Order>, RuntimeException>() {

            @Override
            public void onSuccess(List<Order> result) {
                view.showListOfOrder(result);
            }

            @Override
            public void onErro(RuntimeException err) {
                err.printStackTrace();

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
